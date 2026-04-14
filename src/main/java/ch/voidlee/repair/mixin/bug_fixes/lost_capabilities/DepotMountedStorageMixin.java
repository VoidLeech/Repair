package ch.voidlee.repair.mixin.bug_fixes.lost_capabilities;

import ch.voidlee.repair.implementation.RepairCodecs;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.serialization.Codec;
import com.simibubi.create.content.logistics.depot.storage.DepotMountedStorage;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DepotMountedStorage.class)
public abstract class DepotMountedStorageMixin {
    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/ItemStack;CODEC:Lcom/mojang/serialization/Codec;", opcode = Opcodes.GETSTATIC))
    private static Codec<ItemStack> create_repair$addCaps(Codec<ItemStack> original) {
        return RepairCodecs.CAP_AWARE_ITEM_STACK;
    }
}
