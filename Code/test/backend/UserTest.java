/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package backend;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wang
 */
public class UserTest {

    private User testUser;

    public UserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        testUser = new User("Alice", "123", true);
        testUser.setId(1);
    }

    @After
    public void tearDown() {
        testUser = null;
    }

    /**
     * Test of getId method, of class User.
     */
    @Test
    public void testGetId() {
        System.out.println("Testing getId");
        int expectedId = 1;
        int actualId = testUser.getId();
        assertEquals("User ID should be 1", expectedId, actualId);
    }

    /**
     * Test of setId method, of class User.
     */
    @Test
    public void testSetId() {
        System.out.println("Testing setId");
        int newId = 2;
        testUser.setId(newId);
        assertEquals("User ID should be updated to 2", newId, testUser.getId());
    }

    /**
     * Test of getName method, of class User.
     */
    @Test
    public void testGetName() {
        System.out.println("Testing getName");
        String expectedName = "Alice";
        String actualName = testUser.getName();
        assertEquals("User name should be 'Alice'", expectedName, actualName);
    }

    /**
     * Test of getPassword method, of class User.
     */
    @Test
    public void testGetPassword() {
        System.out.println("Testing getPassword");
        String expectedPassword = "123";
        String actualPassword = testUser.getPassword();
        assertEquals("User password should be 'password123'", expectedPassword, actualPassword);

    }

    /**
     * Test of isStudent method, of class User.
     */
    @Test
    public void testIsStudent() {
        System.out.println("Testing isStudent");
        boolean expectedRole = true;
        boolean actualRole = testUser.isStudent();
        assertEquals("User role should be student (true)", expectedRole, actualRole);
    }

}
