package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        // JUnit 5 不再需要构造函数传入测试名称
    }

    /**
     * @return the suite of tests being tested
     */
    public static void initTestSuite()
    {
        // JUnit 5 不再需要手动定义测试套件
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void testApp()
    {
        assertTrue(true);
    }
}
