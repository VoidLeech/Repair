package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.logistics.stockTicker.StockTickerBlockEntity;
import com.simibubi.create.foundation.item.SmartInventory;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(StockTickerBlockEntity.class)
public abstract class StockTickerBlockEntityMixin implements Clearable {
    @Shadow
    protected List<ItemStack> categories;

    @Shadow
    protected SmartInventory receivedPayments;

    @Override
    public void clearContent() {
        categories.clear();
        receivedPayments.clearContent();
    }
}
