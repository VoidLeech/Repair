package ch.voidlee.repair.mixin.bug_fixes.dupes.voiding;

import ch.voidlee.repair.mixin.accessor.ToolboxInventoryAccessor;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.equipment.toolbox.ToolboxInventory;
import com.simibubi.create.foundation.item.ItemSlots;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

// https://github.com/Creators-of-Create/Create/pull/9757
@Mixin(ToolboxInventory.class)
public abstract class ToolboxInventoryMixin {
    @Inject(method = "deserialize", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/item/ItemSlots;forEach(Lcom/simibubi/create/foundation/item/ItemSlots$SlotConsumer;)V"), remap = false)
    private static void create_repair$setSettlingTrue(ItemSlots slots, List<ItemStack> filters, CallbackInfoReturnable<ToolboxInventory> cir, @Local(name = "inventory") ToolboxInventory inventory) {
        ((ToolboxInventoryAccessor)inventory).create_repair$setSettling(true);
    }

    @Inject(method = "deserialize", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/item/ItemSlots;forEach(Lcom/simibubi/create/foundation/item/ItemSlots$SlotConsumer;)V", shift = At.Shift.AFTER), remap = false)
    private static void create_repair$setSettlingFalse(ItemSlots slots, List<ItemStack> filters, CallbackInfoReturnable<ToolboxInventory> cir, @Local(name = "inventory") ToolboxInventory inventory) {
        ((ToolboxInventoryAccessor)inventory).create_repair$setSettling(false);
    }
}
