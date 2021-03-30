package com.win.itemsharingplatform.validation;

import com.win.itemsharingplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueEmail email) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        System.out.println(email);
        System.out.println(userRepository.findByEmail(email).isPresent());
        return !userRepository.findByEmail(email)
                .isPresent();
    }
}