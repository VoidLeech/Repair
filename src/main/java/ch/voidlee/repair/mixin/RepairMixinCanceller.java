package ch.voidlee.repair.mixin;

import com.bawnorton.mixinsquared.api.MixinCanceller;

import java.util.List;

public class RepairMixinCanceller implements MixinCanceller {
    private boolean shouldCancelJourneymapMixin;

    public RepairMixinCanceller() {
        if (RepairMixinPlugin.isModEarlyLoaded("journeymap")) {
            // Todo: version check
            shouldCancelJourneymapMixin = true;
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
