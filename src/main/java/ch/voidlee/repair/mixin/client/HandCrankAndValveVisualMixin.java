package ch.voidlee.repair.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.crank.HandCrankBlockEntity;
import com.simibubi.create.content.kinetics.crank.HandCrankVisual;
import com.simibubi.create.content.kinetics.crank.ValveHandleVisual;
import net.createmod.catnip.math.AngleHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/pull/8828
@Mixin({HandCrankVisual.class, ValveHandleVisual.class})
public abstract class HandCrankAndValveVisualMixin {
    @WrapOperation(method = "rotateCrank", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/crank/HandCrankBlockEntity;getIndependentAngle(F)F"), remap = false)
    public float convertToRadians(HandCrankBlockEntity instance, float partialTicks, Operation<Float> original) {
        return AngleHelper.rad(original.call(instance, partialTicks));
    }
}
