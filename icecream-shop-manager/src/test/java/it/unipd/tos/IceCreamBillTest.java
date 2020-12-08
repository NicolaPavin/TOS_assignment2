////////////////////////////////////////////////////////////////////
// Nicola Pavin 1193215
////////////////////////////////////////////////////////////////////
package it.unipd.tos;

import java.util.Calendar;
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
    User user;

    @Before
    public void reset() {
        items = new LinkedList<>();
        user = new User(1, "Joe", "Doe", 20);
    }

    // TEST getOrderPrice --- Requisiti 1 - 5
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

        double totalPrice = bill.getOrderPrice(items, user, new Date());

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

        double totalPrice = bill.getOrderPrice(items, user, new Date());

        assertEquals(40, totalPrice, 0);
    }

    @Test
    public void testGetOrderPrice_CostMoreThan50_Discount() throws TakeAwayBillException {

        for(int i =0; i<10; i++) {
            items.add(new MenuItem("Banana", itemTypes.Budini, 5));
        }
        items.add(new MenuItem("Coppa Nafta", MenuItem.itemTypes.Gelati, 4));
        items.add(new MenuItem("Premium Water", itemTypes.Bevande, 6));
        // 5*10 + 4 + 6 - 60/10 = 54

        double totalPrice = bill.getOrderPrice(items, user, new Date());

        assertEquals(54, totalPrice, 0);
    }

    @Test
    public void testGetOrderPrice_5orMoreIcecreamsAndCostMoreThan50_Discount() throws TakeAwayBillException {

        for(int i =0; i<10; i++) {
            items.add(new MenuItem("Banana", itemTypes.Budini, 5));
            items.add(new MenuItem("Mela", itemTypes.Gelati, 6));
        }
        // 10*(6+5) - 6/2 = 107 => -107/10 => 96.3

        double totalPrice = bill.getOrderPrice(items, user, new Date());

        assertEquals(96.3, totalPrice, 0);
    }

    @Test(expected = TakeAwayBillException.class)
    public void testGetOrderPrice_30orMoreItems_Exception() throws TakeAwayBillException {

        for(int i =0; i<35; i++) {
            items.add(new MenuItem("Coke", itemTypes.Bevande, 10));
        }

        double totalPrice = bill.getOrderPrice(items, user, new Date());
    }

    @Test
    public void testGetOrderPrice_CostIsLessThan10_Commission() throws TakeAwayBillException {

        items.add(new MenuItem("Frenzy", itemTypes.Bevande, 7.5));
        // 7.5 => +0.5 => 8

        double totalPrice = bill.getOrderPrice(items, user, new Date());

        assertEquals(8, totalPrice, 0);
    }

    @Test
    public void testGetOrderPrice_5IcecreamsAndCostIsLessThan10_DiscoutnAndCommission() 
    throws TakeAwayBillException {

        for(int i =0; i<8; i++) {
            items.add(new MenuItem("Cilli", itemTypes.Gelati, 1));
        }
        // 1*8 - 1/2 + 0.5 = 8

        double totalPrice = bill.getOrderPrice(items, user, new Date());

        assertEquals(8, totalPrice, 0);
    }

    // Requisito 6
    @Test
    public void testGetPrice() throws TakeAwayBillException {
        
        items.add(new MenuItem("Coppa GOLD", MenuItem.itemTypes.Gelati, 12));
        IceCreamBill bill = new IceCreamBill(items, user, new Date());

        assertEquals(12, bill.getPrice(), 0);
    }

    @Test
    public void testMakeFree_20OrdersFrom18To19ByMinors_FreeOrders() 
    throws TakeAwayBillException {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        Date date = calendar.getTime();
        List<IceCreamBill> bills = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            items.add(new MenuItem("Banana", itemTypes.Budini, 5));
            bills.add(new IceCreamBill(items, new User(i, "Joe", "Doe", 18), date));

            items = new LinkedList<>();
        }

        IceCreamBill.makeFree(bills);

        assertFrees(10, bills);
    }

    @Test
    public void testMakeFree_10OrdersFrom18To19By5Minors_FreeOrders() 
    throws TakeAwayBillException {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        Date date = calendar.getTime();
        List<IceCreamBill> bills = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            items.add(new MenuItem("Banana", itemTypes.Budini, 5));
            bills.add(new IceCreamBill(items, new User(i%5, "Joe", "Doe", 18), date));

            items = new LinkedList<>();
        }

        IceCreamBill.makeFree(bills);

        assertFrees(5, bills);
    }

    @Test
    public void testMakeFree_FiveOrdersFrom18To19ByMinorsAndOthers_FreeOrdersAndNormalOnes() 
    throws TakeAwayBillException {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        Date date = calendar.getTime();
        List<IceCreamBill> bills = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            items.add(new MenuItem("Banana", itemTypes.Budini, 5));
            bills.add(new IceCreamBill(items, new User(i, "Joe", "Doe", 18), date));

            items = new LinkedList<>();
        }

        for (int i = 0; i < 10; i++) {
            items.add(new MenuItem("Banana", itemTypes.Budini, 5));
            bills.add(new IceCreamBill(items, new User(i+5, "Joe", "Doe", 20), date));

            items = new LinkedList<>();
        }

        IceCreamBill.makeFree(bills);

        assertFrees(5, bills);
    }

    @Test
    public void testMakeFree_NoOrdersFrom18To19ByMinors_NormalOnes() 
    throws TakeAwayBillException {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        Date date = calendar.getTime();
        List<IceCreamBill> bills = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            items.add(new MenuItem("Banana", itemTypes.Budini, 5));
            bills.add(new IceCreamBill(items, new User(i, "Joe", "Doe", 18), date));    // Orario invalido

            items = new LinkedList<>();
        }

        calendar.set(Calendar.HOUR_OF_DAY, 18);
        date = calendar.getTime();
        for (int i = 0; i < 5; i++) {
            items.add(new MenuItem("Banana", itemTypes.Budini, 5));
            bills.add(new IceCreamBill(items, new User(i+5, "Joe", "Doe", 20), date));  // Eta' invalida

            items = new LinkedList<>();
        }

        IceCreamBill.makeFree(bills);

        assertFrees(0, bills);
    }

    private void assertFrees(int expectedFreeBills, List<IceCreamBill> bills) 
    throws TakeAwayBillException{
        
        int freeBills = 0;
        for (IceCreamBill bill: bills) {
            if (bill.getPrice() == 0) {
               freeBills++;
            }
        }
        assertEquals(expectedFreeBills, freeBills);
    }
}
