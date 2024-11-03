/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package backend;

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
public class QADAOTest {

    public QADAOTest() {
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
    }

    /**
     * Test of insertQA method, of class QADAO.
     */
    @Test
    public void testInsertQA1() {
        System.out.println("Testing insertQA");
        
        // 创建一个新的QA对象
        QA qa = new QA("How many colors are there in a rainbow?", "There are seven colors in a rainbow: red, orange, yellow, green, blue, indigo, and violet.", 1);
        
        boolean result = QADAO.insertQA(qa);
        assertTrue("The QA entry should be inserted successfully", result);
    }
    

    /**
     * Test of searchQA method, of class QADAO.
     */
    @Test
    public void testSearchQA() {
        System.out.println("Testing searchQA");

        // 执行搜索并检查结果
        QA[] results = QADAO.searchQA(1, "Lisa");

        // 验证查询结果非空且包含预期的QA对象
        assertNotNull("Search results should not be null", results);
        assertTrue("At least one result should be returned", results.length > 0);	

        // 检查第一个结果的内容是否正确
        assertEquals("The question text should match", "Who painted the Mona Lisa?", results[0].getQuestion());
        assertEquals("The answer text should match", "The Mona Lisa was painted by Leonardo da Vinci.", results[0].getAnswer());
    }

}
