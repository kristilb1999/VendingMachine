package com.mthree.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Change {

    int quarters;
    int dimes;
    int nickels;
    int pennies;

    public Change() {
        this.quarters = 0;
        this.dimes = 0;
        this.nickels = 0;
        this.pennies = 0;
    }

    public int getQuarters() {
        return quarters;
    }

    public int getDimes() {
        return dimes;
    }

    public int getNickels() {
        return nickels;
    }

    public int getPennies() {
        return pennies;
    }

    public String returnMoney(BigDecimal coinsPaid) {
        double totalChange = coinsPaid.doubleValue();
        int pennies = (int) (totalChange / 100);

        if(totalChange / 100 > 0) {
            pennies += totalChange * 100;
        }

        while(pennies >= CoinValue.QUARTER.getValue()) {
            this.quarters++;
            pennies -= 25;
        }

        while(pennies >= CoinValue.DIME.getValue()) {
            this.dimes++;
            pennies -= 10;
        }

        while(pennies >= CoinValue.NICKEL.getValue()) {
            this.nickels++;
            pennies -= 5;
        }

        while(pennies >= CoinValue.PENNY.getValue()) {
            this.pennies++;
            pennies--;
        }

        return this.quarters + " quarters, " + this.dimes + " dimes, " + this.nickels + " nickels, and " + this.pennies + " pennies.";
    }

    public String getChange(BigDecimal coinsPaid, InventoryItem itemToPurchase) {

        BigDecimal cost = itemToPurchase.getItemCost();

        BigDecimal extraChange = coinsPaid.subtract(cost, new MathContext(2, RoundingMode.HALF_UP));

        //find the number of pennies paid by the user
        double totalChange = extraChange.doubleValue();

        int pennies = (int) (totalChange / 100);

        if(totalChange / 100 > 0) {
            pennies += totalChange * 100;
        }


        while(pennies >= 25) {
            this.quarters++;
            pennies -= 25;
        }

        while(pennies >= 10) {
            this.dimes++;
            pennies -= 10;
        }

        while(pennies >= 5) {
            this.nickels++;
            pennies -= 5;
        }

        while(pennies > 0) {
            this.pennies++;
            pennies--;
        }

        return this.quarters + " quarters, " + this.dimes + " dimes, " + this.nickels + " nickels, and " + this.pennies + " pennies.";
    }

}
