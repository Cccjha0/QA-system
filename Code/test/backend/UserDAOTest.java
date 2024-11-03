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
import java.sql.*;

/**
 *
 * @author wang
 */
public class UserDAOTest {

    private User testUser;

    public UserDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        testUser = new User("Wang", "abc", true);
    }

    @After
    public void tearDown() {
        if (testUser.getId() > 0) {
            try ( Connection connection = DatabaseConnection.getConnection();  PreparedStatement statement = connection.prepareStatement("DELETE FROM Users WHERE id = ?")) {
                statement.setInt(1, testUser.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        testUser = null;
    }

    /**
     * Test of registerUser method, of class UserDAO.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("Testing registerUser");

        int userId = UserDAO.registerUser(testUser);
        
        testUser.setId(userId); // 设置 ID 以便于清理操作
        
        // 检查返回的 ID 是否有效
        assertTrue("User ID should be greater than 0 after registration", userId > 0);
    }

    /**
     * Test of loginUser method, of class UserDAO.
     */
    @Test
    public void testLoginUser() {
        System.out.println("Testing loginUser");

        // 注册用户，以便登录测试
        int userId = UserDAO.registerUser(testUser);
        testUser.setId(userId);

        User loggedInUser = UserDAO.loginUser(userId, "abc");

        // 验证登录是否成功
        assertNotNull("User should be able to log in with correct ID and password", loggedInUser);
        assertEquals("Logged in user name should match", testUser.getName(), loggedInUser.getName());
    }

    /**
     * Test of updatePassword method, of class UserDAO.
     */
    @Test
    public void testUpdatePassword() {
        System.out.println("Testing updatePassword");

        // 注册用户，以便密码更新测试
        int userId = UserDAO.registerUser(testUser);
        testUser.setId(userId);

        // 更新密码
        boolean isUpdated = UserDAO.updatePassword(userId, "123");

        // 验证密码是否成功更新
        assertTrue("Password should be updated successfully", isUpdated);
        // 尝试使用新密码登录
        User loggedInUser = UserDAO.loginUser(userId, "123");
        assertNotNull("User should be able to log in with the new password", loggedInUser);
    }

}
