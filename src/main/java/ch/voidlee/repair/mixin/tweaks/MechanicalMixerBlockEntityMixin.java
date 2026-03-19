package ch.voidlee.repair.mixin.tweaks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/pull/10022
@Mixin(MechanicalMixerBlockEntity.class)
public abstract class MechanicalMixerBlockEntityMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(III)I", remap = true), remap = false)
    private int create_repair$mixersMayGoSlower(int pValue, int pMin, int pMax, Operation<Integer> original) {
        if (pValue < pMax) {
            return original.call(pValue, pMin, pMax);
        }
        return Math.max(pValue, pMin);
    }
}
