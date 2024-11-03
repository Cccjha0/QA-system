/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package GUI;

import backend.QA;
import backend.User;
import javax.swing.JComboBox;
import javax.swing.JPanel;
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
public class StudentFrameTest {

    private StudentFrame studentFrame;
    private User user;

    public StudentFrameTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        user = new User("Peter", "123", true);
        user.setId(2);
        studentFrame = new StudentFrame(user);
    }

    @After
    public void tearDown() {
        studentFrame.dispose(); // 关闭窗口以释放资源
    }

    /**
     * Test of initializeFrameComponents method, of class StudentFrame.
     */
    @Test
    public void testInitializeFrameComponents() {
        studentFrame.initializeFrameComponents();
        // 验证帧标题是否正确
        assertEquals("Students QA_System", studentFrame.getTitle());
        assertNotNull(studentFrame.mainPanel.getComponent(0)); 
        assertNotNull(studentFrame.mainPanel.getComponent(1)); 
    }

    /**
     * Test of initializeQueryPanel method, of class StudentFrame.
     */
    @Test
    public void testInitializeQueryPanel() {
        JPanel queryPanel = new JPanel(); // 创建一个新的 JPanel
        studentFrame.initializeQueryPanel(queryPanel);

        // 验证查询面板中是否包含特定组件
<<<<<<< HEAD
        assertNotNull(queryPanel.getComponent(0));
        assertNotNull(queryPanel.getComponent(1)); 
=======
        assertNotNull(queryPanel.getComponent(0)); 
        assertNotNull(queryPanel.getComponent(1));
>>>>>>> a26087a0a8d848afa9454a7266dfaf0cc2a9e1eb
        assertNotNull(queryPanel.getComponent(2)); 
        assertNotNull(queryPanel.getComponent(3)); 
        assertNotNull(queryPanel.getComponent(4)); 
    }

    /**
     * Test of handleRecentQuery method, of class StudentFrame.
     */
    @Test
    public void testHandleRecentQuery() {
        JComboBox<String> questionComboBox = new JComboBox<>(new String[]{"What", "Lisa"});
        questionComboBox.setSelectedItem("Lisa");
        studentFrame.handleRecentQuery(questionComboBox);
        // 验证 qArea 中是否设置了所选的内容 但是查询后会自动清空qArea
        assertEquals("", studentFrame.qArea.getText());
    }

    /**
     * Test of searchable method, of class StudentFrame.
     */
    @Test
    public void testSearchable() {
        int userid = 2; 
        String keyword = "Lisa";
        
        QA qa = new QA("Who painted the Mona Lisa?", "The Mona Lisa was painted by Leonardo da Vinci.", 1);       
        QA[] expResult = { qa }; // 预期结果
        String expQuestion = expResult[0].getQuestion();
        String expAnswer   = expResult[0].getAnswer();
        
        QA[] result = studentFrame.searchable(userid, keyword); 
        
        // 验证结果是否匹配
        assertEquals(expQuestion , result[0].getQuestion());
        assertEquals(expAnswer, result[0].getAnswer());
    }


}
