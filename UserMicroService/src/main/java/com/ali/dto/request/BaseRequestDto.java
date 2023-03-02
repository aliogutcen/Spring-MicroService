package com.ali.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseRequestDto {

    @NotNull
    @Size(min = 8)
    String token;
}
