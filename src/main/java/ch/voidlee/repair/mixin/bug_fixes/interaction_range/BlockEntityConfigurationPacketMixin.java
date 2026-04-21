package ch.voidlee.repair.mixin.bug_fixes.interaction_range;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.foundation.networking.BlockEntityConfigurationPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/f99fe5778b127d45c1fc2dc7d1f3599ba9f5e71a
@Mixin(BlockEntityConfigurationPacket.class)
public abstract class BlockEntityConfigurationPacketMixin {
    @Shadow(remap = false)
    protected BlockPos pos;

    @Shadow(remap = false)
    protected abstract int maxRange();

    @ModifyExpressionValue(method = "lambda$handle$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;closerThan(Lnet/minecraft/core/Vec3i;D)Z"))
    private boolean create_repair$checkRangeAttribute(boolean original, @Local(name = "player") ServerPlayer player) {
        return player.canReach(pos, maxRange());
    }
}
