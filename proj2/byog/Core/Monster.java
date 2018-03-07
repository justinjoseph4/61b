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
        world[x][y] = Tileset.MONSTER;
    }

    public void moveMonsters() {
        if (movement == "up") {
            if (world[xpos][ypos + 1].description().equals(Tileset.WALL.description()) ||
                    world[xpos][ypos + 1].description().equals(Tileset.LOCKED_DOOR.description()) ||
                    world[xpos][ypos + 1].description().equals(Tileset.KEY.description())) {
                movement = "down";
            }
            else if(world[xpos][ypos + 1].description().equals(Tileset.FIRE.description())) {
                world[xpos][ypos] = Tileset.FIRE;
                ypos ++;
                world[xpos][ypos] = Tileset.MONSTER;
            }
            else {
                world[xpos][ypos] = Tileset.FLOOR;
                ypos ++;
                world[xpos][ypos] = Tileset.MONSTER;
            }
        }
        if (movement == "down") {
            if (world[xpos][ypos - 1].description().equals(Tileset.WALL.description()) ||
                    world[xpos][ypos - 1].description().equals(Tileset.LOCKED_DOOR.description()) ||
                    world[xpos][ypos - 1].description().equals(Tileset.KEY.description())) {
                movement = "up";
            }
            else if(world[xpos][ypos - 1].description().equals(Tileset.FIRE.description())) {
                world[xpos][ypos] = Tileset.FIRE;
                ypos --;
                world[xpos][ypos] = Tileset.MONSTER;
            }
            else {
                world[xpos][ypos] = Tileset.FLOOR;
                ypos --;
                world[xpos][ypos] = Tileset.MONSTER;
            }
        }
        if (movement == "right") {
            if (world[xpos + 1][ypos].description().equals(Tileset.WALL.description()) ||
                    world[xpos + 1][ypos].description().equals(Tileset.LOCKED_DOOR.description()) ||
                    world[xpos + 1][ypos].description().equals(Tileset.KEY.description())) {
                movement = "left";
            }
            else if(world[xpos + 1][ypos].description().equals(Tileset.FIRE.description())) {
                world[xpos][ypos] = Tileset.FIRE;
                xpos ++;
                world[xpos][ypos] = Tileset.MONSTER;

            }
            else {
                world[xpos][ypos] = Tileset.FLOOR;
                xpos ++;
                world[xpos][ypos] = Tileset.MONSTER;
            }
        }
        if (movement == "left") {
            if (world[xpos - 1][ypos].description().equals(Tileset.WALL.description()) ||
                    world[xpos - 1][ypos].description().equals(Tileset.LOCKED_DOOR.description()) ||
                    world[xpos - 1][ypos].description().equals(Tileset.KEY.description())) {
                movement = "right";
            }
            else if(world[xpos - 1][ypos].description().equals(Tileset.FIRE.description())) {
                world[xpos][ypos] = Tileset.FIRE;
                xpos --;
                world[xpos][ypos] = Tileset.MONSTER;
            }
            else {
                world[xpos][ypos] = Tileset.FLOOR;
                xpos --;
                world[xpos][ypos] = Tileset.MONSTER;
            }
        }

    }

}
