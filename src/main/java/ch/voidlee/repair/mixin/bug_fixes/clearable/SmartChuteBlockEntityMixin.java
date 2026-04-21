package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.logistics.chute.SmartChuteBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
// https://github.com/Creators-of-Create/Create/pull/10196
@Mixin(SmartChuteBlockEntity.class)
public abstract class SmartChuteBlockEntityMixin extends ChuteBlockEntityMixin {
    @Shadow
    FilteringBehaviour filtering;

    @Override
    public void clearContent() {
        super.clearContent();
        filtering.setFilter(ItemStack.EMPTY);
    }
}
