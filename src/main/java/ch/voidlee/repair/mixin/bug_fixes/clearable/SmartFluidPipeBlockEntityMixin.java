package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.fluids.pipes.SmartFluidPipeBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(SmartFluidPipeBlockEntity.class)
public abstract class SmartFluidPipeBlockEntityMixin implements Clearable {
    @Shadow(remap = false)
    private FilteringBehaviour filter;

    @Override
    public void clearContent() {
        filter.setFilter(ItemStack.EMPTY);
    }
}
