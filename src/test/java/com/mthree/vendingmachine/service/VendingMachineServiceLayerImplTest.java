package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachineAuditDao;
import com.mthree.vendingmachine.dao.VendingMachineDao;
import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.InventoryItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineServiceLayerImplTest {

    private VendingMachineServiceLayer service;

    public VendingMachineServiceLayerImplTest() {
//        VendingMachineDao dao = new VendingMachineDaoStubImpl();
//        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
//
//        service = new VendingMachineServiceLayerImpl(dao, auditDao);

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        service = ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);

    }

    @Test
    public void testAddItem() {
        InventoryItem item = new InventoryItem("pie");
        item.setItemCost(new BigDecimal("5.00"));
        item.setNumInInventory(4);
        try {
            service.addItem(item.getItemName(), item);
        } catch (VendingMachinePersistenceException e) {
            fail("Item was valid. No exception should have been thrown.");
        }
    }

    @Test
    public void testGetAllItems() throws Exception {
        InventoryItem testClone = new InventoryItem("fudge");
        testClone.setItemCost(new BigDecimal("2.25"));
        testClone.setNumInInventory(15);

        assertEquals(1, service.getAllItems().size(), "Should only have one item.");
        assertTrue(service.getAllItems().contains(testClone), "The one item should be 'fudge'");
    }

    @Test
    public void testGetItem() throws Exception {
        InventoryItem testClone = new InventoryItem("fudge");
        testClone.setItemCost(new BigDecimal("2.25"));
        testClone.setNumInInventory(15);

        InventoryItem shouldBeFudge = service.getItem("fudge");
        assertNotNull(shouldBeFudge, "Getting 'fudge' should not be null.");
        assertEquals(testClone, shouldBeFudge, "Inventory Item should be 'fudge'");

        InventoryItem shouldBeNull = service.getItem("cake");
        assertNull(shouldBeNull, "Inventory Item doesn't exist, should be null.");

    }

    @Test
    public void testRemoveItem() throws Exception {
        InventoryItem testClone = new InventoryItem("fudge");
        testClone.setItemCost(new BigDecimal("2.25"));
        testClone.setNumInInventory(15);

        InventoryItem shouldBeFudge = service.removeItem("fudge");
        assertNotNull(shouldBeFudge, "Removing 'fudge' should not be null.");
        assertEquals(testClone, shouldBeFudge, "Item removed should be 'fudge'.");

        InventoryItem shouldBeNull = service.removeItem("half moon");
        assertNull(shouldBeNull, "Removing 'half moon' should be null.");
    }

    @Test
    void testSellItemCorrectly() {
        InventoryItem testClone = new InventoryItem("fudge");
        testClone.setItemCost(new BigDecimal("2.25"));
        testClone.setNumInInventory(15);

        BigDecimal moneyPaid = new BigDecimal("2.25");

        try{
            String change = service.sellItem(testClone.getItemName(), moneyPaid);
            assertEquals("0 quarters, 0 dimes, 0 nickels, and 0 pennies.", change, "There should have been no change received from this transaction.");
        } catch(VendingMachinePersistenceException | NoItemInventoryException | InsufficientFundsException e) {
            return;
        }
    }

    @Test
    public void testSellItemNotEnoughMoney() {
        InventoryItem testClone = new InventoryItem("fudge");
        testClone.setItemCost(new BigDecimal("2.25"));
        testClone.setNumInInventory(15);

        BigDecimal moneyPaid = new BigDecimal("2.00");

        try{
            String change = service.sellItem(testClone.getItemName(), moneyPaid);
            fail("Expected InsufficientFundsException was not thrown.");
        } catch(VendingMachinePersistenceException | NoItemInventoryException e) {
            fail("Incorrect exception was thrown.");
        } catch (InsufficientFundsException e) {
            return;
        }
    }

//    @Test
//    public void testSellItemNotInStock() {
//        InventoryItem testClone = new InventoryItem("cake");
//        testClone.setItemCost(new BigDecimal("2.25"));
//        testClone.setNumInInventory(15);
//
//        BigDecimal moneyPaid = new BigDecimal("2.00");
//
//        try{
//            String change = service.sellItem(testClone.getItemName(), moneyPaid);
//            fail("Expected NoItemInventoryException was not thrown.");
//        } catch(VendingMachinePersistenceException | InsufficientFundsException e) {
//            fail("Incorrect exception was thrown.");
//        } catch (NoItemInventoryException e) {
//            return;
//        }
//    }

}