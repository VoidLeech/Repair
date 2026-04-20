package ch.voidlee.repair.mixin.client.crash_fixes;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.simibubi.create.content.equipment.clipboard.ClipboardScreen;
import net.createmod.catnip.gui.AbstractSimiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClipboardScreen.class)
public abstract class ClipboardScreenMixin extends AbstractSimiScreen {

    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/equipment/clipboard/ClipboardScreen;removed()V"))
    private boolean repair$dontCallRemoved(ClipboardScreen instance) {
        minecraft.setScreen(null);
        return false;
    }
}
