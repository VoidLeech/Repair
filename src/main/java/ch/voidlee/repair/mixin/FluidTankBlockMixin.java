package ch.voidlee.repair.mixin;

import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// https://github.com/Creators-of-Create/Create/pull/9814/
@Mixin(FluidTankBlock.class)
public abstract class FluidTankBlockMixin extends Block {
    public FluidTankBlockMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "onPlace", at = @At("TAIL"))
    private void create_repair$onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moved, CallbackInfo ci) {
        BlockState newState = world.getBlockState(pos);
        if (state != newState && newState.getBlock() == this) {
            world.markAndNotifyBlock(pos, world.getChunkAt(pos), oldState, newState, UPDATE_ALL_IMMEDIATE, 512);
        }
    }
}
