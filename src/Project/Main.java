package Project;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //PrimaryApp app = new PrimaryApp();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Project3App primaryApp = new Project3App();
                primaryApp.setVisible(true);
            }
        });
    }
}