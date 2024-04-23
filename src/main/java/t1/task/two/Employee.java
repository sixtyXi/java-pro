package t1.task.two;

public class Employee {
    public String name;
    public int age;
    public String title;

    public Employee(String name, int age, String title) {
        this.name = name;
        this.age = age;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", title='" + title + '\'' +
                '}';
    }
}
