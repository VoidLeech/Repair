package ch.voidlee.repair.mixin.client.bug_fixes;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.contraptions.wrench.RadialWrenchMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/issues/9608
@Mixin(RadialWrenchMenu.class)
public abstract class RadialWrenchMenuMixin {
    @WrapOperation(method = "renderRadialSectors", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntity;getLevel()Lnet/minecraft/world/level/Level;"))
    private Level create_repair$maybeThereIsNoLevel(BlockEntity blockEntity, Operation<Level> original) {
        return blockEntity != null ? original.call(blockEntity) : null;
    }

    @WrapWithCondition(method = "renderRadialSectors", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntity;setLevel(Lnet/minecraft/world/level/Level;)V"))
    private boolean create_repair$onlyIfTheBlockEntityExists(BlockEntity blockEntity, Level level) {
        return blockEntity != null;
    }
}
