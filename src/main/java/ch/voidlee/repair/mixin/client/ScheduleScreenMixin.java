package ch.voidlee.repair.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.InputConstants;
import com.simibubi.create.content.trains.schedule.ScheduleMenu;
import com.simibubi.create.content.trains.schedule.ScheduleScreen;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

// https://github.com/Creators-of-Create/Create/pull/9735
@Mixin(ScheduleScreen.class)
public abstract class ScheduleScreenMixin extends AbstractSimiContainerScreen<ScheduleMenu> {
    @Shadow
    private Consumer<Boolean> onEditorClose;

    @Shadow
    protected abstract void stopEditing();

    public ScheduleScreenMixin(ScheduleMenu container, Inventory inv, Component title) {
        super(container, inv, title);
    }

    // Expressions can't target the necessary code for an 'easy' diff.
    // We also need to inject in two places due to bytecode getting reordered in compilation

    // Primary fix
    @Inject(method = "keyPressed", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/gui/menu/AbstractSimiContainerScreen;keyPressed(III)Z", ordinal = 1), remap = false, cancellable = true)
    private void create_repair$allowTypingE2(int pKeyCode, int pScanCode, int pModifiers, CallbackInfoReturnable<Boolean> cir, @Local(name = "hitEnter") boolean hitEnter, @Local(name = "mouseKey") InputConstants.Key mouseKey){
        boolean hitE = getFocused() == null || minecraft.options.keyInventory.isActiveAndMatches(mouseKey);
        if (hitEnter) {
            onEditorClose.accept(true);
            stopEditing();
            cir.setReturnValue(true);
            return;
        }
        if (hitE) {
            cir.setReturnValue(false);
            return;
        }
        cir.setReturnValue(super.keyPressed(pKeyCode, pScanCode, pModifiers));
    }

    // Niche code path to make code flow fully match 'upstream'
    @Inject(method = "keyPressed", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"), remap = false, cancellable = true)
    private void create_repair$allowTypingE1(int pKeyCode, int pScanCode, int pModifiers, CallbackInfoReturnable<Boolean> cir, @Local(name = "hitEnter") boolean hitEnter, @Local(name = "mouseKey") InputConstants.Key mouseKey){
        boolean hitE = getFocused() == null || minecraft.options.keyInventory.isActiveAndMatches(mouseKey);
        if (hitEnter) return;
        if (hitE) {
            cir.setReturnValue(false);
            return;
        }
        cir.setReturnValue(super.keyPressed(pKeyCode, pScanCode, pModifiers));
    }
}
