package net.leloubil.clonecordserver.validation;

import net.leloubil.clonecordserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameConstraintValidator implements ConstraintValidator<UniqueUsername, String> {

   @Autowired
   private UserService userService;



   public void initialize(UniqueUsername constraint) {

   }

   public boolean isValid(String username, ConstraintValidatorContext context) {
      return userService.getUserByName(username).isEmpty();
   }
}
