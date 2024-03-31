package t1.task.one;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        try {
            TestRunner.runTests(Testable.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
