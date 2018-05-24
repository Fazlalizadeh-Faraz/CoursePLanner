package ca.course.model.CourseFields;



/**
 * this class takes care of the Enrollment activities such as
 * adding ... combining
 * */

public class Enrollment{
    private int enrollmentTotal =0;
    private int enrollmentCap =0;

    public Enrollment() {

    }

    public Enrollment(int enrollmentTotal, int enrollmentCap) {
        this.enrollmentTotal = enrollmentTotal;
        this.enrollmentCap = enrollmentCap;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public void increametnCapacity(int value){
        enrollmentCap += value;

    }

    public void increamentTotal(int value ){
        enrollmentTotal += value;
    }


    @Override
    public String toString() {
        return
                enrollmentTotal + "/" + enrollmentCap;
    }

}
