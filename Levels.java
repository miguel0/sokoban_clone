import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import javax.swing.JPanel;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.event.KeyEvent;

public class Levels extends JPanel implements KeyListener{
    private Tile[][] levels;
    private Tile[] layout;
    private LinkedList<Tile>[] tileList;
    private int current;
    private Start parent;

    public Levels(Start parent) {
        super(new GridLayout(12, 12));
        this.parent = parent;
        this.current = 0;

        this.loadLevel(this.current);
        this.addKeyListener(this);
        this.setFocusable(true);
    }

    private void loadLevel(int n){
        this.levels = new Tile[5][144];
        this.layout = new Tile[144];
        this.current = n;

        this.resetFile();

        this.layout = this.levels[n];
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
        try(BufferedReader br = new BufferedReader(new FileReader("Levels.txt"))){
            String temp;
            String[] listStr;
            int cont;
            for(int i=0; i<this.levels.length; i++){
                temp = br.readLine();
                cont = 0;
                for(int j=0; j<12; j++){
                    listStr = temp.split(",");
                    for(int k=0; k<12; k++){
                        this.levels[i][cont]= new Tile(Integer.parseInt(listStr[k]), cont);
                        cont++;
                    }
                    temp = br.readLine();
                }
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
                        if(this.current < 4){
                            this.loadLevel(++this.current);
                        } else if(this.current == 4){
                            this.parent.loadMap(new Win(this.parent));
                        }
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
            this.loadLevel(this.current);
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