package ca.course.PrintAndRead;

import ca.course.model.Course;
import ca.course.model.CourseFields.Department;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * this class extracts a list of all the departmetn within the list
 */
public class ExtractDepartment {
    ArrayList<Department> allDepartmnets = new ArrayList<>();
    ArrayList<Course> allCourses = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger();
    private ArrayList<ExtractCourseNumber> cN;

    public ExtractDepartment(ArrayList<Course> givenCourses) {
        this.allCourses = givenCourses;
        findAllDepartmetns();
    }

    public void findAllDepartmetns() {
        for (Course course : allCourses) {
            if (!contains(allDepartmnets, course.getDepartmentObj())) {
                Department newDep = course.getDepartmentObj();
                newDep.setDeptId(idCounter.getAndIncrement());

                allDepartmnets.add(newDep);
            }
        }
        distributeId();
    }

    private void distributeId() {
        for (Department d : allDepartmnets) {
            for (Course c : allCourses) {
                if (c.getDepartmentObj().getName().equals(d.getName())) {
                    c.getDepartmentObj().setDeptId(d.getDeptId());

                }
            }
        }
    }


    public boolean contains(ArrayList<Department> givenList, Department givenDep) {

        for (Department department : givenList) {


            if (department.equals(givenDep.getName())) {
                return true;
            }

        }
        return false;


    }

    public AtomicInteger getIdCounter() {
        return idCounter;
    }

    public ArrayList<Department> getAllDepartmnets() {

        Collections.sort(allDepartmnets);
        return allDepartmnets;
    }


    @Override
    public String toString() {
        return "ExtractDepartment{" +
                "allDepartmnets=" + allDepartmnets +
                '}';
    }
}
