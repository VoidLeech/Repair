package ch.voidlee.repair.mixin.bug_fixes;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/6409450
@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract BlockState getBlockStateOn();

    @Shadow
    public abstract BlockPos getOnPos();

    @ModifyExpressionValue(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getOnPosLegacy()Lnet/minecraft/core/BlockPos;"))
    private BlockPos create_repair$create$fixSeatBouncing(BlockPos original) {
        return getBlockStateOn().getBlock() instanceof SeatBlock ? getOnPos() : original;
    }
}
