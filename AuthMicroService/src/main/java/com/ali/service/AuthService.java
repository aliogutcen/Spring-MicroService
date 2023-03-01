package com.ali.service;

import com.ali.dto.request.RegisterRequestDto;
import com.ali.exception.AuthMicroServiceException;
import com.ali.exception.ErrorType;
import com.ali.mapper.IAuthMapper;
import com.ali.repository.entity.Auth;
import com.ali.repository.entity.IAuthRepository;
import com.ali.utility.ServiceManager;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;

@Service
public class AuthService extends ServiceManager<Auth,Long>{

    private final IAuthRepository authRepository;

    public AuthService(IAuthRepository authRepository){
        super(authRepository);
        this.authRepository=authRepository;
    }


    public Boolean register(RegisterRequestDto dto){
        if(authRepository.isUser(dto.getUsername()))
            throw  new AuthMicroServiceException(ErrorType.AUTH_USERNAME_ERROR);
        save(IAuthMapper.INSTANCE.fromRegisterDto(dto));
        return true;
    }

}
