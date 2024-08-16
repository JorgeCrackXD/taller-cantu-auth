package com.taller.cantu.auth.service.impl;

import com.taller.cantu.auth.dto.ErrorDTO;
import com.taller.cantu.auth.dto.GlobalResponse;
import com.taller.cantu.auth.dto.UserRegisterDTO;
import com.taller.cantu.auth.entity.User;
import com.taller.cantu.auth.repository.UserRepository;
import com.taller.cantu.auth.repository.UserRoleRepository;
import com.taller.cantu.auth.service.UserService;
import com.taller.cantu.auth.utils.RegexUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public GlobalResponse registerUser(UserRegisterDTO userRegisterDTO) {
        RegexUtils.isValidEmail(userRegisterDTO.getEmail());
        RegexUtils.isValidPassword(userRegisterDTO.getPassword());

        userRegisterDTO.setPassword(hashPassword(userRegisterDTO.getPassword()));

        User user = modelMapper.map(userRegisterDTO, User.class);
        user.setRole(userRoleRepository.findById(1L).orElse(null));

        //HERE WE SHOULD SEND EMAIL TO USER TO VERIFY HIS EMAIL

        user = userRepository.save(user);

        return new GlobalResponse("User registered successfully", user, null);
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean checkPassword(String password, String passwordHash) {
        return passwordEncoder.matches(password, passwordHash);
    }
}
