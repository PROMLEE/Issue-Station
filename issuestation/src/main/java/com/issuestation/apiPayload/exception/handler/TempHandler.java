package com.issuestation.apiPayload.exception.handler;

import com.issuestation.apiPayload.code.BaseErrorCode;
import com.issuestation.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}