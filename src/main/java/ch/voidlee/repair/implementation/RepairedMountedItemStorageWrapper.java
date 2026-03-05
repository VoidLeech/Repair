package ch.voidlee.repair.implementation;

// https://github.com/Creators-of-Create/Create/pull/9706/'s optimization of MountedItemStorageWrapper
public interface RepairedMountedItemStorageWrapper {
    int[] create_repair$slotToStorage();
    int[] create_repair$slotOffsets();

    default int create_repair$getIndexForSlot(int slot) {
        if (slot < 0 || slot >= create_repair$slotToStorage().length) {
            return -1;
        }
        return create_repair$slotToStorage()[slot];
    }

    default int create_repair$getSlotFromIndex(int slot, int index) {
        return slot - create_repair$slotOffsets()[index];
    }
}
