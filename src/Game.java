import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

public class Game extends JLabel implements ActionListener, Comment {
    Timer timer;
    int time = 100;
    boolean isGameOver,isGameStop;
    int totalScore = 0;
    int bestScore = readScoreInFile();
    int [] foodXPos = new int[23];
    int [] foodYPos = new int[23];
    Image food = food2;
    int XPos,YPos;
    Random random = new Random();
    Snake snake = new Snake();
    Game(){
        for (int i = 0; i < foodXPos.length; i++) {
            foodXPos[i] = foodYPos[i] = 20+20*i;
        }
        XPos = random.nextInt(foodXPos.length-1);
        YPos = 6+random.nextInt(foodXPos.length-7);
        timer = new Timer(time,this);
        timer.start();
        this.setPreferredSize(W_H);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,this.getWidth(),this.getHeight());
        g2d.setColor(new Color(0x141414));
        for (int i = 6; i <= 482; i += 17) {
            for (int j = 6; j <= 482; j += 17) {
                g2d.fillRect(i, j, 13, 13);
            }
        }
        g2d.setColor(Color.BLACK);
        g2d.fillRect(20,20,W_h_Game,W_h_Game);
        drawText(g2d);
        g2d.drawImage(food,foodXPos[XPos],foodYPos[YPos],null);
        snake.draw(g2d);
        CheckCollisionHeadBody(g2d);
        if (isGameStop){
            g2d.setColor(new Color(0x838D8D8D, true));
            g2d.fillRect(W_h_Snake, W_h_Snake, W_h_Game, W_h_Game);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            g2d.drawString("Game Stop", 110, 220);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Press Space To Continue", 130, 260);
            timer.stop();
        }
    }
    void CheckCollision(){
        if (snake.boardX[0] == foodXPos[XPos] && snake.boardY[0] == foodYPos[YPos]){
            snake.lengthOfSnake++;
            totalScore+=15;
            if (random.nextInt(2) == 0)food = food1;
            else food = food2;
            if (time>=50){
                time--;
                timer.setDelay(time);
                timer.start();
            }
        }
        for (int i = 0; i < snake.lengthOfSnake; i++) {
            if (snake.boardX[i] == foodXPos[XPos] && snake.boardY[i] == foodYPos[YPos]){
                XPos = random.nextInt(foodXPos.length-1);
                YPos = random.nextInt(foodXPos.length-1);
            }
        }
    }
    void CheckCollisionHeadBody(Graphics2D g2d){
        for (int i = 1; i < snake.lengthOfSnake; i++) {
            if (snake.boardX[i] == snake.boardX[0] && snake.boardY[i] == snake.boardY[0]) {
                {
                    snake.move = false;
                    if (snake.right)
                        g2d.drawImage(lockToRight, snake.boardX[1], snake.boardY[1], W_h_Snake, W_h_Snake, null);
                    else if (snake.left)
                        g2d.drawImage(lockToLeft, snake.boardX[1], snake.boardY[1], W_h_Snake, W_h_Snake, null);
                    else if (snake.top)
                        g2d.drawImage(lockToTop, snake.boardX[1], snake.boardY[1], W_h_Snake, W_h_Snake, null);

                    else if (snake.bottom)
                        g2d.drawImage(lockToBottom, snake.boardX[1], snake.boardY[1], W_h_Snake, W_h_Snake, null);
                    timer.stop();
                    isGameOver = true;
                    snake.isGameOver = true;
                    g2d.setColor(new Color(0x838D8D8D, true));
                    g2d.fillRect(W_h_Snake, W_h_Snake, W_h_Game, W_h_Game);
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Arial", Font.BOLD, 50));
                    g2d.drawString("Game Over", 110, 220);
                    g2d.setFont(new Font("Arial", Font.BOLD, 20));
                    g2d.drawString("Press Space To Restart", 130, 260);
                    repaint();
                    writeScoreInFile();
                }
            }
        }
    }
    public void writeScoreInFile(){
        try {
            FileWriter file = new FileWriter("./score.bin");
            file.write(String.valueOf(bestScore));
            file.close();
        }catch (Exception e){
            System.out.println("error");
        }
    }
    public int readScoreInFile(){
        StringBuilder str = new StringBuilder();
        try {
            FileReader file = new FileReader("./score.bin");
            while (file.ready()){
                str.append((char) file.read());
            }
            file.close();
            if (str.toString().equals("")){
                return 0;
            }else {
                return Integer.parseInt(str.toString());
            }
        }catch (Exception e){
            return 0;
        }
    }
    public void drawText(Graphics2D g2d){
        if (bestScore<=totalScore) {
            bestScore=totalScore;
        }
        g2d.setColor(new Color(0x595858));
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString("Best Score : "+bestScore,30 , W_h_Snake +25);
        g2d.drawString("Total Score : "+totalScore, 30 , W_h_Snake +55);
        //g2d.setColor(new Color(0xE3007D));
        g2d.drawString("Snake Game",  W_h_Game-90, W_h_Snake +25);
        g2d.drawString("By badie",  W_h_Game-60, W_h_Snake +55);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        CheckCollision();
        if (!isGameOver){
            snake.update();
        }
        repaint();
    }
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT->{
                if (!isGameStop) {
                    snake.left = !snake.right;
                    snake.top = snake.bottom = false;
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if (!isGameStop) {
                    snake.move = true;
                    snake.right = !snake.left;
                    snake.top = snake.bottom = false;
                }
            }
            case KeyEvent.VK_UP -> {
                if (!isGameStop) {
                    snake.top = !snake.bottom;
                    snake.left = snake.right = false;
                }
            }
            case KeyEvent.VK_DOWN -> {
                if (!isGameStop) {
                    snake.bottom = !snake.top;
                    snake.left = snake.right = false;
                }
            }
            case KeyEvent.VK_SPACE -> {
                if (!isGameOver && !isGameStop){
                    isGameStop = true;
                } else if (!isGameOver) {
                    timer.start();
                    isGameStop = false;
                }
                if (isGameOver){
                    isGameOver = false;
                    XPos = random.nextInt(foodXPos.length-1);
                    YPos = 6+random.nextInt(foodXPos.length-7);
                    timer.start();
                    totalScore = 0;
                    bestScore = readScoreInFile();
                    snake.init();
                    snake.move = true;
                }
            }
        }
    }
}