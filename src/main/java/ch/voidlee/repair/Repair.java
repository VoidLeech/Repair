package ch.voidlee.repair;

import ch.voidlee.repair.data.RepairDynamicPack;
import com.mojang.logging.LogUtils;
import io.github.fabricators_of_create.porting_lib.event.common.AddPackFindersEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;

public class Repair implements ModInitializer {

    public static final String NAME = "Create Repair";
    public static final String MOD_ID = "create_repair";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Repair() {
        LOGGER.info("{} {} initializing! Commit hash: {}", NAME, RepairBuildInfo.VERSION, RepairBuildInfo.GIT_COMMIT);
        LOGGER.info("{} modifies Create heavily to fix bugs! This should be regarded to any log-consumers.", NAME);
    }


    @Override
    public void onInitialize() {
        ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation(Repair.MOD_ID, "create_repair_asset_overrides"),
                FabricLoader.getInstance().getModContainer(Repair.MOD_ID).get(),
                Component.literal("Create Repair Asset Overrides"),
                ResourcePackActivationType.DEFAULT_ENABLED
                );
        AddPackFindersEvent.EVENT.register(Repair::addPackFinders);
    }

    private static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            RepairDynamicPack pack = new RepairDynamicPack(PackType.SERVER_DATA);
            pack.populate();
            event.addRepositorySource(pack.getDynamicSource());
        }
}
}
