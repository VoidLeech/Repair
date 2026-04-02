package ch.voidlee.repair.mixin.client.bug_fixes.wrench_menu;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "com.simibubi.create.content.contraptions.wrench.RadialWrenchMenu$EvilBlockElement")
public abstract class RadialWrenchMenu$EvilBlockElementMixin {
    @WrapOperation(method = "renderBlockEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderDispatcher;getRenderer(Lnet/minecraft/world/level/block/entity/BlockEntity;)Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;", remap = true), remap = false)
    private BlockEntityRenderer<BlockEntity> create_repair$blockWithoutEntityRenderer(BlockEntityRenderDispatcher instance, BlockEntity blockEntity, Operation<BlockEntityRenderer<BlockEntity>> original) {
        return blockEntity != null ? original.call(instance, blockEntity) : null;
    }
}
