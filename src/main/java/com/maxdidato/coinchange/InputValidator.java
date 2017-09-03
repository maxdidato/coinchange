package com.maxdidato.coinchange;

import java.security.InvalidParameterException;


public class InputValidator {
    static void validate(int pence) {
        if ((pence < 0) || (pence > 100000)) {
            throw new InvalidParameterException(
                    String.format("Invalid parameter '%s'. The input must be a in 0..100000 range", pence));
        }
    }
}
