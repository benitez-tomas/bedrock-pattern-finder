package com.mike;

public class Main {
    public static void main(String[] args) {
        long seed = Long.parseLong(args[0]);
        int x = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        int xSize = Integer.parseInt(args[3]);
        int zSize = Integer.parseInt(args[4]);
        int minY = Integer.parseInt(args[5]);
        int maxY = Integer.parseInt(args[6]);

        boolean[][][] bedrock = getBedrockMap(seed, x, z, xSize, zSize, minY, maxY);

        while (true) {
            try {
                String coordinates = System.console().readLine();
                String[] splitCoordinates = coordinates.split(" ");

                int xCoord = Integer.parseInt(splitCoordinates[0]) - x;
                int zStartCoord = Integer.parseInt(splitCoordinates[1]) - z;
                int zEndCoord = Integer.parseInt(splitCoordinates[2]) - z;

                // For y values, we will just print the bedrock value at the first y value
                for (int yCoord = maxY; yCoord >= minY; yCoord--) {
                    String row = "";

                    for (int zCoord = zStartCoord; zCoord <= zEndCoord; zCoord++) {
                        row += bedrock[xCoord][yCoord - minY][zCoord] ? "X" : " ";
                    }

                    System.out.println(row);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Coordinates out of bounds");
            }
        }
    }

    static boolean[][][] getBedrockMap(long seed, int x, int z, int xSize, int zSize, int minY, int maxY) {
        BedrockReader bedrockReader = new BedrockReader(seed);

        boolean[][][] bedrock = new boolean[xSize][maxY - minY + 1][zSize];

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < zSize; j++) {
                for (int k = 0; k < maxY - minY + 1; k++) {
                    bedrock[i][k][j] = bedrockReader.isBedrock(x + i, minY + k, z + j);
                }
            }
        }

        return bedrock;
    }
}
