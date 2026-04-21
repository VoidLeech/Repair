package ch.voidlee.repair.mixin.client.crash_fixes;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.simibubi.create.content.equipment.clipboard.ClipboardScreen;
import net.createmod.catnip.gui.AbstractSimiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/394073dc1af30b5190504c10a0482cc6e2580703
@Mixin(ClipboardScreen.class)
public abstract class ClipboardScreenMixin extends AbstractSimiScreen {

    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/equipment/clipboard/ClipboardScreen;removed()V"))
    private boolean create_repair$dontCallRemoved(ClipboardScreen instance) {
        minecraft.setScreen(null);
        return false;
    }
}
