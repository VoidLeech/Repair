package ch.voidlee.repair.mixin;

import com.simibubi.create.content.redstone.link.controller.LecternControllerBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LecternControllerBlockEntity.class)
public class LecternControllerPrintingMixin {
    @Shadow
    private ItemStackHandler controllerFreq;

    @Inject(method="<init>", at=@At("RETURN"))
    private void init(BlockEntityType<?> type, BlockPos pos, BlockState state, CallbackInfo ci) {
        this.controllerFreq = new ItemStackHandler(12);
    }
}
