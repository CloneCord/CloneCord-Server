package net.leloubil.clonecordserver.validation;

import net.leloubil.clonecordserver.services.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailConstraintValidator implements ConstraintValidator<UniqueEmail, String> {

   @Autowired
   private LoginUserService loginUserService;

   public void initialize(UniqueEmail constraint) {
   }

   public boolean isValid(String email, ConstraintValidatorContext context) {
      return loginUserService.getLoginUserByEmail(email).isEmpty();
   }
}
