package ca.course.model.ObserverPattern;

import ca.course.model.Course;

public interface Observer {

    Course course = null;

    public abstract void update(Course c);

    public void setCourse(Course course);
}
