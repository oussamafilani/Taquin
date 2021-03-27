package taquin;

import javax.swing.JFrame;

public class Taquin {
    
    public static void main(String[] args) {
        JFrame window = new JFrame("Taquin");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setContentPane(new Window());
        window.setVisible(true);
        window.pack();
    }
    
}
