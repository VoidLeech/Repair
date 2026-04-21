package ch.voidlee.repair.mixin.client.bug_fixes.interaction_range;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.equipment.clipboard.ClipboardScreen;
import net.createmod.catnip.gui.AbstractSimiScreen;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/394073dc1af30b5190504c10a0482cc6e2580703
@Mixin(ClipboardScreen.class)
public abstract class ClipboardScreenMixin extends AbstractSimiScreen {
    @Shadow
    public BlockPos targetedBlock;

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;closerThan(Lnet/minecraft/core/Vec3i;D)Z"))
    private boolean create_repair$checkRangeAttribute(boolean original) {
        return minecraft.player.canReach(targetedBlock, 10);
    }
}
