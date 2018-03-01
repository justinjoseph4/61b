package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;


import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
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
        ter.initialize(WIDTH, HEIGHT);

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
        //ter.renderFrame(finalWorldFrame);




        return finalWorldFrame;
    }
}

    /*
<<<<<<< HEAD

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 60);
        String input = "N6344734423n";
        TETile[][] finalWorldFrame = new TETile[80][60];



        char[] seed = input.toCharArray();
        char option1 = seed[0];
        char option2 = seed[seed.length - 1];

        char[] trueseed = new char[seed.length - 2];
        System.arraycopy(seed,1,trueseed,0,seed.length-2);
        String numseed = new String(trueseed);

        Long put = Long.parseLong(numseed);
        Random random = new Random(put);    //save the seed into a random
        Big world = new Big(finalWorldFrame, random, 80, 60);
        world.constructWorld(); //constructs the world with rooms, hallways, and walls
        ter.renderFrame(finalWorldFrame);
        int jj = 5 + 5;


    }
=======
>>>>>>> cda413f1c8c63587bc83917f58c07d7d30e85390
} */


