package com.nael.catalog.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.nael.catalog.validator.AuthorNameValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = AuthorNameValidator.class)
public @interface ValidAuthorName {

    String message() default "author name invalid";

    Class<?>[] groups() default {};

    // TODO: Lanjut untuk custom validation
    Class<? extends Payload>[] payload() default {};

}
