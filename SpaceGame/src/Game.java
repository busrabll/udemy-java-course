
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Fire {

    private int x;  //koordinatlar
    private int y;

    public Fire(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

public class Game extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer(1, this);
    private int passingTime = 0;
    private int usedFire = 0;
    private BufferedImage image;

    private ArrayList<Fire> fires = new ArrayList<Fire>();

    private int fire_Y = 1;

    private int ballX = 0;

    private int ball_Y = 2;

    private int spaceShipX = 0;

    private int space_X = 20;

    public boolean control() {

        for (Fire fire : fires) {

            if (new Rectangle(fire.getX(), fire.getY(), 10, 20).intersects(new Rectangle(ballX, 0, 20, 20))) {
                return true;
            }
        }
        return false;
    }

    public Game() {

        try {
            image = ImageIO.read(new FileImageInputStream(new File("space-ship.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

        setBackground(Color.GRAY);

        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.

        passingTime += 5;
        g.setColor(Color.red);
        g.fillOval(ballX, 0, 20, 20);
        g.drawImage(image, spaceShipX, 490, image.getWidth() / 10, image.getHeight() / 10, this);

        for (Fire fire : fires) {
            if (fire.getY() < 0) {
                fires.remove(fire);
            }
        }

        g.setColor(Color.BLUE);

        for (Fire fire : fires) {
            g.fillRect(fire.getX(), fire.getY(), 10, 20);
        }

        if (control()) {
            timer.stop();

            String message = "congratulations you win...\n"
                    + "used fire : " + usedFire
                    + " passing time : " + passingTime / 1000.0 + "second";

            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }

    }

    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int c = e.getKeyCode();

        if (c == KeyEvent.VK_LEFT) {
            if (spaceShipX <= 0) {

                spaceShipX = 0;
            } else {

                spaceShipX -= space_X;
            }

        } else if (c == KeyEvent.VK_RIGHT) {

            if (spaceShipX >= 750) {

                spaceShipX = 750;
            } else {

                spaceShipX += space_X;
            }

        } else if (c == KeyEvent.VK_CONTROL) {

            fires.add(new Fire(spaceShipX + 15, 470));

            usedFire++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (Fire fire : fires) {
            fire.setY(fire.getY() - fire_Y);
        }

        ballX += ball_Y;

        if (ballX >= 750) {
            ball_Y = -ball_Y;
        }
        if (ballX <= 0) {
            ball_Y = -ball_Y;
        }

        repaint();
    }

}
