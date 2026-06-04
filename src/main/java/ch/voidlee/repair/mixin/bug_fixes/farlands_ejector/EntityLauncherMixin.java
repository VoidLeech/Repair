package ch.voidlee.repair.mixin.bug_fixes.farlands_ejector;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.depot.EntityLauncher;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// https://github.com/Creators-of-Create/Create/pull/10395
@Mixin(EntityLauncher.class)
public abstract class EntityLauncherMixin {
    @Shadow(remap = false)
    private int verticalDistance;

    @ModifyArg(method = "getGlobalPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;<init>(DDD)V", ordinal = 0), remap = false, index = 0)
    private double create_repair$floatingDouble1(double impreciseOriginal, @Local(argsOnly = true) BlockPos launcher) {
        return launcher.getX() + 0.5;
    }

    @ModifyArg(method = "getGlobalPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;<init>(DDD)V", ordinal = 0), remap = false, index = 1)
    private double create_repair$floatingDouble2(double impreciseOriginal, @Local(argsOnly = true) BlockPos launcher) {
        return launcher.getY() + 0.5;
    }

    @ModifyArg(method = "getGlobalPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;<init>(DDD)V", ordinal = 0), remap = false, index = 2)
    private double create_repair$floatingDouble3(double impreciseOriginal, @Local(argsOnly = true) BlockPos launcher) {
        return launcher.getZ() + 0.5;
    }

    @ModifyArg(method = "getGlobalPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;<init>(DDD)V", ordinal = 1), remap = false, index = 1)
    private double create_repair$floatingDouble4(double impreciseOriginal, @Local(name = "yt") float yt, @Local(name = "correctionStrength") double correctionStrength) {
        return yt + (verticalDistance - yt) * correctionStrength * 0.5;
    }
}