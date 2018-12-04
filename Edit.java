import java.awt.event.MouseListener;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Random;
import java.lang.Math;

public class Edit extends JPanel implements MouseListener, KeyListener{
    private Start parent;
    private String file;
    private Tile[] layout;
    private int selec;
    private Image img;
    private LinkedList<Tile>[] tileList;

    private int wall, ground, gems;

    public Edit(int lvl, Start parent){
        super(new GridLayout(12, 12));
        this.parent = parent;
        this.selec = 0;

        this.wall = 5;
        this.ground = 10;
        this.gems = 6;

        switch(lvl) {
            case 1: this.file = "Created_1.txt";
                break;
            case 2: this.file = "Created_2.txt";
                break;
            case 3: this.file = "Created_3.txt";
                break;
            case 4: this.file = "Created_4.txt";
                break;
            default: this.file = "Created_1.txt";
                break;
        }

        this.showLevel();

        this.layout[135].setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
        
        this.layout[136].setBorder(BorderFactory.createLineBorder(Color.black, 5));
        
        this.layout[137].setBorder(BorderFactory.createLineBorder(Color.black, 5));
        
        this.layout[138].setBorder(BorderFactory.createLineBorder(Color.black, 5));
        
        this.layout[139].setBorder(BorderFactory.createLineBorder(Color.black, 5));

        this.addKeyListener(this);
        this.setFocusable(true);
    }

    private void showLevel(){
        this.layout = new Tile[144];

        try(BufferedReader br = new BufferedReader(new FileReader(this.file))){
            String[] listStr;            
            String temp = br.readLine();
            int cont = 0;
            for(int j=0; j<12; j++){
                listStr = temp.split(",");
                for(int k=0; k<12; k++){
                    this.layout[cont]= new Tile(Integer.parseInt(listStr[k]), cont);
                    this.add(this.layout[cont]);
                    this.layout[cont].addMouseListener(this);
                    if((cont > 133) && (cont < 141)){
                        switch(cont){
                            case 134: this.img = new ImageIcon("Random.png").getImage();
                            break;
                            case 135: this.img = new ImageIcon("Wall.png").getImage();
                            break;
                            case 136: this.img = new ImageIcon("Ground.png").getImage();
                            break;
                            case 137: this.img = new ImageIcon("Goal.png").getImage();
                            break;
                            case 138: this.img = new ImageIcon("Down_1.png").getImage();
                            break;
                            case 139: this.img = new ImageIcon("Move.png").getImage();
                            break;
                            case 140: this.img = new ImageIcon("Save.png").getImage();
                            break;
                            default: this.img = new ImageIcon("Wall.png").getImage();
                            break;
                        }
                        this.layout[cont].setImageFile(this.img);
                    }
                    cont++;
                }
                temp = br.readLine();
            }
        } catch(IOException e){
            System.out.println("No file found.");
        }

        this.revalidate();
    }

    private void setSelected(int n){
        switch(this.selec){
            case 0: this.layout[135].setBorder(BorderFactory.createLineBorder(Color.black, 5));
            break;
            case 1: this.layout[136].setBorder(BorderFactory.createLineBorder(Color.black, 5));
            break;
            case 2: this.layout[137].setBorder(BorderFactory.createLineBorder(Color.black, 5));
            break;
            case 4: this.layout[138].setBorder(BorderFactory.createLineBorder(Color.black, 5));
            break;
            case 5: this.layout[139].setBorder(BorderFactory.createLineBorder(Color.black, 5));
            break;
            default: break;
        }

        switch(n){
            case 135: this.selec = 0;
            break;
            case 136: this.selec = 1;
            break;
            case 137: this.selec = 2;
            break;
            case 138: this.selec = 4;
            break;
            case 139: this.selec = 5;
            break;
            default: break;
        }

        this.layout[n].setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
        this.layout[n].repaint();
    }

    private void saveLevel(){
        try(PrintWriter pw = new PrintWriter(new FileWriter(this.file))){
            int cont = 0;
            for(int i=0; i<12; i++){
                for(int j=0; j<12; j++){
                    pw.print(this.layout[cont].getImageValue() + ",");
                    cont++;
                }
                pw.println();
            }
        } catch(IOException e){
           System.out.println(); 
        }
    }

    private void genRandom(){
        Tile[] ranLayout = this.genRanLayout();
        if(this.checkSolvable(ranLayout)){
            this.layout = ranLayout;
            this.displayLayout();
        } else {
            this.genRandom();
        }
    }

    private Tile[] genRanLayout(){
        this.tileList = new LinkedList[6];
        for(int i=0; i<6; i++){
            this.tileList[i] = new LinkedList<Tile>();
        }

        LinkedList<Integer> optionList = new LinkedList<Integer>();
        optionList.add(2);
        for(int i=0; i<this.ground; i++){
            optionList.add(1);
        }
        for(int i=0; i<this.wall; i++){
            optionList.add(0);
        }

        Tile[] ranLayout = new Tile[144];
        Random gen = new Random();
        int ranNum, index;
        Tile tile;
        boolean aux = true;

        for(int i=0; i<ranLayout.length; i++){
            if((i<12) || (i%12 == 0) || (i%12 == 11) || (i>130)){
                tile = new Tile(0, i);
            } else {
                ranNum = optionList.get(gen.nextInt(optionList.size()));
                tile = new Tile(ranNum, i);
            }
            index = tile.getImageValue();
            ranLayout[i] = tile;
            this.tileList[index].add(tile);

            if((this.tileList[2].size() > this.gems) && aux){
                optionList.set(0, 1);
                if(this.tileList[2].size() > 2){
                    aux = false;
                }
            }
        }

        if(this.tileList[2].size() > 0){
            return ranLayout;
        } else {
            return genRanLayout();
        }
    }

    private boolean checkSolvable(Tile[] array){
        Tile player = null;
        for(int i=0; i<this.tileList[2].size(); i++){
                this.tileList[2].get(i).setAbove(5);
                player = this.getFreeSpace(this.tileList[2].get(i).getPos(), array);                
        }
        if(player == null){
            return false;
        }
        this.tileList[4].add(player);

        boolean temp = false;
        for(int i=0; i<100000; i++){
            if(this.moveRandom(array) && i>80000){
                temp = true;
                break;
            }
        }
        if(!temp){
            return false;
        }

        for(int i=0; i<this.tileList.length; i++){
            for(int j=0; j<this.tileList[i].size(); j++){
                this.layout[this.tileList[i].get(j).getPos()] = this.tileList[i].get(j);
                this.layout[this.tileList[i].get(j).getPos()].setImage();
            }
        }
        return true;
    }

    private boolean moveRandom(Tile[] array){
        try{
            Tile player = this.tileList[4].getFirst();
            int playerX = player.getPos()%12;
            int playerY = player.getPos()/12;
            int x, y;
            Random gen = new Random();
            switch(gen.nextInt(4)){
                case 0: x=0;
                y=1;
                break;
                case 1: x=0;
                y=-1;
                break;
                case 2: x=1;
                y=0;
                break;
                case 3: x=-1;
                y=0;
                break;
                default: x=1;
                y=0;
                break;
            }

            Tile next = array[(playerY*12 + (y*12)) + (playerX + (x))];
            Tile prev = array[(playerY*12 - (y*12)) + (playerX - (x))];

            if((next.getBelow() != 0) && (next.getAbove() == 3)) {
                if(prev.getAbove() == 5){
                    prev.setAbove(3);
                    player.setAbove(5);
                    next.setAbove(4);
                } else{
                    player.setAbove(3);
                    next.setAbove(4);
                }
                this.tileList[4].remove();
                this.tileList[4].add(next);
                if(prev.getBelow() == 2){
                    for(int i=0; i<this.tileList[2].size(); i++){
                        if(this.tileList[2].get(i).getAbove() != 3){
                            return false;
                        }
                    }
                    if(player.getBelow() != 2){
                        return true;
                    }
                }
            }
            return false;
        } catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    private Tile getFreeSpace(int n, Tile[] array){
        if((array[n+1].getAbove() == 3) && (array[n+1].getBelow() != 2) && (array[n+1].getBelow() != 0)){
            return array[n+1];
        } else if((array[n-1].getAbove() == 3) && (array[n-1].getBelow() != 2) && (array[n-1].getBelow() != 0)){
            return array[n-1];
        } else if((array[n+12].getAbove() == 3) && (array[n+12].getBelow() != 2) && (array[n+12].getBelow() != 0)){
            return array[n+12];
        } else if((array[n-12].getAbove() == 3) && (array[n-12].getBelow() != 2) && (array[n-12].getBelow() != 0)){
            return array[n-12];
        } else {
            return null;
        }
    }

    private void displayLayout(){
        this.removeAll();
        for(int i=0; i<this.layout.length; i++){
            this.add(this.layout[i]);
            this.layout[i].addMouseListener(this);
            if((i > 133) && (i < 141)){
                switch(i){
                    case 134: this.img = new ImageIcon("Random.png").getImage();
                    break;
                    case 135: this.img = new ImageIcon("Wall.png").getImage();
                    break;
                    case 136: this.img = new ImageIcon("Ground.png").getImage();
                    break;
                    case 137: this.img = new ImageIcon("Goal.png").getImage();
                    break;
                    case 138: this.img = new ImageIcon("Down_1.png").getImage();
                    break;
                    case 139: this.img = new ImageIcon("Move.png").getImage();
                    break;
                    case 140: this.img = new ImageIcon("Save.png").getImage();
                    break;
                    default: this.img = new ImageIcon("Wall.png").getImage();
                    break;
                }
            this.layout[i].setImageFile(this.img);
            }
        }
        this.layout[135].setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
        
        this.layout[136].setBorder(BorderFactory.createLineBorder(Color.black, 5));
        
        this.layout[137].setBorder(BorderFactory.createLineBorder(Color.black, 5));
        
        this.layout[138].setBorder(BorderFactory.createLineBorder(Color.black, 5));
        
        this.layout[139].setBorder(BorderFactory.createLineBorder(Color.black, 5));

        this.selec = 0;
        this.revalidate();
    }

    public void mouseClicked(MouseEvent e){
        Object o = e.getSource();
        if(o instanceof Tile){
            int pos = ((Tile) o).getPos();
            if((pos > 133) && (pos < 141)){
                if(pos == 134){
                    this.genRandom();
                } else if(pos == 140){
                    this.saveLevel();
                } else {
                    this.setSelected(pos);
                }
            } else if (!((pos < 12) || (pos%12 == 0) || (pos %12 == 11) || (pos > 131))){
                ((Tile) o).setImageNum(this.selec);
            }
        }
    }
    public void mousePressed(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){

    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

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