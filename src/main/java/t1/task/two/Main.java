package t1.task.two;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(5, 2, 3);
        List<Employee> employees = Arrays.asList(
                new Employee("Vasya", 20, "developer"),
                new Employee("Petya", 25, "developer"),
                new Employee("Kolya", 30, "lead"),
                new Employee("Fedya", 35, "lead")
        );
        List<String> words = Arrays.asList("word", "cord", "word_word", "word_word_1", "word_word_word");

        System.out.println(getUniq(list));

        System.out.println(getThirdMax(list));

        System.out.println(getThirdMaxUniq(list));

        System.out.println(getThreeOldest(employees));

        System.out.println(getAverageAge(employees));

        System.out.println(getLongestWord(words));

        System.out.println(getWordCountersMap("one two two three three three"));

        System.out.println(getSortedStrings(words));

        System.out.println(getLongestWordFromArray(new String[]{"one two three four five1", "one two three four five2"}));
    }

    static List<?> getUniq(List<?> list) {
        return list.stream().distinct().toList();
    }

    static int getThirdMax(List<Integer> list) {
        if (list.size() < 3) {
           throw new IllegalArgumentException("Длина списка должна быть не меньше 3");
        }
        return list.stream().sorted((prev, next) -> next - prev).toList().get(2);
    }

    static int getThirdMaxUniq(List<Integer> list) {
        if (list.size() < 3) {
            throw new IllegalArgumentException("Длина списка должна быть не меньше 3");
        }
        return list.stream().distinct().sorted((prev, next) -> next - prev).toList().get(2);
    }

    static List<Employee> getThreeOldest(List<Employee> employees) {
        return employees.stream().sorted((prev, next) -> next.age - prev.age).limit(3).toList();
    }

    static double getAverageAge(List<Employee> employees) {
       return employees.stream().mapToDouble(it -> it.age).average().orElse(0.0);
    }

    static String getLongestWord(List<String> words) {
        if (words.isEmpty()) {
            throw new IllegalArgumentException("Длина списка должна быть не меньше 1");
        }
        return words.stream().sorted((prev, next) -> next.length() - prev.length()).toList().get(0);
    }

    static Map<String, Long> getWordCountersMap(String text) {
       return Arrays.stream(text.split(" ")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    static List<String> getSortedStrings(List<String> words) {
        return words.stream().sorted(((prev, next) -> {
            if (prev.length() == next.length()) {
                return prev.compareTo(next);
            } else {
                return prev.length() - next.length();
            }
        })).toList();
    }

    static String getLongestWordFromArray(String[] strings) {
       return Arrays.stream(strings)
               .map(it -> it.split(" "))
               .flatMap(Arrays::stream)
               .sorted((prev, next) -> next.length() - prev.length())
               .toList().get(0);
    }
}
