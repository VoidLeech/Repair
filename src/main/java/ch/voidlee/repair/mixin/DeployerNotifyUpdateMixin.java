package ch.voidlee.repair.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.deployer.DeployerBlock;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/dd133f667969870dd6e9b96395097ac93cd91ced
@Mixin(DeployerBlock.class)
public class DeployerNotifyUpdateMixin {
    @WrapOperation(method = "lambda$use$1", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/deployer/DeployerBlockEntity;sendData()V"), remap = false)
    private static void create_repair$actuallyJustNotifyUpdateInstead(DeployerBlockEntity instance, Operation<Void> original) {
        instance.setChanged();
        original.call(instance);
    }
}
