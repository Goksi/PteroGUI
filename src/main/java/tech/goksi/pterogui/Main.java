package tech.goksi.pterogui;


import tech.goksi.pterogui.apps.FirstTime;
import tech.goksi.pterogui.apps.MainF;

public class Main {

    public static void main(String[] args) {
        FirstTime ft = new FirstTime();
        ft.init();
        MainF main = new MainF();
        main.init();
    }
}
