package net.leloubil.clonecordserver.validation;

import com.google.common.base.Joiner;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword,String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
         new CharacterRule(EnglishCharacterData.UpperCase,1),
         new CharacterRule(EnglishCharacterData.Digit,1),
         new CharacterRule(EnglishCharacterData.Special,1),
         new IllegalSequenceRule(EnglishSequenceData.Alphabetical,3,false),
         new IllegalSequenceRule(EnglishSequenceData.Numerical,3,false),
         new IllegalSequenceRule(EnglishSequenceData.USQwerty,3,false)
        )
        );
        RuleResult result = validator.validate(new PasswordData(password));
        if(result.isValid()){
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(validator.getMessages(result).get(0)).addConstraintViolation();
        return false;
    }
}
