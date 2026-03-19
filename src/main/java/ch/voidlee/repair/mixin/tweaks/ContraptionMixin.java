package ch.voidlee.repair.mixin.tweaks;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/pull/9907
@Mixin(Contraption.class)
public abstract class ContraptionMixin {
    @Definition(id = "getBlock", method = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;")
    @Definition(id = "SeatBlock", type = SeatBlock.class)
    @Definition(id = "state", local = @Local(type = BlockState.class, name = "state"))
    @Expression("state.getBlock() instanceof SeatBlock")
    @WrapOperation(method = "moveBlock", at = @At("MIXINEXTRAS:EXPRESSION"), remap = false)
    private boolean create_repair$detectAllTaggedSeats(Object object, Operation<Boolean> original, @Local(name = "state") BlockState state) {
        return original.call(object) || AllTags.AllBlockTags.SEATS.matches(state);
    }
}
