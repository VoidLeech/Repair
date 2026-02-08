package ch.voidlee.repair.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.equipment.armor.BacktankItem;
import com.simibubi.create.foundation.item.LayeredArmorItem;
import com.simibubi.create.foundation.mixin.accessor.HumanoidArmorLayerAccessor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Supplier;

// https://github.com/Creators-of-Create/Create/issues/9792
// https://github.com/Creators-of-Create/Create/commit/79b5d3b37e2d1970818dd97ca460b649cd0a456c
@Mixin(BacktankItem.Layered.class)
public abstract class LayeredBacktankItemMixin extends BacktankItem implements LayeredArmorItem {
    public LayeredBacktankItemMixin(ArmorMaterial material, Properties properties, ResourceLocation textureLoc, Supplier<BacktankBlockItem> placeable) {
        super(material, properties, textureLoc, placeable);
    }

    @Override
    public void renderArmorPiece(HumanoidArmorLayer<?, ?, ?> layer, PoseStack poseStack, MultiBufferSource bufferSource, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<?> originalModel, ItemStack stack) {
        LayeredArmorItem.super.renderArmorPiece(layer, poseStack, bufferSource, entity, slot, light, originalModel, stack);

        HumanoidArmorLayerAccessor accessor = (HumanoidArmorLayerAccessor) layer;
        boolean glint = stack.hasFoil();
        if (glint) {
            accessor.create$getInnerModel().renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.armorEntityGlint()), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            accessor.create$getOuterModel().renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.armorEntityGlint()), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
