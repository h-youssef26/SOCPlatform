// validation/SoarCommandValidator.java
package com.soc.backend_core.validation;

import com.soc.backend_core.dto.SoarCommandRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SoarCommandValidator
        implements ConstraintValidator<ValidSoarCommand, SoarCommandRequest> {

    @Override
    public boolean isValid(SoarCommandRequest req,
                           ConstraintValidatorContext ctx) {

        // Context tells us nothing — we rely on the endpoint being called correctly.
        // We validate whichever optional field is present.

        ctx.disableDefaultConstraintViolation();
        boolean valid = true;

        // If targetProcess is present it must not be blank
        if (req.getTargetProcess() != null && req.getTargetProcess().isBlank()) {
            ctx.buildConstraintViolationWithTemplate("targetProcess must not be blank")
                    .addPropertyNode("targetProcess")
                    .addConstraintViolation();
            valid = false;
        }

        // If targetIp is present it must not be blank
        if (req.getTargetIp() != null && req.getTargetIp().isBlank()) {
            ctx.buildConstraintViolationWithTemplate("targetIp must not be blank")
                    .addPropertyNode("targetIp")
                    .addConstraintViolation();
            valid = false;
        }

        // At least one of the two optional targets must be supplied
        boolean hasProcess = req.getTargetProcess() != null
                && !req.getTargetProcess().isBlank();
        boolean hasIp      = req.getTargetIp() != null
                && !req.getTargetIp().isBlank();

        if (!hasProcess && !hasIp) {
            ctx.buildConstraintViolationWithTemplate(
                            "Either targetProcess or targetIp must be provided")
                    .addPropertyNode("targetProcess")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
