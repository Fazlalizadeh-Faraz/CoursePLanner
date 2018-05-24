package ca.course.PrintAndRead;

import ca.course.model.CourseFields.CatalogNumber;
import ca.course.model.Course;
import ca.course.model.CourseFields.Department;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * this class extracts all the courses within the given department
 */

public class ExtractCourseNumber {

    ArrayList<CatalogNumber> allNumebrs = new ArrayList<>();
    ArrayList<Course> courses = new ArrayList<>();
    ArrayList<Course> specificDepartment = new ArrayList<>();
    Department givenDepartmetn = new Department();
    private AtomicInteger idCounter = new AtomicInteger();

    public ExtractCourseNumber(ArrayList<Course> courses, Department d) {
        this.courses = courses;
        this.givenDepartmetn = d;
        getAllCourseNumbers();
    }

    public AtomicInteger getIdCounter() {
        return idCounter;
    }

    public void getAllCourseNumbers() {
        for (Course course : courses) {
            if (course.getDepartmentObj().getName().
                    equals(givenDepartmetn.getName())) {
                if (!contains(allNumebrs, course.getCatalogNumberObj())) {
                    course.getCatalogNumberObj().setCourseId(idCounter.getAndIncrement());

                    allNumebrs.add(course.getCatalogNumberObj());
                }
                specificDepartment.add(course);
            }
        }
        distribute();

    }

    private void distribute() {
        for (CatalogNumber catalogNumber : allNumebrs) {
            for (Course c : getSpecificDepartment()) {
                if (c.getCatalogNumberObj().getCatalogNumber().equals(catalogNumber.getCatalogNumber())
                        && c.getCatalogNumberObj().getCourseId() != catalogNumber.getCourseId()) {
                    c.getCatalogNumberObj().setCourseId(catalogNumber.getCourseId());

                }
            }
        }
    }

    public ArrayList<Course> getSpecificDepartment() {
        return specificDepartment;
    }

    public boolean contains(ArrayList<CatalogNumber> givenList, CatalogNumber dep) {

        for (CatalogNumber catalogNumber : givenList) {


            if (catalogNumber.getCatalogNumber().equals(dep.getCatalogNumber())) {


                return true;
            }

        }
        return false;


    }

    public ArrayList<CatalogNumber> getAllNumebrs() {
        Collections.sort(allNumebrs);
        return allNumebrs;
    }

    @Override
    public String toString() {
        return
                "allNumebrs=" + allNumebrs + "\n";
    }
}
