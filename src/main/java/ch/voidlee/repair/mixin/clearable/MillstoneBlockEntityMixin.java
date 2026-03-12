package ch.voidlee.repair.mixin.clearable;

import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerContainer;
import net.minecraft.world.Clearable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(MillstoneBlockEntity.class)
public abstract class MillstoneBlockEntityMixin implements Clearable {

    @Shadow
    public ItemStackHandlerContainer inputInv;

    @Override
    public void clearContent() {
        inputInv.clearContent();
    }
}
