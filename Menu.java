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

public class Menu extends JPanel implements ActionListener{
    private Start parent;
    private Image menu;
    private JButton start;
    private JButton create;

    public Menu(Start parent) {
        super();
        this.setLayout(null);
        this.setPreferredSize(new Dimension(768, 768));
        this.parent = parent;
        this.menu = new ImageIcon("Menu.png").getImage();

        start = new JButton("Start");        
        start.setFont(new Font("Fixedsys", Font.PLAIN, 15));
        start.setSize(100, 50);        
        this.add(start);
        start.setLocation(234, 512);
        start.addActionListener(this);
        
        create = new JButton("Create");
        create.setFont(new Font("Fixedsys", Font.PLAIN, 15));
        create.setSize(100, 50);
        this.add(create);
        create.setLocation(434, 512);
        create.addActionListener(this);

        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.menu, 0, 0, this);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.start){
            this.parent.loadMap(new Levels(this.parent));
        } else if(e.getSource() == this.create){
            this.parent.loadMap(new CreateMenu(this.parent));
        }
    }
}