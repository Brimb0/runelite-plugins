package eu.jodelahithit;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import static net.runelite.api.AnimationID.*;

public class Constants {
    static final Set<Integer> COOKING_ANIMATIONS = ImmutableSet.of(COOKING_FIRE, COOKING_RANGE, COOKING_WINE);
    static final Set<Integer> FIREMAKING_ANIMATIONS = ImmutableSet.of(FIREMAKING); //Todo: barbarian fishing?
    static final Set<Integer> FISHING_ANIMATIONS = ImmutableSet.of(
            FISHING_BARBTAIL_HARPOON,
            FISHING_BAREHAND,
            FISHING_BAREHAND_CAUGHT_SHARK_1,
            FISHING_BAREHAND_CAUGHT_SHARK_2,
            FISHING_BAREHAND_CAUGHT_SWORDFISH_1,
            FISHING_BAREHAND_CAUGHT_SWORDFISH_2,
            FISHING_BAREHAND_CAUGHT_TUNA_1,
            FISHING_BAREHAND_CAUGHT_TUNA_2,
            FISHING_BAREHAND_WINDUP_1,
            FISHING_BAREHAND_WINDUP_2,
            FISHING_BIG_NET,
            FISHING_CAGE,
            FISHING_CRYSTAL_HARPOON,
            FISHING_DRAGON_HARPOON,
            FISHING_HARPOON,
            FISHING_INFERNAL_HARPOON,
            FISHING_KARAMBWAN,
            FISHING_NET,
            FISHING_DRAGON_HARPOON_OR,
            FISHING_TRAILBLAZER_HARPOON,
            FISHING_CRUSHING_INFERNAL_EELS,
            FISHING_CUTTING_SACRED_EELS,
            FISHING_OILY_ROD,
            FISHING_POLE_CAST,
            FISHING_PEARL_ROD,
            FISHING_PEARL_FLY_ROD,
            FISHING_PEARL_BARBARIAN_ROD,
            FISHING_PEARL_ROD_2,
            FISHING_PEARL_FLY_ROD_2,
            FISHING_PEARL_BARBARIAN_ROD_2,
            FISHING_PEARL_OILY_ROD);

    static final Set<Integer> MINING_ANIMATIONS = ImmutableSet.of(
            MINING_BRONZE_PICKAXE,
            MINING_IRON_PICKAXE,
            MINING_STEEL_PICKAXE,
            MINING_BLACK_PICKAXE,
            MINING_MITHRIL_PICKAXE,
            MINING_ADAMANT_PICKAXE,
            MINING_RUNE_PICKAXE,
            MINING_GILDED_PICKAXE,
            MINING_DRAGON_PICKAXE,
            MINING_DRAGON_PICKAXE_OR,
            MINING_DRAGON_PICKAXE_UPGRADED,
            MINING_INFERNAL_PICKAXE,
            MINING_3A_PICKAXE,
            MINING_CRYSTAL_PICKAXE,
            MINING_MOTHERLODE_BRONZE,
            MINING_MOTHERLODE_IRON,
            MINING_MOTHERLODE_STEEL,
            MINING_MOTHERLODE_BLACK,
            MINING_MOTHERLODE_MITHRIL,
            MINING_MOTHERLODE_ADAMANT,
            MINING_MOTHERLODE_RUNE,
            MINING_MOTHERLODE_GILDED,
            MINING_MOTHERLODE_DRAGON,
            MINING_MOTHERLODE_DRAGON_OR,
            MINING_MOTHERLODE_DRAGON_UPGRADED,
            MINING_MOTHERLODE_INFERNAL,
            MINING_MOTHERLODE_3A,
            MINING_DRAGON_PICKAXE_OR_TRAILBLAZER,
            MINING_TRAILBLAZER_PICKAXE,
            MINING_TRAILBLAZER_PICKAXE_2,
            MINING_TRAILBLAZER_PICKAXE_3,
            MINING_MOTHERLODE_DRAGON_OR_TRAILBLAZER,
            MINING_MOTHERLODE_TRAILBLAZER,
            MINING_MOTHERLODE_CRYSTAL,
            DENSE_ESSENCE_CHIPPING
    );

    static final Set<Integer> WOODCUTTING_ANIMATIONS = ImmutableSet.of(
            WOODCUTTING_BRONZE,
            WOODCUTTING_IRON,
            WOODCUTTING_STEEL,
            WOODCUTTING_BLACK,
            WOODCUTTING_MITHRIL,
            WOODCUTTING_ADAMANT,
            WOODCUTTING_RUNE,
            WOODCUTTING_GILDED,
            WOODCUTTING_DRAGON,
            WOODCUTTING_DRAGON_OR,
            WOODCUTTING_INFERNAL,
            WOODCUTTING_3A_AXE,
            WOODCUTTING_CRYSTAL,
            WOODCUTTING_TRAILBLAZER);

    static final Set<Integer> FLETCHING_ANIMATIONS = ImmutableSet.of(
            FLETCHING_BOW_CUTTING,
            FLETCHING_STRING_NORMAL_SHORTBOW,
            FLETCHING_STRING_NORMAL_LONGBOW,
            FLETCHING_STRING_OAK_SHORTBOW,
            FLETCHING_STRING_OAK_LONGBOW,
            FLETCHING_STRING_WILLOW_SHORTBOW,
            FLETCHING_STRING_WILLOW_LONGBOW,
            FLETCHING_STRING_MAPLE_SHORTBOW,
            FLETCHING_STRING_MAPLE_LONGBOW,
            FLETCHING_STRING_YEW_SHORTBOW,
            FLETCHING_STRING_YEW_LONGBOW,
            FLETCHING_STRING_MAGIC_SHORTBOW,
            FLETCHING_STRING_MAGIC_LONGBOW,
            FLETCHING_ATTACH_BOLT_TIPS_TO_BRONZE_BOLT,
            FLETCHING_ATTACH_BOLT_TIPS_TO_IRON_BROAD_BOLT,
            FLETCHING_ATTACH_BOLT_TIPS_TO_BLURITE_BOLT,
            FLETCHING_ATTACH_BOLT_TIPS_TO_STEEL_BOLT,
            FLETCHING_ATTACH_BOLT_TIPS_TO_MITHRIL_BOLT,
            FLETCHING_ATTACH_BOLT_TIPS_TO_ADAMANT_BOLT,
            FLETCHING_ATTACH_BOLT_TIPS_TO_RUNE_BOLT,
            FLETCHING_ATTACH_BOLT_TIPS_TO_DRAGON_BOLT,
            FLETCHING_ATTACH_HEADS,
            FLETCHING_ATTACH_FEATHERS_TO_ARROWSHAFT);

    static final Set<Integer> HERBLORE_ANIMATIONS = ImmutableSet.of(
            HERBLORE_MAKE_TAR,
            HERBLORE_POTIONMAKING,
            HERBLORE_PESTLE_AND_MORTAR
    );

    static final Set<Integer> CRAFTING_ANIMATIONS = ImmutableSet.of(
            GEM_CUTTING_OPAL,
            GEM_CUTTING_JADE,
            GEM_CUTTING_REDTOPAZ,
            GEM_CUTTING_SAPPHIRE,
            GEM_CUTTING_EMERALD,
            GEM_CUTTING_RUBY,
            GEM_CUTTING_DIAMOND,
            GEM_CUTTING_AMETHYST,
            CRAFTING_GLASSBLOWING,
            CRAFTING_SPINNING,
            CRAFTING_LOOM,
            CRAFTING_BATTLESTAVES,
            CRAFTING_LEATHER,
            CRAFTING_POTTERS_WHEEL,
            CRAFTING_POTTERY_OVEN,
            DENSE_ESSENCE_CHISELING,
            SMITHING_SMELTING
    );

    static final Set<Integer> SMITHING_ANIMATIONS = ImmutableSet.of(
            SMITHING_ANVIL,
            SMITHING_SMELTING,
            SMITHING_CANNONBALL,
            SMITHING_IMCANDO_HAMMER
    );
}