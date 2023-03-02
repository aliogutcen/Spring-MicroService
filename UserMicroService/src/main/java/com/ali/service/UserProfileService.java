package com.ali.service;

import com.ali.dto.request.BaseRequestDto;
import com.ali.dto.request.UserSaverRequestDto;
import com.ali.exception.ErrorType;
import com.ali.exception.UserMicroServiceException;
import com.ali.mapper.IUserMapper;
import com.ali.repository.IUserProfileRepository;
import com.ali.repository.entity.UserProfile;
import com.ali.utility.ServiceManager;
import com.ali.utility.TokenManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService  extends ServiceManager<UserProfile,Long> {
    private final TokenManager tokenManager;
    private final IUserProfileRepository userProfileRepository;

    public UserProfileService(IUserProfileRepository userProfileRepository,TokenManager tokenManager){
        super(userProfileRepository);
        this.userProfileRepository=userProfileRepository;
        this.tokenManager=tokenManager;
    }

    public Boolean saveDto(UserSaverRequestDto dto) {
        save(IUserMapper.INSTANCE.toUserProfile(dto));
        return true;
    }

    public List<UserProfile> findAll(String token){
    Long authid = tokenManager.getDecodeToken(token);
    Optional<UserProfile> userProfile= userProfileRepository.findOptionalByAuthid(authid);
            if(userProfile.isPresent()){
                return findAll();
            }else throw new UserMicroServiceException(ErrorType.INVALID_TOKEN);
    }
}
