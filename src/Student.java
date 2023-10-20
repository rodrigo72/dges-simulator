import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Student implements HasCode {
    private final String name;
    private final int NIF;
    private final String district;
    private final float internalEvaluation;
    private final Map<Integer, Float> examGrades;
    private final List<Integer> orderOfPreferredCourses;
    private int courseCode = -1;

    private float finalGrade = 0;
    private boolean isEnrolled = false;
    private boolean finalGradeCalculated = false;

    public Student(String name, int NIF, float internalEvaluation, Map<Integer, Float> examGrades, List<Integer> orderOfPreferredCourses, String district) {
        this.name = name;
        this.NIF = NIF;
        this.internalEvaluation = internalEvaluation;
        this.examGrades = examGrades;
        this.orderOfPreferredCourses = orderOfPreferredCourses;
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return NIF;
    }

    // highest grade possible based on the exam sets of the course
    public float getFinalGrade(int internalEvaluationWeight, int examWeight, List<ExamSet> examSets) {

        if (finalGradeCalculated) {
            return finalGrade;
        }

        boolean flag = false;
        List<Float> possibleGrades = new ArrayList<>();
        for (ExamSet examSet : examSets) {
            List<Exam> exams = examSet.getExams();
            float examGrade = 0;
            for (Exam exam : exams) {
                if (!examGrades.containsKey(exam.getCode())) {
                    flag = true;
                    continue;
                }
                examGrade += examGrades.get(exam.getCode());
            }
            if (flag) {
                flag = false;
                continue;
            }
            examGrade /= exams.size();
            possibleGrades.add(examGrade);
        }

        float finalGrade = 0;
        for (float grade : possibleGrades) {
            if (grade > finalGrade) {
                finalGrade = grade;
            }
        }

        this.finalGrade = finalGrade * (examWeight/100f) + this.internalEvaluation * (internalEvaluationWeight/100f);
        this.finalGradeCalculated = true;
        return this.finalGrade;
    }


    public List<Integer> getOrderOfPreferredCourses() {
        return orderOfPreferredCourses;
    }

    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", NIF=" + NIF +
                ", orderOfPreferredCourses=" + orderOfPreferredCourses +
                ", courseCode=" + courseCode +
                ", finalGrade=" + finalGrade +
                ", isEnrolled=" + isEnrolled +
                ", internalEvaluation=" + internalEvaluation +
                ", examGrades=" + examGrades +
                "}\n";
    }

    public void setCourse(int courseCode) {
        this.courseCode = courseCode;
        this.isEnrolled = true;
    }

    public boolean isEnrolled() {
        return isEnrolled;
    }

}
