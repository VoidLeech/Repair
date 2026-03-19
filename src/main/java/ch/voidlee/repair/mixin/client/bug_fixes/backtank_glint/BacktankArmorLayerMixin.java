package ch.voidlee.repair.mixin.client.bug_fixes.backtank_glint;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.equipment.armor.BacktankArmorLayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/issues/9792
// https://github.com/Creators-of-Create/Create/commit/79b5d3b37e2d1970818dd97ca460b649cd0a456c
@Mixin(BacktankArmorLayer.class)
public abstract class BacktankArmorLayerMixin {
    @WrapOperation(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    public VertexConsumer create_repair$useFoilBuffer(MultiBufferSource instance, RenderType renderType, Operation<VertexConsumer> original,
                                                      PoseStack ms, MultiBufferSource buffer, int light, LivingEntity entity, float yaw, float pitch,
                                                      float pt, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        return ItemRenderer.getFoilBuffer(buffer, Sheets.cutoutBlockSheet(), false, true);
    }
}
