package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

// So far adds rooms to a randomly generated world.
//contains a nested room class
public class BigWorld {
    // This is an array of room objects. it keeps track of room positioning and size
    Room[] rooms;
    TETile[][] world;

    //Constructor for the BigWorld
    public BigWorld(TETile[][] world, int numberOfRooms) {
        rooms = new Room[numberOfRooms];
        this.world = world;
    }


    //Keeps rack of room objects
    public static class Room {
        int xposition;
        int yposition;
        int width;
        int length;

        //constructor for the room objects
        public Room(int xpos, int ypos, int w, int l) {
            xposition = xpos;
            yposition = ypos;
            width = w;
            length = l;
        }

        //create a new random room. here we assume we are given a random object with an inputted seed.
        //the - 5 is supposed to not make rooms to close to edges. we should fix that later
        public static Room createRoom(TETile[][] world, Random RANDOM) {
            int randomx = RANDOM.nextInt(world.length - 5);
            int randomy = RANDOM.nextInt(world[0].length - 5);
            int randomwidth = RandomUtils.uniform(RANDOM, 1, 6);
            int randomlength = RandomUtils.uniform(RANDOM, 1, 6);

            return new Room(randomx, randomy, randomwidth, randomlength);


        }

        // returns leftmost room. if both rooms have same x position, returns bottommost
        private static Room rearrangeRooms(Room r1, Room r2) {
            if (r1.xposition < r2.xposition) {
                return r1;
            }
            if (r2.xposition < r1.xposition) {
                return r2;
            }
            if (r1.yposition < r2.yposition) {
                return r1;
            }
            return r2;
        }

        // Adds a generated room to a generated world/ these things exist already
        public static void addRoom(TETile[][] world, Room r) {
            int xpos = r.xposition;
            int ypos = r.yposition;
            int width = r.width;
            int length = r.length;

            for (int x = xpos; x < xpos + width; x += 1) {
                for (int y = ypos; y < ypos + length; y += 1) {
                    world[x][y] = Tileset.FLOOR;
                }
            }


        }

        // Links two rooms with a hallway. Starts building fromm room start.
        public static void linkRooms(TETile[][] world, Room r1, Room r2) {
            Room start = rearrangeRooms(r1, r2);
            Room end = r2;


            if (start == r2) {
                end = r1;
            }


            int coin = 1;


            //first case: if we need to make a straight path
            if (start.xposition == end.xposition || start.yposition == end.yposition) {
                straightpath(world, start, end);
            }

            //second case: if room 2 is the the right and below room 1
            if (start.yposition > end.yposition) {
                if (coin == 0) {
                    elbowpath1(world, start, end);
                    return;
                }
                elbowpath2(world, start, end);
            }

            //third case: is room 3 is to the right and above room 1
            if (start.yposition < end.yposition) {
                if (coin == 0) {
                    elbowpath3(world, start, end);
                    return;
                }
                elbowpath4(world, start, end);

            }


        }

        //assume r1 is leftmost or bottommost.
        public static void straightpath(TETile[][] world, Room r1, Room r2) {

            //makes straight road left to right. assumes room one is too the left of room 2
            if (r1.yposition == r2.yposition) {
                //makes straight road from left to right
                for (int x = r1.xposition; x < r2.xposition; x += 1) {
                    world[x][r1.yposition] = Tileset.FLOOR;
                }
            }

            // makes straight road up to down. assumes room one is below room 2
            if (r1.xposition == r2.xposition) {
                //makes straight road from left to right
                for (int y = r1.yposition; y < r2.yposition; y += 1) {
                    world[r1.xposition][y] = Tileset.FLOOR;
                }
            }

        }

        // assumes r2 is to the right and below room 1. assumes x is right to left.
        // creates path downward, then too the right
        public static void elbowpath1(TETile[][] world, Room r1, Room r2) {

            for (int y = r1.yposition; y > r2.yposition; y -= 1) {
                world[r1.xposition][y] = Tileset.FLOOR;
            }
            for (int x = r1.xposition; x < r2.xposition; x += 1) {
                world[x][r2.yposition] = Tileset.FLOOR;
            }
        }

        // assumes r2 is to the right and below room 1. assumes x is right to left.
        // creates path rightward, then downward
        public static void elbowpath2(TETile[][] world, Room r1, Room r2) {

            for (int x = r1.xposition; x < r2.xposition; x += 1) {
                world[x][r1.yposition] = Tileset.FLOOR;
            }
            for (int y = r1.yposition; y > r2.yposition; y -= 1) {
                world[r2.xposition][y] = Tileset.FLOOR;
            }
        }


        //assumes r2 is to the right and above room 1.
        // builds to the right. then builds upward
        public static void elbowpath3(TETile[][] world, Room r1, Room r2) {

            for (int x = r1.xposition; x < r2.xposition; x += 1) {
                world[x][r1.yposition] = Tileset.FLOOR;
            }
            for (int y = r1.yposition; y < r2.yposition; y += 1) {
                world[r2.xposition][y] = Tileset.FLOOR;
            }
        }

        //assumes r2 is to the right and above room 1.
        // builds up. then builds rightward
        public static void elbowpath4(TETile[][] world, Room r1, Room r2) {

            for (int y = r1.yposition; y < r2.yposition; y += 1) {
                world[r1.xposition][y] = Tileset.FLOOR;
            }
            for (int x = r1.xposition; x < r2.xposition; x += 1) {
                world[x][r2.yposition] = Tileset.FLOOR;
            }

        }

    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        int width = 50;
        int length = 50;
        ter.initialize(width, length);
        TETile[][] world = new TETile[width][length];

        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < length; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Random RANDOM = new Random(38);


        Room room1 = Room.createRoom(world, RANDOM);
        Room room2 = Room.createRoom(world, RANDOM);


        Room.addRoom(world, room1);
        Room.addRoom(world, room2);
        Room.linkRooms(world, room1, room2);


        ter.renderFrame(world);

    }

}