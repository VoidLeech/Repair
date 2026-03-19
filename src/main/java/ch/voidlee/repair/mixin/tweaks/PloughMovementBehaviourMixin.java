package ch.voidlee.repair.mixin.tweaks;

import ch.voidlee.repair.data.RepairTags;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.contraptions.actors.plough.PloughMovementBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/pull/9346
@Mixin(PloughMovementBehaviour.class)
public class PloughMovementBehaviourMixin {
    @ModifyReturnValue(method = "canBreak", at = @At("TAIL"), remap = false)
    private boolean create_repair$ploughingInBlackAndWhite(boolean original, @Local(argsOnly = true) BlockState state) {
        if (state.is(RepairTags.RepairBlockTags.PLOUGH_BLACKLIST.tag)) {
            return false;
        }
        if (state.is(RepairTags.RepairBlockTags.PLOUGH_WHITELIST.tag)) {
            return true;
        }
        return original;
    }
}
