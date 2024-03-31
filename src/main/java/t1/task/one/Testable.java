package t1.task.one;

import t1.task.one.annotations.*;

public class Testable {
    @BeforeSuite
    public static void beforeSuite1() {
        System.out.println("beforeSuite1");
    }

    @AfterSuite
    public static void afterSuite1() {
        System.out.println("afterSuite1");
    }

    @BeforeTest
    public void beforeTest1() {
        System.out.println("--------------------------------");
    }

    @AfterTest
    public void afterTest1() {
        System.out.println("--------------------------------");
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test(priority = 1)
    public void test2() {
        System.out.println("test2");
    }

    @Test(priority = 10)
    public void test3() {
        System.out.println("test3");
    }

    @Test
    public void test4() {
        System.out.println("test4");
    }

    public void test5() {
        System.out.println("test5");
    }

    public void test6() {
        System.out.println("test6");
    }

    public void test7() {
        System.out.println("test7");
    }

    @Test
    @CsvSource("10, Java, 20, true")
    public void testMethod(int a, String b, int c, boolean d) {
        System.out.println(a + " " + b + " " + c + " " + d);
    }
}
