package ch.voidlee.repair.mixin.bug_fixes.farlands_ejector;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.logistics.depot.EjectorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// https://github.com/Creators-of-Create/Create/pull/10395
@Mixin(EjectorBlockEntity.class)
public abstract class EjectorBlockEntityMixin extends KineticBlockEntity {
    public EjectorBlockEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @ModifyArg(method = "activateDeferred", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setPos(DDD)V", remap = true), remap = false, index = 0)
    private double create_repair$floatingDouble1(double impreciseOriginal) {
        return worldPosition.getX() + 0.5;
    }

    @ModifyArg(method = "activateDeferred", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setPos(DDD)V", remap = true), remap = false, index = 1)
    private double create_repair$floatingDouble2(double impreciseOriginal) {
        return worldPosition.getY() + 1.0;
    }

    @ModifyArg(method = "activateDeferred", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setPos(DDD)V", remap = true), remap = false, index = 2)
    private double create_repair$floatingDouble3(double impreciseOriginal) {
        return worldPosition.getZ() + 0.5;
    }
}