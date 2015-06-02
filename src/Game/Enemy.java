package Game;


import javax.swing.*;
import java.awt.*;

public class Enemy {

    int x;
    int y;
    int v;

    Image image = new ImageIcon("res/enemy.png").getImage();

    Sky sky;

    public Rectangle geyRect() {               //метод для прорисовки тела игрока
        return new Rectangle(x, y, 75, 75);
    }

    public Enemy(int x, int y, int v, Sky sky) {
        this.x = x;
        this.y = y;
        this.v = v;
        this.sky = sky;
    }

    public void moveEnemy() {
        x = x - sky.plane.v + v;  //указали скорость самолетов-врагов
    }

}
