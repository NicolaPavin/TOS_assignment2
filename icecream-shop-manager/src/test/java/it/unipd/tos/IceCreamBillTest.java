////////////////////////////////////////////////////////////////////
// Nicola Pavin 1193215
////////////////////////////////////////////////////////////////////
package it.unipd.tos;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import it.unipd.tos.business.TakeAwayBill;
import it.unipd.tos.business.IceCreamBill;
import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.MenuItem.itemTypes;
import it.unipd.tos.model.User;

public class IceCreamBillTest {
    TakeAwayBill bill = new IceCreamBill();
    User user = new User(1, "Joe", "Doe", 20);

    @Before
    public void reset() {
        user = new User(1, "Joe", "Doe", 20);
    }

    
    @Test(expected = NullPointerException.class)
    public void testGetOrderPrice_null_Exception() throws TakeAwayBillException{
        double zero = bill.getOrderPrice(null, null, null);
    }

    @Test
    public void testGetOrderPrice_EmptyList_Zero() throws TakeAwayBillException{
        double zero = bill.getOrderPrice(Collections.emptyList(), user, new Date());
        assertEquals(0, zero, 0);
    }

    @Test
    public void testGetOrderPrice_ValidList_Calculated() throws TakeAwayBillException {

        List<MenuItem> items = 
        Arrays.asList( new MenuItem("Coppa Nafta", MenuItem.itemTypes.Gelati, 4), new MenuItem("Biancaneve", itemTypes.Budini, 6));
        double totalPrice = bill.getOrderPrice(items, user, new Date());

        assertEquals(10, totalPrice, 0);
    }
}
