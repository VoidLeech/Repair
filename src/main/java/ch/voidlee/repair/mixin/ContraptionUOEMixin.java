package ch.voidlee.repair.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/issues/9459
@Mixin(Level.class)
public class ContraptionUOEMixin {
    @ModifyExpressionValue(method = "blockEntityChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;hasChunkAt(Lnet/minecraft/core/BlockPos;)Z"))
    private boolean create_repair$UOEContraptionFix(boolean original) {
        if ((Object)this instanceof VirtualRenderWorld) {
            return false;
        }
        return original;
    }
}
