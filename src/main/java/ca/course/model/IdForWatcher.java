package ca.course.model;


/**
 * this class holds the ids for the constructed watcher from the server
 */

public class IdForWatcher {
    int deptId;
    int courseId;

    public IdForWatcher() {
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
