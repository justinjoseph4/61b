package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import javafx.scene.layout.CornerRadii;


import java.awt.*;
import java.io.*;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    //Draws game opening screen
    private void drawFrame(int x,int y,int size, String s) {
        Font font = new Font("Arial", Font.BOLD, size);
        StdDraw.setFont(font);
        StdDraw.setPenRadius(1);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(x, y, s);
        StdDraw.show();
    }


    //Screen waits for a seed and converts number typed into a string
    private String seedFrame() {
        int i = 0;
        int x = 40;
        String word = "";
        while (i < 1) {
            while (StdDraw.hasNextKeyTyped()) {
                char ob = StdDraw.nextKeyTyped();
                String l = String.valueOf(ob);
                if (Character.isDigit(ob)) {
                    word = word + ob;
                    Font font = new Font("Arial", Font.BOLD, 30);
                    StdDraw.setFont(font);
                    StdDraw.setPenRadius(1);
                    StdDraw.setPenColor(Color.RED);
                    StdDraw.text(x, 20, l);
                    StdDraw.show();
                    x += 1;

                }
                if (Character.isAlphabetic(ob)) {
                    i += 1;


                }
            }
        }
        return word;
    }


    public void playWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT + 1);
        drawFrame(40, 25, 45, "CS61B: The Game");
        drawFrame(40, 15, 20, "New Game (N)");
        drawFrame(40, 12, 20, "Load Game (L)");
        drawFrame(40, 9, 20, "Quit (Q)");
        TETile[][] finalworld = new TETile[1][1];
        int i = 0;
        while (i < 1) {
            while (StdDraw.hasNextKeyTyped()) {
                char character = StdDraw.nextKeyTyped();
                String let = String.valueOf(character);

                if (let.equals("n")) {
                    StdDraw.clear(Color.black);
                    drawFrame(40, 25, 30, "Input seed");
                    Long sed = Long.parseLong(seedFrame());
                    System.out.print(sed);
                    TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
                    Random random = new Random(sed);
                    Big object = new Big(finalWorldFrame, random, WIDTH, HEIGHT);

                    object.constructWorld();
                    object.p = new Player(object.rooms[0].xposition, object.rooms[0].yposition, object.world, random, object);
                    object.addMonsters(object.numberOfRooms/2);
                    object.player(ter);

                }


                if (let.equals("l")) {
                    Big object1 = null;
                    try {
                        FileInputStream file = new FileInputStream("mygame.data");
                        ObjectInputStream in = new ObjectInputStream(file);
                        object1 = (Big) in.readObject();
                        in.close();
                        file.close();
                    } catch (IOException err) {
                        System.out.println("IOException");
                    } catch (ClassNotFoundException err1) {
                        System.out.println("CLassNotFoundException");

                    }


                    ter.initialize(WIDTH,HEIGHT+1);



                    object1.player(ter);
                }
                if(let.equals("q")) {
                    i += 1;
                }
            }

        }
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

            String numseed = "";
            String instructions = "";

            for (int i = 0; i < input.length(); i++) {
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
                world.constructWorldInput();
                ter.renderFrame(finalWorldFrame);
            } else {
                Big object = null;
                try {
                    FileInputStream file = new FileInputStream("mygame.txt");
                    ObjectInputStream in = new ObjectInputStream(file);
                    object = (Big) in.readObject();
                    in.close();
                    file.close();
                } catch (IOException err) {
                    System.out.println("IOException");
                } catch (ClassNotFoundException err1) {
                    System.out.println("CLassNotFoundException");

                }


                ter.initialize(WIDTH, HEIGHT);
                world = object;
                finalWorldFrame = object.world;

            }


            //constructs the world with rooms, hallways, and walls
            //I LIKE APPLES


            for (int i = 1; i < instructions.length(); i++) {
                if (instructions.charAt(i) == 'W' && finalWorldFrame[world.p.xpos][world.p.ypos + 1].description().equals(Tileset.FLOOR.description())) {
                    finalWorldFrame[world.p.xpos][world.p.ypos] = world.floor();
                    world.p.ypos++;
                    finalWorldFrame[world.p.xpos][world.p.ypos] = Tileset.PLAYER;
                }
                if (instructions.charAt(i) == 'S' && finalWorldFrame[world.p.xpos][world.p.ypos - 1].description().equals(Tileset.FLOOR.description())) {
                    finalWorldFrame[world.p.xpos][world.p.ypos] = world.floor();
                    world.p.ypos--;
                    finalWorldFrame[world.p.xpos][world.p.ypos] = Tileset.PLAYER;
                }
                if (instructions.charAt(i) == 'D' && finalWorldFrame[world.p.xpos + 1][world.p.ypos].description().equals(Tileset.FLOOR.description())) {
                    finalWorldFrame[world.p.xpos][world.p.ypos] = world.floor();
                    world.p.xpos++;
                    finalWorldFrame[world.p.xpos][world.p.ypos] = Tileset.PLAYER;
                }
                if (instructions.charAt(i) == 'A' && finalWorldFrame[world.p.xpos][world.p.ypos - 1].description().equals(Tileset.FLOOR.description())) {
                    finalWorldFrame[world.p.xpos][world.p.ypos] = world.floor();
                    world.p.xpos--;
                    finalWorldFrame[world.p.xpos][world.p.ypos] = Tileset.PLAYER;
                }
                if (finalWorldFrame[world.p.xpos][world.p.ypos].description().equals(Tileset.PLAYER.description())) {
                    finalWorldFrame[world.p.xpos][world.p.ypos] = Tileset.FLOOR;
                    world.p.key = true;
                }
                if (finalWorldFrame[world.p.xpos][world.p.ypos] == Tileset.LOCKED_DOOR && world.p.key) {
                    world.p.gameOver = true;
                    ter.renderFrame(finalWorldFrame);
                    return finalWorldFrame;
                }


                // Moves monsters
                for (Monster monster : world.monsters) {
                    monster.moveMonsters();
                }

                if (finalWorldFrame[world.p.xpos][world.p.ypos] == Tileset.FLOWER) {
                    world.p.gameOver = true;
                    ter.renderFrame(finalWorldFrame);
                    return finalWorldFrame;
                }

                // Saves the world object in a file called mygame
                if (instructions.charAt(i) == 'Q') {
                    try {
                        FileOutputStream file = new FileOutputStream("mygame.txt");

                        ObjectOutputStream out = new ObjectOutputStream(file);


                        out.writeObject(world);
                        out.close();
                        file.close();
                    } catch (IOException err1) {
                        System.out.println("IOException is caught");
                    }
                    break;
                }
            }

            ter.renderFrame(finalWorldFrame);
            return finalWorldFrame;

        }




}
