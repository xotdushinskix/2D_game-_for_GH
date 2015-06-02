package Game;


import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame fr = new JFrame("Sky wars");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setSize(1366, 768);
        fr.setResizable(false);
        fr.setLocationRelativeTo(null);
        fr.add(new Sky());
        fr.setVisible(true);

    }

}
