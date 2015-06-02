package Game;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AudioThread implements Runnable{

    @Override
    public void run() {

        try {
            Player player = new Player(new FileInputStream("res/planeBM.mp3")); //вызываем метод Play из библиотеки Jlayer
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
