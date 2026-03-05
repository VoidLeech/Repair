package ch.voidlee.repair.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.simibubi.create.compat.computercraft.implementation.peripherals.DisplayLinkPeripheral;
import dan200.computercraft.api.lua.LuaException;
import org.spongepowered.asm.mixin.Mixin;

// https://github.com/Creators-of-Create/Create/pull/9984
@Mixin(DisplayLinkPeripheral.class)
public abstract class DisplayLinkPeripheralMixin {
    @WrapMethod(method = "setCursorPos", remap = false)
    private void create_repair$cursorOOB(int x, int y, Operation<Void> original) throws LuaException {
        if (x < 1 || y < 1) {
            throw new LuaException("cursor position must be larger than 0");
        }
        original.call(x, y);
    }
}
