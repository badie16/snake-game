import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class Main{
    public static void main(String[] args) {
       new MyFrame();
    }
    static class MyFrame extends JFrame{
        Game game = new Game();
        MyFrame(){
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);
            this.setLayout(new FlowLayout());
            this.getContentPane().setBackground(Color.BLACK);
            this.add(game);
            this.setTitle("Snake Game");
            this.setIconImage(Comment.logo);
            this.pack();
            this.setVisible(true);
            this.setResizable(false);
            this.addKeyListener(new TAdapter());
            try {
                AudioInputStream audio = AudioSystem.getAudioInputStream(Objects.requireNonNull(Main.class.getResource("img/music.wav")));
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        private class TAdapter extends KeyAdapter {
            @Override
            public void keyPressed(KeyEvent e) {
                game.keyTyped(e);
            }
        }
        static Image getImg(String str){
            ImageIcon img = new ImageIcon(Objects.requireNonNull(MyFrame.class.getResource(str)));
            return img.getImage();
        }
    }
}