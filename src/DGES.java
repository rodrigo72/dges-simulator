import java.util.*;

public class DGES {

    record Tuple<A, B>(A first, B second) {
    }
    private final Map<Integer, Course> courses;
    private final Map<Integer, Student> students;
    private final Map<Integer, Exam> exams;
    private final Map<Integer, ExamSet> examSets;

    private final Map<Integer, List<Tuple<Integer, Student>>> courseQueue = new HashMap<>();

    public DGES() {
        this.courses = new HashMap<>();
        this.students = new HashMap<>();
        this.exams = new HashMap<>();
        this.examSets = new HashMap<>();
    }
    public DGES(Map<Integer, Course> courses, Map<Integer, Student> students, Map<Integer, Exam> exams, Map<Integer, ExamSet> examSets) {
        this.courses = courses;
        this.students = students;
        this.exams = exams;
        this.examSets = examSets;
    }

    private <T extends HasCode> void addItem(Map<Integer, T> map, T item, String errorMessage) {
        if (map.containsKey(item.getCode())) {
            throw new IllegalArgumentException(errorMessage + " already exists");
        } else {
            map.put(item.getCode(), item);
        }
    }

    private <T extends HasCode> void addItems(Map<Integer, T> map, List<T> items, String errorMessage) {
        for (T item : items) {
            addItem(map, item, errorMessage);
        }
    }

    public void addCourse(Course course) {
        addItem(this.courses, course, "Course");
    }

    public void addCourses(List<Course> courses) {
        addItems(this.courses, courses, "Course");
    }

    public void addStudent(Student student) {
        addItem(this.students, student, "Student");
    }

    public void addStudents(List<Student> students) {
        addItems(this.students, students, "Student");
    }

    public void addExam(Exam exam) {
        addItem(this.exams, exam, "Exam");
    }

    public void addExams(List<Exam> exams) {
        addItems(this.exams, exams, "Exam");
    }

    public void addExamSet(ExamSet examSet) {
        addItem(this.examSets, examSet, "ExamSet");
    }

    public void addExamSets(List<ExamSet> examSets) {
        addItems(this.examSets, examSets, "ExamSet");
    }

    public void execute() {
        for (Student student : students.values()) {
            int count = 1;
            for (Integer courseCode : student.getOrderOfPreferredCourses()) {
                if (this.courses.containsKey(courseCode)) {
                    if (courseQueue.containsKey(courseCode)) {
                        courseQueue.get(courseCode).add(new Tuple<>(count, student));
                    } else {
                        courseQueue.put(courseCode, new ArrayList<>(List.of(new Tuple<>(count, student))));
                    }
                    count++;
                }
            }
        }

        for (Integer courseCode : courseQueue.keySet()) {
            if (!this.courses.containsKey(courseCode)) {
                throw new IllegalArgumentException("Course with code " + courseCode + " does not exist");
            }
            List<Tuple<Integer, Student>> students = courseQueue.get(courseCode);
            Course course = this.courses.get(courseCode);
            students.sort((a, b) -> {
                float aFinalGrade = a.second.getFinalGrade(course.getInternalWeight(), course.getExamWeight(), this.courses.get(courseCode).getExamSets());
                float bFinalGrade = b.second.getFinalGrade(course.getInternalWeight(), course.getExamWeight(), this.courses.get(courseCode).getExamSets());
                return Float.compare(bFinalGrade, aFinalGrade);
            });
        }

        for (int p = 1; p <= 3; p++) {
            int changes = 0;
            int changesPrev = -1;

            while (changes != changesPrev) {
                System.out.println("Phase " + p + ": " + changes + " changes");
                changesPrev = changes;
                for (Integer courseCode : courseQueue.keySet()) {
                    if (!this.courses.containsKey(courseCode)) {
                        throw new IllegalArgumentException("Course with code " + courseCode + " does not exist");
                    }
                    Course course = this.courses.get(courseCode);
                    int maxStudents = course.getMaxStudents();

                    List<Tuple<Integer, Student>> students = courseQueue.get(courseCode);

                    int j = 0;
                    for (int i = course.getNumberOfStudents(); i < maxStudents; i++) {
                        if (i >= students.size()) {
                            break;
                        }

                        Student student = students.get(j).second;

                        if (!student.isEnrolled() && students.get(j).first == 1) {
                            student.setCourse(courseCode);
                            course.addStudent(student);
                            changesPrev = changes;
                            changes += removeStudentFromQueues(student);
                        }
                        j++;
                    }
                }
            }
        }
    }

    public int removeStudentFromQueues (Student student) {
        int changes = 0;
        for (Integer courseCode : courseQueue.keySet()) {
            List<Tuple<Integer, Student>> students = courseQueue.get(courseCode);
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).second.getCode() == student.getCode()) {
                    students.remove(i);
                    changes++;
                    break;
                }
            }
        }
        return changes;
    }

    public String getRandomStudentString() {
        Random random = new Random();
        int randomNIF = new ArrayList<>(students.keySet()).get(Math.abs(random.nextInt()) % students.size());
        return students.get(randomNIF).toString();
    }

    public String getRandomCourseString() {
        Random random = new Random();
        int randomCode = new ArrayList<>(courses.keySet()).get(Math.abs(random.nextInt()) % courses.size());
        return courses.get(randomCode).toString();
    }

    public void generateRandomStudents(int N) {
        Random random = new Random();

        for (int i = 0; i < N; i++) {
            String studentName = "Student" + i;
            int studentNIF = 100000000 + random.nextInt(900000000);
            String studentDistrict = "District" + i;
            float studentInternalEvaluation = 10.0f + random.nextFloat() * (20.0f - 10.0f);

            List<Integer> forceExams = List.of(1, 8, 11);
            Map<Integer, Float> examGrades = new HashMap<>();
            for (int j = 0; j < random.nextInt() % 4 + 1; j++) {
                examGrades.put(forceExams.get(random.nextInt(forceExams.size())), 10.0f + random.nextFloat() * (20.0f - 10.0f));
            }

            List<Integer> orderOfPreferredCourses = new ArrayList<>(courses.keySet());
            Collections.shuffle(orderOfPreferredCourses);
            orderOfPreferredCourses = orderOfPreferredCourses.subList(0, Math.abs(random.nextInt()) % 5 + 1);

            Student student = new Student(studentName, studentNIF, studentInternalEvaluation, examGrades, orderOfPreferredCourses, studentDistrict);

            students.put(studentNIF, student);
        }
    }
}
