package ca.course.calculations;


import ca.course.PrintAndRead.Printing;
import ca.course.cotroller.GameController;
import ca.course.model.*;
import ca.course.model.CourseFields.*;
import ca.course.model.Graphing.Graph;
import ca.course.model.ObserverPattern.Watchers;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * this class read the given data and construct courses based on given data
 * and also provides data to the controller
 */

public class CSV_Reader {

    public static final int SEMESTER = 0;
    public static final int DEPARTMENT = 1;
    public static final int CATALOG_NUMBER = 2;
    public static final int LOCATION = 3;
    public static final int ENROLLMENT_CAP = 4;
    public static final int ENROLLMENT_TOTAL = 5;
    public static final int INSTRUCTOR = 6;
    public static final int COMPONENT_CODE = 7;
    AtomicInteger setWatcherID = new AtomicInteger();
    AtomicInteger newCatalogNum = new AtomicInteger();
    AtomicInteger lastCatalogNum = new AtomicInteger();
    AtomicInteger lastUsedDeptID = new AtomicInteger();
    AtomicInteger lastCourseOfferingId = new AtomicInteger();


    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<Course> coursesWithId = new ArrayList<>();
    private ArrayList<String> lineSeperated = new ArrayList<>();
    private ArrayList<Watchers> allWatcher = new ArrayList<>();
    private String fileNameDefined;
    private DataHandler dataHandler;


    private File file;

    /*
     * find the file in order to read the data from
     * */
    public CSV_Reader(String path) {
        try {

            this.fileNameDefined = path;
            this.file = new File(fileNameDefined);
        } catch (Exception e) {

            throw new GameController.fileNotFound(e.getMessage());
        }


    }

    /*
     * read each line and insert into an arraylist of strings
     * */
    public void getFileString() {

        try {
            Scanner inputStream = new Scanner(file);

            // hashNext() loops line-by-line
            boolean firstLine = true;
            while (inputStream.hasNextLine()) {
                //read single line, put in string
                String data = inputStream.nextLine();
                if (firstLine) {
                    firstLine = false;
                } else {


                    lineSeperated.add(data);
                }

            }
            ReadCSVData();
            // after loop, close scanner
            inputStream.close();

        } catch (FileNotFoundException e) {
            throw new GameController.fileNotFound(e.getMessage());
        }


    }


    /*
     * Read the csv data and construct a course per each line
     * */
    public void ReadCSVData() {
        for (String sentence : lineSeperated) {

            sentence = sentence.trim().replaceAll(",", ", ");
            sentence = sentence.trim().replaceAll(" +", "");

            Course course = new Course();
            String[] commaSeperate = sentence.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            Enrollment enrollment = new Enrollment();
            for (int i = 0; i < commaSeperate.length; i++) {
                Instructor instructor;

                switch (i) {
                    case SEMESTER: {
                        if (!commaSeperate[SEMESTER].isEmpty()) {
                            Semester semester = new Semester(commaSeperate[SEMESTER].toLowerCase());
                            course.setSemester(semester);
                        }
                        break;
                    }
                    case DEPARTMENT: {
                        if (!commaSeperate[DEPARTMENT].isEmpty()) {
                            Department department = new Department(commaSeperate[DEPARTMENT].toLowerCase());
                            course.setDepartment(department);

                        }
                        break;
                    }
                    case CATALOG_NUMBER: {
                        if (!commaSeperate[CATALOG_NUMBER].isEmpty()) {
                            CatalogNumber catalogNumber
                                    = new CatalogNumber(commaSeperate[CATALOG_NUMBER].toLowerCase());

                            course.setCatalogNumber(catalogNumber);
                        }
                        break;
                    }
                    case LOCATION: {
                        if (!commaSeperate[LOCATION].isEmpty()) {
                            Location location = new Location(commaSeperate[LOCATION].toLowerCase().trim());
                            course.setLocation(location);
                        }
                        break;
                    }
                    case ENROLLMENT_CAP: {
                        if (!commaSeperate[ENROLLMENT_CAP].isEmpty()) {


                            commaSeperate[ENROLLMENT_CAP] =
                                    commaSeperate[ENROLLMENT_CAP].replaceAll("[^0-9]", "");

                            if (!commaSeperate[ENROLLMENT_CAP].isEmpty()) {

                                enrollment.setEnrollmentCap(Integer.parseInt(commaSeperate[ENROLLMENT_CAP]));

                            }


                        }
                        break;
                    }
                    case ENROLLMENT_TOTAL: {
                        if (!commaSeperate[ENROLLMENT_TOTAL].isEmpty()) {


                            commaSeperate[ENROLLMENT_TOTAL] =
                                    commaSeperate[ENROLLMENT_TOTAL].replaceAll("[^0-9]", "");

                            if (!commaSeperate[ENROLLMENT_TOTAL].isEmpty()) {

                                enrollment.setEnrollmentTotal(Integer.parseInt(commaSeperate[ENROLLMENT_TOTAL]));

                            }
                        }
                        break;
                    }
                    case INSTRUCTOR: {
                        if (!commaSeperate[INSTRUCTOR].isEmpty()) {

                            instructor = new Instructor(commaSeperate[INSTRUCTOR].toLowerCase());
                            course.setInstructors(instructor);
                        }
                        break;
                    }
                    case COMPONENT_CODE: {
                        if (!commaSeperate[COMPONENT_CODE].isEmpty()) {
                            ComponentCode componentCode = new ComponentCode(commaSeperate[COMPONENT_CODE].toLowerCase());
                            course.setComponentCode(componentCode);
                            course.appendNewComponent(componentCode);
                        }
                        break;
                    }

                }
            }

            course.setEnrollment(enrollment);
            course.appendNewSection(enrollment);
            courses.add(course);


        }

    }


    /*
     * in case null fields are not allowed
     * */
    private boolean checkNull(String sentence) {

        if (sentence.contains("null")) {
            return true;
        }
        return false;
    }

    /*
     * clean the constructed courses and also print
     * */
    public void cleanData(boolean printing) {
        Printing print = new Printing(courses);
        print.setPrinting(printing);

        lastUsedDeptID = print.getLastDeptId();
        coursesWithId.addAll(print.getSortedAndCombined());
        dataHandler = new DataHandler(coursesWithId);
    }

    /*
     * return arraylist of departments
     * */
    public ArrayList<Department> returnDep() {
        return dataHandler.getAllDept();
    }


    /*
     * return arraylist of catalog numbers for a department
     * */
    public ArrayList<CatalogNumber> returnCatalogNumbers(Long id) {
        return dataHandler.getCatalogNumFor(id);
    }


    /*
     * return all the watchers created
     * */
    public ArrayList<Watchers> getAllWatcher() {
        return allWatcher;
    }


    /*
     * create a watchers(Observer) for the selected course
     * */
    public Watchers createWatcher(IdForWatcher Ids) {

        Watchers newWatcher = null;

        for (Course c : dataHandler.getSpecificClass(
                dataHandler.getCoursesForSpecificDept(Ids.getDeptId()), Ids.getCourseId())) {

            if (c.getCatalogNumberObj().getCourseId() == Ids.getCourseId() &&
                    c.getDepartmentObj().getDeptId() == Ids.getDeptId()
                    ) {

                newWatcher = new Watchers(c, setWatcherID.getAndIncrement());
                c.addObserver(newWatcher);
                allWatcher.add(newWatcher);

            } else {
                throw new GameController.fileNotFound("sorry could not find " +
                        " dep id: " +
                        Ids.getDeptId() +
                        " course Id: " +
                        Ids.getCourseId());
            }
        }
        return newWatcher;
    }


    /*
     * get the data for selected department
     * */
    public ArrayList<Graph> getGraphData(Long id) {
        ArrayList<Graph> data = new ArrayList<>();
        dataHandler.getSemestersWithLec(id);
        for (Section s : dataHandler.getClenaedSections()) {
            Graph newData = new Graph();
            newData.setSemesterCode(s.getSemesterCode().getSemCode());
            newData.setTotalCoursesTaken(s.getEnrollment().getEnrollmentTotal());
            data.add(newData);
        }
        Collections.sort(data);
        return data;

    }

    /*
     * construct a new course and notify changes if this course already exists
     * */
    public void constructNewCourse(CreateCourse createCourse) {


        Course course = new Course();
        CatalogNumber catalogNumber = new CatalogNumber(String.valueOf(createCourse.getCatalogNumber()).toLowerCase());
        ComponentCode componentCode = new ComponentCode(createCourse.getComponent().toLowerCase());
        Department department = new Department(createCourse.getSubjectName().toLowerCase());
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentCap(createCourse.getEnrollmentCap());
        enrollment.setEnrollmentTotal(createCourse.getEnrollmentTotal());
        Instructor instructor = new Instructor(createCourse.getInstructor().toLowerCase());
        Location location = new Location(createCourse.getLocation().toLowerCase());
        Semester semester = new Semester(String.valueOf(createCourse.getSemester()).toLowerCase());

        course.setCatalogNumber(catalogNumber);
        course.setDepartment(department);
        course.setComponentCode(componentCode);
        course.setEnrollment(enrollment);
        course.appendNewSection(enrollment);
        course.setInstructors(instructor);
        course.setLocation(location);
        course.setSemester(semester);
        course.appendNewComponent(componentCode);


        boolean DepAndCatalogNumExists = false;
        for (Department currentDep : dataHandler.getAllDept()) {
            String newCourseDepName = course.getDepartmentObj().getName();
            if (currentDep.getName().equals(newCourseDepName)) {

                course.getDepartmentObj().setDeptId(currentDep.getDeptId());
                int currentCatalogNumId = 0;
                int newCourseDepId = course.getDepartmentObj().getDeptId();
                for (CatalogNumber currentCatalogNum : dataHandler.getCatalogNumFor(newCourseDepId)) {

                    String newCourseCatalogNumber = course.getCatalogNumberObj().getCatalogNumber();
                    if (currentCatalogNum.getCatalogNumber().equals(newCourseCatalogNumber)) {
                        course.getCatalogNumberObj().setCourseId(currentCatalogNum.getCourseId());
                        DepAndCatalogNumExists = true;
                        break;
                    }
                    currentCatalogNumId++;

                }
                if (currentCatalogNumId >= lastCatalogNum.get() && DepAndCatalogNumExists == false) {
                    lastCatalogNum.set(currentCatalogNumId);
                    course.getCatalogNumberObj().setCourseId(lastCatalogNum.incrementAndGet());
                } else if (course.getCatalogNumberObj() == null) {

                    course.getCatalogNumberObj().setCourseId(lastCatalogNum.get());
                    coursesWithId.add(course);

                    dataHandler = new DataHandler(coursesWithId);
                    return;
                }


            }

        }

        findPlacementForCourse(DepAndCatalogNumExists, course);


    }


    /*
     * if course already exists then merge information
     * */
    private void findPlacementForCourse(boolean depAndCatalogNumExists, Course course) {
        if (depAndCatalogNumExists) {


            for (Course thisCourse : dataHandler.getSpecificClass(
                    dataHandler.getCoursesForSpecificDept(course.getDepartmentObj().getDeptId()),
                    course.getCatalogNumberObj().getCourseId())) {
                String newCourseSemCode = course.getSemesterObj().getSemCode();
                String newCourseLocation = course.getLocationObj().getLocation();

                //if same semester
                if (thisCourse.getSemesterObj().getSemCode().equals(newCourseSemCode) &&
                        thisCourse.getLocationObj().getLocation().equals(newCourseLocation)) {

                    //if same component
                    if (hasComponentCode(course.getComponentCodeObj(), thisCourse.getDiffSections())) {


                        //combine the enrollments
                        thisCourse.setLastChanged(course.getEnrollmentObj());
                        Enrollment e = new Enrollment();

                        e.setEnrollmentTotal(course.getEnrollmentObj().getEnrollmentTotal() +
                                thisCourse.getAllEnrollments().get(thisCourse.positionOfComponent
                                        (course.getComponentCodeObj())).getEnrollmentTotal());


                        e.setEnrollmentCap(course.getEnrollmentObj().getEnrollmentCap() +
                                thisCourse.getAllEnrollments().get(thisCourse.positionOfComponent
                                        (course.getComponentCodeObj())).getEnrollmentCap()
                        );
                        thisCourse.updateSpecificEnrollmetn(
                                thisCourse.positionOfComponent(course.getComponentCodeObj()), e);
                        thisCourse.postNotification();

                        dataHandler = new DataHandler(coursesWithId);
                        return;

                    } else {
                        Enrollment newEnrollment = new Enrollment();
                        newEnrollment.setEnrollmentTotal(course.getEnrollmentObj().getEnrollmentTotal());
                        newEnrollment.setEnrollmentCap(course.getEnrollmentObj().getEnrollmentCap());

                        thisCourse.appendNewComponent(course.getComponentCodeObj());
                        thisCourse.appendNewSection(newEnrollment);
                        thisCourse.addInstructor(course.getInstructorObj());
                        thisCourse.postNotification();

                        dataHandler = new DataHandler(coursesWithId);
                        return;
                    }

                }
                if (thisCourse.getCourseOfferingId() >= lastCourseOfferingId.get()) {
                    lastCourseOfferingId.set(thisCourse.getCourseOfferingId());
                }

            }

            //update the observers
            course.setCourseOfferingId(lastCourseOfferingId.incrementAndGet());
            coursesWithId.add(course);
            course.postNotification();

            dataHandler = new DataHandler(coursesWithId);


        } else {
            course.getDepartmentObj().setDeptId(lastUsedDeptID.getAndIncrement());
            course.getCatalogNumberObj().setCourseId(newCatalogNum.getAndIncrement());
            coursesWithId.add(course);

            dataHandler = new DataHandler(coursesWithId);

            return;

        }
    }


    /*
     * does the component code exists within chosen course
     * */
    private boolean hasComponentCode(ComponentCode newComponent, ArrayList<ComponentCode> diffSections) {

        for (ComponentCode com : diffSections) {

            if (com.getType().toLowerCase().equals(newComponent.getType().toLowerCase())) {
                return true;
            }
        }
        return false;

    }

    /*
     * get list of all events that has occurred
     * */
    public List<String> getEventsFor(int id) {
        for (Watchers w : allWatcher) {
            if (w.getId() == id) {
                return w.getEvents();
            }
        }
        throw new GameController.fileNotFound("Wathcer " + id + " not found");
    }

    /*
     * get all courses in ie: dept CMPT 213
     * */
    public ArrayList<Course> returnCourse(Long id, Long cournum) {

        return dataHandler.getSpecificClass(dataHandler.getCoursesForSpecificDept(id), cournum);
    }

    /*
     * return enrollment and component code
     * */
    public Course returnSectionForSpecificCourse(Long id, Long cousenum, Long course) {
        return dataHandler.getSections(dataHandler.getSpecificClass(
                dataHandler.getCoursesForSpecificDept(id), cousenum), course);
    }

    /*
     * delete the chosen watcher
     * */
    public void deletWatcehr(int watcherId) {
        Watchers deleteMe = new Watchers();
        for (Watchers watcher : getAllWatcher()) {
            if (watcher.getId() == watcherId) {
                deleteMe = watcher;

            }
        }
        if (deleteMe.getDepartment() == null) {
            throw new GameController.fileNotFound("watcher " + watcherId + " not found");
        }
        int depID = deleteMe.getDepartment().getDeptId();
        int courseID = deleteMe.getCatalogNumberObj().getCourseId();
        for (Course c : dataHandler.getSpecificClass(dataHandler.
                getCoursesForSpecificDept(depID), courseID
        )) {

            if (c.getCatalogNumberObj().getCourseId() == courseID &&
                    c.getDepartmentObj().getDeptId() == depID
                    ) {


                c.removeObserver(deleteMe);
                allWatcher.remove(deleteMe);

            } else {
                throw new GameController.fileNotFound("sorry could not find " +
                        " dep id: " +
                        depID +
                        " course Id: " +
                        courseID);
            }
        }
    }


    public ArrayList<Course> getCoursesWithId() {
        return coursesWithId;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }


}
