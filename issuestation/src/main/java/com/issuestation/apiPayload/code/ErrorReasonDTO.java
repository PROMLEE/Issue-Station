package com.issuestation.apiPayload.code;

import com.issuestation.apiPayload.code.status.SuccessStatus;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorReasonDTO {
    private String code;
    private String message;
    private Boolean isSuccess;
    private HttpStatus httpStatus;
}
