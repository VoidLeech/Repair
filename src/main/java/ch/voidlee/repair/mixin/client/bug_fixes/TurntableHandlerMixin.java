package ch.voidlee.repair.mixin.client.bug_fixes;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.kinetics.turntable.TurntableHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/04d39f715bfca4888a68992fd0a580e7c5303b0e
@Mixin(TurntableHandler.class)
public abstract class TurntableHandlerMixin {
    @ModifyExpressionValue(method = "gameRenderTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;blockPosition()Lnet/minecraft/core/BlockPos;", remap = true), remap = false)
    private static BlockPos create_repair$onPos(BlockPos original) {
        return Minecraft.getInstance().player.getOnPos();
    }
}