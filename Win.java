import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Win extends JPanel implements ActionListener{
    private Start parent;
    private Image win;

    public Win(Start parent) {
        super();
        this.setLayout(null);
        this.setPreferredSize(new Dimension(768, 768));
        this.parent = parent;
        this.win = new ImageIcon("Win.jpg").getImage();

        JButton back = new JButton("Back");        
        back.setFont(new Font("Fixedsys", Font.PLAIN, 15));
        back.setSize(100, 50);        
        this.add(back);
        back.setLocation(334, 512);
        back.addActionListener(this);

        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.win, 0, 0, this);
    }

    public void actionPerformed(ActionEvent e){
        this.parent.loadMap(new Menu(this.parent));
    }
}