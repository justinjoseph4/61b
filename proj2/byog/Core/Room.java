package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Room {
    Random random;
    int xposition;
    int yposition;
    int width;
    int length;

    //constructor for the room objects
    public Room(int xpos, int ypos, int w, int l, Random random) {
        this.random = random;
        xposition = xpos;
        yposition = ypos;
        width = w;
        length = l;

    }

    //methods that check if a room is off the screen
    public int checkPosition(int pos, int size, int cor) {
        if (pos + size + 1 < cor || pos - size - 1 > 0) {
            return pos;
        }else {
            pos = RandomUtils.uniform(random, 1, 6);
            return checkPosition(pos, size, cor);
        }

    }



    //adds a generated room to a world
    public void addRoom(TETile[][] world) {
        for (int x = this.xposition; x < this.xposition + this.width; x++) {
            for (int y = this.yposition; y < this.yposition + this.length; y++) {
                world[x][y] = Tileset.FLOOR;
            }
        }
    }


}
