package ch.voidlee.repair.mixin.bug_fixes.clearable;

import ch.voidlee.repair.mixin.accessor.ItemStackHandlerAccessor;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.minecraft.world.Clearable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(SchematicannonBlockEntity.class)
public abstract class SchematicannonBlockEntityMixin implements Clearable {
    @Shadow
    public SchematicannonInventory inventory;

    @Override
    public void clearContent() {
        ((ItemStackHandlerAccessor) inventory).create_repair$getStacks().clear();
    }
}
