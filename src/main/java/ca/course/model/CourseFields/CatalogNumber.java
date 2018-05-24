package ca.course.model.CourseFields;

import ca.course.model.ObserverPattern.Watchers;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;


/**
 * this class stores the course number such as cmpt ->213<-
 */
public class CatalogNumber implements Comparable<CatalogNumber> {
    private String catalogNumber = "";
    private int courseId;

    public CatalogNumber() {
    }

    public CatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;


    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;

    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;

    }


    @JsonIgnore
    @Override
    public String toString() {
        return "catalogNumber= " + catalogNumber + "courseId= " +
                courseId + '\n';
    }

    @Override
    public int compareTo(CatalogNumber o) {
        return this.getCatalogNumber().compareTo(o.getCatalogNumber());
    }


}
