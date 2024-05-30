package com.issuestation.Dto.UserDto.Token;

import com.issuestation.Entity.enums.Status;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {
    Long id;
    String loginId;
    String nickname;
}
