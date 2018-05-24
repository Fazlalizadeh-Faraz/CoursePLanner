package ca.course.PrintAndRead;

import ca.course.model.*;
import ca.course.model.CourseFields.CatalogNumber;
import ca.course.model.CourseFields.ComponentCode;
import ca.course.model.CourseFields.Department;


import java.io.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * this class prints to the console and also makes "farazMasterPiece" since the console cant handle the size
 * of the given input
 */

public class Printing {
    ArrayList<Course> courses = new ArrayList<>();
    ArrayList<Course> sortedAndCombined = new ArrayList<>();
    AtomicInteger lastDeptId = new AtomicInteger();


    public Printing(ArrayList<Course> courses) {
        this.courses = courses;


    }

    public void setPrinting(boolean print) {

        File meFile = new File("./data/farazMasterPiece");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(meFile, true));


            ExtractDepartment extractDepartment = new ExtractDepartment(courses);
            lastDeptId = extractDepartment.getIdCounter();
            // testing
            for (Department chosenDep : extractDepartment.getAllDepartmnets()) {
                ExtractCourseNumber extractCourseNumber = new ExtractCourseNumber(courses, chosenDep);

                for (CatalogNumber chosenCourseNumber : extractCourseNumber.getAllNumebrs()) {

                    if (print == true) {

                        System.out.print("\n\n" + chosenDep.getName().toUpperCase());
                        writer.append("\n\n" + chosenDep.getName().toUpperCase() + "\n");
                        System.out.println("\t " + chosenCourseNumber.getCatalogNumber());
                        writer.append("\t " + chosenCourseNumber.getCatalogNumber() + "\n");
                    }


                    MergeDuplicateCourse MergeDuplicateCourse = new MergeDuplicateCourse(
                            extractCourseNumber.getSpecificDepartment(),
                            chosenDep,
                            chosenCourseNumber.getCatalogNumber()
                    );


                    String title = "";
                    String components = "";

                    for (Course currentCourse :
                            MergeDuplicateCourse.getSpecificCourseNumebr(
                                    MergeDuplicateCourse.getSameDepAndSorted(),
                                    chosenCourseNumber)) {


                        if (print == true) {

                            title = "\n\n\t" + currentCourse.getSemesterObj().getSemCode() + " " +
                                    currentCourse.getLocationObj() + " by ";

                            System.out.println(title + currentCourse.getInstructorObj());
                            writer.append(title + currentCourse.getInstructorObj() + "\n");

                            for (int i = 0; i < currentCourse.getDiffSections().size(); i++) {
                                components = currentCourse.getDiffSections().get(i).toString() +
                                        ", Enrollment= " +
                                        currentCourse.getAllEnrollments().get(i).toString();

                                System.out.println(components);
                                writer.append(components + "\n");
                            }


                        }
                        sortedAndCombined.add(currentCourse);
                    }
                }
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AtomicInteger getLastDeptId() {
        return lastDeptId;
    }


    public ArrayList<Course> getSortedAndCombined() {
        return sortedAndCombined;
    }
}
