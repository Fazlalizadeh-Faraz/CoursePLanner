package ca.course.model.IntroToGame;

public class About {
    private String appName = "Faraz's Lack Of Sleep App";
    private String authorName = "Monster & Caffeine";


    public About() {
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "About{" +
                "appName='" + appName + '\'' +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}
