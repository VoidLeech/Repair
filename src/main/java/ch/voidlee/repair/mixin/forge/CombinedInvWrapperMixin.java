/*package ch.voidlee.repair.mixin.forge;

import ch.voidlee.repair.implementation.RepairedMountedItemStorageWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// https://github.com/Creators-of-Create/Create/pull/9706/
@Mixin(CombinedInvWrapper.class)
public class CombinedInvWrapperMixin {
    @Inject(method = "getIndexForSlot", at = @At("HEAD"), cancellable = true, remap = false)
    public void create_repair$optimizedGetIndexForSlot(int slot, CallbackInfoReturnable<Integer> cir) {
        if(((Object)this) instanceof RepairedMountedItemStorageWrapper wrapper) {
            cir.setReturnValue(wrapper.create_repair$getIndexForSlot(slot));
        }
    }

    @Inject(method = "getSlotFromIndex", at = @At("HEAD"), cancellable = true, remap = false)
    public void create_repair$optimizedGetSlotFromImage(int slot, int index, CallbackInfoReturnable<Integer> cir) {
        if(((Object)this) instanceof RepairedMountedItemStorageWrapper wrapper) {
            cir.setReturnValue(wrapper.create_repair$getSlotFromIndex(slot, index));
        }
    }
}*/
