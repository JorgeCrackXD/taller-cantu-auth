package com.taller.cantu.auth.service.impl;

import com.taller.cantu.auth.dto.ErrorDTO;
import com.taller.cantu.auth.dto.GlobalResponse;
import com.taller.cantu.auth.dto.UserRegisterDTO;
import com.taller.cantu.auth.entity.User;
import com.taller.cantu.auth.exception.BusinessException;
import com.taller.cantu.auth.repository.UserRepository;
import com.taller.cantu.auth.repository.UserRoleRepository;
import com.taller.cantu.auth.service.UserService;
import com.taller.cantu.auth.service.WebClientService;
import com.taller.cantu.auth.utils.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

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
    private ModelMapper modelMapper;

    @Autowired
    private WebClientService webClientService;

    @Override
    public GlobalResponse registerUser(UserRegisterDTO userRegisterDTO) {
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

        //HERE WE SHOULD SEND EMAIL TO USER TO VERIFY HIS EMAIL
        this.sendRegistrationEmail(user.getEmail());
        log.info("Email notification sent to microservice to be delivered to user");

        user = userRepository.save(user);
        return new GlobalResponse("User registered successfully, awaiting for activation.", user, null);
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean checkPassword(String password, String passwordHash) {
        return passwordEncoder.matches(password, passwordHash);
    }

    private void sendRegistrationEmail(String email) {
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
    }
}
