package ch.voidlee.repair;

import ch.voidlee.repair.data.RepairDynamicPack;
import com.mojang.logging.LogUtils;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Repair.MOD_ID)
public class Repair {
    public static final String NAME = "Create Repair";
    public static final String MOD_ID = "create_repair";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Repair() {
        LOGGER.info("{} {} initializing! Commit hash: {}", NAME, RepairBuildInfo.VERSION, RepairBuildInfo.GIT_COMMIT);
        LOGGER.info("{} modifies Create heavily to fix bugs! This should be regarded to any log-consumers.", NAME);

        IEventBus bus = FMLJavaModLoadingContext.get()
                .getModEventBus();

        bus.addListener(Repair::addPackFinders);
    }

    private static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            RepairDynamicPack pack = new RepairDynamicPack(PackType.SERVER_DATA);
            pack.populate();

            event.addRepositorySource(pack.getDynamicSource());
        }
    }
}
