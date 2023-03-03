package com.ali.service;

import com.ali.dto.request.LoginRequestDto;
import com.ali.dto.request.RegisterRequestDto;
import com.ali.dto.request.UserSaverRequestDto;
import com.ali.exception.AuthMicroServiceException;
import com.ali.exception.ErrorType;
import com.ali.manager.IUserProfileManager;
import com.ali.mapper.IAuthMapper;
import com.ali.repository.IAuthRepository;
import com.ali.repository.entity.Auth;
import com.ali.utility.JwtTokenManager;
import com.ali.utility.ServiceManager;
import com.ali.utility.TokenManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final IUserProfileManager userProfileManager;
    private final IAuthRepository authRepository;

    private final TokenManager tokenManager;
    private final JwtTokenManager jwtTokenManager;

    public AuthService(IAuthRepository authRepository, IUserProfileManager userProfileManager, TokenManager tokenManager, JwtTokenManager jwtTokenManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userProfileManager = userProfileManager;
        this.tokenManager = tokenManager;
        this.jwtTokenManager = jwtTokenManager;
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

    public String login(LoginRequestDto dto) {
        Optional<Auth> authOptional = authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (authOptional.isPresent()) {
            Optional<String> token = jwtTokenManager.createToken(authOptional.get().getId());
            if (token.isEmpty()) {
                throw new AuthMicroServiceException(ErrorType.TOKEN_ERROR);

            }
            return token.get();
        } else throw new AuthMicroServiceException(ErrorType.AUTH_LOGIN_ERROR);
    }
}
