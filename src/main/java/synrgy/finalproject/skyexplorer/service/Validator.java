package synrgy.finalproject.skyexplorer.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;

@Service
public class Validator {

    private final jakarta.validation.Validator validator;

    @Autowired
    public Validator(jakarta.validation.Validator validator) {
        this.validator = validator;
    }

    public void validate(Object o, Class<?>... groups) {
        if (groups.length == 0) {
            int length = groups.length;
            groups = Arrays.copyOf(groups, length + 1);
            groups[length] = Default.class;
        }
        Set<ConstraintViolation<Object>> constraintViolation = validator.validate(o,groups);
        if(!constraintViolation.isEmpty()) {
            throw new ConstraintViolationException(constraintViolation);
        }
    }
}
