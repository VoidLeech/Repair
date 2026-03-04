package ch.voidlee.repair.data;

import ch.voidlee.repair.Repair;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.block.CopperBlockSet;
import com.simibubi.create.foundation.pack.DynamicPack;
import com.simibubi.create.foundation.pack.DynamicPackSource;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.*;

import static com.simibubi.create.foundation.block.CopperBlockSet.DEFAULT_VARIANTS;
import static net.minecraft.world.level.block.WeatheringCopper.WeatherState.*;

public class RepairDynamicPack extends DynamicPack {
    private static final String PACK_ID = "create_repair:dynamic_data";

    public RepairDynamicPack(PackType packType) {
        super(PACK_ID, packType);
    }

    @Override
    @Nullable
    public IoSupplier<InputStream> getRootResource(String @NotNull ... elements) {
        if(elements[0].equals("pack.png")) {
            return () -> {
                InputStream stream = Repair.class.getResourceAsStream("/square_icon.png");
                assert stream != null;
                return stream;
            };
        }
        return super.getRootResource(elements);
    }

    public void populate() {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AllBlocks.BOUND_CARDBOARD_BLOCK)
                .requires(AllBlocks.CARDBOARD_BLOCK)
                .requires(Tags.Items.STRING)
                .unlockedBy("has_item", hasItem(AllBlocks.CARDBOARD_BLOCK))
                .save(this::saveFinishedRecipe, Create.asResource("crafting/materials/bound_cardboard_block"));

        new Builder<>("clay", MillingRecipe::new)
                .require(Items.CLAY)
                .output(Items.CLAY_BALL, 4)
                .duration(50)
                .build();

        allWaxedCraftingForState(UNAFFECTED);
        allWaxedCraftingForState(EXPOSED);
        allWaxedCraftingForState(OXIDIZED);
        allWaxedCraftingForState(WEATHERED);
    }

    private InventoryChangeTrigger.TriggerInstance hasItem(ItemLike itemLike) {
        return new InventoryChangeTrigger.TriggerInstance(
                ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                new ItemPredicate[]{ItemPredicate.Builder.item().of(itemLike).build()}
        );
    }

    private void saveFinishedRecipe(FinishedRecipe recipe) {
        this.put(recipe.getId().withPrefix("recipes/"), recipe.serializeRecipe());

        if(recipe.getAdvancementId() == null) return;
        this.put(recipe.getAdvancementId().withPrefix("advancements/"), recipe.serializeAdvancement());
    }

    public DynamicPackSource getDynamicSource() {
        return new DynamicPackSource(PACK_ID, PackType.SERVER_DATA, Pack.Position.TOP, this);
    }

    private void allWaxedCraftingForState(WeatheringCopper.WeatherState state) {
        waxedToSlab(AllBlocks.COPPER_SHINGLES, state);
        waxedToStairs(AllBlocks.COPPER_SHINGLES, state);
        waxedToSlab(AllBlocks.COPPER_TILES, state);
        waxedToStairs(AllBlocks.COPPER_TILES, state);
    }

    private void waxedToSlab(CopperBlockSet set, WeatheringCopper.WeatherState state) {
        BlockEntry<?> block = set.get(DEFAULT_VARIANTS[0], state, true);
        BlockEntry<?> slab = set.get(DEFAULT_VARIANTS[1], state, true);
        shapedSlab(block, slab);
        stonecutting(block, slab, 2);
    }

    private void waxedToStairs(CopperBlockSet set, WeatheringCopper.WeatherState state) {
        BlockEntry<?> block = set.get(DEFAULT_VARIANTS[0], state, true);
        BlockEntry<?> stairs = set.get(DEFAULT_VARIANTS[2], state, true);
        shapedStairs(block, stairs);
        stonecutting(block, stairs, 1);
    }

    private void shapedSlab(ItemLike input, ItemLike output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .define('X', input)
                .pattern("XXX")
                .unlockedBy("has_" + getPath(input), hasItem(input))
                .save(this::saveFinishedRecipe, Create.asResource(getPath(output)));
    }

    private void shapedStairs(ItemLike input, ItemLike output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .define('X', input)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .unlockedBy("has_" + getPath(input), hasItem(input))
                .save(this::saveFinishedRecipe, Create.asResource(getPath(output)));
    }

    private void stonecutting(ItemLike input, ItemLike output, int count) {
        String inPath = getPath(input);
        String outPath = getPath(output);

        String recipePath = outPath + "_from_" + inPath + "_stonecutting";

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, output, count)
                .unlockedBy("has_" + inPath, hasItem(input))
                .save(this::saveFinishedRecipe, Create.asResource(recipePath));
    }

    private String getPath(ItemLike itemLike) {
        ResourceLocation inputLoc = ForgeRegistries.ITEMS.getKey(itemLike.asItem());
        assert inputLoc != null;
        return inputLoc.getPath();
    }

    private class Builder<T extends ProcessingRecipe<?>> extends ProcessingRecipeBuilder<T> {
        public Builder(String path, ProcessingRecipeBuilder.ProcessingRecipeFactory<T> factory) {
            super(factory, Create.asResource(path));
        }

        @Override
        public T build() {
            T t = super.build();
            DataGenResult<T> result = new DataGenResult<>(t, Collections.emptyList());
            RepairDynamicPack.this.put(result.getId().withPrefix("recipes/"), result.serializeRecipe());
            return t;
        }
    }
}
