package mcjty.arienteworld.commands;

import mcjty.arienteworld.cities.City;
import mcjty.arienteworld.cities.CityTools;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CommandFindCity implements ICommand {

    @Override
    public String getName() {
        return "ar_findcity";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return getName() + " [<city type>]";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = (EntityPlayer) sender;
        BlockPos start = player.getPosition();
        int cx = (start.getX() >> 4);
        int cz = (start.getZ() >> 4);
        Optional<ChunkPos> cityCenter;
        if (args.length > 0) {
            cityCenter = findNearbyCityCenter(cx, cz, args[0]);
        } else {
            cityCenter = findNearbyCityCenter(cx, cz);
        }

        if (!cityCenter.isPresent()) {
            sender.sendMessage(new TextComponentString("No nearby city!"));
        } else {
            sender.sendMessage(new TextComponentString("Nearest city at: " + cityCenter.get().x * 16 + "," + cityCenter.get().z * 16));
        }
    }


    @Nonnull
    private Optional<ChunkPos> findNearbyCityCenter(int cx, int cy, String cityType) {
        Optional<ChunkPos> center = findNearbyCityCenter(cx, cy);
        if (center.isPresent() && isCityOfType(center.get(), cityType)) {
            return center;
        }
        for (int d = 1 ; d < 5 ; d++) {
            for (int i = 1 ; i <= d*2 ; i++) {
                center = findNearbyCityCenter((cx-d+i)*8, (cy-d)*8);
                if (center.isPresent() && isCityOfType(center.get(), cityType)) {
                    return center;
                }
                center = findNearbyCityCenter((cx+d)*8, (cy-d+i)*8);
                if (center.isPresent() && isCityOfType(center.get(), cityType)) {
                    return center;
                }
                center = findNearbyCityCenter((cx+d-i)*8, (cy+d)*8);
                if (center.isPresent() && isCityOfType(center.get(), cityType)) {
                    return center;
                }
                center = findNearbyCityCenter((cx-d)*8, (cy+d-i)*8);
                if (center.isPresent() && isCityOfType(center.get(), cityType)) {
                    return center;
                }
            }
        }
        return Optional.empty();
    }

    private static boolean isCityOfType(ChunkPos coord, String cityType) {
        City city = CityTools.getCity(coord);
        return cityType.equals(city.getPlan().getName());
    }

    @Nonnull
    private Optional<ChunkPos> findNearbyCityCenter(int cx, int cz) {
        Optional<ChunkPos> cityCenter = CityTools.getNearestCityCenterO(cx, cz);
        if (cityCenter.isPresent()) {
            return cityCenter;
        }
        cityCenter = CityTools.getNearestCityCenterO(cx - 10, cz);
        if (cityCenter.isPresent()) {
            return cityCenter;
        }
        cityCenter = CityTools.getNearestCityCenterO(cx + 10, cz);
        if (cityCenter.isPresent()) {
            return cityCenter;
        }
        cityCenter = CityTools.getNearestCityCenterO(cx, cz - 10);
        if (cityCenter.isPresent()) {
            return cityCenter;
        }
        cityCenter = CityTools.getNearestCityCenterO(cx, cz + 10);
        return cityCenter;

    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return getName().compareTo(o.getName());
    }
}
