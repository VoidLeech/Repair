package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.logistics.BigItemStack;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.content.logistics.packager.PackagerItemHandler;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.Clearable;
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
        if  (!inventory.isResourceBlank()) {
            try (Transaction transaction = TransferUtil.getTransaction()) {
                inventory.extract(inventory.getResource(), 1, transaction);
                transaction.commit();
            }
        }
        queuedExitingPackages.clear();
    }
}
