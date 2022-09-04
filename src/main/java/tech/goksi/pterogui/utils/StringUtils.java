package tech.goksi.pterogui.utils;

public class StringUtils {

    public static PathStringWrapper difference(String str1, String str2) {
        int at = indexOfDifference(str1, str2);
        if (at == -1) {
            return new PathStringWrapper("", "");
        }
        return new PathStringWrapper(str1.substring(at), str2.substring(at));
    }

    private static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
        if (cs1 == cs2) {
            return -1;
        }
        if (cs1 == null || cs2 == null) {
            return 0;
        }
        int i;
        for (i = 0; i < cs1.length() && i < cs2.length(); ++i) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                break;
            }
        }
        if (i < cs2.length() || i < cs1.length()) {
            return i;
        }
        return -1;
    }
}
