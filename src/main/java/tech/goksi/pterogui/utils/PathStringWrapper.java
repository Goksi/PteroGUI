package tech.goksi.pterogui.utils;

public class PathStringWrapper {
    private final String[] paths;
    public PathStringWrapper(String str1, String str2){
        paths = new String[]{str1, str2};
    }

    public String getAppender(){
        return paths[0];
    }

    public String getDivisor(){
        return paths[1];
    }
}
