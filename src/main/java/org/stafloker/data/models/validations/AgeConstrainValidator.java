package org.stafloker.data.models.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.stafloker.data.models.User;

public class AgeConstrainValidator implements ConstraintValidator<AgeConstraint, Integer> {
    @Override
    public void initialize(AgeConstraint constraint) {
        // Empty, not operation
    }

    @Override
    public boolean isValid(Integer age, ConstraintValidatorContext context) {
        return age != null && age >= User.MIN_AGE && age <= User.MAX_AGE;
    }
}