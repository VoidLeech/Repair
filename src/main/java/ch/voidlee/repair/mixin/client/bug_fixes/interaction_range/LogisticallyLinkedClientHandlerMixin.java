package ch.voidlee.repair.mixin.client.bug_fixes.interaction_range;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBehaviour;
import com.simibubi.create.content.logistics.packagerLink.LogisticallyLinkedClientHandler;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/f99fe5778b127d45c1fc2dc7d1f3599ba9f5e71a
@Mixin(LogisticallyLinkedClientHandler.class)
public abstract class LogisticallyLinkedClientHandlerMixin {
    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;closerThan(Lnet/minecraft/core/Vec3i;D)Z", remap = true), remap = false)
    private static boolean create_repair$checkRangeAttribute1(boolean original, @Local(name = "player") LocalPlayer player, @Local(name = "be") SmartBlockEntity be) {
        return player.canReach(be.getBlockPos(), 64);
    }

    @ModifyExpressionValue(method = "tickPanel", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;closerThan(Lnet/minecraft/core/Vec3i;D)Z", remap = true), remap = false)
    private static boolean create_repair$checkRangeAttribute2(boolean original, @Local(name = "player") LocalPlayer player, @Local(argsOnly = true) FactoryPanelBehaviour fpb) {
        return player.canReach(fpb.getPos(), 64);
    }
}
