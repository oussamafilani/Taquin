package taquin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends JPanel implements MouseListener {

    private Panel G1, G2, G3;
    private Label L1, L2, L3, L4,selL;
    private JButton rej;
    private JTextField field;
    private Color sel, unsel;
    private Game game;
    
    public Window() {
        super(new BorderLayout(0, 10));
        setPreferredSize(new Dimension(500, 560));
        setBackground(new Color(181, 50, 50));
        
        sel = new Color(0, 255, 55);
        unsel = new Color(24, 206, 97);
        
        L1 = new Label("Le tigre taquin : ");
        initLabel(L1, new Color(255, 255, 15));
        L2 = new Label("9 Pieces");
        initLabel(L2, sel);
        selL = L2;
        L3 = new Label("12 Pieces");
        initLabel(L3, unsel);
        L4 = new Label("16 Pieces");
        initLabel(L4, unsel);
        L2.addMouseListener(this);
        L3.addMouseListener(this);
        L4.addMouseListener(this);
        
        G1 = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        G1.add(L1);
        G1.add(L2);
        G1.add(L3);
        G1.add(L4);
        
        add(G1,BorderLayout.NORTH);
        
        G2 = new Panel(new FlowLayout(FlowLayout.CENTER , 0, 20));
        G2.setPreferredSize(new Dimension(500, 400));
        game = new Game(9);
        G2.add(game);
        add(G2,BorderLayout.CENTER);
        
        G3 = new Panel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        rej = new JButton("Rejouer");
        rej.addMouseListener(this);
        field = new JTextField(10);
        G3.add(rej);
        G3.add(field);
        
        add(G3, BorderLayout.SOUTH);
        
    }
    
    private void initLabel(Label lab,Color col) {
        lab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        lab.setForeground(col);
        lab.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof Label) {
            Label lab = (Label) e.getSource();
            if(lab.equals(L2) && !selL.equals(L2)) {
                selL.setForeground(unsel);
                L2.setForeground(sel);
                selL = L2;
                game.setLevel(9);
            } else if(lab.equals(L3) && !selL.equals(L3)) {
                selL.setForeground(unsel);
                L3.setForeground(sel);
                selL = L3;
                game.setLevel(12);
            } else if(lab.equals(L4) && !selL.equals(L4)) {
                selL.setForeground(unsel);
                L4.setForeground(sel);
                selL = L4;
                game.setLevel(16);
            }
        } else if(e.getSource().equals(rej)) {
            game.restar();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
