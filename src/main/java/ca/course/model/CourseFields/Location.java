package ca.course.model.CourseFields;


/**
 * this class takes care of the string "Locations"
 */
public class Location {
    private String location = "";

    public Location(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object obj) {
        return location.equals((Location) obj);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return location.toUpperCase();
    }
}
