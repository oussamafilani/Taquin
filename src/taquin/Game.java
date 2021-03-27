/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taquin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Game extends JPanel implements Runnable, MouseListener {

    private Thread thread;
    private BufferedImage image, im, img[];
    private Graphics2D g;
    private boolean running, rej, mov, win;
    private int level, l, c, maT[][], x, y, x1, y1;

    public Game(int level) {
        this.level = level;
        setPreferredSize(new Dimension(400, 400));
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addMouseListener(this);
            thread.start();
        }
    }

    private void initImg() {
        img = new BufferedImage[level];
        if (level == 9) {
            l = c = 3;
        } else if (level == 12) {
            l = 3;
            c = 4;
        } else if (level == 16) {
            l = c = 4;
        }
        maT = new int[l][c];
        ArrayList<Integer> ranM = new ArrayList();
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                img[i * c + j] = im.getSubimage(j * (400 / c), i * (400 / l), 400 / c, 400 / l);
                ranM.add(i * c + j);
            }
        }

        Random ran = new Random();

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                maT[i][j] = ranM.remove(ran.nextInt(ranM.size()));
                if (maT[i][j] == (l * c - 1)) {
                    x1 = j;
                    y1 = i;
                }
            }
        }

    }

    private void init() {
        try {
            image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
            g = (Graphics2D) image.getGraphics();
            running = true;
            im = ImageIO.read(getClass().getResource("/resources/cat.jpg"));
            initImg();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        init();
        while (running) {
            update();
            draw();
            drawToScreen();
            if (c * l != level || rej) {
                initImg();
                rej = false;
            }
        }
    }

    private void win() {
        win = true;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                if(maT[i][j] != i * c + j) {
                    win = false;
                }
            }
        }
    }

    private void update() {
        win();
        if (mov) {
            if (x1 == x || y1 == y) {
                if (y == y1) {
                    if (x1 > x) {
                        for (; x1 > x; x1--) {
                            int temp = maT[y][x1];
                            maT[y][x1] = maT[y][x1 - 1];
                            maT[y][x1 - 1] = temp;
                        }
                    } else {
                        for (; x1 < x; x1++) {
                            int temp = maT[y][x1];
                            maT[y][x1] = maT[y][x1 + 1];
                            maT[y][x1 + 1] = temp;
                        }
                    }
                } else if (x == x1) {
                    if (y1 > y) {
                        for (; y1 > y; y1--) {
                            int temp = maT[y1][x1];
                            maT[y1][x1] = maT[y1 - 1][x1];
                            maT[y1 - 1][x1] = temp;
                        }
                    } else {
                        for (; y1 < y; y1++) {
                            int temp = maT[y1][x1];
                            maT[y1][x1] = maT[y1 + 1][x1];
                            maT[y1 + 1][x1] = temp;
                        }
                    }
                }
            }
            mov = false;
        }
    }

    private void draw() {
        g.setColor(new Color(181, 50, 50));
        g.fillRect(0, 0, 400, 400);
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                if (!(maT[i][j] == (l * c - 1) && !win)) {
                    g.drawImage(img[maT[i][j]], j * (400 / c), i * (400 / l), this);
                }
            }
        }
    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void restar() {
        rej = true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int width = 400 / c;
        int height = 400 / l;
        if (!mov) {
            x = e.getX() / width;
            y = e.getY() / height;
            mov = !mov;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
