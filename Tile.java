import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Tile extends JPanel{
    private int above; // 3-Nothing 4-Player 5-Movable
    private int below; // 0-Wall 1-Ground 2-Goal
    private int pos;
    private int facing; // -2-Up 1-Right 2-Down -1-Left
    private Image image;

    public Tile(int value, int pos){
        super();

        if(value < 3){
            this.below = value;
            this.above = 3;
        } else{
            this.below = 1;
            this.above = value;
        }
        this.pos = pos;
        this.facing = 2;

        this.setImage();
        this.repaint();
    }

    public void setImage(){
        int imageValue = this.getImageValue();
        switch(imageValue){
            case 0: this.image = new ImageIcon("Wall.png").getImage();
                break;
            case 1: this.image = new ImageIcon("Ground.png").getImage();
                break;
            case 2: this.image = new ImageIcon("Goal.png").getImage();
                break;
            case 4: switch(this.facing){
                        case 2: this.image = new ImageIcon("Down_1.png").getImage();
                            break;
                        case -2: this.image = new ImageIcon("Up_1.png").getImage();
                            break;
                        case 1: this.image = new ImageIcon("Right_1.png").getImage();
                            break;
                        case -1: this.image = new ImageIcon("Left_1.png").getImage();
                            break;
                    }
                break;
            case 5: this.image = new ImageIcon("Move.png").getImage();
                break;
            default: this.image = new ImageIcon("Ground.png").getImage();
                break;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this);
    }

    public int getImageValue(){
        if(this.above == 3){
            return this.below;
        } else{
            return this.above;
        }
    }

    public void setImageNum(int n){
        if(n<3){
            this.above = 3;
            this.below = n;
        } else{
            this.above = n;
            this.below = 1;
        }
        this.setImage();
        this.repaint();
    }

    public void setImageFile(Image img){
        this.image = img;
        this.repaint();
    }

    public void setFacing(int facing){
        this.facing = facing;
    }
    public int getAbove(){
        return this.above;
    }
    public void setAbove(int above){
        this.above = above;
    }
    public int getBelow(){
        return this.below;
    }
    public void setBelow(int below){
        this.below = below;
    }
    public int getPos(){
        return this.pos;
    }
    public void setPos(int pos){
        this.pos = pos;
    }
}