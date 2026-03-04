package ch.voidlee.repair.mixin;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.crank.HandCrankBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/pull/8828
@Mixin(HandCrankBlockEntity.class)
public abstract class HandCrankBlockEntityMixin {
    @Shadow
    public float chasingVelocity;

    @ModifyReturnValue(method = "getIndependentAngle", at = @At(value = "RETURN", ordinal = 0), remap = false)
    private static float convertToRadians(float original, float partialTicks) {
        return original * 360f;
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/crank/HandCrankBlockEntity;getSpeed()F"), remap = false)
    private float convertToAngular(HandCrankBlockEntity instance, Operation<Float> original) {
        return KineticBlockEntity.convertToAngular(original.call(instance));
    }

    @Expression("((? * 10.0 / 3.0) - ?) * 0.25")
    @ModifyExpressionValue(method = "tick", at = @At(value = "MIXINEXTRAS:EXPRESSION"), remap = false)
    private float addChasingVelocity(float value, @Local(name = "actualSpeed") float actualSpeed) {
        return (actualSpeed - this.chasingVelocity) / 4f;
    }
}
