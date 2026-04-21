package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.logistics.funnel.FunnelBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(FunnelBlockEntity.class)
public abstract class FunnelBlockEntityMixin implements Clearable {
    @Shadow(remap = false)
    private FilteringBehaviour filtering;

    @Override
    public void clearContent() {
        filtering.setFilter(ItemStack.EMPTY);
    }
}
