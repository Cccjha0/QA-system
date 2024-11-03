/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package GUI;

import backend.User;
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
public class CenterFrameTest {

    private User testUser;
    private User testUser2;

    public CenterFrameTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        testUser = new User("Wang", "123", true);
        testUser2 = new User("Lucas", "123", true);
    }

    @After
    public void tearDown() {
        if (CenterFrame.getInstance(testUser) != null) {
            CenterFrame.getInstance(testUser).dispose();
        }
        if (CenterFrame.getInstance(testUser2) != null) {
            CenterFrame.getInstance(testUser2).dispose();
        }
    }

    /**
     * Test of getInstance method, of class CenterFrame.
     */
    @Test
    public void testGetInstance() {
        System.out.println("Testing getInstance");

        CenterFrame instance1 = CenterFrame.getInstance(testUser);
        assertNotNull("CenterFrame instance should not be null", instance1);

        // 再次调用 getInstance，检查是否返回同一个实例
        CenterFrame instance2 = CenterFrame.getInstance(testUser);
        assertSame("getInstance should return the same instance", instance1, instance2);

        // 关闭窗口以触发 windowClosed 事件，并将 instance 重置为 null
        instance1.dispose();
        try {
            Thread.sleep(100); // 等待短暂时间，确保窗口关闭事件已处理
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 确保实例已被重置
        CenterFrame instance3 = CenterFrame.getInstance(testUser2);
        assertNotSame("After window is closed, a new instance should be created", instance1, instance3);
    }

}
