package ch.voidlee.repair.mixin.bug_fixes.handle_rotation;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.crank.ValveHandleBlockEntity;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/pull/8828
@Mixin(ValveHandleBlockEntity.class)
public abstract class ValveHandleBlockEntityMixin {
    @WrapOperation(method = "getIndependentAngle", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/base/KineticBlockEntityRenderer;getAngleForBe(Lcom/simibubi/create/content/kinetics/base/KineticBlockEntity;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction$Axis;)F"), remap = false)
    private float create_repair$convertToDegrees(KineticBlockEntity be, BlockPos pos, Direction.Axis axis, Operation<Float> original) {
        return AngleHelper.deg(original.call(be, pos, axis));
    }

    @ModifyReturnValue(method = "getIndependentAngle", at = @At(value = "RETURN", ordinal = 1), remap = false)
    private float create_repair$convertToDegreesTheSequel(float original) {
        return original * Mth.RAD_TO_DEG;
    }
}
