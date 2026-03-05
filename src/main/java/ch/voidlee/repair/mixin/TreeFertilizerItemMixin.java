package ch.voidlee.repair.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.equipment.TreeFertilizerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// https://github.com/Creators-of-Create/Create/pull/9758
@Mixin(TreeFertilizerItem.class)
public abstract class TreeFertilizerItemMixin {
    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private void create_repair$dropBlocksWhenReplaced(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir, @Local(name = "actualPos") BlockPos actualPos) {
        context.getLevel().destroyBlock(actualPos, true);
    }
}
