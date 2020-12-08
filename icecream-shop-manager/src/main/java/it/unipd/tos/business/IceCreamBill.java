////////////////////////////////////////////////////////////////////
// Nicola Pavin 1193215
////////////////////////////////////////////////////////////////////
package it.unipd.tos.business;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.User;
import it.unipd.tos.model.MenuItem.itemTypes;

public class IceCreamBill implements TakeAwayBill {
    
    @Override
    public double getOrderPrice(
        List<MenuItem> itemsOrdered, User user, Date date
        ) throws TakeAwayBillException {

        if (itemsOrdered.size() > 30) {
            throw new TakeAwayBillException();
        }
        
        double totalPrice = calculateCost(itemsOrdered);

        return totalPrice;
    }

    private double calculateCost(List<MenuItem> items) {

        double cost = 0;
        double beveragesCost = 0;
        for(MenuItem item: items) {

            cost += item.getPrice();
            if (item.getType() == itemTypes.Bevande) {
                beveragesCost += item.getPrice();
            }
        }

        cost = cost - icecreamDiscount(items);

        if (cost - beveragesCost >= 50) {
            cost = cost - (cost / 10);
        }

        if (!items.isEmpty() && cost < 10) {
            cost += 0.5;
        }

        return cost;
    }

    private double icecreamDiscount(List<MenuItem> items) {

        int icecreamNumber = 0;
        double lowestIcecreamPrice = Double.MAX_VALUE;
        for(MenuItem item: items) {
            if (item.getType() == itemTypes.Gelati) {
                icecreamNumber++;
                if (item.getPrice() < lowestIcecreamPrice) {
                    lowestIcecreamPrice = item.getPrice();
                }
            }    
        }

        return icecreamNumber >= 5 ? lowestIcecreamPrice/2 : 0;
    }

    // Requisito 6
    List<MenuItem> itemsOrdered;
    User user;
    Date date;
    double price;

    public IceCreamBill() {}

    public IceCreamBill(List<MenuItem> itemsOrdered, User user, Date date)
     throws TakeAwayBillException{
        this.itemsOrdered = itemsOrdered;
        this.user = user;
        this.date = date;
        price = getOrderPrice(itemsOrdered, user, date);
    }

    public double getPrice() throws TakeAwayBillException {

        return price;
    }  

    public static void makeFree(List<IceCreamBill> bills) {

        List<IceCreamBill> validBills = new LinkedList<>();
        Calendar calendar = Calendar.getInstance();
            for (IceCreamBill bill: bills) {
                calendar.setTime(bill.date);
                if (bill.user.getAge() <= 18 
                && calendar.get(Calendar.HOUR_OF_DAY) >= 18 
                && calendar.get(Calendar.HOUR_OF_DAY) < 19) {
                    validBills.add(bill);
                }
            }
        
            Set<Integer> userIDs = new HashSet<>();
            int freeOrders = 0;
            while (freeOrders < 10 && !validBills.isEmpty()) {
                int rand = new Random().nextInt(validBills.size());
                if (userIDs.add(validBills.get(rand).user.getId())) {
                    validBills.get(rand).price = 0;
                    freeOrders++;
                }
                validBills.remove(rand);
            }
    }
}