package ch.voidlee.repair.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.decoration.copycat.CopycatModel;
import io.github.fabricators_of_create.porting_lib.models.QuadTransformers;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/174671d1d721d1f970c77caeb0a79bced59111ad
@Mixin(CopycatModel.class)
public abstract class CopycatModelMixin {

    @WrapOperation(method = "emitBlockQuads", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/renderer/v1/render/RenderContext;pushTransform(Lnet/fabricmc/fabric/api/renderer/v1/render/RenderContext$QuadTransform;)V"), remap = false)
    private void create_repair$setEmissivity(RenderContext instance, RenderContext.QuadTransform quadTransform, Operation<Void> original, @Local(argsOnly = true) BlockAndTintGetter level, @Local(argsOnly = true) BlockPos pos, @Local(name = "material") BlockState material) {
        original.call(instance, quadTransform);
        if (material.emissiveRendering(level, pos)) {
            instance.pushTransform(QuadTransformers.settingMaxEmissivity());
        }
    }

    @WrapOperation(method = "emitBlockQuads", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/renderer/v1/render/RenderContext;popTransform()V"), remap = false)
    private void create_repair$popEmissivity(RenderContext instance, Operation<Void> original, @Local(argsOnly = true) BlockAndTintGetter level, @Local(argsOnly = true) BlockPos pos, @Local(name = "material") BlockState material) {
        original.call(instance);
        if (material.emissiveRendering(level, pos)) {
            instance.popTransform();
        }
    }
}
