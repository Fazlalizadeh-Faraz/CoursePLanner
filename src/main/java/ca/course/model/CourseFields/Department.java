package ca.course.model.CourseFields;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * this class takes care of the department
 * also is able to be sorted
 */

public class Department implements Comparable<Department> {

    private String name = "";
    private int deptId;

    public Department() {

    }

    public Department(String currentDepartment) {
        this.name = currentDepartment;


    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;

    }

    public boolean equals(String obj) {

        if (name.equals((String) obj)) {
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String currentDepartment) {
        this.name = currentDepartment;

    }

    @Override
    public int compareTo(Department o) {
        return this.getName().compareTo(o.getName());
    }

    @JsonIgnore
    @Override
    public String toString() {
        return "deptId:" + deptId
                + "Department: " + name + "\n";
    }


}
