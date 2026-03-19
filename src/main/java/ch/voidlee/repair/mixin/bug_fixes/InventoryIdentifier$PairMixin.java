package ch.voidlee.repair.mixin.bug_fixes;

import com.simibubi.create.api.packager.InventoryIdentifier;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// https://github.com/Creators-of-Create/Create/pull/9793
@Mixin(InventoryIdentifier.Pair.class)
public abstract class InventoryIdentifier$PairMixin {
    @Mutable
    @Final
    @Shadow
    private BlockPos first;
    @Mutable
    @Final
    @Shadow
    private BlockPos second;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void create_repair$sortPair(BlockPos first, BlockPos second, CallbackInfo ci) {
        boolean isFirstLower = first.compareTo(second) < 0;
        this.first = isFirstLower ? first : second;
        this.second = isFirstLower ? second : first;
    }
}
