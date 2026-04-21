package ch.voidlee.repair.mixin.client.bug_fixes.interaction_range;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.entity.TrainRelocator;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/f99fe5778b127d45c1fc2dc7d1f3599ba9f5e71a
@Mixin(TrainRelocator.class)
public class TrainRelocatorMixin {
    @Shadow(remap = false)
    static Vec3 relocatingOrigin;

    @ModifyExpressionValue(method = "onClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;closerThan(Lnet/minecraft/core/Position;D)Z", remap = true), remap = false)
    private static boolean create_repair$checkRangeAttribute1(boolean original, @Local(name = "player") LocalPlayer player) {
        return player.canReach(BlockPos.containing(relocatingOrigin), 24);
    }

    @ModifyExpressionValue(method = "clientTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;closerThan(Lnet/minecraft/core/Position;D)Z", remap = true), remap = false)
    private static boolean create_repair$checkRangeAttribute2(boolean original, @Local(name = "player") LocalPlayer player) {
        return player.canReach(BlockPos.containing(relocatingOrigin), 24);
    }
}
