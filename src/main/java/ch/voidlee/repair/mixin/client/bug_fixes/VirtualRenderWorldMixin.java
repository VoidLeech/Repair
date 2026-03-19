package ch.voidlee.repair.mixin.client.bug_fixes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

// https://github.com/Creators-of-Create/Create/issues/9821
@Mixin(VirtualRenderWorld.class)
public abstract class VirtualRenderWorldMixin {
    @WrapOperation(method = "removeBlockEntity", at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;"))
    private <K, V> V create_repair$callSetRemoved(Map<K, V> instance, Object o, Operation<V> original) {
        V removed = original.call(instance, o);
        if (removed instanceof BlockEntity blockEntity) {
            blockEntity.setRemoved();
        }

        return removed;
    }
}
