package com.taller.cantu.auth.service;

import com.taller.cantu.auth.dto.GlobalResponse;
import com.taller.cantu.auth.dto.UserActivationDTO;
import com.taller.cantu.auth.dto.UserLoginDTO;
import com.taller.cantu.auth.dto.UserRegisterDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {

    public GlobalResponse registerUser(UserRegisterDTO userRegisterDTO) throws Exception;

    public GlobalResponse activateUser(UserActivationDTO userActivationDTO);

    public GlobalResponse loginUser(UserLoginDTO userLoginDTO) throws Exception;
}
