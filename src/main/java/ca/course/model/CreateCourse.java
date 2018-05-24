package ca.course.model;

/**
 * this class is constructed when a new course is added
 */
public class CreateCourse {
    /*
    *   "semester": 1191,
        "subjectName": "CMPT",
        "catalogNumber": 213,
        "location": "SURREY",
        "enrollmentCap": 90,
        "component": "LEC",
        "enrollmentTotal": 89,
        "instructor": "Brian Fraser"
    }*/
    private int semester;
    private int catalogNumber;
    private int enrollmentCap;
    private int enrollmentTotal;
    private String subjectName;
    private String location;
    private String component;
    private String instructor;

    @Override
    public String toString() {
        return "CreateCourse{" +
                "semester=" + semester +
                ", subjectName='" + subjectName + '\'' +
                ", catalogNumber=" + catalogNumber +
                ", location='" + location + '\'' +
                ", enrollmentCap=" + enrollmentCap +
                ", enrollmentTotal=" + enrollmentTotal +
                ", component='" + component + '\'' +
                ", instructor='" + instructor + '\'' +
                '}';
    }

    public CreateCourse() {
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(int catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
