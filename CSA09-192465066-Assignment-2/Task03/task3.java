class Student {
    String regNo;
    String name;

    Student(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return regNo.equals(other.regNo);
    }

    @Override
    public int hashCode() {
        return regNo.hashCode();
    }

    @Override
    public String toString() {
        return regNo + " " + name;
    }
}

public class task3 {
    public static void main(String[] args) {
        java.util.Set<Student> present = new java.util.HashSet<Student>();

        present.add(new Student("192311001", "Arun"));
        present.add(new Student("192311002", "Divya"));
        present.add(new Student("192311001", "Arun"));

        System.out.println("Unique students: " + present.size());

        java.util.Set<Student> sortedStudents = new java.util.TreeSet<Student>(new java.util.Comparator<Student>() {
            public int compare(Student first, Student second) {
                return first.regNo.compareTo(second.regNo);
            }
        });

        sortedStudents.add(new Student("192311002", "Divya"));
        sortedStudents.add(new Student("192311001", "Arun"));
        sortedStudents.add(new Student("192311001", "Arun"));

        System.out.println("TreeSet students: " + sortedStudents);
    }
}