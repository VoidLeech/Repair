package ch.voidlee.repair.mixin;

import com.simibubi.create.content.redstone.link.controller.LecternControllerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// https://github.com/Creators-of-Create/Create/issues/9448
@Mixin(LecternControllerBlockEntity.class)
public abstract class LecternControllerPrintingMixin {
    @Shadow
    private ItemStackHandler controllerFreq;

    @Inject(method="<init>", at=@At("RETURN"))
    private void create_repair$initializeHandler(BlockEntityType<?> type, BlockPos pos, BlockState state, CallbackInfo ci) {
        this.controllerFreq = new ItemStackHandler(12);
    }
}
