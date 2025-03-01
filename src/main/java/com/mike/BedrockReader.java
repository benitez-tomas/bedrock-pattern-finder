package com.mike;

import com.mike.extracted.AbstractRandom;
import com.mike.extracted.MathHelper;
import com.mike.extracted.RandomDeriver;
import com.mike.extracted.RandomProvider;
import com.mike.recreated.Identifier;

public class BedrockReader {
    RandomDeriver floorRandomDeriver;
    RandomDeriver roofRandomDeriver;

    public BedrockReader(long seed) {
        floorRandomDeriver = RandomProvider.XOROSHIRO
                .create(seed).createRandomDeriver()
                .createRandom(BedrockType.BEDROCK_FLOOR.id.toString()).createRandomDeriver();
        
        roofRandomDeriver = RandomProvider.XOROSHIRO
                .create(seed).createRandomDeriver()
                .createRandom(BedrockType.BEDROCK_ROOF.id.toString()).createRandomDeriver();
    }

    boolean isBedrock(int x, int y, int z) {
        double probabilityValue = 0;

        BedrockType bedrockType = y < 0 ? BedrockType.BEDROCK_FLOOR : BedrockType.BEDROCK_ROOF;
        if (bedrockType == BedrockType.BEDROCK_FLOOR) {
            if (y == bedrockType.min) return true;
            if (y > bedrockType.max) return false;

            probabilityValue = MathHelper.lerpFromProgress(y, bedrockType.min, bedrockType.max, 1.0, 0.0);
        } else if (bedrockType == BedrockType.BEDROCK_ROOF) {
            if (y == bedrockType.min) return true;
            if (y < bedrockType.max) return false;

            probabilityValue = MathHelper.lerpFromProgress(y, bedrockType.max, bedrockType.min, 1.0, 0.0);
        }

        RandomDeriver randomDeriver = bedrockType == BedrockType.BEDROCK_FLOOR ? floorRandomDeriver : roofRandomDeriver;
        AbstractRandom abstractRandom = randomDeriver.createRandom(x, y, z);
        return (double)abstractRandom.nextFloat() < probabilityValue;
    }

    public enum BedrockType {
        BEDROCK_FLOOR(new Identifier("bedrock_floor"), -64, -64 + 5),
        BEDROCK_ROOF(new Identifier("bedrock_roof"), 128, 128 - 5);

        public final Identifier id;
        public final int min;
        public final int max;

        BedrockType(Identifier id, int min, int max) {
            this.id = id;
            this.min = min;
            this.max = max;
        }
    }
}
