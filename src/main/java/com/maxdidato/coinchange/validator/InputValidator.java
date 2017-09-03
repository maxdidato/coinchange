package com.maxdidato.coinchange.validator;

import java.security.InvalidParameterException;


public class InputValidator {
    public static void validate(int pence) {
        if ((pence < 0) || (pence > 100000)) {
            throw new InvalidParameterException(
                    String.format("Invalid parameter '%s'. The input must be a in 0..100000 range", pence));
        }
    }
}
