package ch.voidlee.repair.mixin.bug_fixes;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.graph.TrackNodeLocation;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/b91757a3a55205e9888e70fb5cb5c2380b4724c7
@Mixin(TrackNodeLocation.class)
public abstract class TrackNodeLocationMixin {
    @ModifyExpressionValue(method = "<init>(DDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;floor(F)I", ordinal = 0))
    private static int create_repair$longRangeFloat1(int original, @Local(ordinal = 0, argsOnly = true) double x){
        return Mth.floor((double)Math.round(x * 2));
    }

    @ModifyExpressionValue(method = "<init>(DDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;floor(F)I", ordinal = 1))
    private static int create_repair$longRangeFloat2(int original, @Local(ordinal = 2, argsOnly = true) double z){
        return Mth.floor((double)Math.round(z * 2));
    }
}
