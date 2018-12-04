import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class CreateMenu extends JPanel implements ActionListener, KeyListener{
    private Start parent;
    private Image bg;
    private JButton play_1, play_2, play_3, play_4;
    private JButton edit_1, edit_2, edit_3, edit_4;

    public CreateMenu(Start parent){
        super();
        this.parent = parent;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(768, 768));
        this.bg = new ImageIcon("CreateBG.jpg").getImage();



        play_1 = new JButton("Play");     
        play_1.setFont(new Font("Fixedsys", Font.PLAIN, 15));
        play_1.setSize(100, 50);        
        this.add(play_1);
        play_1.setLocation(25, 125);
        play_1.addActionListener(this);
        this.checkLvl(play_1);

        edit_1 = new JButton("Edit");     
        edit_1.setFont(new Font("Fixedsys", Font.PLAIN, 15));
        edit_1.setSize(100, 50);        
        this.add(edit_1);
        edit_1.setLocation(150, 125);
        edit_1.addActionListener(this);
        


        play_2 = new JButton("Play");     
        play_2.setFont(new Font("Fixedsys", Font.PLAIN, 15));
        play_2.setSize(100, 50);        
        this.add(play_2);
        play_2.setLocation(350, 125);
        play_2.addActionListener(this);
        this.checkLvl(play_2);

        edit_2 = new JButton("Edit");     
        edit_2.setFont(new Font("Fixedsys", Font.PLAIN, 15));
        edit_2.setSize(100, 50);        
        this.add(edit_2);
        edit_2.setLocation(475, 125);
        edit_2.addActionListener(this);



        play_3 = new JButton("Play");     
        play_3.setFont(new Font("Fixedsys", Font.PLAIN, 15));
        play_3.setSize(100, 50);        
        this.add(play_3);
        play_3.setLocation(25, 525);
        play_3.addActionListener(this);
        this.checkLvl(play_3);

        edit_3 = new JButton("Edit");     
        edit_3.setFont(new Font("Fixedsys", Font.PLAIN, 15));
        edit_3.setSize(100, 50);        
        this.add(edit_3);
        edit_3.setLocation(150, 525);
        edit_3.addActionListener(this);



        play_4 = new JButton("Play");     
        play_4.setFont(new Font("Fixedsys", Font.PLAIN, 15));
        play_4.setSize(100, 50);        
        this.add(play_4);
        play_4.setLocation(350, 525);
        play_4.addActionListener(this);
        this.checkLvl(play_4);

        edit_4 = new JButton("Edit");     
        edit_4.setFont(new Font("Fixedsys", Font.PLAIN, 15));
        edit_4.setSize(100, 50);        
        this.add(edit_4);
        edit_4.setLocation(475, 525);
        edit_4.addActionListener(this);



        this.addKeyListener(this);
        this.setFocusable(true);
        this.repaint();
    }

    private void checkLvl(JButton btn){
        String file = "";
        if(btn == this.play_1){
            file = "Created_1.txt";
        } else if(btn == this.play_2){
            file = "Created_2.txt";
        } else if(btn == this.play_3){
            file = "Created_3.txt";
        } else if(btn == this.play_4){
            file = "Created_4.txt";
        }
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            int[] array = {0,0,0,0,0,0};
            String temp = br.readLine();
            String[] line;
            for(int i=0; i<11; i++){
                line = temp.split(",");
                for(int j=0; j<line.length; j++){
                    array[Integer.parseInt(line[j])]++;
                }
                temp = br.readLine();
            }
            if((array[4] == 1) && (array[5] == array[2])){
                btn.setEnabled(true);
            } else{
                btn.setEnabled(false);
            }
        } catch(IOException e){
            System.out.println("No file found.");
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.bg, 0, 0, this);
        g.setFont(new Font("Fixedsys", Font.PLAIN, 25));
        g.setColor(Color.WHITE);
        g.drawString("Custom 1", 97, 100);
        g.drawString("Custom 2", 422, 100);
        g.drawString("Custom 3", 97, 500);
        g.drawString("Custom 4", 422, 500);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.play_1){
            this.parent.loadMap(new CreatePlay(1, this.parent));
        } else if(e.getSource() == this.play_2){
            this.parent.loadMap(new CreatePlay(2, this.parent));
        } else if(e.getSource() == this.play_3){
            this.parent.loadMap(new CreatePlay(3, this.parent));
        } else if(e.getSource() == this.play_4){
            this.parent.loadMap(new CreatePlay(4, this.parent));
        } else if(e.getSource() == this.edit_1){
            this.parent.loadMap(new Edit(1, this.parent));
        } else if(e.getSource() == this.edit_2){
            this.parent.loadMap(new Edit(2, this.parent));
        } else if(e.getSource() == this.edit_3){
            this.parent.loadMap(new Edit(3, this.parent));
        } else if(e.getSource() == this.edit_4){
            this.parent.loadMap(new Edit(4, this.parent));
        }
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE){
           this.parent.loadMap(new Menu(this.parent));
        }
    }

    public void keyReleased(KeyEvent e){
        
    }

    public void keyTyped(KeyEvent e){
        
    }
}