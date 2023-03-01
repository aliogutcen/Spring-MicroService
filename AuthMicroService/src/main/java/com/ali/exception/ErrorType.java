package com.ali.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {


    BAD_REQUEST_ERROR(1201,"Geçersiz Parametre Girişi Yaptınız",BAD_REQUEST),

    AUTH_PASSWORD_ERROR(1301,"Şifreler uyuşmuyor",BAD_REQUEST),

    AUTH_USERNAME_ERROR(1302,"Kullanıcı adı daha önce alınmış",BAD_REQUEST),

    INTERNAL_ERROR(3000,"Sunucuda beklenmeyen hata", INTERNAL_SERVER_ERROR),

    KULLANICI_BULUNAMADI(2301,"Aradığınız id'ye ait kullanıcı bulunamamıştır",INTERNAL_SERVER_ERROR);
    private int code;

    private String message;

    private HttpStatus httpStatus;

}
