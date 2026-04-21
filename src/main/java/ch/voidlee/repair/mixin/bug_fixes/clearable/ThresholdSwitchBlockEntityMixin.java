package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.redstone.thresholdSwitch.ThresholdSwitchBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ThresholdSwitchBlockEntity.class)
public abstract class ThresholdSwitchBlockEntityMixin implements Clearable {
    @Shadow(remap = false)
    private FilteringBehaviour filtering;

    @Override
    public void clearContent() {
        filtering.setFilter(ItemStack.EMPTY);
    }
}
