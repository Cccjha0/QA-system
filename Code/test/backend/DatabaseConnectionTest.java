/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package backend;

import java.sql.Connection;
import java.sql.SQLException;
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
public class DatabaseConnectionTest {

    public DatabaseConnectionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        DatabaseConnection.shutdown();
    }

    /**
     * Test of getConnection method, of class DatabaseConnection.
     */
    @Test
    public void testGetConnection() throws Exception {
        System.out.println("Testing getConnection");

        // 检查连接是否不为空且是打开的
        try ( Connection result = DatabaseConnection.getConnection()) {
            // 检查连接是否不为空且是打开的
            assertNotNull("Connection should not be null", result);
            assertFalse("Connection should be open", result.isClosed());
            // 关闭连接
        } catch (SQLException e) {
            // Print the stack trace if an SQL exception occurs
            e.printStackTrace();
        }
    }

    /**
     * Test of shutdown method, of class DatabaseConnection.
     */
    @Test
    public void testShutdown() throws SQLException {
        System.out.println("Testing shutdown");
        
        // 确保连接是打开的
        Connection connection = DatabaseConnection.getConnection();
        assertNotNull("Connection should not be null before shutdown", connection);

        DatabaseConnection.shutdown();
        
        // 检查连接是否已关闭
        assertTrue("Connection should be closed after shutdown", connection.isClosed());
    }

}
