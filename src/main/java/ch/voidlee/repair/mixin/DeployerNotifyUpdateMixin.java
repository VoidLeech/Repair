package ch.voidlee.repair.mixin;

import com.simibubi.create.content.kinetics.deployer.DeployerBlock;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DeployerBlock.class)
public class DeployerNotifyUpdateMixin {
    @Redirect(method = "lambda$use$1", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/deployer/DeployerBlockEntity;sendData()V"), require = 0)
    private static void actuallyJustNotifyUpdateInstead(DeployerBlockEntity instance) {
        instance.notifyUpdate();
    }
}
