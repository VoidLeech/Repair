package ch.voidlee.repair.mixin.clearable;

import com.simibubi.create.content.logistics.packagePort.PackagePortBlockEntity;
import com.simibubi.create.foundation.item.SmartInventory;
import net.minecraft.world.Clearable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(PackagePortBlockEntity.class)
public abstract class PackagePortBlockEntityMixin implements Clearable {
    @Shadow
    public SmartInventory inventory;

    @Override
    public void clearContent() {
        inventory.clearContent();
    }
}
