package ch.voidlee.repair.mixin.bug_fixes.clearable;

import ch.voidlee.repair.mixin.accessor.ItemStackHandlerAccessor;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.logistics.depot.DepotBehaviour;
import net.minecraft.world.Clearable;
import net.minecraftforge.items.ItemStackHandler;
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
        ((ItemStackHandlerAccessor) processingOutputBuffer).create_repair$getStacks().clear();
        incoming.clear();
        heldItem = null;
    }
}
