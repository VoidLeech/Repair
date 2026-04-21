package ch.voidlee.repair.mixin.accessor;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemStackHandler.class)
public interface ItemStackHandlerAccessor {
    @Accessor(value = "stacks", remap = false)
    NonNullList<ItemStack> create_repair$getStacks();
}
