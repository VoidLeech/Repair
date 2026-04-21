package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.logistics.BigItemStack;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.content.logistics.packager.PackagerItemHandler;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(PackagerBlockEntity.class)
public abstract class PackagerBlockEntityMixin implements Clearable {
    @Shadow(remap = false)
    public PackagerItemHandler inventory;

    @Shadow(remap = false)
    public List<BigItemStack> queuedExitingPackages;

    @Override
    public void clearContent() {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
        queuedExitingPackages.clear();
    }
}
