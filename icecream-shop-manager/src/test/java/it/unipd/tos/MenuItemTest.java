////////////////////////////////////////////////////////////////////
// Nicola Pavin 1193215
////////////////////////////////////////////////////////////////////
package it.unipd.tos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.unipd.tos.model.MenuItem;

public class MenuItemTest {
    
    MenuItem item =  new MenuItem("Pistacchio", MenuItem.itemTypes.Gelati, 4);

    @Test
    public void testMenuItem_Name() {
        assertEquals("Pistacchio", item.getName());
    }

    @Test
    public void testMenuItem_Type() {
        assertEquals(MenuItem.itemTypes.Gelati, item.getType());
    }

    @Test
    public void testMenuItem_Price() {
        assertEquals(4, item.getPrice(), 0);
    }
}