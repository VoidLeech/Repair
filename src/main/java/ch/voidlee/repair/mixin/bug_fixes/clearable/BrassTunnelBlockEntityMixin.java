package ch.voidlee.repair.mixin.bug_fixes.clearable;

import ch.voidlee.repair.mixin.accessor.BrassTunnelBlockEntityAccessor;
import com.simibubi.create.content.logistics.tunnel.BrassTunnelBlockEntity;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

// https://github.com/Creators-of-Create/Create/pull/10748
@Mixin(BrassTunnelBlockEntity.class)
public abstract class BrassTunnelBlockEntityMixin implements Clearable {
    @Override
    public void clearContent() {
        // Since we need to accessor `stackToDistribute` anyway we can just bypass the tunnel cap
        ((BrassTunnelBlockEntityAccessor)this).create_repair$setStackToDistribute(ItemStack.EMPTY);
    }
}