package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Change;
import com.mthree.vendingmachine.dto.InventoryItem;
import com.mthree.vendingmachine.service.InsufficientFundsException;
import com.mthree.vendingmachine.service.NoItemInventoryException;

import java.math.BigDecimal;
import java.util.List;

public interface VendingMachineDao {

    /**
     * Add an item into the Inventory of the vending machine by item name.
     * If there is already an Item with that name, it will return that InventoryItem,
     * otherwise it will return null.
     *
     * @param name The name of the item to be added.
     * @param item The InventoryItem to be added.
     * @return The InventoryItem object previously associated with that name
     * if it exists or null.
     * @throws VendingMachinePersistenceException
     */
    InventoryItem addItem(String name, InventoryItem item) throws VendingMachinePersistenceException;

    /**
     * Returns a List of all the InventoryItem Objects in inventory.
     *
     * @return Inventory List containing all InventoryItem objects.
     * @throws VendingMachinePersistenceException
     */
    List<InventoryItem> getAllItems() throws VendingMachinePersistenceException;

    /**
     * Returns the InventoryItem object associated with the given name.
     * Returns null if no such item exists.
     *
     * @param name The name of the InventoryItem to return.
     * @return The InventoryItem associated with the given name,
     * null if no such InventoryItem exists.
     * @throws VendingMachinePersistenceException
     */
    InventoryItem getItem(String name) throws VendingMachinePersistenceException;

    /**
     * Removes the InventoryItem associated with the given name
     * from the inventory. Returns the InventoryItem object that is being
     * removed of null if there is no InventoryItem associated with
     * the specified name.
     *
     * @param name The name of the InventoryItem to be removed.
     * @return InventoryItem object that was removed or null if no
     * InventoryItem was associated with the given name.
     * @throws VendingMachinePersistenceException
     */
    InventoryItem removeItem(String name) throws VendingMachinePersistenceException;

    /**
     * Returns the Change after the user purchases an InventoryItem
     * from the Vending Machine. If the item exists and the cost matches
     * the amount paid, then an item is bought. Else, an exception is thrown.
     *
     * @param name The name of the item to be purchased.
     * @param coinsPaid The amount of money given to the Vending machine.
     * @returns The change a person is owed if they paid too much.
     * @throws InsufficientFundsException If not enough money is given.
     * @throws NoItemInventoryException If the item is out of stock.
     */
    String sellItem(String name, BigDecimal coinsPaid) throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException;

    /**
     * Loads the Inventory into memory.
     */
    void loadInventory() throws VendingMachinePersistenceException;

    /**
     * Writes the inventory into external memory of some type.
     */
    void writeInventory() throws VendingMachinePersistenceException;

}
