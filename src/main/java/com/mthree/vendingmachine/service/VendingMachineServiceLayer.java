package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.InventoryItem;

import java.math.BigDecimal;
import java.util.List;

public interface VendingMachineServiceLayer {

    InventoryItem addItem(String name, InventoryItem item) throws VendingMachinePersistenceException;

    List<InventoryItem> getAllItems() throws VendingMachinePersistenceException;

    InventoryItem getItem(String name) throws VendingMachinePersistenceException;

    InventoryItem removeItem(String name) throws VendingMachinePersistenceException;

    String sellItem(String name, BigDecimal coinsPaid) throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException;

    void loadInventory() throws VendingMachinePersistenceException;

    void writeInventory() throws VendingMachinePersistenceException;

}
