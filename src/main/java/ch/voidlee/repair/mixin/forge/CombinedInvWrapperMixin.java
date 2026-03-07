package ch.voidlee.repair.mixin.forge;

import ch.voidlee.repair.implementation.RepairedMountedItemStorageWrapper;
import ch.voidlee.repair.implementation.RepairedSlotFunction;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.simibubi.create.foundation.item.CombinedSlottedStackStorage;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlottedStackStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedSlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

// https://github.com/Creators-of-Create/Create/pull/9706/
@Mixin(CombinedSlottedStackStorage.class)
public abstract class CombinedInvWrapperMixin<S extends SlottedStackStorage> extends CombinedSlottedStorage<ItemVariant, S> {
    public CombinedInvWrapperMixin(List<S> parts) {
        super(parts);
    }

    // getFromStorage unfortunately has a private class as argument so we can't inject into that method
    // That rests us wrapping every method using it

    @Unique
    private <T> T create_repair$getFromStorage(int slot, RepairedSlotFunction<T> slotFunction, RepairedMountedItemStorageWrapper wrapper) {
        int i = wrapper.create_repair$getIndexForSlot(slot);
        return slotFunction.apply(parts.get(i), wrapper.create_repair$getSlotFromIndex(slot, i));
    }

    @WrapMethod(method = "getStackInSlot", remap = false)
    private ItemStack create_repair$getStackInSlot(int slot, Operation<ItemStack> original) {
        if ((Object) this instanceof RepairedMountedItemStorageWrapper wrapper) {
            return create_repair$getFromStorage(slot, SlottedStackStorage::getStackInSlot, wrapper);
        }
        return original.call(slot);
    }

    @WrapMethod(method = "setStackInSlot", remap = false)
    private void create_repair$setStackInSlot(int slot, ItemStack stack, Operation<Void> original) {
        if ((Object) this instanceof RepairedMountedItemStorageWrapper wrapper) {
            create_repair$getFromStorage(slot, (storage, index) -> {
                storage.setStackInSlot(index, stack);
                return null;
            }, wrapper);
        }
        original.call(slot, stack);
    }

    @WrapMethod(method = "getSlotLimit", remap = false)
    private int create_repair$getSlotLimit(int slot, Operation<Integer> original) {
        if ((Object) this instanceof RepairedMountedItemStorageWrapper wrapper) {
            return create_repair$getFromStorage(slot, SlottedStackStorage::getSlotLimit, wrapper);
        }
        return original.call(slot);
    }

    @WrapMethod(method = "isItemValid", remap = false)
    private boolean create_repair$isItemValid(int slot, ItemVariant resource, int count, Operation<Boolean> original) {
        if ((Object) this instanceof RepairedMountedItemStorageWrapper wrapper) {
            return create_repair$getFromStorage(slot, (storage, index) -> storage.isItemValid(slot, resource, count), wrapper);
        }
        return original.call(slot, resource, count);
    }

    @WrapMethod(method = "insertSlot", remap = false)
    private long create_repair$insertSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction, Operation<Long> original) {
        if ((Object) this instanceof RepairedMountedItemStorageWrapper wrapper) {
            return create_repair$getFromStorage(slot, (storage, index) -> storage.insertSlot(slot, resource, maxAmount, transaction), wrapper);
        }
        return original.call(slot, resource, maxAmount, transaction);
    }

    @WrapMethod(method = "extractSlot", remap = false)
    private long create_repair$extractSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction, Operation<Long> original) {
        if ((Object) this instanceof RepairedMountedItemStorageWrapper wrapper) {
            return create_repair$getFromStorage(slot, (storage, index) -> storage.extractSlot(slot, resource, maxAmount, transaction), wrapper);
        }
        return original.call(slot, resource, maxAmount, transaction);
    }
}
