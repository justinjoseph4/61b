package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Random;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


//Class for the player
public class Player implements Serializable {
    int xpos;
    int ypos;
    TETile[][] world;
    Random random;
    boolean gameOver = false;
    boolean key = false;
    Big object;

    //Constructor for the player object
    public Player(int x, int y, TETile[][] w, Random r, Big object) {
        this.object = object;
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
        if(world[x][y].description().equals(Tileset.FLOOR.description())) {
            drawFrame(6, 30, 20,"Floor");

        }
        if(world[x][y].description().equals(Tileset.WALL.description())) {
            drawFrame(6, 30, 20, "Wall");
        }
        if(world[x][y].description().equals(Tileset.LOCKED_DOOR.description())) {
            drawFrame(6,30, 20,"Locked Door");
        }
        if(world[x][y].description().equals(Tileset.FIRE.description())) {
            drawFrame(6,30,20,"Fire");
        }
        if(world[x][y].description().equals(Tileset.PLAYER.description())) {
            drawFrame(6,30,20,"Player");
        }
        if(world[x][y].description().equals(Tileset.KEY.description())) {
            drawFrame(6,30,20,"Key");
        }
        if(world[x][y].description().equals(Tileset.NOTHING.description())) {
            drawFrame(6, 30,20,"Nothing");
        }
        if(world[x][y].description().equals(Tileset.MONSTER.description())) {
            drawFrame(6, 30,20,"Monster");
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

    //Moves Monsters
    private void moveMonsters(Monster[] mom) {
        for (int i = 0; i < mom.length; i++) {
            mom[i].moveMonsters();
        }
    }




    //moves the player in all direction in the world
    public void movePlayer(TERenderer ter, Monster[] mom) {
        while(!gameOver) {
            mousePosition(); //checks the position of the mouse as the game is going on
            keyStatus();
            ter.renderFrame(world);
            while(StdDraw.hasNextKeyTyped()) {
                ter.renderFrame(world);
                moveMonsters(mom);
                char character = StdDraw.nextKeyTyped();
                String let = String.valueOf(character);
                if(world[xpos][ypos].description().equals(Tileset.FLOOR.description()) ||
                        world[xpos][ypos].description().equals(Tileset.MONSTER.description())) {
                    gameOver = true;
                    gameLosing();
                }
                if(let.equals("w")) {
                    if(world[xpos][ypos+1].description().equals(Tileset.FLOOR.description())) {
                        moveUp();

                    }
                    if(world[xpos][ypos+1].description().equals(Tileset.KEY.description())) {
                        this.key = true;
                        moveUp();
                    }
                    if(world[xpos][ypos + 1].description().equals(Tileset.LOCKED_DOOR.description()) && this.key) {
                        moveUp();
                        this.gameOver = true;
                        gameWining();
                    }
                }
                if(let.equals("a")) {
                    if(world[xpos-1][ypos].description().equals(Tileset.FLOOR.description())) {
                        moveLeft();
                    }
                    if(world[xpos - 1][ypos].description().equals(Tileset.KEY.description())) {
                        this.key = true;
                        moveLeft();
                    }
                    if(world[xpos - 1][ypos].description().equals(Tileset.LOCKED_DOOR.description()) && this.key) {
                        moveLeft();
                        this.gameOver = true;
                        gameWining();
                    }
                }
                if(let.equals("d")) {
                    if(world[xpos + 1][ypos].description().equals(Tileset.FLOOR.description())) {
                       moveRight();
                    }
                    if(world[xpos + 1][ypos].description().equals(Tileset.KEY.description())) {
                        this.key = true;
                        moveRight();
                    }
                    if(world[xpos + 1][ypos].description().equals(Tileset.LOCKED_DOOR.description()) && this.key) {
                        moveRight();
                        this.gameOver = true;
                        gameWining();
                    }
                }
                if(let.equals("s")) {
                    if(world[xpos][ypos - 1].description().equals(Tileset.FLOOR.description())) {
                        moveDown();

                    }
                    if(world[xpos][ypos - 1].description().equals(Tileset.KEY.description())) {
                        this.key = true;
                        moveDown();
                    }
                    if(world[xpos][ypos - 1].description().equals(Tileset.LOCKED_DOOR.description()) && this.key) {
                        moveDown();
                        this.gameOver = true;
                        gameWining();
                    }
                }
                if(let.equals("q")) {
                    try {
                        FileOutputStream file = new FileOutputStream("mygame.data");

                        ObjectOutputStream out = new ObjectOutputStream(file);

                        out.writeObject(object);
                        out.close();
                        file.close();
                    } catch (IOException err1) {
                        System.out.println("IOException is caught");
                    }
                    return;
                }
            }
        }
    }
}


