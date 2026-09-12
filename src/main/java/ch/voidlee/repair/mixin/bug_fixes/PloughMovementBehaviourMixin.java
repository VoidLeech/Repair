package ch.voidlee.repair.mixin.bug_fixes;

import com.simibubi.create.content.contraptions.actors.plough.PloughMovementBehaviour;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// https://github.com/Creators-of-Create/Create/commit/3229cc1059943732dc784490fe7543f590ddb05f
@Mixin(PloughMovementBehaviour.class)
public abstract class PloughMovementBehaviourMixin {
    @ModifyArg(method = "getPlayer", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/actors/plough/PloughBlock$PloughFakePlayer;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V", remap = true), remap = false)
    private ItemStack create_repair$makeHoeUnbreakable(ItemStack stack) {
        stack.getOrCreateTag().putBoolean("Unbreakable", true);
        return stack;
    }
}