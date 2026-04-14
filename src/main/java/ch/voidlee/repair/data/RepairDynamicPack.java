package ch.voidlee.repair.data;

import ch.voidlee.repair.Repair;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.block.CopperBlockSet;
import com.simibubi.create.foundation.data.recipe.Mods;
import com.simibubi.create.foundation.pack.DynamicPack;
import com.simibubi.create.foundation.pack.DynamicPackSource;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import io.github.fabricators_of_create.porting_lib.util.TrueCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.impl.util.version.SemanticVersionImpl;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagFile;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.WeatheringCopper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.*;

import static com.simibubi.create.foundation.block.CopperBlockSet.DEFAULT_VARIANTS;
import static net.minecraft.world.level.block.WeatheringCopper.WeatherState.*;

public class RepairDynamicPack extends DynamicPack {
    private static final String PACK_ID = "create_repair:dynamic_data";

    private final Multimap<ResourceLocation, TagEntry> ITEM_TAGS = HashMultimap.create();

    private static final JsonObject DISABLED_RECIPE;

    public RepairDynamicPack(PackType packType) {
        super(PACK_ID, packType);
    }

    @Override
    @Nullable
    public IoSupplier<InputStream> getRootResource(String @NotNull ... elements) {
        if (elements[0].equals("pack.png")) {
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

        updatedBwgCompat();
        updatedSGCompat();
        updatedEnvironmentalCompat();
        updatedAutumnityCompat();
        fixGalosphereCompat();

        for (Map.Entry<ResourceLocation, Collection<TagEntry>> tags : ITEM_TAGS.asMap().entrySet()) {
            TagFile tagFile = new TagFile(new ArrayList<>(tags.getValue()), false);
            this.put(tags.getKey().withPrefix("tags/items/"), TagFile.CODEC.encodeStart(JsonOps.INSTANCE, tagFile).result().orElseThrow());
        }
        ITEM_TAGS.clear();
    }

    private InventoryChangeTrigger.TriggerInstance hasItem(ItemLike itemLike) {
        return new InventoryChangeTrigger.TriggerInstance(
                ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                new ItemPredicate[]{ItemPredicate.Builder.item().of(itemLike).build()}
        );
    }

    private void saveFinishedRecipe(FinishedRecipe recipe) {
        this.put(recipe.getId().withPrefix("recipes/"), recipe.serializeRecipe());

        if (recipe.getAdvancementId() == null) {
            return;
        }
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

    // https://github.com/Creators-of-Create/Create/pull/9537
    private void updatedBwgCompat() {
        // Removed
        disableRecipe("compat/biomeswevegone/ametrine_ore", CRUSHING);
        disableRecipe("compat/biomeswevegone/anthracite_ore", CRUSHING);
        disableRecipe("compat/biomeswevegone/blue_nether_gold_ore", CRUSHING);
        disableRecipe("compat/biomeswevegone/blue_nether_quartz_ore", CRUSHING);
        disableRecipe("compat/biomeswevegone/brimstone_nether_gold_ore", CRUSHING);
        disableRecipe("compat/biomeswevegone/brimstone_nether_quartz_ore", CRUSHING);
        disableRecipe("compat/biomeswevegone/cryptic_redstone_ore", CRUSHING);
        disableRecipe("compat/biomeswevegone/emeraldite_ore", CRUSHING);
        disableRecipe("compat/biomeswevegone/lignite_ore", CRUSHING);
        disableRecipe("compat/biomeswevegone/pervaded_netherrack", CRUSHING);
        disableRecipe("compat/biomeswevegone/torch_ginger", MILLING);
        // Typo'd or renamed
        disableRecipe("compat/biomeswevegone/orchid", MILLING);
        disableRecipe("compat/biomeswevegone/lolipop_flower", MILLING);
        disableRecipe("compat/biomeswevegone/purple_rose", MILLING);
        disableRecipe("compat/biomeswevegone/compat/biomeswevegone/winter_cyclamen", MILLING);
        disableRecipe("compat/biomeswevegone/compat/biomeswevegone/white_sage", MILLING);
        // Updated typo'd
        new Builder<>("compat/biomeswevegone/japanese_orchid", MillingRecipe::new)
                .require(Mods.BWG, "japanese_orchid")
                .output(Items.PINK_DYE, 2)
                .output(0.05f, Items.WHITE_DYE)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/lollipop_flower", MillingRecipe::new)
                .require(Mods.BWG, "lollipop_flower")
                .output(Items.YELLOW_DYE, 2)
                .output(0.25f, Items.YELLOW_DYE)
                .output(0.05f, Items.GREEN_DYE)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/purple_sage", MillingRecipe::new)
                .require(Mods.BWG, "purple_sage")
                .output(Items.PURPLE_DYE, 2)
                .output(0.1f, Items.MAGENTA_DYE)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/winter_cyclamen", MillingRecipe::new)
                .require(Mods.BWG, "winter_cyclamen")
                .output(Items.CYAN_DYE, 2)
                .output(0.1f, Items.GREEN_DYE)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/white_sage", MillingRecipe::new)
                .require(Mods.BWG, "white_sage")
                .output(Items.WHITE_DYE, 2)
                .output(0.1f, Items.GRAY_DYE)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        // Wrong output
        new Builder<>("compat/biomeswevegone/lush_grass_path", PressingRecipe::new)
                .require(Mods.BWG, "lush_grass_block")
                .output(Mods.BWG, "lush_dirt_path")
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/blue_sage", MillingRecipe::new)
                .require(Mods.BWG, "blue_sage")
                .output(Items.BLUE_DYE, 2)
                .output(0.1f, Items.CYAN_DYE)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/osiria_rose", MillingRecipe::new)
                .require(Mods.BWG, "osiria_rose")
                .output(Items.PINK_DYE, 2)
                .output(0.1f, Items.GREEN_DYE)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/cyan_amaranth", MillingRecipe::new)
                .require(Mods.BWG, "cyan_amaranth")
                .output(Items.CYAN_DYE, 3)
                .output(0.05f, Items.GREEN_DYE, 2)
                .output(0.25f, Items.CYAN_DYE, 2)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/orange_amaranth", MillingRecipe::new)
                .require(Mods.BWG, "orange_amaranth")
                .output(Items.ORANGE_DYE, 3)
                .output(0.05f, Items.GREEN_DYE, 2)
                .output(0.25f, Items.ORANGE_DYE, 2)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        // New
        new Builder<>("compat/biomeswevegone/lush_dirt_path", PressingRecipe::new)
                .require(Mods.BWG, "lush_dirt")
                .output(Mods.BWG, "lush_dirt_path")
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/sandy_dirt_path", PressingRecipe::new)
                .require(Mods.BWG, "sandy_dirt")
                .output(Mods.BWG, "sandy_dirt_path")
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/black_sandstone", MillingRecipe::new)
                .require(Mods.BWG, "black_sandstone")
                .output(Mods.BWG, "black_sand")
                .duration(150)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/blue_sandstone", MillingRecipe::new)
                .require(Mods.BWG, "blue_sandstone")
                .output(Mods.BWG, "blue_sand")
                .duration(150)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/pink_sandstone", MillingRecipe::new)
                .require(Mods.BWG, "pink_sandstone")
                .output(Mods.BWG, "pink_sand")
                .duration(150)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/purple_sandstone", MillingRecipe::new)
                .require(Mods.BWG, "purple_sandstone")
                .output(Mods.BWG, "purple_sand")
                .duration(150)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/white_sandstone", MillingRecipe::new)
                .require(Mods.BWG, "white_sandstone")
                .output(Mods.BWG, "white_sand")
                .duration(150)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/windswept_sandstone", MillingRecipe::new)
                .require(Mods.BWG, "windswept_sandstone")
                .output(Mods.BWG, "windswept_sand")
                .duration(150)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/stripped_palo_verde_log", CuttingRecipe::new)
                .require(Mods.BWG, "stripped_palo_verde_log")
                .output(Items.BIRCH_PLANKS, 6)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/stripped_palo_verde_wood", CuttingRecipe::new)
                .require(Mods.BWG, "stripped_palo_verde_wood")
                .output(Items.BIRCH_PLANKS, 6)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/blue_rose_bush", MillingRecipe::new)
                .require(Mods.BWG, "blue_rose_bush")
                .output(Items.BLUE_DYE, 3)
                .output(0.05f, Items.GREEN_DYE, 2)
                .output(0.25f, Items.BLUE_DYE, 2)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/horseweed", MillingRecipe::new)
                .require(Mods.BWG, "horseweed")
                .output(Items.GREEN_DYE, 2)
                .output(0.25f, Items.BROWN_DYE)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/winter_succulent", MillingRecipe::new)
                .require(Mods.BWG, "winter_succulent")
                .output(Items.GREEN_DYE, 2)
                .output(0.25f, Items.GREEN_DYE)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/mini_cactus", MillingRecipe::new)
                .require(Mods.BWG, "mini_cactus")
                .output(Items.GREEN_DYE, 2)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/prickly_pear_cactus", MillingRecipe::new)
                .require(Mods.BWG, "prickly_pear_cactus")
                .output(Items.GREEN_DYE, 2)
                .output(0.25f, Items.GREEN_DYE)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/golden_spined_cactus", MillingRecipe::new)
                .require(Mods.BWG, "golden_spined_cactus")
                .output(Items.GREEN_DYE, 2)
                .output(0.25f, Items.YELLOW_DYE)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/white_allium", MillingRecipe::new)
                .require(Mods.BWG, "white_allium")
                .output(Items.WHITE_DYE, 2)
                .output(0.1f, Items.LIGHT_GRAY_DYE, 2)
                .output(0.1f, Items.GRAY_DYE)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/white_allium_flower_bush", MillingRecipe::new)
                .require(Mods.BWG, "white_allium_flower_bush")
                .output(Items.WHITE_DYE, 3)
                .output(0.05f, Items.GREEN_DYE, 2)
                .output(0.25f, Items.LIGHT_GRAY_DYE, 2)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        new Builder<>("compat/biomeswevegone/tall_white_allium", MillingRecipe::new)
                .require(Mods.BWG, "tall_white_allium")
                .output(Items.WHITE_DYE, 3)
                .output(0.05f, Items.WHITE_DYE, 2)
                .output(0.25f, Items.LIGHT_GRAY_DYE, 2)
                .duration(50)
                .whenModLoaded(Mods.BWG.getId())
                .build();
        // Tags
        insertIntoTag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag.location(), Mods.BWG.asResource("allium_oddion_soup"), ITEM_TAGS);
        insertIntoTag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag.location(), Mods.BWG.asResource("white_puffball_stew"), ITEM_TAGS);
        insertIntoTag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag.location(), Mods.BWG.asResource("aloe_vera_juice"), ITEM_TAGS);
    }

    // https://github.com/Creators-of-Create/Create/pull/9537
    private void updatedSGCompat() {
        insertIntoTag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag.location(), Mods.SILENT_GEMS.asResource("flower_basket"), ITEM_TAGS);
        insertIntoTag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag.location(), Mods.SILENT_GEMS.asResource("uncooked_meaty_stew"), ITEM_TAGS);
        insertIntoTag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag.location(), Mods.SILENT_GEMS.asResource("meaty_stew"), ITEM_TAGS);
        insertIntoTag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag.location(), Mods.SILENT_GEMS.asResource("uncooked_fishy_stew"), ITEM_TAGS);
        insertIntoTag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag.location(), Mods.SILENT_GEMS.asResource("fishy_stew"), ITEM_TAGS);
    }

    // https://github.com/Creators-of-Create/Create/pull/8471
    private void updatedEnvironmentalCompat() {
        // Wrong/adjusted output
        new Builder<>("path", PressingRecipe::new)
                .require(Ingredient.of(Items.DIRT, Items.COARSE_DIRT, Items.ROOTED_DIRT, Items.MYCELIUM, Items.PODZOL))
                .output(Items.DIRT_PATH)
                .whenModMissing(Mods.ENV.getId())
                .build();
        new Builder<>("compat/environmental/blue_delphinium", MillingRecipe::new)
                .require(Mods.ENV, "blue_delphinium")
                .output(Items.LIGHT_BLUE_DYE, 3)
                .output(0.1f, Items.LIGHT_BLUE_DYE)
                .duration(50)
                .whenModLoaded(Mods.ENV.getId())
                .build();
        new Builder<>("compat/environmental/dianthus", MillingRecipe::new)
                .require(Mods.ENV, "dianthus")
                .output(Items.LIME_DYE, 2)
                .output(0.1f, Items.LIME_DYE)
                .duration(50)
                .whenModLoaded(Mods.ENV.getId())
                .build();
        new Builder<>("compat/environmental/white_lotus_flower", MillingRecipe::new)
                .require(Mods.ENV, "white_lotus_flower")
                .output(Items.WHITE_DYE, 2)
                .output(0.1f, Items.WHITE_DYE)
                .duration(50)
                .whenModLoaded(Mods.ENV.getId())
                .build();
        // New
        new Builder<>("compat/environmental/dirt_path", PressingRecipe::new)
                .require(Ingredient.of(Items.DIRT, Items.COARSE_DIRT, Items.ROOTED_DIRT))
                .output(Mods.ENV, "dirt_path")
                .whenModLoaded(Mods.ENV.getId())
                .build();
        new Builder<>("path_from_grass", PressingRecipe::new)
                .require(Items.GRASS_BLOCK)
                .output(Items.DIRT_PATH)
                .build();
        new Builder<>("compat/environmental/tasselflower", MillingRecipe::new)
                .require(Mods.ENV, "tasselflower")
                .output(Items.ORANGE_DYE, 2)
                .output(0.1f, Items.GREEN_DYE)
                .duration(50)
                .whenModLoaded(Mods.ENV.getId())
                .build();
        // Tags
        insertIntoTag(AllTags.AllItemTags.MODDED_STRIPPED_LOGS.tag.location(), Mods.ENV.asResource("stripped_pine_log"), ITEM_TAGS);
        insertIntoTag(AllTags.AllItemTags.MODDED_STRIPPED_WOOD.tag.location(), Mods.ENV.asResource("stripped_pine_wood"), ITEM_TAGS);
        insertIntoTag(AllTags.AllItemTags.MODDED_STRIPPED_LOGS.tag.location(), Mods.ENV.asResource("stripped_plum_log"), ITEM_TAGS);
        insertIntoTag(AllTags.AllItemTags.MODDED_STRIPPED_WOOD.tag.location(), Mods.ENV.asResource("stripped_plum_wood"), ITEM_TAGS);
    }

    // https://github.com/Creators-of-Create/Create/pull/8471
    private void updatedAutumnityCompat() {
        insertIntoTag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag.location(), Mods.AUTUM.asResource("foul_soup"), ITEM_TAGS);
        insertIntoTag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag.location(), Mods.AUTUM.asResource("syrup_bottle"), ITEM_TAGS);
        insertIntoTag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag.location(), Mods.AUTUM.asResource("sap_bottle"), ITEM_TAGS);
        insertIntoTag(AllTags.AllItemTags.MODDED_STRIPPED_LOGS.tag.location(), Mods.AUTUM.asResource("stripped_maple_log"), ITEM_TAGS);
        insertIntoTag(AllTags.AllItemTags.MODDED_STRIPPED_WOOD.tag.location(), Mods.AUTUM.asResource("stripped_maple_wood"), ITEM_TAGS);
    }

    // Not as trivial as just fixing the recipes, don't want to require a specific version of Galosphere
    private void fixGalosphereCompat() {
        if (!FabricLoader.getInstance().isModLoaded(Mods.GS.getId())) {
            return;
        }
        SemanticVersion hasPalladiumStartingFrom = null;
        try {
            hasPalladiumStartingFrom = new SemanticVersionImpl("1.20.1-1.5.0", false);
        } catch (VersionParsingException e) {
            throw new RuntimeException(e);
        }
        Version galosphereVersion = FabricLoader.getInstance().getModContainer(Mods.GS.getId()).get().getMetadata().getVersion();
        // < 1.5.0 versions for Forge 1.20.1 had 1.20.1- leading the version; but later Neoforge (and the 1.20.1 backport) no longer have it
        // Fabric doesn't have a 1.20.1 1.5.x release (yet?) but the 1.21.1 version still has it; just use Fabric's system until proven we need something else
        if (galosphereVersion.compareTo(hasPalladiumStartingFrom) < 0) {
            // Keep old recipes
            return;
        }
        disableRecipe("galosphere/crushed_raw_silver", SPLASHING);
        disableRecipe("silver_ingot_compat_galosphere", SMELTING);
        disableRecipe("silver_ingot_compat_galosphere", BLASTING);
    }

    private void insertIntoTag(ResourceLocation tag, ResourceLocation itemId, Multimap<ResourceLocation, TagEntry> tagMap) {
        tagMap.put(tag, TagEntry.optionalElement(itemId));
    }

    private String getPath(ItemLike itemLike) {
        ResourceLocation inputLoc = BuiltInRegistries.ITEM.getKey(itemLike.asItem());
        assert inputLoc != null;
        return inputLoc.getPath();
    }

    private static final String SPLASHING = "splashing";
    private static final String SMELTING = "smelting";
    private static final String BLASTING = "blasting";
    private static final String MILLING = "milling";
    private static final String CRUSHING = "crushing";

    private void disableRecipe(String recipeId, String recipeType) {
        RepairDynamicPack.this.put(Create.asResource(recipeId).withPrefix(recipeType + "/").withPrefix("recipes/"), DISABLED_RECIPE);
    }

    private class Builder<T extends ProcessingRecipe<?>> extends ProcessingRecipeBuilder<T> {
        public Builder(String path, ProcessingRecipeBuilder.ProcessingRecipeFactory<T> factory) {
            super(factory, Create.asResource(path));
        }

        public T build() {
            T t = super.build();
            DataGenResult<T> result = new DataGenResult<>(t, recipeConditions);
            RepairDynamicPack.this.put(result.getId().withPrefix("recipes/"), result.serializeRecipe());
            return t;
        }
    }

    static {
        JsonObject jsonObject = new JsonObject();
        JsonArray conditions = new JsonArray(1);
        conditions.add(DefaultResourceConditions.not(TrueCondition.INSTANCE).toJson());
        jsonObject.add(ResourceConditions.CONDITIONS_KEY, conditions);
        DISABLED_RECIPE = jsonObject;
    }
}
