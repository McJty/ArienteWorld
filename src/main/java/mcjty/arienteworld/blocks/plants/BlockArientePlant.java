package mcjty.arienteworld.blocks.plants;

import mcjty.lib.McJtyLib;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class BlockArientePlant extends BushBlock implements net.minecraftforge.common.IShearable {

    protected static final VoxelShape BLACK_BUSH_AABB = VoxelShapes.create(0.1, 0.0D, 0.1, 0.9, 0.8, 0.9);

    public BlockArientePlant() {
        super(Block.Properties.create(Material.PLANTS)
                .hardnessAndResistance(0.0f)
                .sound(SoundType.PLANT)
        );
    }

    public void initModel() {
        McJtyLib.proxy.initStandardItemModel(this);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BLACK_BUSH_AABB;
    }

    @Override
    public MaterialColor getMaterialColor(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return MaterialColor.WOOD;
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.GRASS;
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return true;
    }

//@todo 1.15 loot tables
//    @Override
//    public int quantityDropped(Random random) {
//        return random.nextInt(3);
//    }
//
//    @Override
//    public Item getItemDropped(BlockState state, Random rand, int fortune) {
//        return Items.STICK;
//    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS) {
            spawnAsEntity(worldIn, pos, new ItemStack(this, 1));
        } else {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IWorldReader world, BlockPos pos) {
        return true;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IWorld world, BlockPos pos, int fortune) {
        return Arrays.asList(new ItemStack(this));
    }
}