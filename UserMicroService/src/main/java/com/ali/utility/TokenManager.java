package com.ali.utility;

import com.ali.exception.ErrorType;
import com.ali.exception.UserMicroServiceException;
import org.springframework.stereotype.Component;

@Component
public class TokenManager {

    public String createToken(Long id){

        String token = "tkn..:"+id;
        return token;
    }

    public Long getDecodeToken(String token){
        Long id=0L;
        try{
            id=Long.parseLong(token.substring(token.indexOf(":")+1));
        }catch (Exception e){
            throw  new UserMicroServiceException(ErrorType.INVALID_TOKEN);
        }
        return id;
    }
}
