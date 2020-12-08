////////////////////////////////////////////////////////////////////
// Nicola Pavin 1193215
////////////////////////////////////////////////////////////////////
package it.unipd.tos.business;

import java.util.Date;
import java.util.List;
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
}