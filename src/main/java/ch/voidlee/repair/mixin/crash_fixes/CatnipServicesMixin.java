package ch.voidlee.repair.mixin.crash_fixes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.createmod.catnip.platform.CatnipServices;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ServiceLoader;

// https://github.com/Creators-of-Create/Ponder/pull/82
// Also see https://github.com/jaredlll08/Multiloader-Template/issues/97
@Mixin(CatnipServices.class)
public abstract class CatnipServicesMixin {
    @WrapOperation(method = "load", at = @At(value = "INVOKE", target = "Ljava/util/ServiceLoader;load(Ljava/lang/Class;)Ljava/util/ServiceLoader;"), remap = false)
    private static <S> ServiceLoader<S> create_repair$safeloader(Class<S> service, Operation<ServiceLoader<S>> original) {
        return ServiceLoader.load(service, CatnipServices.class.getClassLoader());
    }
}