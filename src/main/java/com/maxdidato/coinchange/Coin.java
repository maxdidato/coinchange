package com.maxdidato.coinchange;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Coin {
    private final int denomination;

    public Coin(int denomination) {
       this.denomination = denomination;
    }
}
