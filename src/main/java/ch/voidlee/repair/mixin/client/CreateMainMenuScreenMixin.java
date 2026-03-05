package ch.voidlee.repair.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.infrastructure.gui.CreateMainMenuScreen;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateMainMenuScreen.class)
public abstract class CreateMainMenuScreenMixin extends Screen {
    @Unique
    private AbstractWidget create_repair$create_report_issues_button;

    protected CreateMainMenuScreenMixin(Component pTitle) {
        super(pTitle);
    }

    @WrapOperation(method = "addButtons", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/infrastructure/gui/CreateMainMenuScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", ordinal = 5))
    private GuiEventListener create_repair$storeAndDisableReportIssues(CreateMainMenuScreen screen, GuiEventListener button, Operation<GuiEventListener> original) {
        if(button instanceof AbstractWidget widget) {
            create_repair$create_report_issues_button = widget;
            widget.active = false;
        }
        return original.call(screen, button);
    }

    @Inject(method = "renderWindowForeground", at = @At(value = "INVOKE", target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V"), remap = false)
    private void create_repair$renderIssuesTooltip(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (mouseX < create_repair$create_report_issues_button.getX() || mouseX > create_repair$create_report_issues_button.getX() + 98)
            return;
        if (mouseY < create_repair$create_report_issues_button.getY() || mouseY > create_repair$create_report_issues_button.getY() + 20)
            return;
        graphics.renderComponentTooltip(font,
                FontHelper.cutTextComponent(Component.translatable("create_repair.menu.unsupported"), FontHelper.Palette.ALL_GRAY), mouseX,
                mouseY);
    }
}
