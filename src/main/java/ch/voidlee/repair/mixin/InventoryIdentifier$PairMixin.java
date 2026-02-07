package ch.voidlee.repair.mixin;

import com.simibubi.create.api.packager.InventoryIdentifier;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryIdentifier.Pair.class)
public class InventoryIdentifier$PairMixin {
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
