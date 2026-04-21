package ch.voidlee.repair.mixin.optimization;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.foundation.blockEntity.behaviour.inventory.InvManipulationBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

// https://github.com/Creators-of-Create/Create/pull/9706/
@Mixin(InvManipulationBehaviour.class)
public abstract class InvManipulationBehaviourMixin {
    @Shadow(remap = false)
    protected abstract Predicate<ItemStack> getFilterTest(Predicate<ItemStack> customFilter);

    @Inject(method = "extract(Lcom/simibubi/create/foundation/item/ItemHelper$ExtractionCountMode;ILjava/util/function/Predicate;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/blockEntity/behaviour/inventory/InvManipulationBehaviour;getFilterTest(Ljava/util/function/Predicate;)Ljava/util/function/Predicate;"), cancellable = true, remap = false)
    public void create_repair$extract(ItemHelper.ExtractionCountMode mode, int amount, Predicate<ItemStack> filter, CallbackInfoReturnable<ItemStack> cir, @Local(name = "inventory") Storage<ItemVariant> inventory, @Local(name = "shouldSimulate") boolean shouldSimulate) {
        Predicate<ItemStack> test = getFilterTest(filter);
        cir.setReturnValue(ItemHelper.extract(inventory, test, mode, amount, shouldSimulate));
    }
}
