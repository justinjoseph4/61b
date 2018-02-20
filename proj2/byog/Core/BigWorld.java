package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

// So far adds rooms to a randomly generated world.
//contains a nested room class
public class BigWorld {
    // This is an array of room objects. it keeps track of room positioning and size
    Room[] Rooms;


    //Keeps rack of room objects
    public static class Room {
        int xposition;
        int yposition;
        int width;
        int length;

        public Room(int xpos, int ypos, int w, int l) {
            xposition = xpos;
            yposition = ypos;
            width = w;
            length = l;
        }

        //create a new random room. here we assume we are given a random object with an inputted seed.
        //the - 5 is supposed to not make rooms to close to edges. we should fix that later
        public static Room createRoom(TETile[][] world,Random RANDOM) {
            int randomx = RANDOM.nextInt(world.length - 5);
            int randomy = RANDOM.nextInt(world[0].length - 5);
            int randomwidth = RandomUtils.uniform(RANDOM,1,6);
            int randomlength = RandomUtils.uniform(RANDOM,1,6);

            return new Room(randomx,randomy,randomwidth,randomlength);


        }

        // Adds a genrated room to a generated world/ these things exist already
        public static void addRoom(TETile[][] world, Room r) {
            int xpos = r.xposition;
            int ypos = r.yposition;
            int width = r.width;
            int length = r.length;

            for (int x = xpos; x < xpos + width; x += 1) {
                for (int y = ypos; y < ypos + length; y += 1) {
                    world[x][y] = Tileset.FLOWER;
                }
            }


        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        int width = 40;
        int length = 40;
        ter.initialize(width, length);
        TETile[][] world = new TETile[width][length];

        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < length; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Random RANDOM = new Random(676);

        for (int i = 0; i < 5; i ++) {
            Room newroom = Room.createRoom(world,RANDOM);
            Room.addRoom(world,newroom);
        }


        Room room1 = Room.createRoom(world,RANDOM);
        Room room2 = Room.createRoom(world,RANDOM);
        Room room3 = Room.createRoom(world,RANDOM);
        Room room4 = Room.createRoom(world,RANDOM);
        Room room5 = Room.createRoom(world,RANDOM);

        Room.addRoom(world,room1);
        Room.addRoom(world,room2);
        Room.addRoom(world,room3);
        Room.addRoom(world,room4);
        Room.addRoom(world,room5);

        ter.renderFrame(world);

    }

}
