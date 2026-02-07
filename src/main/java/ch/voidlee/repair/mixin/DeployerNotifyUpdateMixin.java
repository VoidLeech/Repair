package ch.voidlee.repair.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.deployer.DeployerBlock;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DeployerBlock.class)
public class DeployerNotifyUpdateMixin {
    @WrapOperation(method = "lambda$use$1", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/deployer/DeployerBlockEntity;sendData()V"))
    private static void create_repair$actuallyJustNotifyUpdateInstead(DeployerBlockEntity instance, Operation<Void> original) {
        instance.setChanged();
        original.call(instance);
    }
}
