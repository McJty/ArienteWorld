package mcjty.arienteworld.ai;

import mcjty.ariente.api.ICityAISystem;
import mcjty.lib.worlddata.AbstractWorldData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class CityAISystem extends AbstractWorldData<CityAISystem> implements ICityAISystem {

    private static final String NAME = "ArienteCityAI";

    // Indexed by city center
    private Map<ChunkPos, CityAI> cityAIMap = new HashMap<>();

    public CityAISystem(String name) {
        super(name);
    }

    @Override
    public void clear() {
        cityAIMap.clear();
    }

    @Override
    public CityAI getCityAI(ChunkPos coord) {
        if (!cityAIMap.containsKey(coord)) {
            CityAI cityAI = new CityAI(coord);
            cityAIMap.put(coord, cityAI);
            save();
        }
        return cityAIMap.get(coord);
    }

    @Override
    public void saveSystem() {
        save();
    }

    @Nonnull
    public static CityAISystem getCityAISystem(World world) {
        return getData(world, CityAISystem.class, NAME);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList cityList = compound.getTagList("cities", Constants.NBT.TAG_COMPOUND);
        cityAIMap.clear();
        for (int i = 0 ; i < cityList.tagCount() ; i++) {
            NBTTagCompound nbt = cityList.getCompoundTagAt(i);
            int chunkX = nbt.getInteger("chunkx");
            int chunkZ = nbt.getInteger("chunkz");
            ChunkPos coord = new ChunkPos(chunkX, chunkZ);
            CityAI ai = new CityAI(coord);
            ai.readFromNBT(nbt);
            cityAIMap.put(coord, ai);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList cityList = new NBTTagList();
        for (Map.Entry<ChunkPos, CityAI> entry : cityAIMap.entrySet()) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("chunkx", entry.getKey().x);
            nbt.setInteger("chunkz", entry.getKey().z);
            entry.getValue().writeToNBT(nbt);
            cityList.appendTag(nbt);
        }
        compound.setTag("cities", cityList);

        return compound;
    }
}
