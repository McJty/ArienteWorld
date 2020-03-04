package mcjty.arienteworld.dimension.features;

import mcjty.ariente.api.MarbleColor;
import mcjty.arienteworld.ArienteStuff;
import mcjty.arienteworld.dimension.GeneratorTools;
import mcjty.arienteworld.dimension.PrimerTools;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

public class SpikesFeature implements IFeature {

    public static final String FEATURE_SPIKES = "spikes";

    private NoiseGeneratorPerlin featureNoise;

    public SpikesFeature() {
    }

    private NoiseGeneratorPerlin getNoise(World world) {
        if (featureNoise == null) {
            Random random = new Random(world.getSeed() * 817504943L + 101754694003L);
            featureNoise = new NoiseGeneratorPerlin(random, 4);
        }
        return featureNoise;
    }

    @Override
    public String getId() {
        return FEATURE_SPIKES;
    }

    @Override
    public void generate(World world, ChunkPrimer primer, int chunkX, int chunkZ, int dx, int dz) {

        if (dx != 0 || dz != 0) {
            return;
        }

        Random random = new Random(world.getSeed() + chunkX * 899809363L + chunkZ * 953485367L);
        random.nextFloat();

        char block1 = (char) Block.BLOCK_STATE_IDS.get(ArienteStuff.marble.getDefaultState().with(MarbleColor.COLOR, MarbleColor.BLACK));
        char block2 = (char) Block.BLOCK_STATE_IDS.get(ArienteStuff.marble.getDefaultState().with(MarbleColor.COLOR, MarbleColor.DARKBLUE));
        char blockG = (char) Block.BLOCK_STATE_IDS.get(ArienteStuff.marble.getDefaultState().with(MarbleColor.COLOR, MarbleColor.GRAY));
        char air = (char) Block.BLOCK_STATE_IDS.get(Blocks.AIR.getDefaultState());

        int radius = random.nextInt(4) + 2;
        int centerx = random.nextInt(10) + 3;
        int centerz = random.nextInt(10) + 3;

        int index = (centerx << 12) | (centerz << 8);
        int centery = PrimerTools.findTopBlock(primer, index, 200, air)-5;
        if (centery < 1) {
            centery = 1;
        }

        for (int x = centerx - radius / 2; x <= centerx + radius / 2; x++) {
            int distx = Math.abs(x-centerx);
            for (int z = centerz - radius / 2; z <= centerz + radius / 2; z++) {
                int distz = Math.abs(z-centerz);
                int avgdist = (distx+distz)/2;
                index = (x * 16 + z) * 256 + centery;
                int y = 0;
                while (y+centery < 255) {
                    if (primer.data[index+y-1] != air) {
                        float factor = 1.0f - y / (120.0f - avgdist * 3);
                        if (random.nextFloat() < factor) {
                            if (random.nextFloat() > (y-10) / 5.0f) {
                                primer.data[index+y] = blockG;
                            } else if (random.nextFloat() > (y-20) / 6.0f) {
                                primer.data[index+y] = block2;
                            } else {
                                primer.data[index+y] = block1;
                            }
                        }
                        y++;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    @Override
    public double getFactor(World world, int chunkX, int chunkZ) {
        double v = (getNoise(world).getValue(chunkX / 20.0f, chunkZ / 20.0f) - 1) / 70;
        if (v < 0) {
            return 0;
        } else if (v > 1) {
            return 1;
        } else {
            return v;
        }
    }

    @Override
    public Random getRandom(World world, int chunkX, int chunkZ) {
        return new Random(world.getSeed() + chunkZ * 836962723L + chunkX * 953480239L);
    }

    @Override
    public boolean isBase() {
        return false;
    }
}
