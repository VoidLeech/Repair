package ch.voidlee.repair.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.decoration.copycat.CopycatModel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.QuadTransformers;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(CopycatModel.class)
public class CopycatModelMixin {
    @Unique
    private static final ModelProperty<Boolean> CREATE_REPAIR$IS_EMISSIVE_PROPERTY = new ModelProperty<>();

    @ModifyReturnValue(method = "gatherModelData", at = @At("TAIL"), remap = false)
    private ModelData.Builder create_repair$withEmissiveData(ModelData.Builder original, @Local(name = "material") BlockState material,
                                                             @Local(argsOnly = true) BlockAndTintGetter level, @Local(argsOnly = true) BlockPos pos) {
        original.with(CREATE_REPAIR$IS_EMISSIVE_PROPERTY, material.emissiveRendering(level, pos));
        return original;
    }

    @ModifyReturnValue(method = "getQuads(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/util/RandomSource;Lnet/minecraftforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)Ljava/util/List;",
            at = @At("TAIL"), remap = false)
    private List<BakedQuad> create_repair$setEmissivity(List<BakedQuad> original, @Local(argsOnly = true) ModelData data) {
        if (Boolean.TRUE.equals(data.get(CREATE_REPAIR$IS_EMISSIVE_PROPERTY))) {
            QuadTransformers.settingMaxEmissivity().processInPlace(original);
        }
        return original;
    }
}
