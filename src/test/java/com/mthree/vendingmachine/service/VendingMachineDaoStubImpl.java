package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachineDao;
import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Change;
import com.mthree.vendingmachine.dto.InventoryItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendingMachineDaoStubImpl implements VendingMachineDao {

    public InventoryItem onlyItem;

    public VendingMachineDaoStubImpl() {
        onlyItem = new InventoryItem("fudge");
        onlyItem.setItemCost(new BigDecimal("2.25"));
        onlyItem.setNumInInventory(15);
    }

    public VendingMachineDaoStubImpl(InventoryItem testItem) {
        this.onlyItem = testItem;
    }

    @Override
    public InventoryItem addItem(String name, InventoryItem item) throws VendingMachinePersistenceException {
        if(name.equals(onlyItem.getItemName())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public List<InventoryItem> getAllItems() throws VendingMachinePersistenceException {
        List<InventoryItem> inventoryList = new ArrayList<>();
        inventoryList.add(onlyItem);
        return inventoryList;
    }

    @Override
    public InventoryItem getItem(String name) throws VendingMachinePersistenceException {
        if(name.equals(onlyItem.getItemName())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public InventoryItem removeItem(String name) throws VendingMachinePersistenceException {
        if(name.equals(onlyItem.getItemName())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public String sellItem(String name, BigDecimal coinsPaid) throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {

            if(name.equals(onlyItem.getItemName())) {
                int numInInventory = onlyItem.getNumInInventory();
                numInInventory--;
                onlyItem.setNumInInventory(numInInventory);
                Change yourChange = new Change();
                String change = yourChange.getChange(coinsPaid, onlyItem);
                return change;
            }

        return "";
    }

    @Override
    public void loadInventory() throws VendingMachinePersistenceException {

    }

    @Override
    public void writeInventory() throws VendingMachinePersistenceException {

    }
}
