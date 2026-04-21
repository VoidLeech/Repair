package ch.voidlee.repair.mixin.accessor;

import com.simibubi.create.content.equipment.toolbox.ToolboxInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ToolboxInventory.class)
public interface ToolboxInventoryAccessor {
    @Accessor(value = "settling", remap = false)
    void create_repair$setSettling(boolean val);
}
