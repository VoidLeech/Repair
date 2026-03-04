package ch.voidlee.repair.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.logistics.packagePort.postbox.PostboxBlock;
import com.simibubi.create.content.logistics.packagePort.postbox.PostboxBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PostboxBlockEntity.class)
public class PostboxBlockEntityMixin {
    @WrapOperation(method = "onOpenChange", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    public boolean checkForNewBlockState(Level level, BlockPos pos, BlockState ogState, Operation<Boolean> original, boolean open) {
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof PostboxBlock))
            return false;

        return original.call(level, pos, state.setValue(PostboxBlock.OPEN, open));
    }
}
