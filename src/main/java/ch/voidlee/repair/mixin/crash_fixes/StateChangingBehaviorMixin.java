package ch.voidlee.repair.mixin.crash_fixes;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.simibubi.create.api.behaviour.spouting.BlockSpoutingBehaviour;
import com.simibubi.create.api.behaviour.spouting.StateChangingBehavior;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Predicate;

// https://github.com/Creators-of-Create/Create/commit/640d35c3cbba4002bcd46c0eb016bcff8ebd78f9
@Mixin(StateChangingBehavior.class)
public abstract class StateChangingBehaviorMixin {
    @WrapMethod(method = "incrementingState", remap = false)
    private static BlockSpoutingBehaviour create_repair$missingCauldronLevel(int amount, Predicate<Fluid> fluidTest, IntegerProperty property, Operation<BlockSpoutingBehaviour> original){
        StateChangingBehavior orig = (StateChangingBehavior) original.call(amount, fluidTest, property);
        int max = property.getPossibleValues().stream().max(Integer::compareTo).orElseThrow();
        return new StateChangingBehavior(orig.amount(), orig.fluidTest(), state -> state.hasProperty(property) && state.getValue(property) < max, orig.fillFunction());
    }
}
