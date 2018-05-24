package ca.course.cotroller;


import ca.course.calculations.CSV_Reader;
import ca.course.model.*;
import ca.course.model.CourseFields.CatalogNumber;
import ca.course.model.CourseFields.Department;
import ca.course.model.CourseFields.Section;
import ca.course.model.Graphing.Graph;
import ca.course.model.IntroToGame.About;
import ca.course.model.ObserverPattern.Watchers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * game controller class allows the server and the program to communicate
 */

@RestController
public class GameController {
    String path = "C:\\Users\\Faraz Fazlalizadeh\\Desktop\\cmpt\\213\\a5\\course_planner\\data" +
            "\\course_data_2016" + ".csv";
    CSV_Reader reader = new CSV_Reader(path);


    public void chekcSetUp() {


        if (reader.getCourses().isEmpty()) {
            reader.getFileString();
        }
        if (reader.getCoursesWithId().isEmpty()) {
            reader.cleanData(false);
        }
    }

    @GetMapping("/api/about")
    public About returnName() {
        return new About() {
            @Override
            public String getAuthorName() {
                return super.getAuthorName();
            }

            @Override
            public String getAppName() {
                return super.getAppName();
            }
        };

    }


    @GetMapping("/api/dump-model")
    public void returnModelCleanData() {
        CSV_Reader reader = new CSV_Reader(path);
        reader.getFileString();
        reader.cleanData(true);
    }

    @GetMapping("/api/departments")
    public List<Department> returnDepartmetns() {

        chekcSetUp();
        return reader.returnDep();
    }

    @GetMapping("/api/departments/{deptId}/courses")
    public ArrayList<CatalogNumber> returnCourses(@PathVariable("deptId") long departmetnID) {
        chekcSetUp();
        return reader.returnCatalogNumbers(departmetnID);
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings")
    public ArrayList<Course> returnCourse(@PathVariable("deptId") long departmetnID,
                                          @PathVariable("courseId") long courseNum) {
        chekcSetUp();
        return reader.returnCourse(departmetnID, courseNum);
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings/{offeringId}")
    public ArrayList<Section> returnSections(@PathVariable("deptId") long departmetnID,
                                             @PathVariable("courseId") long courseNum,
                                             @PathVariable("offeringId") long sections) {
        chekcSetUp();
        Course c = new Course();
        c = reader.returnSectionForSpecificCourse(departmetnID, courseNum, sections);
        Section s = new Section(c);

        return s.getAllSections();

    }

    @GetMapping("/api/stats/students-per-semester")
    public ArrayList<Graph> data(
            @RequestParam(value = "deptId", required = true) long deptId) {
        chekcSetUp();
        return reader.getGraphData(deptId);
    }

    @PostMapping("/api/addoffering")
    public CreateCourse postClase(@RequestBody CreateCourse course) {
        chekcSetUp();
        CreateCourse c = new CreateCourse();
        c = course;
        reader.constructNewCourse(c);
        return course;
    }

    @PostMapping("/api/watchers")
    public Watchers creatWatcher(@RequestBody IdForWatcher watchers) {
        return reader.createWatcher(watchers);

    }

    @GetMapping("/api/watchers")
    public List<Watchers> getAllWatchers() {
        return reader.getAllWatcher();

    }

    @GetMapping("/api/watchers/{id}")
    public List<String> getEvents(@PathVariable("id") int id) {
        return reader.getEventsFor(id);
    }

    @DeleteMapping("/api/watchers/{id}")
    public void DeleteWatcher(@PathVariable("id") int id) {
        reader.deletWatcehr(id);
    }


    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public static class fileNotFound extends RuntimeException {
        public fileNotFound(String Msg) {
            super(Msg);
        }
    }


}
