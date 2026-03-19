package ch.voidlee.repair.mixin.bug_fixes.clearable;

import ch.voidlee.repair.mixin.accessor.ItemStackHandlerAccessor;
import com.simibubi.create.content.logistics.vault.ItemVaultBlockEntity;
import net.minecraft.world.Clearable;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(ItemVaultBlockEntity.class)
public abstract class ItemVaultBlockEntityMixin implements Clearable {
    @Shadow
    protected ItemStackHandler inventory;

    @Override
    public void clearContent() {
        ((ItemStackHandlerAccessor) inventory).create_repair$getStacks().clear();
    }
}
