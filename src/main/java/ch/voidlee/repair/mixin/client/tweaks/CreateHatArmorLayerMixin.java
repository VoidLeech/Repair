package ch.voidlee.repair.mixin.client.tweaks;

import ch.voidlee.repair.mixin.client.accessor.RabbitModelAccessor;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.equipment.hats.CreateHatArmorLayer;
import com.simibubi.create.content.trains.schedule.hat.TrainHatInfo;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = CreateHatArmorLayer.class)
public class CreateHatArmorLayerMixin {

    @Inject(method = "registerOn", at = @At("HEAD"), cancellable = true, remap = false)
    private static void addCustomModelException(EntityRenderer<?> entityRenderer, CallbackInfo ci) {
        if (entityRenderer instanceof LivingEntityRenderer<?, ?> livingRenderer) {
            if (livingRenderer.getModel() instanceof RabbitModel) {
                CreateHatArmorLayer<?, ?> layer = new CreateHatArmorLayer<>(livingRenderer);
                livingRenderer.addLayer((CreateHatArmorLayer) layer);
                ci.cancel();
            }
        }
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z", ordinal = 0), remap = false)
    private void injectCustomHatModelRender(CallbackInfo ci, @Local(argsOnly = true) PoseStack ms, @Local(name = "entityModel") EntityModel<?> entityModel, @Local(name = "info") TrainHatInfo info, @Local(name = "partsToHead") List<ModelPart> partsToHead) {
        if (entityModel instanceof RabbitModel<?> model) {
            if (model.young) {
                ms.scale(0.56666666F, 0.56666666F, 0.56666666F);
                ms.translate(0.0F, 1.375F, 0.125F);
            } else {
                ms.scale(0.6F, 0.6F, 0.6F);
                ms.translate(0.0F, 1.0F, 0.0F);
            }
            ModelPart head = ((RabbitModelAccessor) model).create_repair$getHead();
            partsToHead.addAll(TrainHatInfo.getAdjustedPart(info, head, "head"));
        }
    }
}
