import java.awt.*;

public class Snake implements Comment {
    final int[] boardX = new int[484];
    final int[] boardY = new int[484];
    boolean  move,left, right, top, bottom;
    Image lock = lockToRight;
    boolean isGameOver =false;
    int lengthOfSnake =3;

    Snake() {
        right = true;
        boardX[2] = 40;
        boardX[1] = 60;
        boardX[0] = 80;
        boardY[2] = 100;
        boardY[1] = 100;
        boardY[0] = 100;
    }
    void init(){
        lock = lockToRight;
        isGameOver = false;
        left = top = bottom = false;
        right = true;
        lengthOfSnake = 3;
        boardX[2] = 40;
        boardX[1] = 60;
        boardX[0] = 80;
        boardY[2] = 100;
        boardY[1] = 100;
        boardY[0] = 100;
    }
    void draw(Graphics2D g2d) {
        if(!isGameOver)
            g2d.drawImage(lock, boardX[0], boardY[0],W_h_Snake,W_h_Snake, null);
        for (int i = 1; i < lengthOfSnake; i++) {
            g2d.drawImage(body, boardX[i], boardY[i],W_h_Snake,W_h_Snake, null);
        }
    }

    void update() {
        if (move){
            if (right) {
                lock = lockToRight;
                for (int i = lengthOfSnake - 1; i >= 0; i--)
                    boardY[i + 1] = boardY[i];

                for (int i = lengthOfSnake; i >= 0; i--)
                {
                    if (i == 0)
                        boardX[i] = boardX[i] + W_h_Snake;
                    else
                        boardX[i] = boardX[i - 1];


                    if (boardX[i] > W_h_Game)
                        boardX[i] = W_h_Snake;
                }

            }
            else if (left) {
                lock = lockToLeft;
                for (int i = lengthOfSnake - 1; i >= 0; i--)
                    boardY[i + 1] = boardY[i];
                for (int i = lengthOfSnake; i >= 0; i--) {
                    if (i == 0)
                        boardX[i] = boardX[i] - W_h_Snake;

                    else
                        boardX[i] = boardX[i - 1];

                    if (boardX[i] < W_h_Snake)
                        boardX[i] = W_h_Game;
                }
            } else if (top) {
                lock = lockToTop;
                for (int i = lengthOfSnake - 1; i >= 0; i--)
                    boardX[i + 1] = boardX[i];

                for (int i = lengthOfSnake; i >= 0; i--)
                {
                    if (i == 0)
                        boardY[i] = boardY[i] - W_h_Snake;
                    else
                        boardY[i] = boardY[i - 1];

                    if (boardY[i] < W_h_Snake)
                        boardY[i] = W_h_Game;
                }
            }else if (bottom){
                lock = lockToBottom;
                for (int i = lengthOfSnake - 1; i >= 0; i--)
                    boardX[i + 1] = boardX[i];
                for (int i = lengthOfSnake; i >= 0; i--)
                {
                    if (i == 0)
                        boardY[i] = boardY[i] + W_h_Snake;
                    else
                        boardY[i] = boardY[i - 1];

                    if (boardY[i] > W_h_Game)
                        boardY[i] = W_h_Snake;
                }
            }
        }
    }
}