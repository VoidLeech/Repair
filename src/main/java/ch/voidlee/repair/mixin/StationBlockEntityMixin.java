package ch.voidlee.repair.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.compat.computercraft.AbstractComputerBehaviour;
import com.simibubi.create.compat.computercraft.events.ComputerEvent;
import com.simibubi.create.compat.computercraft.events.StationTrainPresenceEvent;
import com.simibubi.create.content.trains.station.StationBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/5288372891bd35b603a2dcf74fd880f1761a0006
@Mixin(StationBlockEntity.class)
public abstract class StationBlockEntityMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/compat/computercraft/AbstractComputerBehaviour;prepareComputerEvent(Lcom/simibubi/create/compat/computercraft/events/ComputerEvent;)V"), remap = false)
    private void create_repair$(AbstractComputerBehaviour instance, ComputerEvent event, Operation<Void> original){
        if (event instanceof StationTrainPresenceEvent trainPresenceEvent && trainPresenceEvent.train != null) {
            original.call(instance, event);
        }
    }
}
