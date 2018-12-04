import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.IOException;

public class Start extends JFrame{
    private JPanel current;

    public Start () {
        super("Sokoban");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.current = null;
        this.loadMap(new Menu(this));
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    public void loadMap(JPanel map) {
        if(this.current != null){
            this.remove(this.current);
        }
        this.add(map);
        this.current = map;
        this.validate();
        this.current.requestFocus();
    }

    public static void main(String[] args) {
        Start game = new Start();
    }
}