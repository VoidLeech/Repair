package ch.voidlee.repair.mixin.crash_fixes;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.RotationPropagator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/pull/9803
// https://github.com/Creators-of-Create/Create/pull/10013
@Mixin(RotationPropagator.class)
public abstract class RotationPropagatorMixin {
    @ModifyExpressionValue(method = "propagateNewSource", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/base/KineticBlockEntity;getTheoreticalSpeed()F", ordinal = 2), remap = false)
    private static float create_repair$fixFloatImprecision(float original, @Local(name = "newSpeed") float newSpeed) {
        if (Math.abs(original - newSpeed) <= 1e-4f) {
            return newSpeed;
        }
        return original;
    }
}
