package com.techupdating.techupdating.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class GmailValidator implements ConstraintValidator<Gmail, String>{

    private static final String GMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
    private static final Pattern PATTERN = Pattern.compile(GMAIL_REGEX);

    @Override
    public void initialize(Gmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // email empty return false
        if (email == null) return false;

        // email right format return true else false
        return PATTERN.matcher(email).matches();
    }
}
