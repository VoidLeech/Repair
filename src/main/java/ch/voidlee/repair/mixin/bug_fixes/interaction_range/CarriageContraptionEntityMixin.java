package ch.voidlee.repair.mixin.bug_fixes.interaction_range;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/f99fe5778b127d45c1fc2dc7d1f3599ba9f5e71a
@Mixin(CarriageContraptionEntity.class)
public abstract class CarriageContraptionEntityMixin extends AbstractContraptionEntityMixin {
    @ModifyExpressionValue(method = "control", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;closerThan(Lnet/minecraft/core/Position;D)Z", remap = true), remap = false)
    private boolean create_repair$checkRangeAttribute(boolean original, @Local(argsOnly = true) Player player, @Local(argsOnly = true) BlockPos controlsLocalPos) {
        return create_repair$canInteractWithBlock(player, VecHelper.getCenterOf(controlsLocalPos), 8);
    }
}
