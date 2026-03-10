package ch.voidlee.repair.mixin.clearable;

import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorBlockEntity;
import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorPackage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Clearable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;
import java.util.Set;

// https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24
@Mixin(ChainConveyorBlockEntity.class)
public abstract class ChainConveyorBlockEntityMixin implements Clearable {
    @Shadow
    public Set<BlockPos> connections;

    @Shadow
    Map<BlockPos, List<ChainConveyorPackage>> travellingPackages;

    @Shadow
    List<ChainConveyorPackage> loopingPackages;

    @Override
    public void clearContent() {
        connections.clear();
        travellingPackages.clear();
        loopingPackages.clear();
    }
}
