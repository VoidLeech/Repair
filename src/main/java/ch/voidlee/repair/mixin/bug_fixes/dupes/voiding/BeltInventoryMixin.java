package ch.voidlee.repair.mixin.bug_fixes.dupes.voiding;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.simibubi.create.content.kinetics.belt.transport.BeltInventory;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

// The diff of these diffs
// https://github.com/Creators-of-Create/Create/pull/9954
// https://github.com/Creators-of-Create/Create/pull/10017
@Mixin(BeltInventory.class)
public abstract class BeltInventoryMixin {
    @Shadow(remap = false)
    @Final
    List<TransportedItemStack> toInsert;

    @Shadow(remap = false)
    @Final
    List<TransportedItemStack> toRemove;

    @Shadow(remap = false)
    protected abstract void insert(TransportedItemStack newStack);

    @Shadow(remap = false)
    @Final
    private List<TransportedItemStack> items;

    @WrapMethod(method = "write", remap = false)
    private CompoundTag create_repair$fixBeltDupe(Operation<CompoundTag> original) {
        if (!toInsert.isEmpty() || !toRemove.isEmpty()) {
            toInsert.forEach(this::insert);
            toInsert.clear();
            items.removeAll(toRemove);
            toRemove.clear();
        }
        return original.call();
    }
}
