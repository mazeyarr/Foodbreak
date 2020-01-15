package com.example.foodbeak.foodbreak.inc.modules.shared.exceptions;

import com.example.foodbeak.foodbreak.inc.types.ErrorCodeType;

public class ModulesNotInitializedException extends Exception {

    private ErrorCodeType errorCodeType;

    public ModulesNotInitializedException() {
        super("Module has not been initialized...");

        this.errorCodeType = ErrorCodeType.ERROR;
    }

    public ModulesNotInitializedException(ErrorCodeType errorCodeType) {
        super("Module has not been initialized...");

        this.errorCodeType = errorCodeType;
    }

    public ModulesNotInitializedException(String message, ErrorCodeType errorCodeType) {
        super(message);

        this.errorCodeType = errorCodeType;
    }

    public ErrorCodeType getErrorCodeType() {
        return errorCodeType;
    }
}
