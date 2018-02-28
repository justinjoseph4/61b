package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Room {
    Random random;
    int xposition;
    int yposition;
    int x2;
    int y2;
    int width;
    int length;

    //constructor for the room objects
    public Room(int xpos, int ypos, int w, int l, Random random) {
        this.random = random;
        xposition = xpos + 1;
        yposition = ypos + 1;
        width = w;
        length = l;
        x2 = xposition + w;
        y2 = yposition + l;

    }

    //Check if a room overlaps any other room
    public void roomOverLap(Room[] r, int x, int worldWidth, int worldHieght) {
        for( int i = 0; i < x; i++) {
            if (r[i].xposition > this.xposition || this.x2 > r[i].x2 + 1
                    && r[i].yposition > this.yposition || this.y2  > r[i].y2 + 1) {

            }
            else {
                this.length= RandomUtils.uniform(random, 3, 10);
                this.width = RandomUtils.uniform(random, 3, 10);
                this.xposition = RandomUtils.uniform(random, 1, worldWidth - 10);
                this.yposition = RandomUtils.uniform(random, 1, worldHieght - 10);
                this.xposition = this.checkPosition(this.xposition, this.width, worldWidth);
                this.yposition = this.checkPosition(this.yposition, this.length, worldHieght);
                this.roomOverLap(r, i, worldWidth, worldHieght);
            }
        }
    }

    //methods that check if a room is off the screen
    public int checkPosition(int pos, int size, int cor) {
        if (pos + size + 1 < cor || pos - size - 1 > 0) {
            return pos;
        } else {
            pos = RandomUtils.uniform(random, 3, 10);
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

    //makes horizontal hallways from right to left
    private void horizontalHallways(TETile[][] world, int x1, int x2, int ypos) {
        for (int x = x1; x < x2 + 1; x++) {
            world[x][ypos] = Tileset.FLOOR;
        }
    }

    //makes a vertical hallways upwards
    private void verticalHallways(TETile[][] world, int y1, int y2, int xpos) {
        for (int y = y1; y < y2 + 1; y++) {
            world[xpos][y] = Tileset.FLOOR;
        }
    }

    //makes an up right hallway
    private void upRightHallway(TETile[][] world, int x1, int x2, int y1, int y2) {
        verticalHallways(world, y1, y2, x1);
        horizontalHallways(world, x1, x2, y2);
    }

    //makes a right down hallway
    private void rightDownHallway(TETile[][] world, int x1, int x2, int y1, int y2) {
        horizontalHallways(world, x1, x2, y2);
        verticalHallways(world, y1, y2, x2);
    }

    //makes a down left hallway
    private void downLeftHallway(TETile[][] world, int x1, int x2, int y1, int y2) {
        verticalHallways(world, y1, y2, x2);
        horizontalHallways(world, x1, x2, y1);
    }

    //makes a down right hallway
    private void downRightHallway(TETile[][] world, int x1, int x2, int y1, int y2) {
        verticalHallways(world, y1, y2, x1);
        horizontalHallways(world, x1, x2, y1);
    }


    //connects two rooms to make a hallway
    public void linkRooms(TETile[][] world, Room r) {
        if (this.xposition < r.xposition) {
            if (this.yposition == r.yposition) {
                horizontalHallways(world, this.xposition, r.xposition, this.yposition);
            } else if (this.yposition < r.yposition) {
                upRightHallway(world, this.xposition, r.xposition, this.yposition, r.yposition);
            } else {
                rightDownHallway(world, this.xposition, r.xposition, r.yposition, this.yposition);
            }
        } else if (this.xposition > r.xposition) {
            if (this.yposition == r.yposition) {
                horizontalHallways(world, r.xposition, this.xposition, r.yposition);
            } else if (this.yposition > r.yposition) {
                downLeftHallway(world, r.xposition, this.xposition, r.yposition, this.yposition);
            } else {
                downRightHallway(world, r.xposition, this.xposition, this.yposition, r.yposition);
            }
        } else {
            if (r.yposition < this.yposition) {
                verticalHallways(world, r.yposition, this.yposition, r.xposition);
            } else {
                verticalHallways(world, this.yposition, r.yposition, r.xposition);
            }
        }
    }
}
