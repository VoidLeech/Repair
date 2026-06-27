package ch.voidlee.repair.mixin.bug_fixes.farlands_ejector;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.depot.EjectorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/pull/10395
@Mixin(EjectorBlock.class)
public abstract class EjectorBlockMixin {
    @ModifyExpressionValue(method = "updateEntityAfterFallOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;blockPosition()Lnet/minecraft/core/BlockPos;"))
    private BlockPos create_repair$blockPosition(BlockPos original, @Local(argsOnly = true) Entity entity) {
        return entity.getOnPosLegacy();
    }
}