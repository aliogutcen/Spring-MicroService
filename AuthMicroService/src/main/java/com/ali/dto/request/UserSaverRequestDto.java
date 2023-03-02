package com.ali.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSaverRequestDto {

    String username;

    String email;

    Long authid;
}
