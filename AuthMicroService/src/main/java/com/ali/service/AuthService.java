package com.ali.service;

import com.ali.dto.request.LoginRequestDto;
import com.ali.dto.request.RegisterRequestDto;
import com.ali.dto.request.UserSaverRequestDto;
import com.ali.exception.AuthMicroServiceException;
import com.ali.exception.ErrorType;
import com.ali.manager.IUserProfileManager;
import com.ali.mapper.IAuthMapper;
import com.ali.repository.entity.Auth;
import com.ali.repository.entity.IAuthRepository;
import com.ali.utility.ServiceManager;
import com.ali.utility.TokenManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final IUserProfileManager userProfileManager;
    private final IAuthRepository authRepository;

    private TokenManager tokenManager;
    public AuthService(IAuthRepository authRepository, IUserProfileManager userProfileManager,TokenManager tokenManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userProfileManager = userProfileManager;
        this.tokenManager=tokenManager;
    }


    public Boolean register(RegisterRequestDto dto) {
        if (authRepository.isUser(dto.getUsername()))
            throw new AuthMicroServiceException(ErrorType.AUTH_USERNAME_ERROR);
        Auth auth = save(IAuthMapper.INSTANCE.fromRegisterDto(dto));
        userProfileManager.save(UserSaverRequestDto.builder()
                        .authid(auth.getId())
                        .email(auth.getEmail())
                        .username(auth.getUsername())
                .build());
        return true;
    }

    public String login(LoginRequestDto dto){
        Optional<Auth> authOptional = authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if(authOptional.isPresent()){
            return tokenManager.createToken(authOptional.get().getId());
        }else throw new AuthMicroServiceException(ErrorType.AUTH_LOGIN_ERROR);
    }
}
