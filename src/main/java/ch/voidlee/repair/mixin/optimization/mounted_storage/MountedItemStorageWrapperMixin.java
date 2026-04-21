package ch.voidlee.repair.mixin.optimization.mounted_storage;

import ch.voidlee.repair.implementation.RepairedMountedItemStorageWrapper;
import com.google.common.collect.ImmutableMap;
import com.simibubi.create.api.contraption.storage.item.MountedItemStorage;
import com.simibubi.create.api.contraption.storage.item.MountedItemStorageWrapper;
import net.minecraft.core.BlockPos;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// https://github.com/Creators-of-Create/Create/pull/9706/
@Mixin(MountedItemStorageWrapper.class)
public class MountedItemStorageWrapperMixin extends CombinedInvWrapper implements RepairedMountedItemStorageWrapper {
    @Unique private int[] create_repair$slotToStorage;
    @Unique private int[] create_repair$slotOffsets;

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    public void initOptimization(ImmutableMap<BlockPos, MountedItemStorage> storages, CallbackInfo ci) {
        // Build lookup arrays
        int totalSlots = getSlots();
        this.create_repair$slotToStorage = new int[totalSlots];
        this.create_repair$slotOffsets = new int[itemHandler.length];

        int currentSlot = 0;
        for (int storageIdx = 0; storageIdx < itemHandler.length; storageIdx++) {
            create_repair$slotOffsets[storageIdx] = currentSlot;
            int slotsInStorage = itemHandler[storageIdx].getSlots();

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
