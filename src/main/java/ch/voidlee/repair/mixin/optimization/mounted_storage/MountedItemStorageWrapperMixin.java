package ch.voidlee.repair.mixin.optimization.mounted_storage;

import ch.voidlee.repair.implementation.RepairedMountedItemStorageWrapper;
import com.google.common.collect.ImmutableMap;
import com.simibubi.create.api.contraption.storage.item.MountedItemStorage;
import com.simibubi.create.api.contraption.storage.item.MountedItemStorageWrapper;
import com.simibubi.create.foundation.item.CombinedSlottedStackStorage;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// https://github.com/Creators-of-Create/Create/pull/9706/
@Mixin(MountedItemStorageWrapper.class)
public class MountedItemStorageWrapperMixin extends CombinedSlottedStackStorage<MountedItemStorage> implements RepairedMountedItemStorageWrapper {
    @Unique private int[] create_repair$slotToStorage;
    @Unique private int[] create_repair$slotOffsets;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void initOptimization(ImmutableMap<BlockPos, MountedItemStorage> storages, CallbackInfo ci) {
        // Build lookup arrays
        int totalSlots = getSlotCount();
        this.create_repair$slotToStorage = new int[totalSlots];
        this.create_repair$slotOffsets = new int[parts.size()];

        int currentSlot = 0;
        for (int storageIdx = 0; storageIdx < parts.size(); storageIdx++) {
            create_repair$slotOffsets[storageIdx] = currentSlot;
            int slotsInStorage = parts.get(storageIdx).getSlotCount();

            for (int i = 0; i < slotsInStorage; i++) {
                create_repair$slotToStorage[currentSlot + i] = storageIdx;
            }

            currentSlot += slotsInStorage;
        }
    }

    @Override
    public int[] create_repair$slotToStorage() {
        return create_repair$slotToStorage;
    }

    @Override
    public int[] create_repair$slotOffsets() {
        return create_repair$slotOffsets;
    }
}
