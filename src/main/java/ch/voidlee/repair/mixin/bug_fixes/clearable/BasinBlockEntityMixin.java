package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinInventory;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.item.SmartInventory;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(BasinBlockEntity.class)
public abstract class BasinBlockEntityMixin implements Clearable {
    @Shadow(remap = false)
    protected List<ItemStack> spoutputBuffer;

    @Shadow(remap = false)
    public BasinInventory inputInventory;

    @Shadow(remap = false)
    protected SmartInventory outputInventory;

    @Shadow(remap = false)
    private FilteringBehaviour filtering;

    @Override
    public void clearContent() {
        spoutputBuffer.clear();
        inputInventory.clearContent();
        outputInventory.clearContent();
        filtering.setFilter(ItemStack.EMPTY);
    }
}
