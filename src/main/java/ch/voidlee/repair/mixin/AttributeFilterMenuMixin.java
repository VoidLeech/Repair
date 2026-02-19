package ch.voidlee.repair.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.filter.AbstractFilterMenu;
import com.simibubi.create.content.logistics.filter.AttributeFilterMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/pull/9729
@Mixin(AttributeFilterMenu.class)
public abstract class AttributeFilterMenuMixin extends AbstractFilterMenu {
    protected AttributeFilterMenuMixin(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
    }

    @ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;getItem(I)Lnet/minecraft/world/item/ItemStack;"), remap = false)
    private ItemStack create_repair$moveCorrectStack(ItemStack original, @Local(argsOnly = true) int index){
        return slots.get(index).getItem();
    }
}
