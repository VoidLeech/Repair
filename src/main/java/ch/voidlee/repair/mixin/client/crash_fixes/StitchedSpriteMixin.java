package ch.voidlee.repair.mixin.client.crash_fixes;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.createmod.catnip.render.StitchedSprite;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// https://github.com/Creators-of-Create/Ponder/commit/89819291b726708d119b118dea01667eae0b64c9
@Mixin(StitchedSprite.class)
public abstract class StitchedSpriteMixin {
    @Mutable
    @Shadow(remap = false)
    @Final
    private static Map<ResourceLocation, List<StitchedSprite>> ALL;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void create_repair$concurrentMap(CallbackInfo ci) {
        ALL = new ConcurrentHashMap<>();
    }

    @ModifyReturnValue(method = "lambda$new$0", at = @At("RETURN"), remap = false)
    private static List<StitchedSprite> create_repair$synchronizedList(List<StitchedSprite> original) {
        return Collections.synchronizedList(original);
    }
}