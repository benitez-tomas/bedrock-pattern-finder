package com.mike;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PatternMaker {

    private static final String DIRECTORY = "pattern";

    public static List<BedrockBlock> convertAll() throws IOException {
        List<BedrockBlock> blocks = new ArrayList<>();
        Path dir = Paths.get(DIRECTORY);
        if (!Files.isDirectory(dir)) {
            System.err.println("Directory not found: " + DIRECTORY);
            return blocks;
        }

        DirectoryStream<Path> stream;
        try {
            stream = Files.newDirectoryStream(dir, "*.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Path file : stream) {
                String name = file.getFileName().toString();
                String baseName = name.substring(0, name.lastIndexOf('.'));
                convertFile(file, baseName, blocks);
            }
        return blocks;
    }

    private static void convertFile(Path file, String yLevel, List<BedrockBlock> blocks) throws IOException {
        List<String> lines = Files.readAllLines(file);
        for (int z = 0; z < lines.size(); z++) {
            String line = lines.get(z);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == '0' || c == '1') {
                    String blockStr = String.format("%d,%s,%d:%c", x, yLevel, z, c);
                    blocks.add(new BedrockBlock(blockStr));
                }
            }
        }
    }
}
