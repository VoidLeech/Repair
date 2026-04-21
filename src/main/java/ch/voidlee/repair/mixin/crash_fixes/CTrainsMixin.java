package ch.voidlee.repair.mixin.crash_fixes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.infrastructure.config.CTrains;
import net.createmod.catnip.config.ConfigBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/84dd4500125ab793fac93d72f4391e36b0193cff
@Mixin(CTrains.class)
public abstract class CTrainsMixin extends ConfigBase {
    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/infrastructure/config/CTrains;i(IILjava/lang/String;[Ljava/lang/String;)Lnet/createmod/catnip/config/ConfigBase$ConfigInt;", ordinal = 0), remap = false)
    private ConfigBase.ConfigInt create_repair$addAssemblyLengthLimit(CTrains config, int d, int min, String name, String[] comment, Operation<ConfigBase.ConfigInt> original) {
        return i(d, min, 512, name, comment);
    }

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/infrastructure/config/CTrains;i(IILjava/lang/String;[Ljava/lang/String;)Lnet/createmod/catnip/config/ConfigBase$ConfigInt;", ordinal = 1), remap = false)
    private ConfigBase.ConfigInt create_repair$addBogeyCountLimit(CTrains config, int d, int min, String name, String[] comment, Operation<ConfigBase.ConfigInt> original) {
        return i(d, min, 200, name, comment);
    }
}
