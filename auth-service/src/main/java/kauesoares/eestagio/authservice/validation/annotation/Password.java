package kauesoares.eestagio.authservice.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kauesoares.eestagio.authservice.validation.validator.PasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "{PASSWORD_INVALID}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
