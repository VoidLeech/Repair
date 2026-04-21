package ch.voidlee.repair.mixin.bug_fixes.interaction_range;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.TrainRelocationPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/f99fe5778b127d45c1fc2dc7d1f3599ba9f5e71a
@Mixin(TrainRelocationPacket.class)
public abstract class TrainRelocationPacketMixin {
    @Shadow(remap = false)
    BlockPos pos;

    @ModifyExpressionValue(method = "lambda$handle$3", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;closerThan(Lnet/minecraft/core/Position;D)Z", ordinal = 0))
    private boolean create_repair$checkRangeAttribute1(boolean original, @Local(name = "sender") ServerPlayer sender, @Local(name = "verifyDistance") int verifyDistance) {
        return sender.canReach(pos, verifyDistance);
    }

    @ModifyExpressionValue(method = "lambda$handle$3", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;closerThan(Lnet/minecraft/core/Position;D)Z", ordinal = 1))
    private boolean create_repair$checkRangeAttribute2(boolean original, @Local(name = "sender") ServerPlayer sender, @Local(name = "verifyDistance") int verifyDistance, @Local(name = "cce") CarriageContraptionEntity cce) {
        return sender.canReach(cce, verifyDistance);
    }
}
