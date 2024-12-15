package com.taller.cantu.auth.service.impl;

import com.taller.cantu.auth.dto.*;
import com.taller.cantu.auth.entity.EmailVerificationToken;
import com.taller.cantu.auth.entity.User;
import com.taller.cantu.auth.exception.BusinessException;
import com.taller.cantu.auth.repository.EmailVerificationTokenRepository;
import com.taller.cantu.auth.repository.UserRepository;
import com.taller.cantu.auth.repository.UserRoleRepository;
import com.taller.cantu.auth.service.EmailVerificationTokenService;
import com.taller.cantu.auth.service.JwtService;
import com.taller.cantu.auth.service.UserService;
import com.taller.cantu.auth.service.WebClientService;
import com.taller.cantu.auth.utils.RegexUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.AbstractJpaQuery;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WebClientService webClientService;

    @Autowired
    private EmailVerificationTokenService emailVerificationTokenService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public GlobalResponse registerUser(UserRegisterDTO userRegisterDTO) throws Exception {
        RegexUtils.isValidEmail(userRegisterDTO.getEmail());
        this.validateNewUserRegistration(userRegisterDTO.getEmail());

        RegexUtils.isValidPassword(userRegisterDTO.getPassword());
        log.info("User information is valid");

        //DELETE NON VERIFIED USERS WITH SAME EMAIL
        this.deleteNonVerifiedUsers(userRegisterDTO.getEmail());

        //HASHING PASSWORD
        userRegisterDTO.setPassword(hashPassword(userRegisterDTO.getPassword()));

        User user = modelMapper.map(userRegisterDTO, User.class);
        user.setRole(userRoleRepository.findById(1L).orElse(null));

        //SAVE USER TO DATABASE
        user = userRepository.save(user);

        //GENERATE ACTIVATION CODE
        String activationToken = emailVerificationTokenService.generateEmailVerificationToken(user);

        //HERE WE SHOULD SEND EMAIL TO USER TO VERIFY HIS EMAIL
        this.sendRegistrationEmail(user, activationToken);
        log.info("Email notification sent to microservice to be delivered to user");

        return new GlobalResponse("User registered successfully, awaiting for activation.", user, null);
    }

    @Override
    public GlobalResponse activateUser(UserActivationDTO userActivationDTO) {
        User user = userRepository.findById(userActivationDTO.getUser_id()).orElse(null);
        if(user == null) {
            throw new BusinessException("Token is invalid.");
        }

        EmailVerificationToken token = emailVerificationTokenRepository.findByTokenAndUser(userActivationDTO.getUser_id(), userActivationDTO.getCode()).orElse(null);
        if(token == null) {
            throw new BusinessException("Token is invalid.");
        }

        if(!validateEmailToken(token)) {
            //Delete the token because is expired.
            emailVerificationTokenRepository.delete(token);

            throw new BusinessException("The Activation Token is expired, it will be deleted, you have to generate new one.");
        }

        // All validations ok, lets activate the user hehe.
        user.setBlocked(false);
        user.setVerified(true);
        userRepository.save(user);
        return new GlobalResponse("User activated successfully.", null, null);
    }

    @Override
    public GlobalResponse loginUser(UserLoginDTO userLoginDTO) throws Exception {
        RegexUtils.isValidEmail(userLoginDTO.getEmail());

        String hashedPassword = userRepository.findPasswordByEmail(userLoginDTO.getEmail()).orElse("");

        if(hashedPassword.isEmpty()) {
            throw new BusinessException("Invalid Credentials, try again.");
        }

        if(!checkPassword(userLoginDTO.getPassword(), hashedPassword)) {
            throw new BusinessException("Invalid Credentials, try again.");
        }

        Optional<User> loggedUser = userRepository.findByEmailAndVerified(userLoginDTO.getEmail());
        if(loggedUser.isEmpty()){
            throw new BusinessException("User is not verified, please check your confirmation email.");
        }

        // Generar el token
        String token = jwtService.generateJwtToken(loggedUser.get());

        LoginSuccessDTO loginSuccessDTO = new LoginSuccessDTO("JWT", "3600", token);
        return new GlobalResponse("Login success.", loginSuccessDTO, null);
    }

    @Override
    public GlobalResponse blockUser(String email, boolean blocked) {
        RegexUtils.isValidEmail(email);

        if(userRepository.findByEmailAndVerified(email).isEmpty()) {
            throw new BusinessException("The user your are trying to block don't exists.");
        }

        UserBlockedDTO userBlocked = modelMapper.map(userRepository.blockUser(email, blocked), UserBlockedDTO.class);
        if(userBlocked == null) {
            throw new BusinessException("Error trying to block the user.");
        }
        return new GlobalResponse("User updated successfully.", userBlocked, null);
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean checkPassword(String password, String passwordHash) {
        return passwordEncoder.matches(password, passwordHash);
    }

    private void sendRegistrationEmail(User user, String activationToken) {
        Mono<String> response = webClientService.sendGetRequest("");

        // Procesar la respuesta de manera asíncrona
        response.subscribe(result -> {
            // Aquí puedes manejar el resultado de la respuesta asíncrona
            System.out.println("Resultado: " + result);
            // Enviar email, o realizar cualquier otra acción asíncrona
        }, error -> {
            // Manejar errores en la respuesta
            System.err.println("Error: " + error.getMessage());
        });
    }

    private void validateNewUserRegistration(String email) {
        User userDatabase = userRepository.findByEmail(email).orElse(null);
        if(userDatabase != null && userDatabase.isVerified()) {
            log.error("User with email {} is already registered", email);
            throw new BusinessException("User already exists.", HttpStatus.CONFLICT);
        }
    }

    private void deleteNonVerifiedUsers(String email) {
        List<User> nonVerifiedUsers = userRepository.findByEmailAndNonVerified(email);

        if(nonVerifiedUsers != null && !nonVerifiedUsers.isEmpty()) {
            log.info("Deleting non verified users with email {}", email);
            userRepository.deleteAll(nonVerifiedUsers);
        }
        entityManager.flush();
    }

    private boolean validateEmailToken(EmailVerificationToken token) {
        return token.getExpiration_datetime().isAfter(LocalDateTime.now());
    }

}
