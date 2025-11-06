package ch.voidlee.repair;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Repair.MOD_ID)
public class Repair {
    public static final String MOD_ID = "create_repair";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Repair(FMLJavaModLoadingContext context)
    {

    }
}
