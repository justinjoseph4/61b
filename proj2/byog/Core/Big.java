package byog.Core;

import byog.TileEngine.TETile;

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


    //generates random rooms and add them to the array objects
    public void constructRooms() {
        for (int i = 0; i < rooms.length; i++) {
            int l = RandomUtils.uniform(random, 1, 6);
            int w = RandomUtils.uniform(random, 1, 6);
            int xpos =  RandomUtils.uniform(random, 0, worldWidth - 6);
            int ypos = RandomUtils.uniform(random, 0, worldHieght - 6);
            Room r = new Room(xpos, ypos, w, l, random);
            r.xposition = r.checkPosition(r.xposition, r.width, worldWidth);
            r.yposition = r.checkPosition(r.yposition, r.length, worldHieght);
            r.addRoom(world);
            rooms[i] = r;
        }
    }

    public void constructHallways() {
        Room r = rooms[0];
        int i = 1;
        while (i < rooms.length) {
            r.linkRooms(world, rooms[i]);
            r = rooms[i];
            i += 1;
        }
    }





}
