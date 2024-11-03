/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package backend;

import java.util.List;
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
public class RecentQueriesDAOTest {

    private int testUserId;

    public RecentQueriesDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        testUserId = 3;
        try {
            RecentQueriesDAO.getRecentQueries(testUserId).clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try {
            RecentQueriesDAO.getRecentQueries(testUserId).clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DatabaseConnection.shutdown();
    }

    /**
     * Test of saveOrUpdateQuery method, of class RecentQueriesDAO.
     */
    @Test
    public void testSaveOrUpdateQuery() {
        System.out.println("Testing saveOrUpdateQuery");

        String queryText = "Lisa";
        RecentQueriesDAO.saveOrUpdateQuery(testUserId, queryText);

        List<String> recentQueries = RecentQueriesDAO.getRecentQueries(testUserId);
        assertTrue("Recent queries should contain the saved query", recentQueries.contains(queryText));

        RecentQueriesDAO.saveOrUpdateQuery(testUserId, queryText);

        recentQueries = RecentQueriesDAO.getRecentQueries(testUserId);
        assertTrue("Updated query should still be in recent queries", recentQueries.contains(queryText));
    }

    /**
     * Test of getRecentQueries method, of class RecentQueriesDAO.
     */
    @Test
    public void testGetRecentQueries() {
        System.out.println("Testing getRecentQueries");

        // 插入超过10条查询记录
        for (int i = 1; i <= 12; i++) {
            RecentQueriesDAO.saveOrUpdateQuery(testUserId, "Query " + i);
        }

        // 获取最近10条查询
        List<String> recentQueries = RecentQueriesDAO.getRecentQueries(testUserId);

        // 验证检索到的查询数量为10
        assertEquals("Recent queries should contain 10 entries", 10, recentQueries.size());

        // 验证最早插入的2条记录被删除，剩余的是最后的10条
        assertFalse("Oldest queries should be removed", recentQueries.contains("Query 1"));
        assertFalse("Oldest queries should be removed", recentQueries.contains("Query 2"));
        assertTrue("Recent queries should contain the latest query", recentQueries.contains("Query 12"));

    }
}
