////////////////////////////////////////////////////////////////////
// Nicola Pavin 1193215
////////////////////////////////////////////////////////////////////
package it.unipd.tos.business;

import java.util.Date;
import java.util.List;
import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.User;

public class IceCreamBill implements TakeAwayBill {
    
    @Override
    public double getOrderPrice(
        List<MenuItem> itemsOrdered, User user, Date date
        ) throws TakeAwayBillException {
        
        double totalPrice = calculateCost(itemsOrdered);

        return totalPrice;
    }

    private double calculateCost(List<MenuItem> items) {

        double cost = 0;
        for(MenuItem item: items) {
            cost += item.getPrice();
        }

        return cost;
    }
}