import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public List<Student> createRandomStudents() {
        return null;
    }

    public static void main(String[] args) {

        DGES dges = new DGES();

        List<Exam> exams = new ArrayList<>();
        exams.add(new Exam(1, "Matemática", 2023));
        exams.add(new Exam(2, "Português", 2023));
        exams.add(new Exam(3, "Inglês", 2023));
        exams.add(new Exam(4, "Desenho", 2023));
        exams.add(new Exam(5, "História", 2023));
        exams.add(new Exam(6, "Geografia", 2023));
        exams.add(new Exam(7, "Biologia", 2023));
        exams.add(new Exam(8, "Física e Química", 2023));
        exams.add(new Exam(9, "Filosofia", 2023));
        exams.add(new Exam(10, "Economia", 2023));
        exams.add(new Exam (11, "Geometria Descritiva", 2023));

        dges.addExams(exams);

        ExamSet examSet1 = new ExamSet(new ArrayList<>(List.of(exams.get(0))), 1);
        ExamSet examSet2 = new ExamSet(new ArrayList<>(List.of(exams.get(0), exams.get(7))), 2);
        ExamSet examSet3 = new ExamSet(new ArrayList<>(List.of(exams.get(7))), 3);
        ExamSet examSet4 = new ExamSet(new ArrayList<>(List.of(exams.get(10), exams.get(1))), 4);


        dges.addExamSets(new ArrayList<>(List.of(examSet1, examSet2, examSet3, examSet4)));

        List<ExamSet> list1 = new ArrayList<>();
        list1.add(examSet1);
        list1.add(examSet2);

        List<ExamSet> list2 = new ArrayList<>();
        list2.add(examSet3);
        list2.add(examSet4);

        List<ExamSet> list3 = new ArrayList<>();
        list3.add(examSet1);
        list3.add(examSet3);

        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Engenharia Informática", 1, 120, list1, "Porto"));
        courses.add(new Course("Engenharia Civil", 2, 100, list1, "Braga"));
        courses.add(new Course("Engenharia Mecânica", 3, 100, list1, "Porto"));
        courses.add(new Course("Engenharia Biomédica", 5, 100, list1, "Porto"));
        courses.add(new Course("Engenharia Física", 6, 70, list2, "Braga"));
        courses.add(new Course("Engenharia Química", 7, 40, list3, "Porto"));
        courses.add(new Course("Arquitetura", 8, 90, list3, "Porto"));

        dges.addCourses(courses);

        dges.generateRandomStudents(700);
        dges.executeFirstPhase();
        System.out.println(dges.getRandomCourseString());
        System.out.println(dges.getRandomStudentString());


    }
}