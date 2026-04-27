package ch.voidlee.repair.mixin.accessor;

import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RabbitModel.class)
public interface RabbitModelAccessor {
    @Accessor("head")
    ModelPart create_repair$getHead();
}
