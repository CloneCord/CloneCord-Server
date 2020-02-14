package net.leloubil.clonecordserver.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailConstraintValidator.class)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "E-Mail already taken";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
