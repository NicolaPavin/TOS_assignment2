////////////////////////////////////////////////////////////////////
// Nicola Pavin 1193215
////////////////////////////////////////////////////////////////////
package it.unipd.tos.business;

import java.util.Date;
import java.util.List;
import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.User;

public interface TakeAwayBill {

  double getOrderPrice(
    List<MenuItem> itemsOrdered, User user, Date date
    ) throws TakeAwayBillException;
}