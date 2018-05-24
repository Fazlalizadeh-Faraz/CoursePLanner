package ca.course.model.CourseFields;


/**
 * this class stores the component code such as Lec...Sec.. Lab
 */
public class ComponentCode {
    private String type = "";

    public ComponentCode(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return
                "\t" + "Type= " + type.toUpperCase();
    }
}
