package ch.voidlee.repair.mixin.client.crash_fixes.ponder_keybind;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import net.createmod.ponder.enums.PonderKeybinds;
import net.createmod.ponder.foundation.PonderTooltipHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Ponder/commit/551790b9363e1d5c01f48fd21b37b00c775484ff
@Mixin(PonderTooltipHandler.class)
public class PonderTooltipHandlerMixin {
    @WrapOperation(method = "deferredTick", at = @At(value = "INVOKE", target = "Lnet/createmod/ponder/enums/PonderKeybinds;isDown()Z"), remap = false)
    private static boolean create_repair$assertOnRenderThread(PonderKeybinds instance, Operation<Boolean> original) {
        return RenderSystem.isOnRenderThread() && original.call(instance);
    }
}
