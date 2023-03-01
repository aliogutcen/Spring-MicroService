package com.ali.controller;

import com.ali.dto.request.RegisterRequestDto;
import com.ali.exception.AuthMicroServiceException;
import com.ali.exception.ErrorType;
import com.ali.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

import static com.ali.constant.RestEndPoints.*;

@RestController
@RequestMapping(API + VERSION + AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping(REGISTER)
    public ResponseEntity<Boolean> register(@RequestBody @Valid RegisterRequestDto dto){
        if(dto.getPassword().equals(dto.getRepassword()))
           throw new AuthMicroServiceException(ErrorType.AUTH_PASSWORD_ERROR);
            authService.register(dto);
            return ResponseEntity.ok(true);
    }

}
