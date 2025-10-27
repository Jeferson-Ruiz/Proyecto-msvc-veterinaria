package com.jeferson.msvc.workstaff.exeptions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

@Documented
@Constraint(validatedBy = {})
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "^[0-9]*$", 
    message = "El número de telefonico solo puede contener dígitos numericos sin espacio en blanco")
public @interface PhoneNumber {

    String message() default "Número de telefonico inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}



