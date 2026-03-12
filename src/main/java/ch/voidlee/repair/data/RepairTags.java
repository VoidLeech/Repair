package ch.voidlee.repair.data;

import ch.voidlee.repair.Repair;
import com.simibubi.create.Create;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.Locale;

public class RepairTags {
    public enum RepairBlockTags {
        PLOUGH_BLACKLIST,
        PLOUGH_WHITELIST;

        public final TagKey<Block> tag;

        RepairBlockTags() {
            tag = TagKey.create(BuiltInRegistries.BLOCK.key(), Create.asResource(name().toLowerCase(Locale.ROOT)));
        }
    }
}
