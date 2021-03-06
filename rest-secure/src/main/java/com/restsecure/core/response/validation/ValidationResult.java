package com.restsecure.core.response.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationResult {
    private ValidationStatus status;
    private String errorText;

    public ValidationResult(ValidationStatus status) {
        this.status = status;
        errorText = "";
    }

    public boolean isFail() {
        return this.status.equals(ValidationStatus.FAIL);
    }

    public boolean isSuccess() {
        return this.status.equals(ValidationStatus.SUCCESS);
    }
}
