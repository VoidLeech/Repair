package ch.voidlee.repair.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.foundation.blockEntity.IMultiBlockEntityContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/pull/9813/
@Mixin(ConnectivityHandler.class)
public class ConnectivityHandlerMixin {
    @WrapOperation(method = "tryToFormNewMultiOfWidth", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/blockEntity/IMultiBlockEntityContainer;notifyMultiUpdated()V", ordinal = 1), remap = false)
    private static void doNotNotifyMultiUpdatedTwice(IMultiBlockEntityContainer instance, Operation<Void> original) {
        // noop
    }
}
