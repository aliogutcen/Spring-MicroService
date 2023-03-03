package com.ali.service;

import com.ali.dto.request.UserSaverRequestDto;
import com.ali.exception.ErrorType;
import com.ali.exception.UserMicroServiceException;
import com.ali.mapper.IUserMapper;
import com.ali.repository.IUserProfileRepository;
import com.ali.repository.entity.UserProfile;
import com.ali.utility.JwtTokenManager;
import com.ali.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {
    private final JwtTokenManager jwtTokenManager;
    private final IUserProfileRepository userProfileRepository;
    public UserProfileService(IUserProfileRepository userProfileRepository, JwtTokenManager jwtTokenManager) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenManager = jwtTokenManager;
    }
    public Boolean saveDto(UserSaverRequestDto dto){
        UserProfile userProfile= IUserMapper.INSTANCE.toUserProfile(dto);
        save(userProfile);
        return true;
    }

    public List<UserProfile> findAll(String token) {
        Optional<Long> authid = jwtTokenManager.decodeToken(token);
        if (authid.isEmpty()) throw new UserMicroServiceException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByAuthid(authid.get());
        if (userProfile.isPresent()) {
            return findAll();
        } else throw new UserMicroServiceException(ErrorType.INVALID_TOKEN);
    }
}
