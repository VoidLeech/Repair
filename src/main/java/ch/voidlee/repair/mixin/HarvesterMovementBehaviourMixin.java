package ch.voidlee.repair.mixin;

import ch.voidlee.repair.compat.RepairCompat;
import ch.voidlee.repair.compat.farmersdelight.FarmersDelightCompat;
import com.simibubi.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HarvesterMovementBehaviour.class)
public class HarvesterMovementBehaviourMixin {
    @Inject(method = "isValidOther", at = @At("HEAD"), cancellable = true, remap = false)
    public void dontHarvestMushroomsOnRichSoil(Level world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof MushroomBlock && RepairCompat.FARMERSDELIGHT.isLoaded()) {
            cir.setReturnValue(FarmersDelightCompat.shouldHarvestMushroom(world, pos));
        }
    }
}
