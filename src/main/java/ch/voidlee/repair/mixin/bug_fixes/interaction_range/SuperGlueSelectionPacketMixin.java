package ch.voidlee.repair.mixin.bug_fixes.interaction_range;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.contraptions.glue.SuperGlueSelectionPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/f99fe5778b127d45c1fc2dc7d1f3599ba9f5e71a
@Mixin(SuperGlueSelectionPacket.class)
public abstract class SuperGlueSelectionPacketMixin {
    @Shadow(remap = false)
    private BlockPos to;

    @Definition(id = "distanceToSqr", method = "Lnet/minecraft/server/level/ServerPlayer;distanceToSqr(Lnet/minecraft/world/phys/Vec3;)D", remap = true)
    @Expression("?.distanceToSqr(?) > ?")
    @ModifyExpressionValue(method = "lambda$handle$0", at = @At("MIXINEXTRAS:EXPRESSION"), remap = false)
    private boolean create_repair$checkRangeAttribute(boolean original, @Local(name = "player") ServerPlayer player) {
        return player.canReach(to, 2);
    }
}