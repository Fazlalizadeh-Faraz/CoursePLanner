package ca.course.model.Graphing;

public class Graph implements Comparable<Graph> {
    private String semesterCode;
    private int totalCoursesTaken;

    public Graph() {
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
    }

    public int getTotalCoursesTaken() {
        return totalCoursesTaken;
    }

    public void setTotalCoursesTaken(int totalCoursesTaken) {
        this.totalCoursesTaken = totalCoursesTaken;
    }

    @Override
    public int compareTo(Graph o) {
        if (Integer.parseInt(this.getSemesterCode()) > Integer.parseInt(o.getSemesterCode())) {
            return 1;
        }
        return -1;
    }
}
