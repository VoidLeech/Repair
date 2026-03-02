package ch.voidlee.repair.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/pull/8828
@Mixin(KineticBlockEntity.class)
public class KineticBlockEntityMixin {
    @ModifyReturnValue(method = "convertToAngular", at = @At(value = "RETURN", ordinal = 0), remap = false)
    private static float convertToRadians(float original, float speed) {
        return speed * 360f / 60f / 20f;
    }
}
