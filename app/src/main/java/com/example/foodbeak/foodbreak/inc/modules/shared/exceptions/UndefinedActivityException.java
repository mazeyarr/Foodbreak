package com.example.foodbeak.foodbreak.inc.modules.shared.exceptions;

import com.example.foodbeak.foodbreak.inc.types.ErrorCodeType;

public class UndefinedActivityException extends Exception {
    private static final String DEFAULT_MESSAGE = "Activity does not exist in this module";

    private ErrorCodeType errorCodeType;

    public UndefinedActivityException() {
        super(DEFAULT_MESSAGE);

        this.errorCodeType = ErrorCodeType.ERROR;
    }

    public UndefinedActivityException(ErrorCodeType errorCodeType) {
        super(DEFAULT_MESSAGE);

        this.errorCodeType = errorCodeType;
    }

    public UndefinedActivityException(String message, ErrorCodeType errorCodeType) {
        super(message);

        this.errorCodeType = errorCodeType;
    }

    public ErrorCodeType getErrorCodeType() {
        return errorCodeType;
    }
}
