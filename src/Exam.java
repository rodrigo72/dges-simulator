public class Exam implements HasCode {
    private final int code;
    private final String name;
    private final int year;

    public Exam(int code, String name, int year) {
        this.code = code;
        this.name = name;
        this.year = year;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Exam{" + "code=" + code + ", name=" + name + ", year=" + year + "}\n";
    }
}
