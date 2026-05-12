package ch.voidlee.repair.mixin.bug_fixes.clearable;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/pull/10224
@Mixin(ArmBlockEntity.class)
public abstract class ArmBlockEntityMixin implements Clearable {
    @Shadow(remap = false)
    ItemStack heldItem;

    @Override
    public void clearContent() {
        heldItem = ItemStack.EMPTY;
    }
}