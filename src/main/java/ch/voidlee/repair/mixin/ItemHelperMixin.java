/*package ch.voidlee.repair.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.foundation.item.ItemHelper;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/pull/9706/
@Mixin(ItemHelper.class)
public class ItemHelperMixin {
    @WrapOperation(method = "extract(Lnet/minecraftforge/items/IItemHandler;Ljava/util/function/Predicate;Lcom/simibubi/create/foundation/item/ItemHelper$ExtractionCountMode;IZ)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"), remap = false)
    private static int skipEmptyStacks(int a, int b, Operation<Integer> original, @Local(name = "slot") int slot, @Local(name = "inv")IItemHandler inv) {
        ItemStack slotStack = inv.getStackInSlot(slot);
        if(slotStack.isEmpty()) return 0;
        return original.call(a, b);
    }
}*/
