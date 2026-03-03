package ch.voidlee.repair.mixin.client;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.Window;
import com.simibubi.create.compat.trainmap.XaeroTrainMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// https://github.com/Creators-of-Create/Create/commit/a9df5515a18c6906ab64242a195d12c21a6373c3
@Mixin(XaeroTrainMap.class)
public class XaeroTrainMapMixin {
    @Definition(id = "mapScale", local = @Local(type = double.class, name = "mapScale"))
    @Definition(id = "guiScale", local = @Local(type = double.class, name = "guiScale"))
    @Expression("mapScale / guiScale")
    @ModifyExpressionValue(method = "onRender", at = @At("MIXINEXTRAS:EXPRESSION"), remap = false)
    private static double create_repair$respectGuiScale(double original, @Local(name = "window") Window window) {
        return original / ((double) window.getWidth() / window.getScreenWidth());
    }
}
