package ch.voidlee.repair.mixin.crash_fixes;

import net.createmod.catnip.net.ServerboundConfigPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// https://github.com/Creators-of-Create/Ponder/pull/77
@Mixin(ServerboundConfigPacket.class)
public abstract class ServerboundConfigPacketMixin<T> {
    @Inject(method = "serialize", at = @At(value = "INVOKE", target = "Ljava/lang/IllegalArgumentException;<init>(Ljava/lang/String;)V"), remap = false, cancellable = true)
    private void create_repair$serializeString(T value, CallbackInfoReturnable<String> cir) {
        if (value instanceof String string) {
            cir.setReturnValue(string);
        }
    }

    @Inject(method = "deserialize", at = @At(value = "INVOKE", target = "Ljava/lang/IllegalArgumentException;<init>(Ljava/lang/String;)V"), remap = false, cancellable = true)
    private static void create_repair$deserializeString(Object type, String sValue, CallbackInfoReturnable<Object> cir) {
        if (type instanceof String) {
            cir.setReturnValue(sValue);
        }
    }
}