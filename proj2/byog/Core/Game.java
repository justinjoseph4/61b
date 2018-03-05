package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;



import java.io.*;
import java.util.Random;

public class Game implements Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        /*ter.initialize(WIDTH, HEIGHT + 2);

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];

        char[] seed = input.toCharArray();
        char[] trueseed = new char[seed.length - 2];
        System.arraycopy(seed, 1, trueseed, 0, seed.length - 2);
        String peach = new String(trueseed);

        Long put = Long.parseLong(peach);
        Random random = new Random(put);    //save the seed into a random
        Big world = new Big(finalWorldFrame, random, WIDTH, HEIGHT);
        world.constructWorld(); //constructs the world with rooms, hallways, and walls


        world.player(ter);
        ter.renderFrame(finalWorldFrame);*/


        //return finalWorldFrame;
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        ter.initialize(WIDTH, HEIGHT + 2);

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];

        char[] seed = input.toCharArray();
        char[] trueseed = new char[seed.length - 2];
        System.arraycopy(seed, 1, trueseed, 0, seed.length - 2);
        String peach = new String(trueseed);

        Long put = Long.parseLong(peach);
        Random random = new Random(put);    //save the seed into a random
        Big world = new Big(finalWorldFrame, random, WIDTH, HEIGHT);
        world.constructWorld(); //constructs the world with rooms, hallways, and walls


        world.player(ter);
        ter.renderFrame(finalWorldFrame);


        return finalWorldFrame;
        /*String numseed = "";
        String instructions = "";

        for (int i = 0; i < input.length(); i ++) {
            char key = input.charAt(i);
            if (Character.isDigit(key)) {
                numseed = numseed + key;
            } else {
                instructions = instructions + key;
            }
        }

        Big world;
        TETile[][] finalWorldFrame;

        if (instructions.charAt(0) == 'N') {
            Long put = Long.parseLong(numseed);

            Random random = new Random(put);

            ter.initialize(WIDTH, HEIGHT);
            finalWorldFrame = new TETile[WIDTH][HEIGHT];
            world = new Big(finalWorldFrame, random, WIDTH, HEIGHT);
            world.seed = put;
            world.constructWorld();
        } else {
            Big object = null;
            try
            {
                FileInputStream file = new FileInputStream("mygame.data");
                ObjectInputStream in = new ObjectInputStream(file);
                object = (Big)in.readObject();
                in.close();
                file.close();
            }
            catch (IOException err)
            {
                System.out.println("IOException");
            }
            catch (ClassNotFoundException err1)
            {
                System.out.println("CLassNotFoundException");

            }


            ter.initialize(WIDTH, HEIGHT);
            finalWorldFrame = object.world;
            world = object;


        }





        //constructs the world with rooms, hallways, and walls

        for  (int i = 1; i < instructions.length(); i ++) {
            if (instructions.charAt(i) == 'W' && finalWorldFrame[world.p.xpos][world.p.ypos + 1] != Tileset.WALL) {
                finalWorldFrame[world.p.xpos][world.p.ypos] = Tileset.FLOOR;
                world.p.ypos ++;
                finalWorldFrame[world.p.xpos][world.p.ypos] = Tileset.PLAYER;
            }
            if (instructions.charAt(i) == 'S' && finalWorldFrame[world.p.xpos][world.p.ypos - 1] != Tileset.WALL) {
                finalWorldFrame[world.p.xpos][world.p.ypos] = Tileset.FLOOR;
                world.p.ypos --;
                finalWorldFrame[world.p.xpos][world.p.ypos] = Tileset.PLAYER;
            }
            if (instructions.charAt(i) == 'D' && finalWorldFrame[world.p.xpos + 1][world.p.ypos] != Tileset.WALL) {
                finalWorldFrame[world.p.xpos][world.p.ypos] = Tileset.FLOOR;
                world.p.xpos ++;
                finalWorldFrame[world.p.xpos][world.p.ypos] = Tileset.PLAYER;
            }
            if (instructions.charAt(i) == 'A' && finalWorldFrame[world.p.xpos][world.p.ypos - 1] != Tileset.WALL) {
                finalWorldFrame[world.p.xpos][world.p.ypos] = Tileset.FLOOR;
                world.p.xpos --;
                finalWorldFrame[world.p.xpos][world.p.ypos] = Tileset.PLAYER;
            }


            // Moves monsters
            for (Monster monster:world.monsters) {
                monster.moveMonsters();
            }

            // Saves the world object in a file called mygame
            if(instructions.charAt(i) == 'Q'){
                try
                {
                    FileOutputStream file = new FileOutputStream("mygame.data");

                    ObjectOutputStream out = new ObjectOutputStream(file);



                    out.writeObject(world);
                    out.close();
                    file.close();
                }
                catch (IOException err1)
                {
                    System.out.println("IOException is caught");
                }
                break;
            }
        }

        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        String input = "N3242";
        Big big;





        Game game = new Game();
        game.playWithInputString(input);
    }*/


    }
}

