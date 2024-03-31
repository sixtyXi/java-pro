package t1.task.one;

import t1.task.one.annotations.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    static void runTests(Class c) throws Exception {
        List<Method> testMethods = new ArrayList<Method>();
        List<Method> beforeTestMethods = new ArrayList<Method>();
        List<Method> afterTestMethods = new ArrayList<Method>();
        Method beforeSuite = null;
        Method afterSuite = null;

        for (Method declaredMethod : c.getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(Test.class)) {
                if (Modifier.isStatic(declaredMethod.getModifiers())) {
                    throw new Exception("Аннотация @Test не может использоваться для статических методов");
                }
                int priority = declaredMethod.getAnnotation(Test.class).priority();

                if (priority > 10 || priority < 1) {
                    throw new Exception("Параметр priority для аннотации @Test может принимать значения от 1 до 10 включительно");
                }

                testMethods.add(declaredMethod);
            }

            if (declaredMethod.isAnnotationPresent(BeforeTest.class)) {
                beforeTestMethods.add(declaredMethod);
            }

            if (declaredMethod.isAnnotationPresent(AfterTest.class)) {
                afterTestMethods.add(declaredMethod);
            }

            if (declaredMethod.isAnnotationPresent(BeforeSuite.class)) {
                if (!Modifier.isStatic(declaredMethod.getModifiers())) {
                    throw new Exception("Аннотация @BeforeSuite может использоваться только со статическими методами");
                }

                if (beforeSuite != null) {
                    throw new Exception("Методов с аннотациями @BeforeSuite должно быть не больше одного");
                }

                beforeSuite = declaredMethod;
            }

            if (declaredMethod.isAnnotationPresent(AfterSuite.class)) {
                if (!Modifier.isStatic(declaredMethod.getModifiers())) {
                    throw new Exception("Аннотация @AfterSuite может использоваться только со статическими методами");
                }

                if (afterSuite != null) {
                    throw new Exception("Методов с аннотациями @AfterSuite должно быть не больше одного");
                }

                afterSuite = declaredMethod;
            }
        }

        Object o = c.getDeclaredConstructor().newInstance();

        if (beforeSuite != null) {
            beforeSuite.invoke(o);
        }


        testMethods.sort((m1, m2) -> m2.getAnnotation(Test.class).priority() - m1.getAnnotation(Test.class).priority());


        for (Method testMethod : testMethods) {
            for (Method beforeTestMethod : beforeTestMethods) {
                beforeTestMethod.invoke(o);
            }

            if (testMethod.isAnnotationPresent(CsvSource.class)) {
                runCsvMethod(testMethod);
            } else {
                testMethod.invoke(o);
            }

            System.out.println("priority: " + testMethod.getAnnotation(Test.class).priority());
            for (Method afterTestMethod : afterTestMethods) {
                afterTestMethod.invoke(o);
            }
        }


        if (afterSuite != null) {
            afterSuite.invoke(o);
        }

    }

    private static void runCsvMethod(Method declaredMethod) throws Exception {
        String[] rawArgs = declaredMethod.getAnnotation(CsvSource.class).value().split(",");


        Class[] parameterTypes = declaredMethod.getParameterTypes();

        if (parameterTypes.length != rawArgs.length) {
            throw new Exception("исправьте value для аннотации @CsvSource");
        }

        List args = new ArrayList<>();

        for (int i = 0; i < parameterTypes.length; i++) {
            String typeName = parameterTypes[i].getTypeName();

            String trimmedArg = rawArgs[i].trim();


            try {
                switch (typeName) {
                    case "int" -> args.add(Integer.parseInt(trimmedArg));
                    case "boolean" -> args.add(trimmedArg.equals("true"));
                    case "java.lang.String" -> args.add(trimmedArg);
                    default -> throw new Exception("Не поддерживаемый тип");
                }

            } catch (Exception e) {
                throw e;
            }
        }


        Object o = declaredMethod.getDeclaringClass().getDeclaredConstructor().newInstance();
        declaredMethod.invoke(o, args.get(0), args.get(1), args.get(2), args.get(3));
    }
}
