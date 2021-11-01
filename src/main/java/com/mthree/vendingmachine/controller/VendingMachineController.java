package com.mthree.vendingmachine.controller;

import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.InventoryItem;
import com.mthree.vendingmachine.service.InsufficientFundsException;
import com.mthree.vendingmachine.service.NoItemInventoryException;
import com.mthree.vendingmachine.service.VendingMachineServiceLayer;
import com.mthree.vendingmachine.ui.VendingMachineView;

import java.math.BigDecimal;

public class VendingMachineController {

    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        String leavingOrMoney = "";
        String menuSelection = "";
        BigDecimal money = new BigDecimal("0");
        try {
            loadInventoryIn();
            while (keepGoing) {

                printMenu();

                leavingOrMoney = getLeavingOrMoney();

                if(leavingOrMoney.equalsIgnoreCase("leave")) {
                    menuSelection = "exit";
                } else {
                    money = getMoney(leavingOrMoney);
                    menuSelection = getMenuSelection();
                }

                switch (menuSelection) {
                    case "cookie":
                        buyCookies(money, menuSelection);
                        break;
                    case "cracker":
                        buyCrackers(money, menuSelection);
                        break;
                    case "candy":
                        buyCandy(money, menuSelection);
                        break;
                    case "gum":
                        buyGum(money, menuSelection);
                        break;
                    case "pop tart":
                        buyPopTart(money, menuSelection);
                        break;
                    case "soda":
                        buySoda(money, menuSelection);
                        break;
                    case "exit":
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
            updateInventoryOut();
        } catch (NoItemInventoryException | InsufficientFundsException |VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void loadInventoryIn() throws VendingMachinePersistenceException {
        service.loadInventory();
    }

    private void updateInventoryOut() throws VendingMachinePersistenceException {
        service.writeInventory();
    }

    private void printMenu() throws VendingMachinePersistenceException {
        view.printMenu(service.getAllItems());
    }

    private String getLeavingOrMoney() throws VendingMachinePersistenceException {
        return view.getLeavingOrMoneyAmount();
    }

    private BigDecimal getMoney(String moneyAsString) throws VendingMachinePersistenceException {
        BigDecimal money = new BigDecimal(moneyAsString);
        return money;
    }

    private String getMenuSelection(){
        return view.getSelection();
    }

    private void buyCookies(BigDecimal money, String selection) throws NoItemInventoryException, VendingMachinePersistenceException, InsufficientFundsException {
        view.displayBuyCookiesBanner();
            try {
                String change = service.sellItem(selection, money);
                view.printChange(change);
            } catch (NoItemInventoryException | InsufficientFundsException | VendingMachinePersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
    }

    private void buyCrackers(BigDecimal money, String selection) throws NoItemInventoryException, VendingMachinePersistenceException, InsufficientFundsException {
        view.displayBuyCrackersBanner();
            try {
                String change = service.sellItem(selection, money);
                view.printChange(change);
            } catch (NoItemInventoryException | InsufficientFundsException | VendingMachinePersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
    }

    private void buyCandy(BigDecimal money, String selection) throws NoItemInventoryException, VendingMachinePersistenceException, InsufficientFundsException {
        view.displayBuyCandyBanner();
            try {
                String change = service.sellItem(selection, money);
                view.printChange(change);
            } catch (NoItemInventoryException | InsufficientFundsException | VendingMachinePersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
    }

    private void buyGum(BigDecimal money, String selection) throws NoItemInventoryException, VendingMachinePersistenceException, InsufficientFundsException {
        view.displayBuyGumBanner();
            try {
                String change = service.sellItem(selection, money);
                view.printChange(change);
            } catch (NoItemInventoryException | InsufficientFundsException | VendingMachinePersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
    }

    private void buyPopTart(BigDecimal money, String selection) throws NoItemInventoryException, VendingMachinePersistenceException, InsufficientFundsException {
        view.displayBuyPopTartBanner();
            try {
                String change = service.sellItem(selection, money);
                view.printChange(change);
            } catch (NoItemInventoryException | InsufficientFundsException | VendingMachinePersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
    }

    private void buySoda(BigDecimal money, String selection) throws NoItemInventoryException, VendingMachinePersistenceException, InsufficientFundsException {
        view.displayBuySodaBanner();
            try {
                String change = service.sellItem(selection, money);
                view.printChange(change);
            } catch (NoItemInventoryException | InsufficientFundsException | VendingMachinePersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

}
