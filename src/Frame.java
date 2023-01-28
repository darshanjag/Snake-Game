import javax.swing.JFrame;
import java.awt.Panel;

public class Frame extends JFrame{
    Frame(){


        this.add(new SnakePanel());
        this.setTitle("Snake");
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
