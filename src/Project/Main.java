package Project;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //PrimaryApp app = new PrimaryApp();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PrimaryApp bookEditorExample = new PrimaryApp();
                bookEditorExample.setVisible(true);
            }
        });
    }
}