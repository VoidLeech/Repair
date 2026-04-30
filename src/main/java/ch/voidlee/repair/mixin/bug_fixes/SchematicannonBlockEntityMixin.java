package ch.voidlee.repair.mixin.bug_fixes;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SchematicannonBlockEntity.class)
public abstract class SchematicannonBlockEntityMixin {
    @Inject(method = "refillFuelIfPossible", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/transfer/v1/transaction/Transaction;close()V"), remap = false)
    private void create_repair$commitTransaction(CallbackInfo ci, @Local(name = "t") Transaction t, @Local(name = "externalGunpowderFound") boolean externalGunpowderFound) {
        if (externalGunpowderFound) {
            t.commit();
        }
    }

    @Definition(id = "storage", local = @Local(type = Storage.class, name = "storage"))
    @Expression("storage == null")
    @ModifyExpressionValue(method = "refillFuelIfPossible", at = @At("MIXINEXTRAS:EXPRESSION"), remap = false)
    private boolean create_repair$insertContinue(boolean original, @Local(name = "externalGunpowderFound") boolean externalGunpowderFound) {
        return original || externalGunpowderFound;
    }
}