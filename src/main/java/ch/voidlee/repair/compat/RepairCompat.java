package ch.voidlee.repair.compat;

import net.createmod.catnip.lang.Lang;
import net.minecraftforge.fml.loading.LoadingModList;

public enum RepairCompat {
    FARMERSDELIGHT;

    final String id;
    RepairCompat() {
        id = Lang.asId(name());
    }

    public boolean isLoaded() {
        return LoadingModList.get().getModFileById(id) != null;
    }
}
