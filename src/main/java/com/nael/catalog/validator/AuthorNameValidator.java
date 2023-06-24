package com.nael.catalog.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.nael.catalog.validator.annotation.ValidAuthorName;

@Component
public class AuthorNameValidator implements ConstraintValidator<ValidAuthorName, String> {

    @Override
    public boolean isValid(String authorName, ConstraintValidatorContext arg1) {
        return !authorName.equalsIgnoreCase("Nael");
    }

}
