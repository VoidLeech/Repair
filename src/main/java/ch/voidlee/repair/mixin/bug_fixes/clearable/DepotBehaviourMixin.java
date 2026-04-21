package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.logistics.depot.DepotBehaviour;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.minecraft.world.Clearable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(DepotBehaviour.class)
public abstract class DepotBehaviourMixin implements Clearable {
    @Shadow(remap = false)
    ItemStackHandler processingOutputBuffer;

    @Shadow(remap = false)
    List<TransportedItemStack> incoming;

    @Shadow(remap = false)
    TransportedItemStack heldItem;

    @Override
    public void clearContent() {
        processingOutputBuffer.getSlots().clear();
        incoming.clear();
        heldItem = null;
    }
}
