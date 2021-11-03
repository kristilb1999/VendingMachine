package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachineAuditDao;
import com.mthree.vendingmachine.dao.VendingMachineDao;
import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Change;
import com.mthree.vendingmachine.dto.InventoryItem;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public InventoryItem addItem(String name, InventoryItem item) throws VendingMachinePersistenceException {
        return dao.addItem(name, item);
    }

    @Override
    public List<InventoryItem> getAllItems() throws VendingMachinePersistenceException {
        return dao.getAllItems();
    }

    @Override
    public InventoryItem getItem(String name) throws VendingMachinePersistenceException {
        return dao.getItem(name);
    }

    @Override
    public InventoryItem removeItem(String name) throws VendingMachinePersistenceException {
        return dao.removeItem(name);
    }

    @Override
    public String sellItem(String name, BigDecimal coinsPaid) throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException {

        InventoryItem itemToPurchase = dao.getItem(name);

        Change refund = new Change();
        String totalRefund = refund.returnMoney(coinsPaid);

        if(itemToPurchase.getNumInInventory() <= 0) {
            throw new NoItemInventoryException("ERROR: Sorry, we are out of stock!" + "\nYour change is " + totalRefund);
        }

        if(itemToPurchase.getItemCost().compareTo(coinsPaid) > 0) {
            throw new InsufficientFundsException("ERROR: You don't have enough funds to buy this item! You only paid: " + coinsPaid + ".\nYour change is " + totalRefund);
        }

        String yourChange = dao.sellItem(name, coinsPaid);

        //write to audit log
        auditDao.writeAuditEntry("Item " + name + " SOLD.");

        return yourChange;
    }

    @Override
    public void loadInventory() throws VendingMachinePersistenceException {
        dao.loadInventory();
    }

    @Override
    public void writeInventory() throws VendingMachinePersistenceException {
        dao.writeInventory();
    }
}
