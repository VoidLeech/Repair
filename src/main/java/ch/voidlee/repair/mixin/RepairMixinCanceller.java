package ch.voidlee.repair.mixin;

import com.bawnorton.mixinsquared.api.MixinCanceller;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;

import java.util.List;

public class RepairMixinCanceller implements MixinCanceller {
    private boolean shouldCancelJourneymapMixin;

    public RepairMixinCanceller() {
        if (RepairMixinPlugin.isModEarlyLoaded("journeymap")) {
            Version version = FabricLoader.getInstance().getModContainer("journeymap").get().getMetadata().getVersion();
            // Older versions aren't prefixed with the Minecraft version so this is straight up simpler than the necessary dependency range in mods.toml
            if (version.toString().contains("1.20.1")) {
                shouldCancelJourneymapMixin = true;
            }
        }
    }

    @Override
    public boolean shouldCancel(List<String> targetClassNames, String mixinClassName) {
        if (shouldCancelJourneymapMixin) {
            if (mixinClassName.equals("com.simibubi.create.foundation.mixin.compat.journeymap.JourneyFullscreenMapMixin")) {
                return true;
            }
        }
        return false;
    }
}
