/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package GUI;

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
public class LoginFrameTest {

    private LoginFrame loginFrame;

    public LoginFrameTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        loginFrame = new LoginFrame();
    }

    @After
    public void tearDown() {
        loginFrame = null;
    }

    /**
     * Test of login method, of class LoginFrame.
     */
    @Test
    public void testLogin() {
        int validUserId = 1; 
        String validPassword = "123"; 

        // Test successful login
        assertTrue("Login should succeed with valid credentials", loginFrame.login(validUserId, validPassword));

        // Test failed login with invalid credentials
        assertFalse("Login should fail with invalid credentials", loginFrame.login(validUserId, "abc"));

        // Test failed login with non-existing user ID
        assertFalse("Login should fail with non-existing user ID", loginFrame.login(-1, validPassword));

    }

}
