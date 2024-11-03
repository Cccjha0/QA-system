/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package GUI;

import backend.User;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
public class ResetPasswordFrameTest {
    
    private ResetPasswordFrame resetPasswordFrame;
    
    public ResetPasswordFrameTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        User testUser = new User("Alice", "123", true);
        testUser.setId(1); 
        resetPasswordFrame = new ResetPasswordFrame(testUser);
    }
    
    @After
    public void tearDown() {
        resetPasswordFrame.dispose(); // 关闭窗口以释放资源
    }

    /**
     * Test of initializeFrameComponents method, of class ResetPasswordFrame.
     */
    @Test
    public void testInitializeFrameComponents() {
        assertNotNull(resetPasswordFrame); // 确保resetPasswordFrame实例已初始化     
        
        // 验证标题
        assertEquals("Reset Password", resetPasswordFrame.getTitle());

        // 验证组件是否正确初始化
        assertNotNull(resetPasswordFrame.getContentPane().getComponent(0)); 
        assertTrue(resetPasswordFrame.getContentPane().getComponent(0) instanceof JPanel);

        // 验证密码标签
        JLabel passwordLabel = (JLabel) ((JPanel) resetPasswordFrame.getContentPane().getComponent(0)).getComponent(0);
        assertEquals("New Password:", passwordLabel.getText());

        // 验证密码输入框
        JPasswordField passwordText = (JPasswordField) ((JPanel) resetPasswordFrame.getContentPane().getComponent(0)).getComponent(1);
        assertNotNull(passwordText);

        // 验证重置按钮
        JButton resetButton = (JButton) ((JPanel) resetPasswordFrame.getContentPane().getComponent(0)).getComponent(2);
        assertEquals("Reset", resetButton.getText());
    }
    
}
