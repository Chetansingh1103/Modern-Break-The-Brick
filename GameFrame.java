import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    ImageIcon logo=new ImageIcon("D:\\onedrive\\Desktop\\brickbreaker2\\brick2.png");
        GameFrame(){
            this.add(new GamePlay());
            this.setTitle("Brick Ball Out");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setIconImage(logo.getImage());
            this.pack();
            this.setResizable(false);
            this.setVisible(true);
            this.setLocationRelativeTo(null);
        }
}
