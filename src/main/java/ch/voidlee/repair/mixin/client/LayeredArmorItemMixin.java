package ch.voidlee.repair.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.item.LayeredArmorItem;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// https://github.com/Creators-of-Create/Create/issues/9792
// https://github.com/Creators-of-Create/Create/commit/79b5d3b37e2d1970818dd97ca460b649cd0a456c
@Mixin(LayeredArmorItem.class)
public interface LayeredArmorItemMixin {
    @Inject(method="renderModel", at = @At(value = "TAIL"), remap = false)
    private void create_repair$armorGlint(PoseStack poseStack, MultiBufferSource bufferSource, int light, ArmorItem item, Model model,
                                          boolean glint, float red, float green, float blue, ResourceLocation armorResource, CallbackInfo ci) {
        if (glint) {
            model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.armorEntityGlint()), light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
        }
    }
}
