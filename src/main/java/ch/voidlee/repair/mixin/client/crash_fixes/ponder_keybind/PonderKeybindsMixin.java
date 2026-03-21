package ch.voidlee.repair.mixin.client.crash_fixes.ponder_keybind;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.createmod.ponder.enums.PonderKeybinds;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// https://github.com/Creators-of-Create/Ponder/commit/551790b9363e1d5c01f48fd21b37b00c775484ff
@Mixin(PonderKeybinds.class)
public abstract class PonderKeybindsMixin {
    @Shadow
    @Final
    private KeyMapping mapping;

    @WrapMethod(method = "isDown", remap = false)
    private boolean create_repair$checkMappingBound(Operation<Boolean> original) {
        return !mapping.isUnbound() && original.call();
    }
}
