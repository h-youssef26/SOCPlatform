package com.soc.backend_core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IpValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIp {
    String message() default "Invalid IPv4 address format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
