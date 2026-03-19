package ch.voidlee.repair.mixin.client.bug_fixes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.fluids.drain.ItemDrainRenderer;
import com.simibubi.create.content.kinetics.belt.BeltRenderer;
import com.simibubi.create.content.logistics.depot.DepotRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/pull/9942
@Mixin({ItemDrainRenderer.class, BeltRenderer.class, DepotRenderer.class})
public abstract class UprightItemRendererMixin {
    @WrapOperation(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;position()Lnet/minecraft/world/phys/Vec3;"))
    private static Vec3 create_repair$replacePosition(Entity instance, Operation<Vec3> original) {
        return Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
    }
}
