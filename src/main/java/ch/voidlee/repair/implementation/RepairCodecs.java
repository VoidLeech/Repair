package ch.voidlee.repair.implementation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class RepairCodecs {
    public static final Codec<ItemStack> CAP_AWARE_ITEM_STACK = RecordCodecBuilder.create((itemStackInstance) ->
            itemStackInstance.group(BuiltInRegistries.ITEM.byNameCodec().fieldOf("id").forGetter(ItemStack::getItem),
                            Codec.INT.fieldOf("Count").forGetter(ItemStack::getCount),
                            CompoundTag.CODEC.optionalFieldOf("tag").forGetter((stack) -> Optional.ofNullable(stack.getTag())),
                            CompoundTag.CODEC.optionalFieldOf("ForgeCaps").forGetter((stack -> {
                                CompoundTag tag = stack.serializeNBT();
                                return tag.contains("ForgeCaps") ? Optional.of(tag.getCompound("ForgeCaps")) : Optional.empty();
                            })))
                    .apply(itemStackInstance, (item, count, tag, caps) -> {
                        ItemStack stack = new ItemStack(item, count, caps.orElse(null));
                        tag.ifPresent(stack::setTag);
                        return stack;
                    }));
}
