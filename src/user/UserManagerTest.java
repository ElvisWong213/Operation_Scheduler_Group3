package user;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dataStructure.MyLinkedList;

public class UserManagerTest {
    @Test
    public void testAvailableTime() {

    }

    @Test
    public void testGetAllPatients() {
        MyLinkedList<Patient> patients = UserManager.getAllPatients();
        assertEquals(1, patients.size());
    }

    @Test
    public void testGetAllProfessionals() {
        MyLinkedList<Professional> professionals = UserManager.getAllProfessionals();
        assertEquals(1, professionals.size());
    }
}
