package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.fluids.drain.ItemDrainBlockEntity;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import net.minecraft.world.Clearable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/pull/10352
@Mixin(ItemDrainBlockEntity.class)
public abstract class ItemDrainBlockEntityMixin implements Clearable {
    @Shadow(remap = false)
    TransportedItemStack heldItem;

    @Override
    public void clearContent() {
        heldItem = null;
    }
}