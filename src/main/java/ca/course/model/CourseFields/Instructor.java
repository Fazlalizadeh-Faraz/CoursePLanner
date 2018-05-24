package ca.course.model.CourseFields;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.LinkedHashSet;


/**
 * this class is for adding an instructor
 * ps LINKEDHASHEDSET are blessings
 */

public class Instructor {
    private ArrayList<String> instructors = new ArrayList<>();


    public Instructor() {
    }

    public Instructor(String instructors) {


        if (instructors.contains(",")) {
            String[] profs = instructors.split(",");
            for (String prof : profs) {
                this.instructors.add(prof);
            }
        } else {
            this.instructors.add(instructors);
        }
    }

    public String getInstructors() {
        return toString();
    }

    public void setInstructors(ArrayList<String> instructors) {

        for (String teacher : instructors) {

            this.instructors.add(teacher);
        }
        clearDuplicates();
    }

    private void clearDuplicates() {

        LinkedHashSet<String> lhs = new LinkedHashSet<>();
        lhs.addAll(instructors);
        instructors.clear();
        instructors.addAll(lhs);
    }

    @JsonIgnore
    public ArrayList<String> getArrayOfInstructors() {
        return instructors;
    }

    @Override
    public boolean equals(Object obj) {
        return getArrayOfInstructors().equals((Instructor) obj);
    }

    @Override
    public String toString() {
        String output = "";

        for (String teacher : instructors) {
            if (!teacher.contains("null")) {

                output += teacher;
                output += ", ";
            }
        }

        return output.replaceAll("\"", "");
    }
}
