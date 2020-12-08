////////////////////////////////////////////////////////////////////
// Nicola Pavin 1193215
////////////////////////////////////////////////////////////////////
package it.unipd.tos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.unipd.tos.model.User;

public class UserTest {
    
    User user = new User(1, "Jhon", "Arck", 38);

    @Test
    public void testUser_GetTestID() {
        assertEquals(1, user.getId());
    }

    @Test
    public void testUser_GetFullName() {
        assertEquals("Jhon Arck", user.getFullName());
    }

    @Test
    public void testUser_GetAge() {
        assertEquals(38, user.getAge());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUser_IllegalAge_Exception() {
        user = new User(2, "Jhon", "Mark", -4);
    }
}