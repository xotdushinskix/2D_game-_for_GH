package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class Plane {

    int v = 0;  //��������
    int dv = 0;  //���������
    int s = 0; //���������� �������� ����

    int x = 50; //���������� ��������
    int y = 100; //���������� ��������
    int dy = 0; //���������� ����������� �� ��� �

    int layer1 = 0; //���������� ������� �������� �����������
    int layer2 = 1366; //���������� ������� �������� �����������

    public final static int MAX_V = 70; //������������ ��������
    public final static int MAX_TOP = 10;  //������������ ������
    public final static int MAX_BOTTOM = 570;  //������������ �������

    Image img = new ImageIcon("res/plane1.png").getImage();

    public Rectangle geyRect() {                    //����� ��� ���������� ���� ����������
        return new Rectangle(x, y, 75, 75);
    }

    public void move() {

        s += v;  //���������� ����������� ����
        v += dv; //�������� ���������
        y -= dy; //����������� �� ��� �

        if (v <= 0) v = 0;  //������� ��������
        if (v >= MAX_V) v = MAX_V; //������� ������������ ��������

        if (y <= MAX_TOP) y = MAX_TOP;  //������� ������������ ������
        if (y >= MAX_BOTTOM) y =MAX_BOTTOM;   //������� ������������ �������

        if (layer2 - v <= 0) {  //����������� 2 ���� �����������,��������� �� � ��������� �����������
            layer1 = 0;
            layer2 = 1366;
        } else {
            layer1 -= v; //���������� ������� �������� ����������� �����������,�� ���� ����� ��������� ������� ��������
            layer2 -= v; //���������� ��������������� ����������� �����������,�� ���� ����� ��������� ������� ��������
        }
    }

    public void keyPressed(KeyEvent e) {  //����� ��� ��������� �������,��������� � �������� �������
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {  //��� ������ ������� �� ������� "�����" ��������� �������������
            dv = 1;
        }
        if (key == KeyEvent.VK_LEFT) {   //��� ������ ������� �� ������� "����" ��������� �����������
            dv = -1;
        }
        if (key == KeyEvent.VK_UP) {   //��� ������ ������� �� ������� "�����" ��������� �������� ��������
            dy = 15;

        }
        if (key == KeyEvent.VK_DOWN) {   //��� ������ ������� �� ������� "����" ��������� �������� ��������
            dy = -15;
         }
    }

    public void keyReleased(KeyEvent e) {  //����� ��� ��������� �������,��������� � ������������� �������
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT) {  //��� ���������� ������ "����" � "�����" ��������� dv ���������������
            dv = 0;
        }
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {   //��� ������ ������� �� ������� "����" ��������� �������� ��������
            dy = 0;
           }
    }

}
