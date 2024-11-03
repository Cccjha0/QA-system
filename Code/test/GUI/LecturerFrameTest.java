/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package GUI;

import backend.User;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
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
public class LecturerFrameTest {

    private LecturerFrame lecturerFrame; // 用于测试的 LecturerFrame 实例
    private User user; // 测试用户实例

    public LecturerFrameTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        user = new User("lucas", "123", false);
        user.setId(3);
        lecturerFrame = new LecturerFrame(user);
        lecturerFrame.initializeFrameComponents();
    }

    @After
    public void tearDown() {
        lecturerFrame.dispose(); // 关闭窗口以释放资源
    }

    /**
     * Test of initializeFrameComponents method, of class LecturerFrame.
     */
    @Test
    public void testInitializeFrameComponents() {
        System.out.println("Testing initializeFrameComponents");

        // 验证框架标题是否设置正确
        assertEquals("Lectures QA_System", lecturerFrame.getTitle());
        
        JPanel inputPanel = (JPanel) lecturerFrame.mainPanel.getComponent(1); // 获取 inputPanel
        assertNotNull("Input panel should not be null", inputPanel);
        assertNotNull(lecturerFrame.mainPanel.getComponent(0)); 
        assertNotNull(lecturerFrame.mainPanel.getComponent(1)); 
    }

    /**
     * Test of initializeInputPanelComponents method, of class LecturerFrame.
     */
    @Test
    public void testInitializeInputPanelComponents() {
        System.out.println("Testing initializeInputPanelComponents");

        // 创建一个新的面板用于测试
        JPanel inputPanel = new JPanel();
        lecturerFrame.initializeInputPanelComponents(inputPanel); // 初始化输入面板组件

        // 检查面板上是否添加了问题输入区域和答案输入区域
        assertEquals(7, inputPanel.getComponentCount()); // 期待有7个组件

        // 检查是否有“Input”按钮
        JButton inputButton = (JButton) inputPanel.getComponent(5);
        assertEquals("Input", inputButton.getText());

        // 检查是否有“Quit”按钮
        JButton quitButton = (JButton) inputPanel.getComponent(6);
        assertEquals("Quit", quitButton.getText());
    }

}
