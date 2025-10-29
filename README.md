# Minecraft Bedrock Finder (1.18–1.21)

This program allows you to search for a specific pattern of bedrock blocks in a Minecraft world. It scans a rectangular area of the world and prints the coordinates of every location where the pattern is found.

## Usage

You can build form source by cloning the repo and running

```bash
mvn package
```

from the directory containing the `pom.xml` file. You can find the `.jar` file already built in the releases page.

To run the program, use the following command:

```bash
java -jar bedrock_finder-1.0.jar <worldSeed> <startFromX> <startFromZ> <areaSizeX> <areaSizeZ> [<block>...]
```

### Parameters

- `worldSeed`: the seed of the Minecraft world to search.
- `startFromX` and `startFromZ`: the starting coordinates for the search.
- `areaSizeX` and `areaSizeZ`: the width and length of the area to scan (along X and Z axes).
- `[block]...`: optional list of blocks that define the pattern. Each block is a string in the format:

```
X,Y,Z:B
```

- `X` and `Z` are the relative coordinates of the block within the pattern.
- `Y` is the block’s height (absolute in the world).
- `B` is `1` if the block should be bedrock, `0` if it shouldn't.

### Loading Patterns from Text Files

Instead of specifying each block on the command line, you can load a pattern from text files:

1. Create a folder named `pattern` in the same directory as the `.jar` file.
2. Add one or more `.txt` files. Each file represents a layer of the pattern:

   - Use `1` for bedrock and `0` for empty/non-bedrock.
   - Each line corresponds to the X axis, and the line index corresponds to the Z axis.
   - The filename (without extension) is used as the Y coordinate for all blocks in that file.

3. The program will automatically read all `.txt` files in the folder and convert them into a pattern.

#### Example

A file named `-60.txt` containing:

```
010 11
11  0
010 10
```

would produce a 6×3 pattern at **Y = -60**, where bedrock is at positions marked `1` and no bedrock is at positions marked `0`. Spaces are ignored.

If no `[block]` arguments are provided, the program will automatically use the patterns loaded from the `pattern` folder.
