package org.stafloker.data.models.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.stafloker.data.models.User;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeConstrainValidator.class)
public @interface AgeConstraint {
    String message() default "The age must be in the range [" + User.MIN_AGE + "," + User.MAX_AGE + "] years";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}