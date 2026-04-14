// validation/ValidSoarCommand.java
package com.soc.backend_core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SoarCommandValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSoarCommand {
    String message() default "Invalid SOAR command fields";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
