package ch.voidlee.repair.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.fluids.transfer.FluidFillingBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/ecbb4f2
@Mixin(FluidFillingBehaviour.class)
public class FluidFillingBehaviourMixin {
    @Definition(id = "size", method = "Ljava/util/Set;size()I")
    @Definition(id = "maxBlocks", local = @Local(name = "maxBlocks", type = int.class))
    @Expression("?.size() > maxBlocks")
    @WrapOperation(method = "continueValidation", at = @At("MIXINEXTRAS:EXPRESSION"), remap = false)
    private boolean create_repair$continueValidation$canBeEqualTo(int left, int right, Operation<Boolean> original) {
        return original.call(left, right) || left == right;
    }
}
