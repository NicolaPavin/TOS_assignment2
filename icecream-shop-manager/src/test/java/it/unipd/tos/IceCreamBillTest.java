////////////////////////////////////////////////////////////////////
// Nicola Pavin 1193215
////////////////////////////////////////////////////////////////////
package it.unipd.tos;

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
    List<MenuItem> items;
    User user = new User(1, "Joe", "Doe", 20);

    @Before
    public void reset() {
        items = new LinkedList<>();
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

        items.add(new MenuItem("Coppa Nafta", MenuItem.itemTypes.Gelati, 4));
        items.add(new MenuItem("Biancaneve", itemTypes.Budini, 6));
        double totalPrice = bill.getOrderPrice(items, user, new Date());

        assertEquals(10, totalPrice, 0);
    }

    @Test
    public void testGetOrderPrice_MoreThan5Icecreams_Discount() throws TakeAwayBillException {
        List<MenuItem> items = new LinkedList<>();
        for(int i =0; i<3; i++) {
            items.add(new MenuItem("Cioccolato", itemTypes.Gelati, 5));
        }
        items.add(new MenuItem("Fragola", itemTypes.Gelati, 4));
        items.add(new MenuItem("Neve", itemTypes.Budini, 3));
        items.add(new MenuItem("Pistacchio", itemTypes.Gelati, 5));
        // 5*4 + 3 + 4/2 = 25

        double totalPrice = bill.getOrderPrice(items, new User(1, "Joe", "Doe", 20), new Date());

        assertEquals(25, totalPrice, 0);
    }

    @Test
    public void testGetOrderPrice_MoreThan5nonIcecream_NoDiscount() throws TakeAwayBillException {
        List<MenuItem> items = new LinkedList<>();
        for(int i =0; i<5; i++) {
            items.add(new MenuItem("Neve", itemTypes.Budini, 3));
            items.add(new MenuItem("Frizz", itemTypes.Bevande, 5));
        }
        // 5*5 + 3*5 = 40

        double totalPrice = bill.getOrderPrice(items, new User(1, "Joe", "Doe", 20), new Date());

        assertEquals(40, totalPrice, 0);
    }
}
