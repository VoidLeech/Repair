package ch.voidlee.repair.mixin.bug_fixes;

import ch.voidlee.repair.implementation.RepairCodecs;
import com.mojang.serialization.Codec;
import com.simibubi.create.foundation.item.ItemSlots;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemSlots.class)
public abstract class ItemSlotsMixin {
    @ModifyArg(method = "lambda$static$0", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;unboundedMap(Lcom/mojang/serialization/Codec;Lcom/mojang/serialization/Codec;)Lcom/mojang/serialization/codecs/UnboundedMapCodec;"), index = 1, remap = false)
    private static Codec<ItemStack> create_repair$addCaps(Codec<ItemStack> originalItemStackCodec) {
        return RepairCodecs.CAP_AWARE_ITEM_STACK;
    }
}
