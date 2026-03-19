package ch.voidlee.repair.mixin.bug_fixes;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.redstone.link.controller.LecternControllerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/11c82f6
@Mixin(LecternControllerBlockEntity.class)
public abstract class LecternControllerBlockEntityMixin {

    @ModifyReturnValue(method = "playerInRange", at = @At(value = "RETURN", ordinal = 0), remap = false)
    private static boolean create_repair$withYourEyes(boolean original, Player player, Level world, BlockPos pos, @Local(name = "reach") double reach) {
        return player.getEyePosition().distanceToSqr(Vec3.atCenterOf(pos)) < reach * reach;
    }
}
