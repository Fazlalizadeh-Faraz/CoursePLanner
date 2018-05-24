package ca.course.model;


import ca.course.model.CourseFields.*;
import ca.course.model.ObserverPattern.ObservableInterface;
import ca.course.model.ObserverPattern.Watchers;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;


/**
 * this class is basically each line of the data given
 * store each line of data as a Course
 */

public class Course implements Comparable<Course>, ObservableInterface {

    private int courseOfferingId;

    private Department department;
    private CatalogNumber catalogNumber;
    private Enrollment enrollment;
    private Location location;
    private Instructor instructors;
    private Semester semester;
    private ComponentCode componentCode;
    private Enrollment LastChanged;
    private ComponentCode lastChangedComp;
    private ArrayList<ComponentCode> diffSections = new ArrayList<>();
    private ArrayList<Enrollment> enrollments = new ArrayList<>();
    private List<Watchers> watchers = new ArrayList<>();

    public Course() {
    }


    public void setCourseOfferingId(int courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public boolean isEquals(Course obj) {
        boolean isSameLocation = this.location.getLocation().equals(obj.getLocationObj().getLocation());
        boolean isSameSem = this.semester.getSemCode().equals(obj.getSemesterObj().getSemCode());
        boolean isSameComponent = this.componentCode.getType().equals(obj.getComponentCodeObj().getType());
        boolean isSameCatalogNum = this.catalogNumber.getCatalogNumber().equals(obj.getCatalogNumberObj().getCatalogNumber());
        boolean isSameDepartment = this.department.getName().endsWith(obj.getDepartmentObj().getName());

        if (isSameLocation && isSameSem && isSameComponent && isSameCatalogNum && isSameDepartment) {
            return true;
        }
        return false;

    }

    public void updateSpecificEnrollmetn(int index, Enrollment newEnroll) {
        enrollments.remove(index);
        enrollments.add(index, newEnroll);
    }

    public void appendNewSection(Enrollment e) {
        LastChanged = e;
        enrollments.add(e);
    }

    public void appendNewComponent(ComponentCode s) {
        lastChangedComp = s;
        diffSections.add(s);
    }

    public void setLastChangedComp(ComponentCode lastChangedComp) {
        this.lastChangedComp = lastChangedComp;
    }

    public void setDiffSections(ArrayList<ComponentCode> diffSections) {
        this.diffSections = diffSections;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setLastChanged(Enrollment lastChanged) {
        LastChanged = lastChanged;
    }

    public void setEnrollments(ArrayList<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void setCatalogNumber(CatalogNumber catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setInstructors(Instructor instructors) {
        this.instructors = instructors;
    }

    public void addInstructor(Instructor instructor) {
        this.instructors.setInstructors(instructor.getArrayOfInstructors());
    }

    public void setComponentCode(ComponentCode componentCode) {
        this.componentCode = componentCode;
    }


    @JsonIgnore
    public ArrayList<ComponentCode> getDiffSections() {
        return diffSections;
    }

    @JsonIgnore
    public ComponentCode getLastChangedComp() {
        return lastChangedComp;
    }

    @JsonIgnore
    public ArrayList<Enrollment> getAllEnrollments() {
        return enrollments;
    }

    @JsonIgnore
    public Enrollment lastChanged() {
        return LastChanged;
    }

    @JsonIgnore
    public int positionOfComponent(ComponentCode componentCode) {
        for (int i = 0; i < diffSections.size(); i++) {
            if (componentCode.getType().equals(diffSections.get(i).getType())) {
                return i;
            }
        }
        return -1;
    }

    @JsonIgnore
    public Department getDepartmentObj() {
        return department;
    }

    @JsonIgnore
    public CatalogNumber getCatalogNumberObj() {
        return catalogNumber;
    }

    @JsonIgnore
    public Enrollment getEnrollmentObj() {
        return enrollment;
    }

    @JsonIgnore
    public Section getSpecificSection(String section) {
        Section s = new Section();
        for (int i = 0; i < diffSections.size(); i++) {
            if (diffSections.get(i).getType().equals(section)) {
                s.setComponentCode(diffSections.get(i));
                s.setEnrollment(enrollments.get(i));
                s.setSemesterCode(semester);
            }
        }
        return s;

    }

    @JsonIgnore
    public Location getLocationObj() {
        return location;
    }

    @JsonIgnore
    public Instructor getInstructorObj() {
        return instructors;
    }

    @JsonIgnore
    public Semester getSemesterObj() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    @JsonIgnore
    public ComponentCode getComponentCodeObj() {
        return componentCode;
    }

    @Override
    public int compareTo(Course o) {
        return this.getSemesterObj().getSemCode().compareTo(o.getSemesterObj().getSemCode());
    }

    @JsonIgnore
    public List<Watchers> getWatchers() {
        return watchers;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseOfferingId=" + courseOfferingId +
                ", department=" + department +
                ", catalogNumber=" + catalogNumber +
                ", enrollment=" + enrollment +
                ", location=" + location +
                ", instructors=" + instructors +
                ", semester=" + semester +
                ", componentCode=" + componentCode +
                ", diffSections=" + diffSections +
                ", enrollments=" + enrollments +
                '}';
    }

    /*
    * for observable pattern
    * */
    @JsonIgnore
    @Override
    public boolean addObserver(Watchers observer) {
        watchers.add(observer);
        return false;
    }

    @JsonIgnore
    @Override
    public boolean removeObserver(Watchers observer) {
        watchers.remove(observer);
        return false;
    }

    @JsonIgnore
    @Override
    public void postNotification() {

        for (Watchers o : watchers) {
            System.out.println("updating");
            o.update(this);
        }

    }


    /*
     * for UI in order to print
     * */
    public int getCourseOfferingId() {
        return courseOfferingId;
    }

    public String getCatalogNumber() {
        return this.catalogNumber.getCatalogNumber();
    }

    public int getCourseId() {
        return this.catalogNumber.getCourseId();
    }

    public String getLocation() {
        return this.location.getLocation();
    }

    public String getInstructors() {
        return this.instructors.getInstructors();
    }

    public String getTerm() {
        return this.semester.getTerm();
    }

    public int getSemesterCode() {
        return this.semester.getSemesterCode();
    }

    public int getYear() {
        return this.semester.getYear();
    }


}
