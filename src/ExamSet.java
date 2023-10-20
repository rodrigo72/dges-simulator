import java.util.List;

public class ExamSet implements HasCode {
    private final List<Exam> exams;
    private final Integer code;

    public ExamSet(List<Exam> exams, Integer code) {
        this.exams = exams;
        this.code = code;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public int getCode() {
        return code;
    }
}
