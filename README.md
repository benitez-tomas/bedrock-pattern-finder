# Minecraft Bedrock Finder (1.18-1.21)
This repo contains the code snippet to generate a Minecraft map of the Bedrock for a given seed in a given region.

## Usage
This code is just a demo and needs to be modified to be used effectively.

The main class is the [`BedrockReader`](https://github.com/Developer-Mike/minecraft-bedrock-generator/blob/main/src/main/java/com/mike/BedrockReader.java) class and has a `.isBedrock(int x, int y, int z)` method that returns true if the block at the given coordinates is bedrock.
```java
BedrockReader bedrockReader = new BedrockReader(seed);
boolean bedrock = bedrockReader.isBedrock(x, y, z);
```

To compile the code to a jar file, you can use the following command:
```bash
mvn package
```