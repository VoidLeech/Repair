package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.logistics.depot.DepotBehaviour;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import net.minecraft.world.Clearable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(DepotBlockEntity.class)
public abstract class DepotBlockEntityMixin implements Clearable {
    @Shadow(remap = false)
    DepotBehaviour depotBehaviour;

    @Override
    public void clearContent() {
        ((Clearable) depotBehaviour).clearContent();
    }
}
