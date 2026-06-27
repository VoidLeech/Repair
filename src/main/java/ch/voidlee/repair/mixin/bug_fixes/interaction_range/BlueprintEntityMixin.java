package ch.voidlee.repair.mixin.bug_fixes.interaction_range;

import com.simibubi.create.content.equipment.blueprint.BlueprintEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// https://github.com/Creators-of-Create/Create/commit/9c2f16dd8614544a8d47c6bc514f511be22e865a
@Mixin(BlueprintEntity.class)
public abstract class BlueprintEntityMixin extends HangingEntity {
    protected BlueprintEntityMixin(EntityType<? extends HangingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "canPlayerUse", at = @At("HEAD"), remap = false, cancellable = true)
    private void create_repair$checkRangeAttribute(Player player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(player.canReach(this, 8));
    }
}