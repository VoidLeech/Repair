/*
* Released under the terms of the MPL-2.0 license (see other-licenses/mpl-registrate)
* as this is essentially a translation of a MPL-2.0 licensed Java patch into the Mixin DSL.
*/

package ch.voidlee.repair.mixin.crash_fixes;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

// https://github.com/tterrag1098/Registrate/commit/2fd7712817b10c0d9a218301235894ce8148edff
@Mixin(FluidBuilder.class)
public abstract class FluidBuilderMixin<T extends ForgeFlowingFluid, P> extends AbstractBuilder<Fluid, T, P, FluidBuilder<T, P>> {
    @Shadow
    private NonNullConsumer<ForgeFlowingFluid.Properties> fluidProperties;

    public FluidBuilderMixin(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceKey<Registry<Fluid>> registryKey) {
        super(owner, parent, name, callback, registryKey);
    }

    @Inject(method="<init>(Lcom/tterrag/registrate/AbstractRegistrate;Ljava/lang/Object;Ljava/lang/String;Lcom/tterrag/registrate/builders/BuilderCallback;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Lcom/tterrag/registrate/builders/FluidBuilder$FluidTypeFactory;Lcom/tterrag/registrate/util/nullness/NonNullFunction;)V",
            at = @At("TAIL"))
    private void create_repair$resetFluidProps1(AbstractRegistrate owner, Object parent, String name, BuilderCallback callback, ResourceLocation stillTexture, ResourceLocation flowingTexture, FluidBuilder.FluidTypeFactory typeFactory, NonNullFunction fluidFactory, CallbackInfo ci) {
        fluidProperties = $ -> {};
    }

    @Inject(method="<init>(Lcom/tterrag/registrate/AbstractRegistrate;Ljava/lang/Object;Ljava/lang/String;Lcom/tterrag/registrate/builders/BuilderCallback;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Lcom/tterrag/registrate/util/nullness/NonNullSupplier;Lcom/tterrag/registrate/util/nullness/NonNullFunction;)V",
            at = @At("TAIL"))
    private void create_repair$resetFluidProps2(AbstractRegistrate owner, Object parent, String name, BuilderCallback callback, ResourceLocation stillTexture, ResourceLocation flowingTexture, NonNullSupplier fluidType, NonNullFunction fluidFactory, CallbackInfo ci) {
        fluidProperties = $ -> {};
    }

    @WrapMethod(method = "block(Lcom/tterrag/registrate/util/nullness/NonNullBiFunction;)Lcom/tterrag/registrate/builders/BlockBuilder;", remap = false)
    private <B extends LiquidBlock> BlockBuilder<B, FluidBuilder<T, P>> create_repair$blockProps(NonNullBiFunction<NonNullSupplier<? extends T>, BlockBehaviour.Properties, ? extends B> factory, Operation<BlockBuilder<B, FluidBuilder<T, P>>> original){
        var ret = original.call(factory);
        this.fluidProperties = fluidProperties.andThen(p -> p.block(ret.asSupplier()));
        return ret;
    }

    @WrapMethod(method = "bucket(Lcom/tterrag/registrate/util/nullness/NonNullBiFunction;)Lcom/tterrag/registrate/builders/ItemBuilder;", remap = false)
    private <I extends BucketItem> ItemBuilder<I, FluidBuilder<T, P>> create_repair$bucketProps(NonNullBiFunction<Supplier<? extends ForgeFlowingFluid>, Item.Properties, ? extends I> factory, Operation<ItemBuilder<I, FluidBuilder<T, P>>> original) {
        var ret = original.call(factory);
        this.fluidProperties = fluidProperties.andThen(p -> p.bucket(ret.asSupplier()));
        return ret;
    }
}
