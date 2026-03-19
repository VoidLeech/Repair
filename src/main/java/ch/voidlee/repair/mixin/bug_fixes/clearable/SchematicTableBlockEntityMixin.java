package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.schematics.table.SchematicTableBlockEntity;
import net.minecraft.world.Clearable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(SchematicTableBlockEntity.class)
public abstract class SchematicTableBlockEntityMixin implements Clearable {
    @Shadow
    public SchematicTableBlockEntity.SchematicTableInventory inventory;

    @Override
    public void clearContent() {
        inventory.getSlots().clear();
    }
}
