package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Change;
import com.mthree.vendingmachine.dto.InventoryItem;
import com.mthree.vendingmachine.service.InsufficientFundsException;
import com.mthree.vendingmachine.service.NoItemInventoryException;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachineDaoFileImpl implements VendingMachineDao {

    public final String INVENTORY_FILE;
    public static final String DELIMITER = "::";

    private Map<String, InventoryItem> inventory = new HashMap<>();

    public VendingMachineDaoFileImpl() {
        INVENTORY_FILE = "inventory.txt";
    }

    public VendingMachineDaoFileImpl(String inventoryTextFile) {
        INVENTORY_FILE = inventoryTextFile;
    }

    @Override
    public InventoryItem addItem(String name, InventoryItem item) {
        return inventory.put(name, item);
    }

    @Override
    public List<InventoryItem> getAllItems() throws VendingMachinePersistenceException {
        //loadInventory();
        return new ArrayList<>(inventory.values());
    }

    @Override
    public InventoryItem getItem(String name) throws VendingMachinePersistenceException {
        //loadInventory();
        return inventory.get(name);
    }

    @Override
    public InventoryItem removeItem(String name) throws VendingMachinePersistenceException {
        //loadInventory();
        InventoryItem itemToRemove = inventory.remove(name);
        //writeInventory();
        return itemToRemove;
    }

    @Override
    public String sellItem(String name, BigDecimal coinsPaid) throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException {
        //loadInventory();

        InventoryItem itemToPurchase = inventory.get(name);

        int numInInventory = itemToPurchase.getNumInInventory();
        numInInventory--;
        itemToPurchase.setNumInInventory(numInInventory);

        Change change = new Change();

        String yourChange = change.getChange(coinsPaid, itemToPurchase);

        //writeInventory();

        return yourChange;
    }

    //loading, writing, marshalling, and unmarshalling methods
    public void loadInventory() throws VendingMachinePersistenceException {
        Scanner in;

        try {
            in = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException("-_- Could not load inventory data into memory.", e);
        }

        String currentLine;
        InventoryItem currentItem;

        while(in.hasNextLine()) {
            currentLine = in.nextLine();
            currentItem = unmarshallItem(currentLine);
            inventory.put(currentItem.getItemName(), currentItem);
        }
        in.close();
    }

    private InventoryItem unmarshallItem(String itemAsText) {

        //itemName::itemCost::numInInventory
        //  [0]       [1]          [2]
        String[] itemTokens = itemAsText.split(DELIMITER);

        //index 0 - item name
        String itemName = itemTokens[0];

        InventoryItem itemFromFile = new InventoryItem(itemName);

        //index 1 - item cost
        itemFromFile.setItemCost(new BigDecimal(itemTokens[1]));

        //index 2 - number in inventory
        itemFromFile.setNumInInventory(Integer.parseInt(itemTokens[2]));

        return itemFromFile;
    }

    public void writeInventory() throws VendingMachinePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not save inventory data.", e);
        }

        String itemAsText;
        List<InventoryItem> inventoryList = this.getAllItems();

        for(InventoryItem currentItem : inventoryList) {
            itemAsText = marshallItem(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }

    private String marshallItem(InventoryItem anItem) {
        //itemName::itemCost::numInInventory

        //add item name and delimiter
        String itemAsText = anItem.getItemName() + DELIMITER;

        //add cost in next with a delimiter
        itemAsText += anItem.getItemCost().toString() + DELIMITER;

        //finally, add number in inventory
        itemAsText += anItem.getNumInInventory();

        return itemAsText;
    }

}
