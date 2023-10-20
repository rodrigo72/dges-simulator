import java.util.ArrayList;
import java.util.List;

public class Course implements HasCode {
    private final String name;
    private final int code;
    private final int maxStudents;
    private final List<Student> students;
    private final List<ExamSet> exams;
    private final String district;
    private final int internalEvaluationWeight = 50;
    private final int examWeight = 50;

    public Course(String name, int code, int maxStudents, List<ExamSet> exams, String district) {
        this.name = name;
        this.code = code;
        this.maxStudents = maxStudents;
        this.district = district;
        this.students = new ArrayList<>();
        this.exams = exams;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public List<ExamSet> getExamSets() {
        return exams;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void addStudent(Student student) {
        if (students.size() >= maxStudents) {
            throw new IllegalArgumentException("Course is full");
        } else {
            students.add(student);
        }
    }

    public int getNumberOfStudents() {
        return students.size();
    }

    public String toString() {
        return "Course{" + "name=" + name + ", code=" + code + ", enrolled: " + this.getNumberOfStudents() +  ", maxStudents=" + maxStudents + ",\nstudents=" + students + ", exams=" + exams + ", district=" + district + "}\n";
    }

    public int getInternalWeight() {
        return internalEvaluationWeight;
    }

    public int getExamWeight() {
        return examWeight;
    }
}
