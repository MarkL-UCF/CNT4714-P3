package Project;

import javax.swing.*;

@SuppressWarnings("ALL")
public class Main {
    public static void main(String[] args) {

        //using the IntelliJ IDEA tutorial for GUI designer to
        //launch application so that the .form file is used
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Project3App primaryApp = new Project3App();
                primaryApp.setVisible(true);
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Project3AccountantApp secondaryApp = new Project3AccountantApp();
                secondaryApp.setVisible(true);
            }
        });
    }
}