package ch.voidlee.repair.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.redstone.link.controller.LecternControllerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// create_repair$initializeHandler: https://github.com/Creators-of-Create/Create/issues/9448
// create_repair$withYourEyes: https://github.com/Creators-of-Create/Create/commit/11c82f6
@Mixin(LecternControllerBlockEntity.class)
public abstract class LecternControllerBlockEntityMixin {
    @Shadow
    private ItemStackHandler controllerFreq;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void create_repair$initializeHandler(BlockEntityType<?> type, BlockPos pos, BlockState state, CallbackInfo ci) {
        this.controllerFreq = new ItemStackHandler(12);
    }

    @ModifyReturnValue(method = "playerInRange", at = @At(value = "RETURN", ordinal = 0), remap = false)
    private static boolean create_repair$withYourEyes(boolean original, Player player, Level world, BlockPos pos, @Local(name = "reach") double reach) {
        return player.getEyePosition().distanceToSqr(Vec3.atCenterOf(pos)) < reach * reach;
    }
}
