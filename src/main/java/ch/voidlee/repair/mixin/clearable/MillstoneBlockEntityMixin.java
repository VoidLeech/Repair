package ch.voidlee.repair.mixin.clearable;

import ch.voidlee.repair.mixin.accessor.ItemStackHandlerAccessor;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import net.minecraft.world.Clearable;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(MillstoneBlockEntity.class)
public abstract class MillstoneBlockEntityMixin implements Clearable {
    @Shadow
    public ItemStackHandler inputInv;

    @Override
    public void clearContent() {
        ((ItemStackHandlerAccessor) inputInv).create_repair$getStacks().clear();
    }
}
