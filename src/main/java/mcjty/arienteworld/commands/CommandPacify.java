package mcjty.arienteworld.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mcjty.arienteworld.ai.CityAI;
import mcjty.arienteworld.ai.CityAISystem;
import mcjty.arienteworld.cities.City;
import mcjty.arienteworld.cities.CityTools;
import mcjty.arienteworld.dimension.ArienteChunkGenerator;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class CommandPacify implements Command<CommandSource> {

    private static final CommandPacify CMD = new CommandPacify();

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("pacify")
                .requires(cs -> cs.hasPermissionLevel(2))
                .executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        PlayerEntity player = context.getSource().asPlayer();
        BlockPos start = player.getPosition();
        int cx = (start.getX() >> 4);
        int cz = (start.getZ() >> 4);

        ArienteChunkGenerator generator = (ArienteChunkGenerator) (((ServerWorld) player.getEntityWorld()).getChunkProvider().getChunkGenerator());
        City city = CityTools.getNearestDungeon(generator, cx, cz);
        if (city != null) {
            CityAISystem cityAISystem = CityAISystem.getCityAISystem(player.getEntityWorld());
            CityAI cityAI = cityAISystem.getCityAI(city.getCenter());
            if (cityAI != null) {
                cityAI.pacify(player.getEntityWorld());
                cityAISystem.save();
            }
        }
        return 0;
    }
}
