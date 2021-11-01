package com.mthree.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class InventoryItem {

    private String itemName;
    private BigDecimal itemCost;
    private int numInInventory;

    public InventoryItem(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public int getNumInInventory() {
        return numInInventory;
    }

    public void setNumInInventory(int numInInventory) {
        this.numInInventory = numInInventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItem that = (InventoryItem) o;
        return getNumInInventory() == that.getNumInInventory() && getItemName().equals(that.getItemName()) && getItemCost().equals(that.getItemCost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemName(), getItemCost(), getNumInInventory());
    }

    @Override
    public String toString() {
        return "Item Name = '" + itemName + '\'' +
                ", Cost = " + itemCost;
    }
}
