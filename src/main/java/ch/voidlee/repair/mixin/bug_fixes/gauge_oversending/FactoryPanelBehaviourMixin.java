package ch.voidlee.repair.mixin.bug_fixes.gauge_oversending;

import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBehaviour;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlockEntity;
import com.simibubi.create.content.logistics.packagerLink.LogisticsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

// https://github.com/Creators-of-Create/Create/pull/9649
@Mixin(FactoryPanelBehaviour.class)
public abstract class FactoryPanelBehaviourMixin {
    @Shadow
    public abstract FactoryPanelBlockEntity panelBE();

    @Shadow
    private int lastReportedUnloadedLinks;

    @Shadow
    public UUID network;

    @Shadow
    public abstract int getUnloadedLinks();

    @Inject(method = "tickStorageMonitor", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/logistics/factoryBoard/FactoryPanelBehaviour;getLevelInStorage()I", remap = true), remap = false)
    private void create_repair$invalidateSummaries(CallbackInfo ci) {
        FactoryPanelBlockEntity panelBE = panelBE();
        if (!panelBE.restocker && getUnloadedLinks() == 0 && this.lastReportedUnloadedLinks != 0) {
            LogisticsManager.SUMMARIES.invalidate(this.network);
        }
    }
}
