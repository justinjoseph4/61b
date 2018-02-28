package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

//Class for the player
public class Player {
    int xpos;
    int ypos;
    TETile[][] world;
    boolean gameOver = false;
    boolean key = false;

    //Constructor for the player object
    public Player(int x, int y, TETile[][] w) {
        this.xpos = x;
        this.ypos = y;
        world = w;
        world[x][y] = Tileset.PLAYER;
    }

    //moves the player left, right, down, and up
    public void movePlayer(TERenderer ter) {
        while(!gameOver) {
            ter.renderFrame(world);
            while(StdDraw.hasNextKeyTyped()) {
                char character = StdDraw.nextKeyTyped();
                String let = String.valueOf(character);
                if(let.equals("w")) {
                    if(world[xpos][ypos+1] == Tileset.FLOOR) {
                        world[xpos][ypos+1] = Tileset.PLAYER;
                        world[xpos][ypos] = Tileset.FLOOR;
                        this.ypos += 1;

                    }
                }
                if(let.equals("a")) {
                    if(world[xpos-1][ypos] == Tileset.FLOOR) {
                        world[xpos-1][ypos] = Tileset.PLAYER;
                        world[xpos][ypos] = Tileset.FLOOR;
                        this.xpos -= 1;
                    }
                }
                if(let.equals("d")) {
                    if(world[xpos + 1][ypos] == Tileset.FLOOR) {
                        world[xpos + 1][ypos] = Tileset.PLAYER;
                        world[xpos][ypos] = Tileset.FLOOR;
                        this.xpos += 1;
                    }
                }
                if(let.equals("s")) {
                    if(world[xpos][ypos - 1] == Tileset.FLOOR) {
                        world[xpos][ypos - 1] = Tileset.PLAYER;
                        world[xpos][ypos] = Tileset.FLOOR;
                        this.ypos -= 1;
                    }
                }
            }
        }
    }






}

