package ch.voidlee.repair.mixin.bug_fixes.interaction_range;

import ch.voidlee.repair.implementation.RepairedAbstractContraptionEntity;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.actors.trainControls.ControlsInputPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/f99fe5778b127d45c1fc2dc7d1f3599ba9f5e71a
@Mixin(ControlsInputPacket.class)
public abstract class ControlsInputPacketMixin {
    @Shadow
    private BlockPos controlsPos;

    @ModifyExpressionValue(method = "lambda$handle$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;closerThan(Lnet/minecraft/core/Position;D)Z"))
    private boolean create_repair$checkRangeAttribute(boolean original, @Local(name = "ace") AbstractContraptionEntity ace, @Local(name = "player") ServerPlayer player) {
        return ((RepairedAbstractContraptionEntity) ace).create_repair$canInteractWithBlock(player, controlsPos, 16);
    }
}