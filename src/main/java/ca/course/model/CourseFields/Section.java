package ca.course.model.CourseFields;

import ca.course.model.Course;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Section {
    private Enrollment enrollment;
    private ComponentCode componentCode;
    private Semester semesterCode;
    private Course course;
    private ArrayList<Section> allSections = new ArrayList<>();


    public Section() {
    }

    public Section(Course course) {
        this.course = course;
        getSection();

    }

    public void setAllSections(ArrayList<Section> allSections) {
        this.allSections = allSections;
    }

    public void setSemesterCode(Semester semesterCode) {
        this.semesterCode = semesterCode;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public void setComponentCode(ComponentCode componentCode) {
        this.componentCode = componentCode;
    }


    @JsonIgnore
    public Enrollment getEnrollment() {
        return enrollment;
    }

    @JsonIgnore
    public void getSection() {

        for (int i = 0; i < course.getDiffSections().size(); i++) {
            Section s = new Section();
            s.setComponentCode(course.getDiffSections().get(i));
            s.setEnrollment(course.getAllEnrollments().get(i));
            if (!allSections.contains(s)) {

                allSections.add(s);
            }

        }


    }

    @JsonIgnore
    public Semester getSemesterCode() {
        return semesterCode;
    }

    @JsonIgnore
    public ComponentCode getComponentCode() {
        return componentCode;
    }

    @JsonIgnore
    public ArrayList<Section> getAllSections() {
        return allSections;
    }


    @JsonIgnore
    @Override
    public String toString() {
        return "Section{" +
                "enrollment=" + enrollment +
                ", componentCode=" + componentCode +
                ", semesterCode=" + semesterCode +
                '}';
    }

    /*
     * for UI in order to print
     * */
    public String getType() {
        return this.componentCode.getType();
    }

    public int getEnrollmentCap() {
        return this.enrollment.getEnrollmentCap();
    }

    public int getEnrollmentTotal() {
        return this.enrollment.getEnrollmentTotal();
    }

}
