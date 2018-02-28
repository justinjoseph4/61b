package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

//Class for the player
public class Player {
    int xpos;
    int ypos;
    TETile[][] world;
    boolean gameOver = false;

    //Constructor for the player object
    public Player(int x, int y, TETile[][] w) {
        this.xpos = x;
        this.ypos = y;
        world = w;
        world[x][y] = Tileset.PLAYER;
    }
//hi




}

