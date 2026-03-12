package ch.voidlee.repair.mixin.clearable;

import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import com.simibubi.create.content.kinetics.belt.transport.BeltInventory;
import net.minecraft.world.Clearable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(BeltBlockEntity.class)
public abstract class BeltBlockEntityMixin implements Clearable {
    @Shadow
    protected BeltInventory inventory;

    @Override
    public void clearContent() {
        if (inventory != null) {
            inventory.getTransportedItems().clear();
        }
    }
}
