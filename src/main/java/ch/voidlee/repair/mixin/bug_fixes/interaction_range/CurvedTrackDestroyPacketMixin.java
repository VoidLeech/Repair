package ch.voidlee.repair.mixin.bug_fixes.interaction_range;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.track.CurvedTrackDestroyPacket;
import com.simibubi.create.content.trains.track.TrackBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/f99fe5778b127d45c1fc2dc7d1f3599ba9f5e71a
@Mixin(CurvedTrackDestroyPacket.class)
public abstract class CurvedTrackDestroyPacketMixin {
    @ModifyExpressionValue(method = "applySettings(Lnet/minecraft/server/level/ServerPlayer;Lcom/simibubi/create/content/trains/track/TrackBlockEntity;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;closerThan(Lnet/minecraft/core/Vec3i;D)Z", remap = true), remap = false)
    private boolean create_repair$checkRangeAttribute(boolean original, @Local(argsOnly = true) ServerPlayer player, @Local(argsOnly = true) TrackBlockEntity be, @Local(name = "verifyDistance") int verifyDistance) {
        return player.canReach(be.getBlockPos(), verifyDistance);
    }
}
