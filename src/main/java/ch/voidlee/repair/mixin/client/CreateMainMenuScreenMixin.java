package ch.voidlee.repair.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.infrastructure.gui.CreateMainMenuScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreateMainMenuScreen.class)
public abstract class CreateMainMenuScreenMixin extends Screen {
    @Unique
    private final String CREATE_REPAIR_HOW_TO_REPORT_ISSUES = "https://github.com/VoidLeech/Repair/wiki/Reporting-Issues-to-Create";

    @Shadow
    protected abstract void linkTo(String url);

    protected CreateMainMenuScreenMixin(Component pTitle) {
        super(pTitle);
    }

    @WrapOperation(method = "addButtons", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;builder(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/Button$OnPress;)Lnet/minecraft/client/gui/components/Button$Builder;", ordinal = 3, remap = true), remap = false)
    private Button.Builder create_repair$changeReportIssuesLink(Component message, Button.OnPress ogOnPress, Operation<Button.Builder> original) {
        return original.call(message, (Button.OnPress)($ -> linkTo(CREATE_REPAIR_HOW_TO_REPORT_ISSUES)));
    }
}
