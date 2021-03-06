package byog.Core;


import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class Big implements Serializable {
    TETile[][] world;
    Room[] rooms;
    int numberOfRooms;
    int worldWidth;
    int worldHieght;
    Player p;
    Random random;

    private static final long serialVersionUID = 45498234798734234L;


    long seed;
    Monster[] monsters;

    //Constructor for the big world
    public Big(TETile[][] world, Random random, int x, int y) {
        this.random = random;
        numberOfRooms = RandomUtils.uniform(random, 8, 15);
        rooms = new Room[numberOfRooms];
        this.world = world;
        worldWidth = x;
        worldHieght = y;

    }

    //helper method
    private void checkForWall(int x, int y) {

        for (int i = 1; i < this.worldWidth; i++) {
            for (int j = 1; j < this.worldHieght; j++) {
                if (this.world[i][j].description().equals(Tileset.WALL.description())) {
                    this.world[i][j] = Tileset.LOCKED_DOOR;
                    return;
                }
            }
        }
        /**
         if (world[x][y] == Tileset.WALL) {
         world[x][y] = Tileset.LOCKED_DOOR;
         }
         else if(world[x][y+1] == Tileset.WALL) {
         world[x][y+1] = Tileset.LOCKED_DOOR;
         }
         else if(world[x][y+2] == Tileset.WALL) {
         world[x][y+2] = Tileset.LOCKED_DOOR;
         }
         else if(world[x][y+3] == Tileset.WALL) {
         world[x][y+3] = Tileset.LOCKED_DOOR;
         }
         else if(world[x][y+4] == Tileset.WALL) {
         world[x][y+4] = Tileset.LOCKED_DOOR;
         }
         else if(world[x+1][y] == Tileset.WALL) {
         world[x+1][y] = Tileset.LOCKED_DOOR;
         }
         else if(world[x+2][y] == Tileset.WALL) {
         world[x+2][y] = Tileset.LOCKED_DOOR;
         }
         else if(world[x+3][y] == Tileset.WALL) {
         world[x+3][y] = Tileset.LOCKED_DOOR;
         }
         else if(world[x+4][y] == Tileset.WALL) {
         world[x+4][y] = Tileset.LOCKED_DOOR;
         }
         else {
         checkForWall(x + 1, y+ 1);
         }
         */

    }

    //makes a door in the first room

    private void door() {
        Room r = rooms[rooms.length - 3];
        int s = r.xposition;
        int t = r.yyposition22;
        checkForWall(1, 1);

    }


    //generates random rooms and add them to the array objects
    private void constructRooms() {
        for (int i = 0; i < rooms.length; i++) {
            int l = RandomUtils.uniform(random, 3, 10);
            int w = RandomUtils.uniform(random, 3, 10);
            int xpos = RandomUtils.uniform(random, 1, worldWidth - 10);
            int ypos = RandomUtils.uniform(random, 1, worldHieght - 10);
            Room r = new Room(xpos, ypos, w, l, random);
            r.xposition = r.checkPosition(r.xposition, r.width, worldWidth);
            r.yposition = r.checkPosition(r.yposition, r.length, worldHieght);
            r.roomOverLap(rooms, i, worldWidth, worldHieght);
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


        // door();

        //door();

    }

    //helper method to construct top and bottom part of the walls
    private void topBottomWalls() {
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHieght - 1; y++) {
                if (world[x][y] == Tileset.NOTHING && world[x][y + 1] == Tileset.FLOOR) {
                    world[x][y] = Tileset.WALL;
                } else if (world[x][y] == Tileset.FLOOR && world[x][y + 1] == Tileset.NOTHING) {
                    world[x][y + 1] = Tileset.WALL;
                }
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

    //Places a key in a random room
    public void keyPlacer() {
        Room r = rooms[rooms.length / 2];
        world[r.xposition + 1][r.yposition] = Tileset.KEY;
    }

    public void addMonsters(int n) {
        Monster[] list = new Monster[n];
        String[] directions = new String[]{"up", "down", "right", "left"};
        for (int i = 0; i < n; i++) {
            String direction = directions[random.nextInt(4)];
            list[i] = new Monster(rooms[i + 1].xposition, rooms[i + 1].yposition, world, direction);
        }
        this.monsters = list;

    }

    public TETile floor() {
        int r = RandomUtils.uniform(random, 0, 20);
        if (r % 2 == 0) {
            return Tileset.FLOOR;
        }
        return Tileset.FIRE;
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


        for (int x = 0; x < world.length; x += 1) {
            for (int y = 0; y < world[0].length; y += 1) {
                if (world[x][y] == Tileset.NOTHING) {
                    world[x][y] = Tileset.TREE;
                }

            }
        }


        door();
        keyPlacer();

    }

    public void constructWorldKeyboard() {
        for (int x = 0; x < world.length; x += 1) {
            for (int y = 0; y < world[0].length; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        constructRooms();
        constructHallways();
        constructWalls();
        addMonsters(numberOfRooms / 2);

        door();
        keyPlacer();

    }


    //move the player
    public void player(TERenderer ter) {

        p.movePlayer(ter, monsters);
    }

}
