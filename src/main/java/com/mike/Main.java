package com.mike;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    static ArrayList<BedrockBlock> blocks = new ArrayList<>();
    static BedrockReader bedrockReader;

    public static void main(String[] args) throws IOException {
        long seed = 0;
        int x = 0;
        int z = 0;
        int xSize = 0;
        int zSize = 0;
        if (args.length < 5) {
            System.out.println("usage:");
            System.out.println("   java -jar bedrock_finder-1.0.jar <worldSeed> <startFromX> <startFromZ> <areaSizeX> <areaSizeZ> [<block>...]");
            return;
        } else if (args.length == 5) {
            seed = Long.parseLong(args[0]);
            x = Integer.parseInt(args[1]);
            z = Integer.parseInt(args[2]);
            xSize = Integer.parseInt(args[3]);
            zSize = Integer.parseInt(args[4]);
            blocks = new ArrayList<>(PatternMaker.convertAll());
        } else {
            Arrays.stream(args).skip(5).forEach((arg) -> blocks.add(new BedrockBlock(arg)));
        }
        bedrockReader = new BedrockReader(seed);

        for (int ix = x; ix < x + xSize; ix++) {
            for (int iz = z; iz < z + zSize; iz++) {
                if (checkFormation(ix, iz)) {
                    System.out.printf("@%d;%d (%d blocks from origin)\n", ix, iz, (int) Math.hypot(ix, iz));
                }
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
