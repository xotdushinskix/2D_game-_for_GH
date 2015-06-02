package Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.*;


public class Sky extends JPanel implements ActionListener, Runnable{ //Runnablle-��������� ��� �������� ������ ������

    Timer timer = new Timer(20, this);  //������ 20 ����������� ����� ��������� �-� ActionPerformed � ����� ��������

    Image image = new ImageIcon("res/background-sky.png").getImage();

    Plane plane = new Plane();

    Thread enemiesFactory = new Thread(this);  //����� ����� ��� �������� ������

    Thread audioThread = new Thread(new AudioThread()); //����� ����� ��� ����������

    ArrayList<Enemy>enemies = new ArrayList<Enemy>(); //��������� ��� �������� ������

    public Sky() {
        timer.start();   //��������� ������
        enemiesFactory.start(); //��������� �����-�����
        audioThread.start(); //��������� �����-�����
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
    }

    @Override
    public void run() {  //����� ����� �� ������ ����� ������
        while (true) {
            Random random = new Random();
            try {
                Thread.sleep(2000);  //��������� ������ ����� ������������� �� 2� ������
                enemies.add(new Enemy(1200, random.nextInt(600), random.nextInt(60), this )); //�������� ����� ������(��������� �� �,�� �, ��������, ������� �� ������ ������)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyKeyAdapter extends KeyAdapter {  //�����,����������� ������ ��� ������ � ����������
        public void keyPressed(KeyEvent e) {  //����� ������� ������
            plane.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {  //����� ������� �����������
            plane.keyReleased(e);
        }

    }

    public void paint(Graphics g) {
        g = (Graphics2D) g;
        g.drawImage(image, plane.layer1, 0, null); //���������� ������� �������� �����������
        g.drawImage(image, plane.layer2, 0, null); //���������� ������� �������� �����������
        g.drawImage(plane.img, plane.x, plane.y, null); //���������� ��������

        //������ ���������
        double v = (70/plane.MAX_V) * plane.v;  //��\��� � ����� ������� * �������� ��������
        g.setColor(Color.RED);
        Font font = new Font("Arial", Font.ITALIC, 20); //�����
        g.setFont(font);
        g.drawString("Speed: " + v + " m/h", 345, 40);

        Iterator<Enemy>iterator = enemies.iterator();  //����������� �� ��������� � ������� ������
        while (iterator.hasNext()) {
            Enemy e = iterator.next();
            if (e.x >= 1566 || e.y <= -1566){   //���� �������-����� ������� �� ������� ����,�� ��� ���������
                iterator.remove();
            }
            e.moveEnemy();
            g.drawImage(e.image, e.x, e.y, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        plane.move(); //�������� ����� move
        repaint(); //�����������, �������� paint
        testCollisionWithEnemies();
        testWin();
    }

    private void testWin() {

        if (plane.s > 300000) {
            JOptionPane.showMessageDialog(null, "You win!");
            System.exit(0);
        }

    }

    private void testCollisionWithEnemies() {   //����� ��� �������� ������������ ������ � �����������

        Iterator<Enemy>iterator = enemies.iterator();  //����������� �� ��������� � ������� ������
        while (iterator.hasNext()) {
            Enemy e = iterator.next();  //�������� ��������� ������ ���������
            if (plane.geyRect().intersects(e.geyRect())) {   //���� �������(����� � ������) ������������ ���� � ������
                JOptionPane.showMessageDialog(null, "You lost!");    //�� ������� ���������
                System.exit(1);
            }
        }

    }
}
