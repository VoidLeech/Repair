package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

// https://github.com/Creators-of-Create/Create/pull/10400
@Mixin(TableClothBlockEntity.class)
public abstract class TableClothBlockEntityMixin implements Clearable {
    @Shadow(remap = false)
    public List<ItemStack> manuallyAddedItems;

    @Override
    public void clearContent() {
        manuallyAddedItems.clear();
    }
}