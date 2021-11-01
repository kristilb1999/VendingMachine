package com.mthree.vendingmachine.ui;

import com.mthree.vendingmachine.dto.InventoryItem;

import java.util.List;

public class VendingMachineView {

    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void printMenu(List<InventoryItem> inventory) {
        io.print("\nMain Menu");
        for(InventoryItem item : inventory) {
            io.print("");
            io.print(item.toString());
        }
        io.print("\nType 'LEAVE' to leave Vending Machine.\n");
;
    }

    public String getLeavingOrMoneyAmount() {
        return io.readString("Please enter money or leave the Vending Machine: ");
    }

    public String getSelection(){
        return io.readString("What would you like to buy: ");
    }

    public void displayBuyCookiesBanner() {
        io.print("\n========== Buy Cookies ==========\n");
    }

    public void displayBuyCrackersBanner() {
        io.print("\n========== Buy Cookies ==========\n");
    }

    public void displayBuyCandyBanner() {
        io.print("\n========== Buy Candy ==========\n");
    }

    public void displayBuyGumBanner() {
        io.print("\n========== Buy Gum ==========\n");
    }

    public void displayBuyPopTartBanner() {
        io.print("\n========== Buy Pop Tarts ==========\n");
    }

    public void displayBuySodaBanner() {
        io.print("\n========== Buy Soda ==========\n");
    }

    public void printChange(String change) {
        io.print("Your change is " + change);
        io.readString("\nPress enter to return to Main Menu.");
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }


}
