package mcjty.arienteworld;

import mcjty.ariente.api.IAlarmMode;
import mcjty.arienteworld.ai.CityAI;
import mcjty.arienteworld.ai.CityAISystem;
import mcjty.arienteworld.cities.BuildingPart;
import mcjty.arienteworld.cities.City;
import mcjty.arienteworld.cities.CityTools;
import mcjty.arienteworld.config.ConfigSetup;
import mcjty.arienteworld.config.WorldgenConfiguration;
import mcjty.arienteworld.dimension.ArienteChunkGenerator;
import mcjty.arienteworld.dimension.EditMode;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ForgeEventHandlers {

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (ConfigSetup.mainConfig.hasChanged()) {
            ConfigSetup.mainConfig.save();
        }
    }

    @SubscribeEvent
    public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        if (event.isSpawner()) {
            return;
        }
        if (event.getWorld().provider.getDimension() == WorldgenConfiguration.DIMENSION_ID.get()) {
            if (event.getEntity() instanceof EntityMob) {
                return;
            }
            if (event.getEntity() instanceof IAnimals) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

//    @SubscribeEvent
//    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
//        PowerSuitFeatureCache.checkCacheClean(event.getEntity().getEntityId(), event.getSlot(), event.getFrom(), event.getTo());
//    }

    private void onBlockBreakNormal(BlockEvent.BreakEvent event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        TileEntity te = world.getTileEntity(pos);
        if (world.provider.getDimension() == WorldgenConfiguration.DIMENSION_ID.get()) {
            EntityPlayer player = event.getPlayer();
            if (te instanceof IAlarmMode) {
                boolean highAlert = ((IAlarmMode) te).isHighAlert();
                alertCity(world, pos, player, highAlert);
            } else if (world.getBlockState(pos).getBlock() == ArienteStuff.reinforcedMarble) {
                alertCity(world, pos, player, true);
            }
        }
    }

    private void alertCity(World world, BlockPos pos, EntityPlayer player, boolean highAlert) {
        int cx = pos.getX() >> 4;
        int cz = pos.getZ() >> 4;
        ArienteChunkGenerator generator = (ArienteChunkGenerator) (((WorldServer) world).getChunkProvider().chunkGenerator);
        if (CityTools.isDungeonChunk(cx, cz)) {
            City city = CityTools.getNearestDungeon(generator, cx, cz);
            CityAISystem cityAISystem = CityAISystem.getCityAISystem(world);
            CityAI cityAI = cityAISystem.getCityAI(city.getCenter());
            if (cityAI != null) {
                if (highAlert) {
                    cityAI.highAlertMode(player);
                } else {
                    cityAI.alertCity(player);
                }
                cityAISystem.save();
            }
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!EditMode.editMode) {
            onBlockBreakNormal(event);
            return;
        }
        World world = event.getWorld();
        if (!world.isRemote) {
            if (world.provider.getDimension() == WorldgenConfiguration.DIMENSION_ID.get()) {
                ArienteChunkGenerator generator = (ArienteChunkGenerator)(((WorldServer) world).getChunkProvider().chunkGenerator);
                BlockPos pos = event.getPos();
                int cx = pos.getX() >> 4;
                int cz = pos.getZ() >> 4;
                City city = CityTools.getNearestDungeon(generator, cx, cz);
                if (city != null) {
                    List<BuildingPart> parts = CityTools.getBuildingParts(city, cx, cz);
                    if (!parts.isEmpty()) {
                        BuildingPart found = null;
                        int partY = -1;
                        int lowesty = CityTools.getLowestHeight(city, generator, cx, cz);
                        for (BuildingPart part : parts) {
                            int count = part.getSliceCount();
                            if (pos.getY() >= lowesty && pos.getY() < lowesty + count) {
                                found = part;
                                partY = lowesty;
                                break;
                            }
                            lowesty += count;

                        }
                        if (found != null) {
                            EditMode.breakBlock(city, event.getWorld(), found, pos.getX() & 0xf, pos.getY() - partY, pos.getZ() & 0xf);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent event) {
        if (!EditMode.editMode) {
            return;
        }
        World world = event.getWorld();
        if (!world.isRemote) {
            if (world.provider.getDimension() == WorldgenConfiguration.DIMENSION_ID.get()) {
                ArienteChunkGenerator generator = (ArienteChunkGenerator)(((WorldServer) world).getChunkProvider().chunkGenerator);
                BlockPos pos = event.getPos();
                int cx = pos.getX() >> 4;
                int cz = pos.getZ() >> 4;
                City city = CityTools.getNearestDungeon(generator, cx, cz);
                if (city != null) {
                    List<BuildingPart> parts = CityTools.getBuildingParts(city, cx, cz);
                    if (!parts.isEmpty()) {
                        BuildingPart found = null;
                        int partY = -1;
                        int lowesty = CityTools.getLowestHeight(city, generator, cx, cz);
                        for (BuildingPart part : parts) {
                            int count = part.getSliceCount();
                            if (pos.getY() >= lowesty && pos.getY() < lowesty + count) {
                                found = part;
                                partY = lowesty;
                                break;
                            }
                            lowesty += count;

                        }
                        if (found != null) {
                            EditMode.copyBlock(city, event.getWorld(), event.getPlacedBlock(), found, pos.getX() & 0xf, pos.getY() - partY, pos.getZ() & 0xf);
                        }
                    }
                }
            }
        }
    }

}
