package com.tests;

import com.recipeportal.TestClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestClassTests {

    TestClass testClass;

    @Before
    public void initializeTestClassForEachTest() {
        testClass = new TestClass();
    }

    @Test
    public void testAddSuccess() {
        for(int a = -10; a < 11; a++) {
            for(int b = -10; b < 11; b++) {
                Assert.assertEquals(a + b, testClass.add(a, b));
            }
        }
    }

    @Test
    public void testAddFail() {
        for(int a = -10; a < 11; a++) {
            for(int b = -10; b < 11; b++) {
                Assert.assertEquals(a + b + 5, testClass.add(a, b));
            }
        }
    }

}
