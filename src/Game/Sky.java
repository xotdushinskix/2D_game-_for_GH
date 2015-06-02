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


public class Sky extends JPanel implements ActionListener, Runnable{ //Runnablle-интерфейс для создания нового потока

    Timer timer = new Timer(20, this);  //каждые 20 миллисекунд будет выполнять ф-ю ActionPerformed у этого элемента

    Image image = new ImageIcon("res/background-sky.png").getImage();

    Plane plane = new Plane();

    Thread enemiesFactory = new Thread(this);  //Новый поток для создания врагов

    Thread audioThread = new Thread(new AudioThread()); //новый поток для аудиотрека

    ArrayList<Enemy>enemies = new ArrayList<Enemy>(); //коллекция для хранения врагов

    public Sky() {
        timer.start();   //запускаем таймер
        enemiesFactory.start(); //запускаем поток-врага
        audioThread.start(); //запускаем аудио-поток
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
    }

    @Override
    public void run() {  //точка входа во второй поток врагов
        while (true) {
            Random random = new Random();
            try {
                Thread.sleep(2000);  //появления врагов будет производиться от 2х секунд
                enemies.add(new Enemy(1200, random.nextInt(600), random.nextInt(60), this )); //добавили новых врагов(положение по х,по у, скорость, указали на данный обьект)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyKeyAdapter extends KeyAdapter {  //класс,реализующий методы для работы с клавиатуры
        public void keyPressed(KeyEvent e) {  //когда клавиша нажата
            plane.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {  //когда клавиша освобождена
            plane.keyReleased(e);
        }

    }

    public void paint(Graphics g) {
        g = (Graphics2D) g;
        g.drawImage(image, plane.layer1, 0, null); //координата первого фонового изображения
        g.drawImage(image, plane.layer2, 0, null); //координата второго фонового изображения
        g.drawImage(plane.img, plane.x, plane.y, null); //координата самолета

        //задаем спидометр
        double v = (70/plane.MAX_V) * plane.v;  //км\час в одном пикселе * скорость самолета
        g.setColor(Color.RED);
        Font font = new Font("Arial", Font.ITALIC, 20); //шрифт
        g.setFont(font);
        g.drawString("Speed: " + v + " m/h", 345, 40);

        Iterator<Enemy>iterator = enemies.iterator();  //пробегаемся по коллекции с набором врагов
        while (iterator.hasNext()) {
            Enemy e = iterator.next();
            if (e.x >= 1566 || e.y <= -1566){   //если обьекты-враги выходят за границы слоя,то они удаляются
                iterator.remove();
            }
            e.moveEnemy();
            g.drawImage(e.image, e.x, e.y, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        plane.move(); //вызывает метод move
        repaint(); //перерисовка, вызывает paint
        testCollisionWithEnemies();
        testWin();
    }

    private void testWin() {

        if (plane.s > 300000) {
            JOptionPane.showMessageDialog(null, "You win!");
            System.exit(0);
        }

    }

    private void testCollisionWithEnemies() {   //метод для проверки столкновения игрока с противником

        Iterator<Enemy>iterator = enemies.iterator();  //пробегаемся по коллекции с набором врагов
        while (iterator.hasNext()) {
            Enemy e = iterator.next();  //получаем очередной обьект коллекции
            if (plane.geyRect().intersects(e.geyRect())) {   //если обьекты(игрок с врагом) пересекаются друг с другом
                JOptionPane.showMessageDialog(null, "You lost!");    //то выводим сообщение
                System.exit(1);
            }
        }

    }
}
