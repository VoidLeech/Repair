package ch.voidlee.repair.mixin.accessor;

import com.simibubi.create.content.logistics.tunnel.BrassTunnelBlockEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BrassTunnelBlockEntity.class)
public interface BrassTunnelBlockEntityAccessor {
    @Accessor(value = "stackToDistribute", remap = false)
    void create_repair$setStackToDistribute(ItemStack stack);
}
