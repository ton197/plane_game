import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class FirstGUI extends JFrame {

    static int x, y;
	static final int VELOCITY;
    long start_time,end_time;
    boolean up,down,left,right;
    public static int WINDOW_WIDTH = 500;
    public static int WINDOW_HEIGHT = 500;
    ArrayList bulletList = new ArrayList<Bullet>();
    plane p = new plane();

    boolean zh = false;

    Dimension GWS = getSize();
    getScreenSize GSS = new getScreenSize();

    class TRP extends Thread {
        public void run(){
            while (true){
                repaint();

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class KBL extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_UP:
                    up = true;
                    break;
                case KeyEvent.VK_DOWN:
                    down = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    right = true;
                    break;
                case KeyEvent.VK_LEFT:
                    left = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_UP:
                    up = false;
                    break;
                case KeyEvent.VK_DOWN:
                    down = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    right = false;
                    break;
                case KeyEvent.VK_LEFT:
                    left = false;
                    break;
            }
        }
    }

    Image plane = ImgGet.getImage("Images/PlaneWhite.png");
    Image blast = ImgGet.getImage("Images/BlastWhite.png");

    public void launchWindows(){
        this.setTitle("plane game");
        this.setVisible(true);
        this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        this.setLocation(150,150);

        start_time = System.currentTimeMillis();

        for(int i=0;i<15;i++) {
            Bullet bt = new Bullet();
            bulletList.add(bt);
        }

        new TRP().start();
        addKeyListener(new KBL());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == 6){
                    WINDOW_WIDTH = GSS.getScreenWidth();
                    WINDOW_HEIGHT = GSS.getScreenHeight();
                } else if (e.getNewState() == 0){
                    WINDOW_WIDTH = GWS.width;
                    WINDOW_HEIGHT = GWS.height;
                }
            }
        });
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0;i < bulletList.size();i++){

            Bullet b = (Bullet)bulletList.get(i);
            g.drawOval(b.X, b.Y,20,20);
            b.X += b.xc;
            b.Y += b.yc;


            if (b.X > 470 || b.X < 5) {
                b.xc = -b.xc;
            }
            if (b.Y > 470 || b.Y < 35) {
                b.yc = -b.yc;
            }

            boolean isXC = false,isYC = false;

            int[] bWeight = new int[20];
            int[] pWeight = new int[30];

            for (int j = 0;j < 20;j++)
                bWeight[j] = (b.X + j + 1);
            for (int j = 0;j < 30;j++)
                pWeight[j] = (x + j + 1);

            for (int l = 0;l < 20;l++){
                for (int j = 0;j < 30;j++){
                    if (bWeight[l] == pWeight[j])
                        isXC = true;
                }
            }

            int[] bhight = new int[20];
            int[] phight = new int[37];

            for (int j = 0;j < 20;j++) {
                bhight[j] = b.Y + j + 1;
            }
            for (int j = 0;j < 37;j++) {
                phight[j] = y + j + 1;
            }

            for (int l = 0;l < 20;l++){
                for (int j = 0;j < 37;j++){
                    if (bhight[l] == phight[j])
                        isYC = true;
                }
            }


            if ((isXC && isYC)){
                isXC = false;
                isYC = false;
                zh = true;
                break;

            } else {
                g.drawImage(plane,x,y,null);
                isXC = false;
                isYC = false;
            }
        }

        if (zh){
            g.drawImage(blast,x,y,null);
            end_time = System.currentTimeMillis();

            int live_time = (int)((end_time - start_time) / 1000);

            try {
                g.drawString("Your life time is " + live_time + " s",100,100);
                Thread.sleep(5000);
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        if (up){
            try {
                y = p.move("y",-VELOCITY);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } if (down){
            try {
                y = p.move("y",VELOCITY);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } if (left){
            try {
                x = p.move("x",-VELOCITY);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } if (right){
            try {
                x = p.move("x",VELOCITY);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        x = WINDOW_WIDTH / 2;
        y = WINDOW_HEIGHT / 5 * 2;



        FirstGUI f = new FirstGUI();
        f.launchWindows();
    }

}
