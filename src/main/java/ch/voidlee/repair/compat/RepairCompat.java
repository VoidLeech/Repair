package ch.voidlee.repair.compat;

import net.createmod.catnip.lang.Lang;
import net.fabricmc.loader.api.FabricLoader;

public enum RepairCompat {
    FARMERSDELIGHT;

    final String id;
    RepairCompat() {
        id = Lang.asId(name());
    }

    public boolean isLoaded() {
        return FabricLoader.getInstance().isModLoaded(id);
    }
}
