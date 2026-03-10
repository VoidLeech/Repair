package ch.voidlee.repair.mixin.clearable;

import com.simibubi.create.content.logistics.chute.ChuteBlockEntity;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(ChuteBlockEntity.class)
public abstract class ChuteBlockEntityMixin implements Clearable {
    @Shadow
    ItemStack item;

    @Override
    public void clearContent() {
        item = ItemStack.EMPTY;
    }
}
