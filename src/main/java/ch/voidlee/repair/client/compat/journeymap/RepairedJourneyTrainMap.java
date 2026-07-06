package ch.voidlee.repair.client.compat.journeymap;

import ch.voidlee.repair.Repair;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.trainmap.TrainMapManager;
import com.simibubi.create.foundation.gui.RemovedGuiUtils;
import com.simibubi.create.foundation.utility.CreateLang;
import com.simibubi.create.infrastructure.config.AllConfigs;
import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.client.IClientPlugin;
import journeymap.api.v2.client.event.FullscreenRenderEvent;
import journeymap.api.v2.client.fullscreen.IFullscreen;
import journeymap.api.v2.client.util.UIState;
import journeymap.api.v2.common.Context;
import journeymap.api.v2.common.JourneyMapPlugin;
import journeymap.api.v2.common.event.FullscreenEventRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.Mth;

import java.util.List;

// https://github.com/Creators-of-Create/Create/blob/mc1.21.1/dev/src/main/java/com/simibubi/create/compat/trainmap/JourneyTrainMap.java
@JourneyMapPlugin(apiVersion = "2.0.0")
public class RepairedJourneyTrainMap implements IClientPlugin {
    @Override
    public String getModId() {
        return Repair.MOD_ID;
    }

    @Override
    public void initialize(IClientAPI jmClientApi) {
        FullscreenEventRegistry.FULLSCREEN_RENDER_EVENT.subscribe(Repair.MOD_ID, RepairedJourneyTrainMap::onRender);
    }

    // The original class's other public methods (called by TrainMapEvents) are just functional and don't cause classloading issues.

    // Todo: investigate mouse offset w.r.t. shown hover information (might exist on 1.21.1 as well)
    public static void onRender(FullscreenRenderEvent event) {
        GuiGraphics graphics = event.getGraphics();
        IFullscreen fullscreen = event.getFullscreen();
        Screen screen = fullscreen.getScreen();
        double x = fullscreen.getCenterBlockX(true);
        double z = fullscreen.getCenterBlockZ(true);
        int mX = event.getMouseX();
        int mY = event.getMouseY();
        float pt = event.getPartialTicks();

        UIState state = fullscreen.getUiState();
        if (state == null)
            return;
        if (state.ui != Context.UI.Fullscreen)
            return;
        if (!state.active)
            return;
        if (!AllConfigs.client().showTrainMapOverlay.get()) {
            renderToggleWidgetAndTooltip(graphics, screen, mX, mY);
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Window window = mc.getWindow();

        double guiScale = (double) window.getScreenWidth() / window.getGuiScaledWidth();
        double scale = state.blockSize / guiScale;

        PoseStack pose = graphics.pose();
        pose.pushPose();

        pose.translate(screen.width / 2.0f, screen.height / 2.0f, 0);
        pose.scale((float) scale, (float) scale, 1);
        pose.translate(-x, -z, 0);

        float mouseX = mX - screen.width / 2.0f;
        float mouseY = mY - screen.height / 2.0f;
        mouseX /= (float) scale;
        mouseY /= (float) scale;

        Rect2i bounds =
                new Rect2i(Mth.floor(-screen.width / 2.0f / scale + x), Mth.floor(-screen.height / 2.0f / scale + z),
                        Mth.floor(screen.width / scale), Mth.floor(screen.height / scale));

        List<FormattedText> tooltip =
                TrainMapManager.renderAndPick(graphics, Mth.floor(mouseX), Mth.floor(mouseY), pt, false, bounds);

        pose.popPose();

        if (!renderToggleWidgetAndTooltip(graphics, screen, mX, mY) && tooltip != null)
            RemovedGuiUtils.drawHoveringText(graphics, tooltip, mX, mY, screen.width, screen.height, 256, mc.font);
    }

    private static boolean renderToggleWidgetAndTooltip(GuiGraphics graphics, Screen screen, int mouseX,
                                                        int mouseY) {
        TrainMapManager.renderToggleWidget(graphics, 3, 30);
        if (!TrainMapManager.isToggleWidgetHovered(mouseX, mouseY, 3, 30))
            return false;

        RemovedGuiUtils.drawHoveringText(graphics, List.of(CreateLang.translate("train_map.toggle")
                .component()), mouseX, mouseY + 20, screen.width, screen.height, 256, Minecraft.getInstance().font);
        return true;
    }
}
