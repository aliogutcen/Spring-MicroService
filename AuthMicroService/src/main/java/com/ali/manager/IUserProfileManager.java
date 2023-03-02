package com.ali.manager;

import com.ali.dto.request.UserSaverRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.ali.constant.RestEndPoints.SAVE;

/**
 * name: =>uniq olmalıdır . kopyala yapıştır yaparken dikkat etmelisin !
 * url: bağlantı yapacağımız service
 */

@FeignClient(
        name = "user-profile-service-feign",
        url = "${bu-benim-tanimim.userprofile-url}",
        decode404 = true
)
public interface IUserProfileManager {
    @PostMapping(SAVE)
    ResponseEntity<Boolean> save(@RequestBody UserSaverRequestDto dto);
}
