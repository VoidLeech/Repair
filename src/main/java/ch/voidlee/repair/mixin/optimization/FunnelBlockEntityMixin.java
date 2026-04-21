package ch.voidlee.repair.mixin.optimization;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.logistics.funnel.FunnelBlockEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.ref.WeakReference;

// https://github.com/Creators-of-Create/Create/pull/9706/
@Mixin(FunnelBlockEntity.class)
public abstract class FunnelBlockEntityMixin {
    @Shadow(remap = false)
    private WeakReference<Entity> lastObserved;

    @Shadow(remap = false)
    protected abstract AABB getEntityOverflowScanningArea();

    @WrapOperation(method = "activateExtractor", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/logistics/funnel/FunnelBlockEntity;getEntityOverflowScanningArea()Lnet/minecraft/world/phys/AABB;"), remap = false)
    public AABB create_repair$thereIsNoAABB(FunnelBlockEntity instance, Operation<AABB> original) {
        return null;
    }

    // this uses ? because either mcdev or mixin hates me
    @Expression("? == null")
    @WrapOperation(method = "activateExtractor", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 1), remap = false)
    public boolean create_repair$doNotObserve(Object left, Object right, Operation<Boolean> original) {
        return true;
    }

    @Inject(method = "activateExtractor", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/logistics/funnel/FunnelBlockEntity;getAmountToExtract()I"), remap = false)
    public void create_repair$optimizedScanning(CallbackInfo ci) {
        Entity lastEntity = lastObserved != null ? lastObserved.get() : null;
        if (lastEntity != null && lastEntity.isAlive()) {
            AABB area = getEntityOverflowScanningArea();
            if (lastEntity.getBoundingBox().intersects(area))
                return;
            lastObserved = null;
        }
    }

    @ModifyArg(method = "activateExtractor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;", remap = true), index = 1, remap = false)
    private AABB create_repair$thereIsAnAABB(AABB original) {
        return getEntityOverflowScanningArea();
    }
}
