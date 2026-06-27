package ch.voidlee.repair.mixin.bug_fixes.interaction_range;

import com.simibubi.create.content.schematics.table.SchematicTableBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// https://github.com/Creators-of-Create/Create/commit/9c2f16dd8614544a8d47c6bc514f511be22e865a
@Mixin(SchematicTableBlockEntity.class)
public abstract class SchematicTableBlockEntityMixin extends SmartBlockEntity {
    public SchematicTableBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "canPlayerUse", at = @At("HEAD"), remap = false, cancellable = true)
    private void create_repair$deferToSuper(Player player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.canPlayerUse(player));
    }
}