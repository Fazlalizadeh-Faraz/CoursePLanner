package ca.course.calculations;

import ca.course.cotroller.GameController;
import ca.course.model.*;
import ca.course.model.CourseFields.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * this class fetches and gives the data needed
 * designed as the data finder
 */

public class DataHandler {
    private ArrayList<Course> allCourse = new ArrayList<>();
    private ArrayList<Section> clenaedSections = new ArrayList<>();
    private AtomicInteger lastCatalogNumId = new AtomicInteger();


    public DataHandler(ArrayList<Course> allCourse) {
        this.allCourse = allCourse;
    }

    /*
     * get the the courses within a selected department
     * */
    public ArrayList<Course> getCoursesForSpecificDept(long id) {

        ArrayList<Course> dep = new ArrayList<>();
        for (Course c : allCourse) {

            if (c.getDepartmentObj().getDeptId() == id) {

                dep.add(c);
            }
        }
        if (dep.isEmpty()) {
            throw new GameController.fileNotFound("Department Was not Found" + "id: " + id);

        }
        return dep;
    }

    /*
     * get the list of all the  departments
     * */
    public ArrayList<Department> getAllDept() {

        ArrayList<Department> departments = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        for (Course c : allCourse) {
            if (!contain(ids, c.getDepartmentObj().getDeptId())) {
                ids.add(c.getDepartmentObj().getDeptId());

                departments.add(c.getDepartmentObj());

            }

        }
        if (departments.isEmpty()) {
            throw new GameController.fileNotFound("Department Was not Found");
        }
        return departments;
    }

    /*
     * check to see if the Same ids appear
     * */
    private boolean contain(ArrayList<Integer> ids, long id) {
        for (Integer curId : ids) {
            if (curId == id) {
                return true;
            }
        }
        return false;
    }

    /*
     * get the list of all the cmpt 213 classes
     * */
    public ArrayList<Course> getSpecificClass(ArrayList<Course> specificDepartmet, long courseNum) {
        ArrayList<Course> SpecificCourse = new ArrayList<>();
        for (Course c : specificDepartmet) {
            if (c.getCatalogNumberObj().getCourseId() == courseNum) {

                SpecificCourse.add(c);
            }
        }
        if (SpecificCourse.isEmpty()) {
            throw new GameController.fileNotFound("Catalog Number Was not Found" + "id: " + courseNum);

        }
        return SpecificCourse;
    }

    /*
     * get the section for a specific course offering
     * */
    public Course getSections(ArrayList<Course> specificCourse, long courseNum) {
        for (Course course : specificCourse) {
            if (course.getCourseOfferingId() == courseNum) {

                return course;
            }
        }
        throw new GameController.fileNotFound("Section Was not Found" + "id: " + courseNum);


    }

    /*
     * get all the catalog numbers within a department
     * */
    public ArrayList<CatalogNumber> getCatalogNumFor(long id) {
        ArrayList<CatalogNumber> catalogNumbers = new ArrayList<>();
        lastCatalogNumId.set(0);
        for (Course c : getCoursesForSpecificDept(id)) {
            if (!containCatalogNum(catalogNumbers, c.getCatalogNumberObj())) {
                catalogNumbers.add(c.getCatalogNumberObj());
            }
            if (c.getCatalogNumberObj().getCourseId() > lastCatalogNumId.get()) {
                lastCatalogNumId.set(c.getCatalogNumberObj().getCourseId());


            }
        }

        return catalogNumbers;

    }


    public AtomicInteger getLastCatalogNumId() {
        return lastCatalogNumId;
    }

    private boolean containCatalogNum(ArrayList<CatalogNumber> catalogNumbers, CatalogNumber catalogNumber) {
        for (CatalogNumber c : catalogNumbers) {
            if (catalogNumber.getCourseId() == c.getCourseId()) {
                return true;
            }
        }
        return false;

    }

    public ArrayList<Section> getClenaedSections() {
        return clenaedSections;
    }

    /*
     * get all the semesters that contain "lec"
     * */
    public void getSemestersWithLec(long idOfDept) {

        ArrayList<Section> sections = new ArrayList<>();
        ArrayList<Semester> semestesWithLectSection = new ArrayList<>();
        for (Course c : getCoursesForSpecificDept(idOfDept)) {

            //check to see if it has "lec"
            if (hasComponent(c.getDiffSections())) {

                sections.add(c.getSpecificSection("lec"));
                semestesWithLectSection.add(c.getSemesterObj());
            }


        }
        combineTheData(sections, semestesWithLectSection);

    }

    /*
     * sum up the data for the graph
     * */
    private void combineTheData(ArrayList<Section> sections, ArrayList<Semester> semestesWithLectSection) {

        ArrayList<String> sems = new ArrayList<>();
        for (Semester s : semestesWithLectSection) {
            sems.add(s.getSemCode());
        }
        List<String> al = new ArrayList<>();
        al.addAll(sems);
        Set<String> hs = new HashSet<>();
        hs.addAll(al);
        al.clear();
        al.addAll(hs);


        clenaedSections = new ArrayList<>();
        for (String sem : al) {
            Section newSection = new Section();
            Semester newSem = new Semester(sem);
            newSection.setSemesterCode(newSem);
            ComponentCode cc = new ComponentCode("lec");
            newSection.setComponentCode(cc);
            int enrollmentCap = 0;
            int enrollmentTot = 0;
            for (Section s : sections) {

                if (s.getSemesterCode().getSemCode().equals(sem)) {


                    enrollmentCap += s.getEnrollment().getEnrollmentCap();
                    enrollmentTot += s.getEnrollment().getEnrollmentTotal();

                }

            }
            Enrollment e = new Enrollment();
            e.setEnrollmentTotal(enrollmentTot);
            e.setEnrollmentCap(enrollmentCap);
            newSection.setEnrollment(e);
            clenaedSections.add(newSection);

        }


    }

    private boolean hasComponent(ArrayList<ComponentCode> diffSections) {
        for (ComponentCode c : diffSections) {
            if (c.getType().equals("lec")) {
                return true;
            }
        }
        return false;


    }


}
