package dj.missionknolskape.main.utils;

/**
 * Created by DJphy on 28-09-2016.
 */
public class URLHelper {

    public static final String END_POINT = "https://api.nasa.gov/";
    public static final String API_KEY_NASA = "vtRoSiuO7Ze134C859OhPy8AqLOXxIvDmNfVmHOU";

    public static final String getApodAPI(){
        return END_POINT + VERB.APOD_API;
    }

    private static final class VERB{
        static final String APOD_API = "planetary/apod?";
    }
}
