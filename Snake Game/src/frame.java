import javax.swing.JFrame;
public class frame extends JFrame{

    frame()
    {
        this.setTitle("Snake Game");
        this.setVisible(true);
        this.add(new Panel());
        this.setResizable(false);
        this.pack();
    }

}
