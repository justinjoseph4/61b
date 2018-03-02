package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;

//Class for the player
public class Player {
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
    private void drawFrame(String s) {
        Font font = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenRadius(1);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(6, 30, s);
        StdDraw.show();

    }

    //checks mouse position
    private void checkPosition() {
        double xx = StdDraw.mouseX();
        double yy = StdDraw.mouseY();
        int x = (int) xx;
        int y = (int) yy;
        if(world[x][y] == Tileset.FLOOR) {
            drawFrame("Floor");

        }
        if(world[x][y] == Tileset.WALL) {
            drawFrame("Wall");
        }
        if(world[x][y] == Tileset.LOCKED_DOOR) {
            drawFrame("Locked Door");
        }
        if(world[x][y] == Tileset.FIRE) {
            drawFrame("Fire");
        }
        if(world[x][y] == Tileset.PLAYER) {
            drawFrame("Player");
        }
        if(world[x][y] == Tileset.KEY) {
            drawFrame("Key");
        }
        if(world[x][y] == Tileset.NOTHING) {
            drawFrame("Nothing");
        }
    }


    //moves the player in all direction in the world
    public void movePlayer(TERenderer ter) {
        while(!gameOver) {
            checkPosition(); //checks the position of the mouse as the game is going on
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
                    }
                }
            }
        }
    }
}

