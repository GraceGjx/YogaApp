package jiaxuan2.yoga.BaseClass;

/**
 * Created by jiaxuan2 on 4/18/17.
 */

public class PracticeData {
    private String date;
    private double practiceTime;

    public PracticeData() {
    }

    public PracticeData(String date, double practiceTime) {
        this.date = date;
        this.practiceTime = practiceTime;
    }

    public String getDate() {
        return date;
    }

    public double getPracticeTime() {
        return practiceTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PracticeData) {
            PracticeData data = (PracticeData) obj;
            return date.equals(data.date) && practiceTime == data.practiceTime;
        }
        return false;
    }

    @Override
    public String toString() {
        return "PracticeData{" +
                "date='" + date + '\'' +
                ", practiceTime=" + practiceTime +
                '}';
    }

}
