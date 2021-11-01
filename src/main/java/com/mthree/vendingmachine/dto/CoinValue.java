package com.mthree.vendingmachine.dto;

public enum CoinValue {
    PENNY(1), NICKEL(5), DIME(10), QUARTER(25);
    private int value;

    private CoinValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
