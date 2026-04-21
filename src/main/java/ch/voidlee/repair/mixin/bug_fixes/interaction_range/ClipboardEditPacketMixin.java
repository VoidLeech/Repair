package ch.voidlee.repair.mixin.bug_fixes.interaction_range;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.equipment.clipboard.ClipboardEditPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/394073dc1af30b5190504c10a0482cc6e2580703
@Mixin(ClipboardEditPacket.class)
public abstract class ClipboardEditPacketMixin {
    @Shadow(remap = false)
    private BlockPos targetedBlock;

    @ModifyExpressionValue(method = "lambda$handle$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;closerThan(Lnet/minecraft/core/Vec3i;D)Z"))
    private boolean create_repair$checkRangeAttribute(boolean original, @Local(name = "sender") ServerPlayer sender) {
        return sender.canReach(targetedBlock, 20);
    }
}
