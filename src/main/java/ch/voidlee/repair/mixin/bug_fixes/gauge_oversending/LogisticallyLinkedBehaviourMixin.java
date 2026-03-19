package ch.voidlee.repair.mixin.bug_fixes.gauge_oversending;

import com.simibubi.create.content.logistics.packagerLink.LogisticallyLinkedBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// https://github.com/Creators-of-Create/Create/pull/9649
@Mixin(LogisticallyLinkedBehaviour.class)
public abstract class LogisticallyLinkedBehaviourMixin {
    @Shadow
    public static void keepAlive(LogisticallyLinkedBehaviour behaviour) {}

    @Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/logistics/packagerLink/GlobalLogisticsManager;linkLoaded(Ljava/util/UUID;Lnet/minecraft/core/GlobalPos;)V"), remap = false)
    private void create_repair$keepMeAlive(CallbackInfo ci) {
        keepAlive((LogisticallyLinkedBehaviour)(Object) this);
    }
}
