package ch.voidlee.repair.mixin.crash_fixes;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.foundation.item.ItemHelper;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemHelper.class)
public abstract class ItemHelperMixin {
    @WrapOperation(method = "calcRedstoneFromInventory", at = @At(value = "INVOKE", target = "Lio/github/fabricators_of_create/porting_lib/transfer/TransferUtil;getTransaction()Lnet/fabricmc/fabric/api/transfer/v1/transaction/Transaction;"), remap = false, require = 0)
    private static Transaction create_repair$fapiDoesntUseThisAndNeitherDoWe(Operation<Transaction> original) {
        return null;
    }

    @WrapWithCondition(method = "calcRedstoneFromInventory", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/transfer/v1/transaction/Transaction;close()V"), remap = false, require = 0)
    private static boolean create_repair$thereIsNothingToClose(Transaction instance) {
        return false;
    }
}