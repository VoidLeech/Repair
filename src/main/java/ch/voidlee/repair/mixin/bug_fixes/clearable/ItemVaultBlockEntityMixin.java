package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.logistics.vault.ItemVaultBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.minecraft.world.Clearable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(ItemVaultBlockEntity.class)
public abstract class ItemVaultBlockEntityMixin implements Clearable {

    @Shadow
    protected ItemStackHandler inventory;

    @Override
    public void clearContent() {
        inventory.getSlots().clear();
    }
}
