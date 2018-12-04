import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import javax.swing.JPanel;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.event.KeyEvent;

public class CreatePlay extends JPanel implements KeyListener{
    private Start parent;
    private Tile[] layout;
    private LinkedList<Tile>[] tileList;
    private String file;
    private int current;

    public CreatePlay(int lvl, Start parent){
        super(new GridLayout(12, 12));
        this.parent = parent;
        this.current = lvl;

        switch(this.current) {
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

        this.loadLevel();
        this.addKeyListener(this);
        this.setFocusable(true);
    }

    private void loadLevel(){
        this.layout = new Tile[144];
        this.resetFile();
        this.tileList = new LinkedList[6];
        for(int i=0; i<6; i++){
            this.tileList[i] = new LinkedList<Tile>();
        }

        Tile tile;
        int index;
        this.removeAll();
        this.revalidate();
        for(int i=0; i<this.layout.length; i++){
            tile = this.layout[i];
            this.add(tile);
            index = tile.getImageValue();
            this.tileList[index].add(tile);
        }
    }

    private void resetFile(){
        try(BufferedReader br = new BufferedReader(new FileReader(this.file))){
            String[] listStr;            
            String temp = br.readLine();
            int cont = 0;
            for(int j=0; j<12; j++){
                listStr = temp.split(",");
                for(int k=0; k<12; k++){
                    this.layout[cont]= new Tile(Integer.parseInt(listStr[k]), cont);
                    cont++;
                }
                temp = br.readLine();
            }
        } catch(IOException e){
            System.out.println("No file found.");
        }
    }

    private void movePlayer(int x, int y){
        try{
            Tile player = this.tileList[4].getFirst();
            int playerX = player.getPos()%12;
            int playerY = player.getPos()/12;        
            Tile next = this.layout[(playerY*12 + (y*12)) + (playerX + (x))];
            Tile nextNext = this.layout[(playerY*12 + 2*(y*12)) + (playerX + (2*x))];

            if(next.getBelow() != 0){
                if(next.getAbove() == 3){
                    player.setAbove(3);
                    player.setImage();
                    player.repaint();

                    this.tileList[next.getImageValue()].remove(next);

                    next.setAbove(4);
                    next.setFacing(x + 2*y);
                    next.setImage();
                    next.repaint();

                    this.tileList[4].add(next);
                    this.tileList[player.getImageValue()].add(tileList[4].removeFirst());
                } 
                if(next.getAbove() == 5 && nextNext.getAbove() == 3 && nextNext.getBelow() != 0){
                    player.setAbove(3);
                    player.setImage();
                    player.repaint();

                    this.tileList[next.getImageValue()].remove(next);

                    next.setAbove(4);
                    next.setFacing(x + 2*y);                    
                    next.setImage();
                    next.repaint();

                    this.tileList[4].add(next);
                    this.tileList[player.getImageValue()].add(tileList[4].removeFirst());

                    if(nextNext.getBelow() == 1){
                        nextNext.setAbove(5);
                        nextNext.setImage();
                        nextNext.repaint();

                        this.tileList[1].remove(nextNext);
                        this.tileList[5].add(nextNext);
                    } else if(nextNext.getBelow() == 2){
                        nextNext.setAbove(5);
                        nextNext.setImage();
                        nextNext.repaint();
                        
                        this.tileList[2].remove(nextNext);
                        this.tileList[5].add(nextNext);

                        for(Tile tile : this.layout){
                            if(tile.getBelow() == 2 && tile.getAbove() != 5){
                                return;
                            }
                        }
                        this.parent.loadMap(new Menu(this.parent));
                    }
                }
            }
        } catch (IndexOutOfBoundsException e){
            return;
        }
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
    	if (key == KeyEvent.VK_R) {
            this.loadLevel();
        } else if (key == KeyEvent.VK_ESCAPE){
            this.parent.loadMap(new Menu(this.parent));
        } else if (key == KeyEvent.VK_RIGHT){
            this.movePlayer(1, 0);
        } else if (key == KeyEvent.VK_DOWN){
            this.movePlayer(0, 1);
        } else if (key == KeyEvent.VK_LEFT){
            this.movePlayer(-1, 0);
        } else if (key == KeyEvent.VK_UP){
            this.movePlayer(0, -1);
        }
    }

    public void keyReleased(KeyEvent e){
        
    }

    public void keyTyped(KeyEvent e){
        
    }
}