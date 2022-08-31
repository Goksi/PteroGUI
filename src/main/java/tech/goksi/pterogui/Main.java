package tech.goksi.pterogui;


import tech.goksi.pterogui.apps.FirstTime;
import tech.goksi.pterogui.apps.MainWindow;

public class Main {

    public static void main(String[] args) {
        FirstTime ft = new FirstTime();
        ft.init();
        MainWindow main = new MainWindow();
        main.init();
    }
}
