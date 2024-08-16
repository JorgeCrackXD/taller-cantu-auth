package com.taller.cantu.auth.service;

import com.taller.cantu.auth.dto.GlobalResponse;
import com.taller.cantu.auth.dto.UserRegisterDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public GlobalResponse registerUser(UserRegisterDTO userRegisterDTO);
}
