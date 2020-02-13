package net.leloubil.clonecordserver.validation;

import net.leloubil.clonecordserver.services.UniqueProof;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class UniqueUsernameConstraintValidator implements ConstraintValidator<UniqueUsername, String> {

   @Autowired
   private UserService userService;



   public void initialize(UniqueUsername constraint) {

   }

   public boolean isValid(String username, ConstraintValidatorContext context) {
      return userService.getUserByName(username).isEmpty();
   }
}
