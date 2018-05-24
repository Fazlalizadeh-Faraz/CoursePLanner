package ca.course.PrintAndRead;

import ca.course.model.CourseFields.CatalogNumber;
import ca.course.model.Course;
import ca.course.model.CourseFields.Department;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * this class takes care of the combining the same classes that have the same
 * dep...course#...sem...
 */

public class MergeDuplicateCourse {
    ArrayList<Course> sameDepartmentAndCombined = new ArrayList<>();
    ArrayList<Course> allCourses = new ArrayList<>();
    ArrayList<Course> specificCourseNumber = new ArrayList<>();
    Department allDepartments = new Department();
    CatalogNumber courseNum;
    AtomicInteger courseId = new AtomicInteger();

    public MergeDuplicateCourse() {
    }

    public MergeDuplicateCourse(ArrayList<Course> allCourses, Department allDepartments, String c) {
        this.allCourses = allCourses;
        this.allDepartments = allDepartments;
        this.courseNum = new CatalogNumber(c);

        combineSimiliarCourses();
    }

    public void combineSimiliarCourses() {


        for (Course chosenCourse : allCourses) {
            if (chosenCourse.getDepartmentObj().getName().equals(
                    allDepartments.getName())
                    && chosenCourse.getCatalogNumberObj().getCatalogNumber().equals(
                    courseNum.getCatalogNumber())
                    && !contains(sameDepartmentAndCombined, chosenCourse)) {


                sameDepartmentAndCombined.add(chosenCourse);

            }
        }

    }

    public boolean contains(ArrayList<Course> givenList, Course givenCourse) {

        for (Course course : givenList) {


            if (course.isEquals(givenCourse)) {
                course.getEnrollmentObj().increametnCapacity(givenCourse.getEnrollmentObj().getEnrollmentCap());
                course.getEnrollmentObj().increamentTotal(givenCourse.getEnrollmentObj().getEnrollmentTotal());
                course.addInstructor(givenCourse.getInstructorObj());
                course.setCourseOfferingId(givenCourse.getCourseOfferingId());


                return true;
            }


        }

        return false;


    }

    public ArrayList<Course> getSpecificCourseNumebr(ArrayList<Course> sortedAndCombined
            , CatalogNumber cn) {
        for (Course c : sortedAndCombined) {
            if (c.getCatalogNumberObj().getCatalogNumber().equals(cn.getCatalogNumber())
                    && !hasit(specificCourseNumber, c)) {
                c.setCourseOfferingId(courseId.getAndIncrement());
                specificCourseNumber.add(c);
//                !specificCourseNumber.contains(c)

            }

        }
        return specificCourseNumber;
    }

    private boolean hasit(ArrayList<Course> specificCourseNumber, Course c) {
        for (Course cc : specificCourseNumber) {
            if (cc.getSemesterObj().getSemCode().equals(c.getSemesterObj().getSemCode())
                    && cc.getLocationObj().getLocation().equals(c.getLocationObj().getLocation())
                    ) {
                cc.appendNewComponent(c.getComponentCodeObj());
                cc.appendNewSection(c.getEnrollmentObj());
                cc.addInstructor(c.getInstructorObj());

                return true;
            }
        }
        return false;


    }

    public ArrayList<Course> getSameDepAndSorted() {
        Collections.sort(sameDepartmentAndCombined);

        return sameDepartmentAndCombined;
    }
}
