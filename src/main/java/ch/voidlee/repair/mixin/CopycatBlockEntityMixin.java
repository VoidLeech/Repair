package ch.voidlee.repair.mixin;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.copycat.CopycatBlockEntity;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Create/commit/efc3d3e
@Mixin(CopycatBlockEntity.class)
public abstract class CopycatBlockEntityMixin implements Clearable {
    @Shadow
    private BlockState material;

    @Shadow
    private ItemStack consumedItem;

    @Override
    public void clearContent() {
        this.material = AllBlocks.COPYCAT_BASE.getDefaultState();
        this.consumedItem = ItemStack.EMPTY;
    }
}
