package com.recipeportal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClassTests {

    TestClass testClass;

    @BeforeEach
    public void initializeTestClassForEachTest() {
        testClass = new TestClass();
    }

    @Test
    public void testAddSuccess() {
        for(int a = -10; a < 11; a++) {
            for(int b = -10; b < 11; b++) {
                assertEquals(a + b, testClass.add(a, b));
            }
        }
    }

}
