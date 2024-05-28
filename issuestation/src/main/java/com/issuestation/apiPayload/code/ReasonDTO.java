package com.issuestation.apiPayload.code;

import lombok.Getter;

@Getter
public class ReasonDTO {

    private String code;
    private String message;
    private Boolean isSuccess;

    public ReasonDTO(String code, String message, Boolean isSuccess) {
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
    }

}
