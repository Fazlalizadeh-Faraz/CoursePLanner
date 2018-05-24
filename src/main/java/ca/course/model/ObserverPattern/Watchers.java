package ca.course.model.ObserverPattern;


import ca.course.model.CourseFields.CatalogNumber;
import ca.course.model.Course;
import ca.course.model.CourseFields.Department;
import ca.course.model.watcherCourse;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * this class watches specific course
 * a.k.a the Observer
 */
public class Watchers implements Observer {

    private int id;
    private Department department;
    private CatalogNumber catalogNumber;
    private Course course;
    List<String> events = new ArrayList<>();


    public Watchers() {
    }

    public Watchers(Course c, int id) {
        this.id = id;
        this.course = c;
        this.department = c.getDepartmentObj();
        this.catalogNumber = c.getCatalogNumberObj();
    }

    public List<String> getEvents() {
        return events;
    }

    public void setCatalogNumber(CatalogNumber catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @JsonIgnore
    public Course getCourseObj() {
        return course;
    }

    @JsonIgnore
    public CatalogNumber getCatalogNumberObj() {
        return catalogNumber;
    }

    @Override
    public void update(Course c) {

        Event newEvent = new Event(c.getDepartmentObj(), c);

        System.out.println(c);
        events.add(newEvent.toString());
    }


    /*
     * for UI in order to print
     * */
    public int getId() {
        return id;
    }

    public Department getDepartment() {
        return department;
    }

    public watcherCourse getWatcherCourse() {
        return new watcherCourse() {
            @Override
            public String getCatalogNumber() {
                return course.getCatalogNumberObj().getCatalogNumber();
            }

            @Override
            public int getCourseId() {
                return course.getCatalogNumberObj().getCourseId();
            }


        };
    }


}
