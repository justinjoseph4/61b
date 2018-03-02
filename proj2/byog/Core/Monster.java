package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.Serializable;

public class Monster implements Serializable {
    int xpos;
    int ypos;
    String movement;
    TETile[][] world;

    //class for monsters
    public Monster(int x, int y, TETile[][] w,String move) {
        this.xpos = x;
        this.ypos = y;
        this.movement = move;
        world = w;
        world[x][y] = Tileset.FLOWER;
    }

    public void moveMonsters() {
        if (movement == "up") {
            if (world[xpos][ypos + 1] == Tileset.WALL) {
                movement = "down";
            } else {
                world[xpos][ypos] = Tileset.FLOOR;
                ypos ++;
                world[xpos][ypos] = Tileset.FLOWER;
            }
        }
        if (movement == "down") {
            if (world[xpos][ypos - 1] == Tileset.WALL) {
                movement = "up";
            } else {
                world[xpos][ypos] = Tileset.FLOOR;
                ypos --;
                world[xpos][ypos] = Tileset.FLOWER;
            }
        }
        if (movement == "right") {
            if (world[xpos + 1][ypos] == Tileset.WALL) {
                movement = "left";
            } else {
                world[xpos][ypos] = Tileset.FLOOR;
                xpos ++;
                world[xpos][ypos] = Tileset.FLOWER;
            }
        }
        if (movement == "left") {
            if (world[xpos - 1][ypos] == Tileset.WALL) {
                movement = "right";
            } else {
                world[xpos][ypos] = Tileset.FLOOR;
                xpos --;
                world[xpos][ypos] = Tileset.FLOWER;
            }
        }

    }

}
