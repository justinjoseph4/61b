package byog.Core;


import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Big {
    TETile[][] world;
    Room[] rooms;
    int numberOfRooms;
    int worldWidth;
    int worldHieght;
    Random random;

    //Constructor for the big world
    public Big(TETile[][] world, Random random, int x, int y) {
        this.random = random;
        numberOfRooms = RandomUtils.uniform(random, 12, 25);
        rooms = new Room[numberOfRooms];
        this.world = world;
        worldWidth = x;
        worldHieght = y;

    }

    //makes a door in the first room
    private void door() {
        Room r = rooms[0];
        world[r.xposition][r.yposition] = Tileset.UNLOCKED_DOOR;
    }


    //generates random rooms and add them to the array objects
    private void constructRooms() {
        for (int i = 0; i < rooms.length; i++) {
            int l = RandomUtils.uniform(random, 1, 6);
            int w = RandomUtils.uniform(random, 1, 6);
            int xpos = RandomUtils.uniform(random, 0, worldWidth - 6);
            int ypos = RandomUtils.uniform(random, 0, worldHieght - 6);
            Room r = new Room(xpos, ypos, w, l, random);
            r.xposition = r.checkPosition(r.xposition, r.width, worldWidth);
            r.yposition = r.checkPosition(r.yposition, r.length, worldHieght);
            r.addRoom(world);
            rooms[i] = r;

        }

    }

    //constructs hallways and then adds a door in the first room
    private void constructHallways() {
        Room r = rooms[0];
        int i = 1;
        while (i < rooms.length) {
            r.linkRooms(world, rooms[i]);
            r = rooms[i];
            i += 1;
        }
    }

    //constructs walls around rooms and hallways
    public void constructWalls() {
        topBottomWalls();
        leftRightWalls();
        door();
    }

    //helper method to construct top and bottom part of the walls
    private void topBottomWalls() {
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHieght - 1; y++)
                if (world[x][y] == Tileset.NOTHING && world[x][y + 1] == Tileset.FLOOR) {
                    world[x][y] = Tileset.WALL;
                } else if (world[x][y] == Tileset.FLOOR && world[x][y + 1] == Tileset.NOTHING) {
                    world[x][y + 1] = Tileset.WALL;
                }
        }
    }

    //Helper method to construct right side and left side walls
    private void leftRightWalls() {
        for (int y = 0; y < worldHieght; y++) {
            for (int x = 0; x < worldWidth - 1; x++) {
                if (world[x][y] == Tileset.NOTHING && world[x + 1][y] == Tileset.FLOOR) {
                    world[x][y] = Tileset.WALL;
                } else if (world[x][y] == Tileset.FLOOR && world[x + 1][y] == Tileset.NOTHING) {
                    world[x + 1][y] = Tileset.WALL;
                }
            }
        }
    }

    //constructs rooms, hallways, and walls in the world
    public void constructWorld() {
        for (int x = 0; x < world.length; x += 1) {
            for (int y = 0; y < world[0].length; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        constructRooms();
        constructHallways();
        constructWalls();
    }
}
