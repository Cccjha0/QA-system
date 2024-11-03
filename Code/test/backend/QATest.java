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
public class QATest {
    
    private QA qaInstance;
    
    public QATest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        qaInstance = new QA("What is the capital of France?", "The capital of France is Paris.", 1);
        qaInstance.setId(1); 
        qaInstance.setCreatedAt("2024-11-03 10:00:00"); 
    }
    
    @After
    public void tearDown() {
        qaInstance = null;
    }

    /**
     * Test of getId method, of class QA.
     */
    @Test
    public void testGetId() {
        int expResult = 1;
        int result = qaInstance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class QA.
     */
    @Test
    public void testSetId() {
        int newId = 2;
        qaInstance.setId(newId);
        assertEquals(newId, qaInstance.getId());
    }

    /**
     * Test of getQuestion method, of class QA.
     */
    @Test
    public void testGetQuestion() {
        String expResult = "What is the capital of France?";
        String result = qaInstance.getQuestion();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAnswer method, of class QA.
     */
    @Test
    public void testGetAnswer() {
        String expResult = "The capital of France is Paris.";
        String result = qaInstance.getAnswer();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCreatedBy method, of class QA.
     */
    @Test
    public void testGetCreatedBy() {
        int expResult = 1;
        int result = qaInstance.getCreatedBy();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCreatedAt method, of class QA.
     */
    @Test
    public void testGetCreatedAt() {
        String expResult = "2024-11-03 10:00:00";
        String result = qaInstance.getCreatedAt();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCreatedAt method, of class QA.
     */
    @Test
    public void testSetCreatedAt() {
        String newTimestamp = "2024-11-04 12:00:00";
        qaInstance.setCreatedAt(newTimestamp);
        assertEquals(newTimestamp, qaInstance.getCreatedAt());
    }
    
}
