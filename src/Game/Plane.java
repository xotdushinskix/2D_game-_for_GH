package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class Plane {

    int v = 0;  //скорость
    int dv = 0;  //ускорение
    int s = 0; //абсолютная величина пути

    int x = 50; //координата самолета
    int y = 100; //координата самолета
    int dy = 0; //координата перемещения по оси у

    int layer1 = 0; //координата первого фонового изображение
    int layer2 = 1366; //координата второго фонового изображение

    public final static int MAX_V = 70; //максимальная скорость
    public final static int MAX_TOP = 10;  //максимальная высота
    public final static int MAX_BOTTOM = 570;  //максимальная глубина

    Image img = new ImageIcon("res/plane1.png").getImage();

    public Rectangle geyRect() {                    //метод для прорисовки тела противника
        return new Rectangle(x, y, 75, 75);
    }

    public void move() {

        s += v;  //накопление пройденного пути
        v += dv; //контроль ускорения
        y -= dy; //перемещение по оси у

        if (v <= 0) v = 0;  //условие скорости
        if (v >= MAX_V) v = MAX_V; //условие максимальной скорости

        if (y <= MAX_TOP) y = MAX_TOP;  //условие максимальной высоты
        if (y >= MAX_BOTTOM) y =MAX_BOTTOM;   //условие максимальной глубины

        if (layer2 - v <= 0) {  //зацикливаем 2 слоя изображения,передавая их к начальным координатам
            layer1 = 0;
            layer2 = 1366;
        } else {
            layer1 -= v; //координаты первого фонового изображения уменьшились,за счет этого создается иллюзия движения
            layer2 -= v; //координаты второгофонового изображения уменьшились,за счет этого создается иллюзия движения
        }
    }

    public void keyPressed(KeyEvent e) {  //метод для обработки событий,связанный с нажатием клавиши
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {  //при каждом нажатии на клавишу "право" ускорение увеличивается
            dv = 1;
        }
        if (key == KeyEvent.VK_LEFT) {   //при каждом нажатии на клавишу "лево" ускорение уменьшается
            dv = -1;
        }
        if (key == KeyEvent.VK_UP) {   //при каждом нажатии на клавишу "вверх" положение самолета меняется
            dy = 15;

        }
        if (key == KeyEvent.VK_DOWN) {   //при каждом нажатии на клавишу "вниз" положение самолета меняется
            dy = -15;
         }
    }

    public void keyReleased(KeyEvent e) {  //метод для обработки событий,связанный с освобождением клавиши
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT) {  //при освождение клавиш "лево" и "право" ускорение dv останавливается
            dv = 0;
        }
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {   //при каждом нажатии на клавишу "вниз" положение самолета меняется
            dy = 0;
           }
    }

}
