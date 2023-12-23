import java.awt.*;

public interface Comment {
    Image lockToLeft = Main.MyFrame.getImg("img/lockToLeft.png");
    Image lockToRight = Main.MyFrame.getImg("img/lockToRight.png");
    Image lockToTop = Main.MyFrame.getImg("img/lockToTop.png");
    Image lockToBottom = Main.MyFrame.getImg("img/lockToBottom.png");
    Image body = Main.MyFrame.getImg("img/body.png");
    Image food1 = Main.MyFrame.getImg("img/food.png");
    Image food2 = Main.MyFrame.getImg("img/food2.png");
    Image logo = Main.MyFrame.getImg("img/logo.png");
    int W_h_Snake = 20;
    int W_h_Game = 460;
    Dimension W_H = new Dimension(500,500);
}
