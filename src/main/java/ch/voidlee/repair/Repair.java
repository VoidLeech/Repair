package ch.voidlee.repair;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Repair.MOD_ID)
public class Repair {
    public static final String NAME = "Create Repair";
    public static final String MOD_ID = "create_repair";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Repair() {
        LOGGER.info("{} {} initializing! Commit hash: {}", NAME, RepairBuildInfo.VERSION, RepairBuildInfo.GIT_COMMIT);
        LOGGER.info("{} modifies Create heavily to fix bugs! This should be regarded to any log-consumers.", NAME);
    }
}
