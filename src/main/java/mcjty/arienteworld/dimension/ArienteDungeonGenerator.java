package mcjty.arienteworld.dimension;

import mcjty.ariente.api.MarbleColor;
import mcjty.arienteworld.ArienteStuff;
import mcjty.arienteworld.cities.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArienteDungeonGenerator {

    private static Set<Character> rotatableChars = null;
    private boolean initialized = false;
    private ArienteChunkGenerator generator;

    private char airChar;
    private char baseChar;
    private char fillerChar;
    private char cityWallChar;
    private char cityWallTop;

    public void initialize(ArienteChunkGenerator generator) {
        this.generator = generator;
        if (!initialized) {
            airChar = (char) Block.BLOCK_STATE_IDS.get(Blocks.AIR.getDefaultState());
            baseChar = (char) Block.BLOCK_STATE_IDS.get(ArienteStuff.marble.getDefaultState());
            fillerChar = (char) Block.BLOCK_STATE_IDS.get(ArienteStuff.marble_bricks.getDefaultState().withProperty(MarbleColor.COLOR, MarbleColor.BLACK));
            cityWallChar = (char) Block.BLOCK_STATE_IDS.get(ArienteStuff.marble.getDefaultState().withProperty(MarbleColor.COLOR, MarbleColor.BLACK));
            cityWallTop = (char) Block.BLOCK_STATE_IDS.get(ArienteStuff.marble_smooth.getDefaultState().withProperty(MarbleColor.COLOR, MarbleColor.BLACK));

            initialized = true;
        }
    }

    public char getCityWallChar() {
        return cityWallChar;
    }

    public char getCityWallTop() {
        return cityWallTop;
    }

    public static Set<Character> getRotatableChars() {
        if (rotatableChars == null) {
            rotatableChars = new HashSet<>();
            addStates(Blocks.ACACIA_STAIRS, rotatableChars);
            addStates(Blocks.BIRCH_STAIRS, rotatableChars);
            addStates(Blocks.BRICK_STAIRS, rotatableChars);
            addStates(Blocks.QUARTZ_STAIRS, rotatableChars);
            addStates(Blocks.STONE_BRICK_STAIRS, rotatableChars);
            addStates(Blocks.DARK_OAK_STAIRS, rotatableChars);
            addStates(Blocks.JUNGLE_STAIRS, rotatableChars);
            addStates(Blocks.NETHER_BRICK_STAIRS, rotatableChars);
            addStates(Blocks.OAK_STAIRS, rotatableChars);
            addStates(Blocks.PURPUR_STAIRS, rotatableChars);
            addStates(Blocks.RED_SANDSTONE_STAIRS, rotatableChars);
            addStates(Blocks.SANDSTONE_STAIRS, rotatableChars);
            addStates(Blocks.SPRUCE_STAIRS, rotatableChars);
            addStates(Blocks.STONE_STAIRS, rotatableChars);
            addStates(Blocks.LADDER, rotatableChars);
            addStates(ArienteStuff.fluxBeamBlock, rotatableChars);
            addStates(ArienteStuff.slopeBlock, rotatableChars);
            addStates(ArienteStuff.rampBlock, rotatableChars);
            addStates(ArienteStuff.storageBlock, rotatableChars);
            addStates(ArienteStuff.flatLightBlock, rotatableChars);
            addStates(ArienteStuff.doorMarkerBlock, rotatableChars);
        }
        return rotatableChars;
    }

    private static void addStates(Block block, Set<Character> set) {
        for (int m = 0; m < 16; m++) {
            try {
                IBlockState state = block.getStateFromMeta(m);
                set.add((char) Block.BLOCK_STATE_IDS.get(state));
            } catch (Exception e) {
                // Ignore
            }
        }
    }

    public void generate(World worldIn, int x, int z, ChunkPrimer primer) {
        if (CityTools.isPortalChunk(x, z)) {
            int minHeight = getPortalHeight(generator, x, z);
            generatePart(primer, "portal", AssetRegistries.PARTS.get("portal"), Transform.ROTATE_NONE, 4, minHeight, 4);

            return;
        }

        City city = CityTools.getNearestDungeon(generator, x, z);
        if (city != null) {
            List<BuildingPart> parts = CityTools.getBuildingParts(city, x, z);
            if (!parts.isEmpty()) {
                int lowestY = CityTools.getLowestHeight(city, generator, x, z);
                int y = lowestY;
                CityPlan plan = city.getPlan();
                for (BuildingPart part : parts) {
                    generatePart(primer, plan.getPalette(), part, Transform.ROTATE_NONE, 0, y, 0);
                    y += part.getSliceCount();
                }

                // Make pilars down if needed
                if (!city.getPlan().isFloating()) {
                    CityIndex cityIndex = CityTools.getDungeonIndex(x, z);
                    assert cityIndex != null;
                    if (cityIndex.isTopLeft()) {
                        fillDown(primer, lowestY, 2, 2);
                    }
                    if (cityIndex.isTopRight()) {
                        fillDown(primer, lowestY, 13, 2);
                    }
                    if (cityIndex.isBottomLeft()) {
                        fillDown(primer, lowestY, 2, 13);
                    }
                    if (cityIndex.isBottomRight()) {
                        fillDown(primer, lowestY, 13, 13);
                    }
                }
            }
        }
    }

    public static int getPortalHeight(ArienteChunkGenerator generator, int x, int z) {
        ChunkHeightmap heightmap = generator.getHeightmap(x, z);
        int minHeight = 160;
        for (int dx = 4 ; dx <= 10 ; dx++) {
            for (int dz = 4 ; dz <= 10 ; dz++) {
                int h = heightmap.getHeight(dx, dz);
                if (h < minHeight) {
                    minHeight = h;
                }
            }
        }
        return minHeight;
    }

    private void fillDown(ChunkPrimer primer, int lowestY, int dx, int dz) {
        int y = lowestY-1;
        int index = (dx << 12) | (dz << 8);
        while (y > 1) {
            if (primer.data[index+y] == airChar) {
                primer.data[index+y] = fillerChar;
            } else {
                break;
            }
            y--;
        }
    }

    public int generatePart(ChunkPrimer primer, String palette,
                                    BuildingPart part,
                                    Transform transform,
                                    int ox, int oy, int oz) {
        CompiledPalette compiledPalette = CompiledPalette.getCompiledPalette(palette);

        for (int x = 0; x < part.getXSize(); x++) {
            for (int z = 0; z < part.getZSize(); z++) {
                BuildingPart.PalettedSlice vs = part.getVSlice(x, z);
                if (vs != null) {
                    int rx = ox + transform.rotateX(x, z);
                    int rz = oz + transform.rotateZ(x, z);
                    int index = (rx << 12) | (rz << 8) + oy;
                    int len = vs.getSlice().size();
                    boolean allSpaces = true;
                    for (int y = 0; y < len; y++) {
                        PaletteIndex c = vs.getSlice().get(y);
                        Character b = compiledPalette.get(c);
                        if (b == null) {
                            throw new RuntimeException("Could not find entry '" + c + "' in the palette for part '" + part.getName() + "'!");
                        }

                        if (transform != Transform.ROTATE_NONE) {
                            if (getRotatableChars().contains(b)) {
                                IBlockState bs = Block.BLOCK_STATE_IDS.getByValue(b);
                                bs = bs.withRotation(transform.getMcRotation());
                                b = (char) Block.BLOCK_STATE_IDS.get(bs);
                            }
                        }
                        if (allSpaces) {
                            // Skip all initial spaces. This is to avoid ugly generation issues with floating cities
                            if (b != airChar) {
                                primer.data[index] = b;
                                allSpaces = false;
                            }
                        } else {
                            primer.data[index] = b;
                        }
                        index++;
                    }
                }
            }
        }
        return oy + part.getSliceCount();
    }


}
