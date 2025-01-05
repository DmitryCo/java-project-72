package hexlet.code.util;

import lombok.Generated;

@Generated
public class Routes {
    public static String mainPath() {
        return "/";
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String urlPath(Long id) {
        return urlPath(String.valueOf(id));
    }

    public static String urlPath(String id) {
        return "/urls/" + id;
    }

    public static String urlChecks(Long id) {
        return urlChecks(String.valueOf(id));
    }

    public static String urlChecks(String id) {
        return "/urls/" + id + "/checks";
    }

}