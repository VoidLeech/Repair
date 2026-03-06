package ch.voidlee.repair.compat.farmersdelight;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import vectorwing.farmersdelight.common.registry.ModBlocks;

// https://github.com/Creators-of-Create/Create/pull/9940
public class FarmersDelightCompat {
    public static boolean shouldHarvestMushroom(Level world, BlockPos pos) {
        return !world.getBlockState(pos.below()).is(ModBlocks.RICH_SOIL.get());
    }
}
