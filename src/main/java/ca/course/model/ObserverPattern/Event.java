package ca.course.model.ObserverPattern;

import ca.course.model.CourseFields.CatalogNumber;
import ca.course.model.Course;
import ca.course.model.CourseFields.Department;
import ca.course.model.CourseFields.Enrollment;

import java.util.Date;

/**
 * this class is created whenever a change has occurred to an observable class
 */
public class Event {
    Course course;
    Department department;
    CatalogNumber catalogNumber;
    Enrollment oldEnrolls;
    String event;
    Date date;

    public Event(Department department, Course course) {
        this.department = department;
        this.course = course;
        date = new Date();
        oldEnrolls = course.getEnrollmentObj();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public CatalogNumber getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(CatalogNumber catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return date.toString() +
                " Added section: " +
                course.getLastChangedComp().getType() +
                " with" +
                " enrollment" +
                " (" +
                course.lastChanged().getEnrollmentTotal() +
                "/ " +
                course.lastChanged().getEnrollmentCap() +
                ")\n" +
                "to offering" +
                course.getSemesterObj().toString() +
                "\n";
    }
}
