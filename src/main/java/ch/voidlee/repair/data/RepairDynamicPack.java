package ch.voidlee.repair.data;

import ch.voidlee.repair.Repair;
import com.mojang.logging.LogUtils;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.pack.DynamicPack;
import com.simibubi.create.foundation.pack.DynamicPackSource;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;

import java.util.*;

public class RepairDynamicPack extends DynamicPack {
    private static final String PACK_ID = "create_repair:dynamic_data";

//    private final RepairFilter filter;

    public RepairDynamicPack(PackType packType) {
        super(PACK_ID, packType);
//        this.filter = new RepairFilter();
    }

    public void populate() {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AllBlocks.BOUND_CARDBOARD_BLOCK)
                .requires(AllBlocks.CARDBOARD_BLOCK)
                .requires(Tags.Items.STRING)
                .unlockedBy("has_item", hasItem(AllBlocks.CARDBOARD_BLOCK))
                .save(this::saveFinishedRecipe, Repair.asResource("crafting/materials/bound_cardboard_block"));

        new Builder<>("clay", MillingRecipe::new)
                .require(Items.CLAY)
                .output(Items.CLAY_BALL, 4)
                .duration(50)
                .build();
    }

    private InventoryChangeTrigger.TriggerInstance hasItem(ItemLike itemLike) {
        return new InventoryChangeTrigger.TriggerInstance(
                ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                new ItemPredicate[]{ItemPredicate.Builder.item().of(itemLike).build()}
        );
    }

    private void saveFinishedRecipe(FinishedRecipe recipe) {
        LogUtils.getLogger().info("awesome recipe: {}", recipe.serializeRecipe());
        this.put(recipe.getId(), recipe.serializeRecipe());
        this.put(recipe.getAdvancementId(), recipe.serializeAdvancement());
    }

//    @Override
//    @Nullable
//    @SuppressWarnings("unchecked")
//    public <T> T getMetadataSection(@NotNull MetadataSectionSerializer<T> deserializer) throws IOException {
//        if(deserializer == PackMetadataSection.TYPE) return super.getMetadataSection(deserializer);
//        if(deserializer == ResourceFilterSection.TYPE) return (T)this.filter;
//        return null;
//    }

//    private static class RepairFilter extends ResourceFilterSection {
//        private final Map<String, List<String>> filteredFiles;
//
//        public RepairFilter() {
//            super(List.of());
//            filteredFiles = new HashMap<>();
//        }
//
//        public void add(String namespace, String path) {
//            this.filteredFiles.computeIfAbsent(namespace, k -> new ArrayList<>())
//                    .add(path);
//        }
//
//        @Override
//        public boolean isNamespaceFiltered(@NotNull String namespace) {
//            return filteredFiles.containsKey(namespace);
//        }
//
//        @Override
//        public boolean isPathFiltered(@NotNull String path) {
//            return filteredFiles.values().stream().anyMatch(
//                    l -> l.stream().anyMatch(path::equals)
//                    );
//        }
//    }

    public DynamicPackSource getDynamicSource() {
        return new DynamicPackSource(PACK_ID, PackType.SERVER_DATA, Pack.Position.TOP, this);
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
