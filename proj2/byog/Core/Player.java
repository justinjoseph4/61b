package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Random;

//Class for the player
public class Player implements Serializable {
    int xpos;
    int ypos;
    TETile[][] world;
    Random random;
    boolean gameOver = false;
    boolean key = false;

    //Constructor for the player object
    public Player(int x, int y, TETile[][] w, Random r) {
        random = r;
        this.xpos = x;
        this.ypos = y;
        world = w;
        world[x][y] = Tileset.PLAYER;
    }

    //Moves player up
    private void moveUp() {
        world[xpos][ypos+1] = Tileset.PLAYER;
        world[xpos][ypos] = floor();
        this.ypos += 1;
    }

    //Moves player left
    private void moveLeft() {
        world[xpos-1][ypos] = Tileset.PLAYER;
        world[xpos][ypos] = floor();
        this.xpos -= 1;
    }

    //Moves player right
    private void moveRight() {
        world[xpos + 1][ypos] = Tileset.PLAYER;
        world[xpos][ypos] = floor();
        this.xpos += 1;
    }

    //Moves player down
    private void moveDown() {
        world[xpos][ypos - 1] = Tileset.PLAYER;
        world[xpos][ypos] = floor();
        this.ypos -= 1;
    }

    //helper methods that returns a floor or fire randomly
    private TETile floor() {
        int r = RandomUtils.uniform(random, 0, 20);
        if(r%2 == 0) {
            return Tileset.FLOOR;
        }
        return Tileset.FIRE;
    }

    //draws the position of the mouse
    private void drawFrame(int x,int y, int size,String s) {
        Font font = new Font("Arial", Font.BOLD, size);
        StdDraw.setFont(font);
        StdDraw.setPenRadius(1);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(x, y, s);
        StdDraw.show();

    }

    private void mousePosition() {
        try {
            checkPosition();
        } catch (ArrayIndexOutOfBoundsException e) {
            drawFrame(6, 30, 20, "");
        }
    }

    //checks mouse position
    private void checkPosition() {
        double xx = StdDraw.mouseX();
        double yy = StdDraw.mouseY();
        int x = (int) xx;
        int y = (int) yy;
        if(world[x][y] == Tileset.FLOOR) {
            drawFrame(6, 30, 20,"Floor");

        }
        if(world[x][y] == Tileset.WALL) {
            drawFrame(6, 30, 20, "Wall");
        }
        if(world[x][y] == Tileset.LOCKED_DOOR) {
            drawFrame(6,30, 20,"Locked Door");
        }
        if(world[x][y] == Tileset.FIRE) {
            drawFrame(6,30,20,"Fire");
        }
        if(world[x][y] == Tileset.PLAYER) {
            drawFrame(6,30,20,"Player");
        }
        if(world[x][y] == Tileset.KEY) {
            drawFrame(6,30,20,"Key");
        }
        if(world[x][y] == Tileset.NOTHING) {
            drawFrame(6, 30,20,"Nothing");
        }
    }

    //the status of the key
    private void keyStatus() {
        if(this.key) {
            drawFrame(40, 30, 20,"Key : Yes");
        }
        if(!this.key){
            drawFrame(40, 30, 20,"Key : No");
        }
    }

    //Game Winning screen
    private void gameWining() {
        StdDraw.clear(Color.BLACK);
        drawFrame(40, 15, 40, "You Win!!");
    }

    //Game Losing screen
    private  void gameLosing() {
        StdDraw.clear(Color.BLACK);
        drawFrame(40, 15, 40, "You Lose :(");
    }




    //moves the player in all direction in the world
    public void movePlayer(TERenderer ter) {
        while(!gameOver) {
            mousePosition(); //checks the position of the mouse as the game is going on
            keyStatus();
            ter.renderFrame(world);
            while(StdDraw.hasNextKeyTyped()) {
                char character = StdDraw.nextKeyTyped();
                String let = String.valueOf(character);
                if(let.equals("w")) {
                    if(world[xpos][ypos+1] == Tileset.FLOOR) {
                        moveUp();

                    }
                    if(world[xpos][ypos+1] == Tileset.KEY) {
                        this.key = true;
                        moveUp();
                    }
                    if(world[xpos][ypos + 1] == Tileset.LOCKED_DOOR && this.key) {
                        moveUp();
                        this.gameOver = true;
                        gameWining();
                    }
                }
                if(let.equals("a")) {
                    if(world[xpos-1][ypos] == Tileset.FLOOR) {
                        moveLeft();
                    }
                    if(world[xpos - 1][ypos] == Tileset.KEY) {
                        this.key = true;
                        moveLeft();
                    }
                    if(world[xpos - 1][ypos] == Tileset.LOCKED_DOOR && this.key) {
                        moveLeft();
                        this.gameOver = true;
                        gameWining();
                    }
                }
                if(let.equals("d")) {
                    if(world[xpos + 1][ypos] == Tileset.FLOOR) {
                       moveRight();
                    }
                    if(world[xpos + 1][ypos] == Tileset.KEY) {
                        this.key = true;
                        moveRight();
                    }
                    if(world[xpos + 1][ypos] == Tileset.LOCKED_DOOR && this.key) {
                        moveRight();
                        this.gameOver = true;
                        gameWining();
                    }
                }
                if(let.equals("s")) {
                    if(world[xpos][ypos - 1] == Tileset.FLOOR) {
                        moveDown();

                    }
                    if(world[xpos][ypos - 1] == Tileset.KEY) {
                        this.key = true;
                        moveDown();
                    }
                    if(world[xpos][ypos - 1] == Tileset.LOCKED_DOOR && this.key) {
                        moveDown();
                        this.gameOver = true;
                        gameWining();
                    }
                }
            }
        }
    }
}

