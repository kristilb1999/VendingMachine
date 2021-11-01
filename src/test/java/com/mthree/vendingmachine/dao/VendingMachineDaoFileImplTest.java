package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.InventoryItem;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineDaoFileImplTest {

    VendingMachineDao testDao;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
        String testFile = "testInventory.txt";

        new FileWriter(testFile);
        testDao = new VendingMachineDaoFileImpl(testFile);
    }

    @org.junit.jupiter.api.Test
    void testAddGetItem() throws Exception {
        //create test inputs
        String itemName = "pop rocks";
        InventoryItem item = new InventoryItem(itemName);
        item.setItemCost(new BigDecimal("1.15"));
        item.setNumInInventory(5);

        //add the item to the dao
        testDao.addItem(itemName, item);

        //get the item from the dao
        InventoryItem retrievedItem = testDao.getItem(itemName);

        //check that the data is equal
        assertEquals(itemName, retrievedItem.getItemName(), "Checking item name.");
        assertEquals(item.getItemCost(), retrievedItem.getItemCost(), "Checking item cost.");
        assertEquals(item.getNumInInventory(), retrievedItem.getNumInInventory(), "Checking number in inventory.");


    }

    @org.junit.jupiter.api.Test
    void testGetAllItems() throws Exception {
        //create test inputs
        String itemName = "pop rocks";
        InventoryItem item = new InventoryItem(itemName);
        item.setItemCost(new BigDecimal("1.15"));
        item.setNumInInventory(5);

        String itemName2 = "cake";
        InventoryItem item2 = new InventoryItem(itemName2);
        item2.setItemCost(new BigDecimal("4.65"));
        item2.setNumInInventory(3);

        //add to dao
        testDao.addItem(itemName, item);
        testDao.addItem(itemName2, item2);

        //retrieve the list of items in inventory
        List<InventoryItem> allItems = testDao.getAllItems();

        //check general contents
        assertNotNull(allItems, "The list of items must not be null.");
        assertEquals(2, allItems.size(), "There should be 2 items in the list.");

        //check the specific contents of the list
        assertTrue(testDao.getAllItems().contains(item), "The list should include 'pop rocks'.");
        assertTrue(testDao.getAllItems().contains(item2), "The list should include 'cake'.");
    }

    @org.junit.jupiter.api.Test
    void testRemoveItem() throws Exception {
        //create test inputs
        String itemName = "pop rocks";
        InventoryItem item = new InventoryItem(itemName);
        item.setItemCost(new BigDecimal("1.15"));
        item.setNumInInventory(5);

        String itemName2 = "cake";
        InventoryItem item2 = new InventoryItem(itemName2);
        item2.setItemCost(new BigDecimal("4.65"));
        item2.setNumInInventory(3);

        //add to dao
        testDao.addItem(itemName, item);
        testDao.addItem(itemName2, item2);

        //remove first item
        InventoryItem removedItem = testDao.removeItem(itemName);

        //check the correct object was removed
        assertEquals(item, removedItem, "The removed item should be 'pop rocks'.");

        //get list and check to make sure item was properly deleted
        List<InventoryItem> allItems = testDao.getAllItems();

        assertNotNull(allItems, "The list should not be null.");
        assertEquals(1, allItems.size(), "There should only be 1 item in inventory.");

        assertFalse(allItems.contains(item), "This item should no longer be inside the list.");
        assertTrue(allItems.contains(item2), "This item should still be in the list.");

        //remove the second item and repeat
        removedItem = testDao.removeItem(itemName2);

        assertEquals(item2, removedItem, "The removed item should be 'cake'.");

        allItems = testDao.getAllItems();

        assertTrue(allItems.isEmpty(), "The retrieved inventory list should be empty.");

        //try to get both items to make sure they aren't still in the dao
        InventoryItem retrievedItem = testDao.getItem(itemName);
        assertNull(retrievedItem, "'pop rocks' was removed, should be null.");

        retrievedItem = testDao.getItem(itemName2);
        assertNull(retrievedItem, "'cake' was removed, should be null.");

    }

    @org.junit.jupiter.api.Test
    void testSellItem() throws Exception {
        //create test inputs
        String itemName = "pop rocks";
        InventoryItem item = new InventoryItem(itemName);
        item.setItemCost(new BigDecimal("1.15"));
        item.setNumInInventory(1);

        String itemName2 = "cake";
        InventoryItem item2 = new InventoryItem(itemName2);
        item2.setItemCost(new BigDecimal("4.65"));
        item2.setNumInInventory(3);

        //add to dao
        testDao.addItem(itemName, item);
        testDao.addItem(itemName2, item2);

        //sell pop rocks with correct amount of money
        BigDecimal moneyPaid = new BigDecimal("1.15");
        String change = testDao.sellItem(itemName, moneyPaid);

        assertEquals("0 quarters, 0 dimes, 0 nickels, and 0 pennies.", change, "There should be no problems here.");

    }
}