package dj.missionknolskape.main.model;

import java.util.List;

/**
 * Created by User on 30-10-2016.
 */
public class GalleryDataObject {

    List<String> paths;
    String title;

    public GalleryDataObject(List<String> paths, String title) {
        this.paths = paths;
        this.title = title;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
