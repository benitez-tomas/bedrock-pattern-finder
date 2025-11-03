package com.mike;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    static ArrayList<BedrockBlock> blocks = new ArrayList<>();
    static BedrockReader bedrockReader;

    public static void main(String[] args) throws IOException {
        long seed;
        int xFrom, zFrom, xTo, zTo;

        if (args.length < 5) {
            System.out.println("usage:");
            System.out.println("   java -jar bedrock_finder-1.0.jar <worldSeed> <startFromX> <startFromZ> <areaSizeX> <areaSizeZ> [<block>...]");
            return;
        }

        seed = Long.parseLong(args[0]);
        xFrom = Integer.parseInt(args[1]);
        zFrom = Integer.parseInt(args[2]);
        xTo = Integer.parseInt(args[3]);
        zTo = Integer.parseInt(args[4]);
        long totalArea = Math.abs((long) (xTo - xFrom) * (zTo - zFrom));
        ProgressBar bar = new ProgressBar("Searching...", totalArea);

        if (args.length == 5) {
            blocks = new ArrayList<>(PatternMaker.convertAll());
        } else {
            Arrays.stream(args).skip(5).forEach(arg -> blocks.add(new BedrockBlock(arg)));
        }

        bedrockReader = new BedrockReader(seed);

        for (int x = xFrom; x < xTo; x++) {
            for (int z = zFrom; z < zTo; z++) {
                if (checkFormation(x, z)) {
                    System.out.printf("\r\033[2K@%d;%d (%d blocks from origin)\n", x, z, (int) Math.hypot(x, z));
                }
                bar.step();
            }
        }

        System.out.println("search finished");
    }

    static boolean checkFormation(int x, int z) {
        for (BedrockBlock block : blocks) {
            if (block.shouldBeBedrock != bedrockReader.isBedrock(x + block.x, block.y, z + block.z)) return false;
        }
        return true;
    }
}