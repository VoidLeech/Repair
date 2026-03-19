package ch.voidlee.repair.mixin.bug_fixes;

import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// https://github.com/Creators-of-Create/Create/pull/9921
@Mixin(MechanicalMixerBlockEntity.class)
public abstract class MechanicalMixerBlockEntityMixin extends BasinOperatingBlockEntity {
    @Shadow
    public int runningTicks;

    public MechanicalMixerBlockEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/mixer/MechanicalMixerBlockEntity;isVirtual()Z"), remap = false)
    private void create_repair$tick(CallbackInfo ci) {
        if (getSpeed() == 0 || !isSpeedRequirementFulfilled()) {
            if (runningTicks < 20) {
                runningTicks = 40 - runningTicks;
            }
            else if (runningTicks == 20) {
                runningTicks++;
            }
        }
    }
}
