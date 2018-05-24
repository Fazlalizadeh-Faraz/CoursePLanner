package ca.course.model.CourseFields;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * this class takes care of decoding the "term" input
 */

public class Semester {

    private String term = "";
    private int year = 0;
    private String semCode = "";
    private int semesterCode;


    public Semester(String semCode) {
        this.semCode = semCode;
        this.semesterCode = Integer.valueOf(semCode);
        if (!semCode.contains("null")) {

            setSemester();
            setYear();
        }


    }

    public String getTerm() {
        return term;
    }

    public String getSemCode() {

        return semCode;
    }

    public int getYear() {
        return year;
    }

    private void setYear() {
        semCode = semCode.replaceAll(" +", "");
        semCode = semCode.trim();
        if (semCode != null) {

            year = Integer.valueOf(semCode.substring(0, 2));
            if (semCode.charAt(0) == '1') {
                year = 2000;
                year += Integer.valueOf(semCode.substring(1, 3).toLowerCase());
            }

        }
    }

    public void setSemester() {
        term += semCode.toLowerCase().charAt(3);
        if (term.equals("1")) {
            term = "Spring";
        }
        if (term.equals("4")) {
            term = "Summer";
        }
        if (term.equals("7")) {
            term = "Fall";
        }
    }

    public int getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(int semesterCode) {
        this.semesterCode = semesterCode;
    }

    @Override
    public boolean equals(Object obj) {
        return getTerm().equals((Semester) obj);
    }

    @Override
    public String toString() {
        return
                term + "  " + year;
    }


}
