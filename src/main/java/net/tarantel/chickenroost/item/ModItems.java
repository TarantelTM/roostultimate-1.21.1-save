package net.tarantel.chickenroost.item;


import com.google.gson.Gson;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.*;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.entity.ModEntities;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.util.ChickenData;
import net.tarantel.chickenroost.util.GsonChickenReader;
import net.tarantel.chickenroost.util.ModDataComponents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.text.DecimalFormat;

public class ModItems {



	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems("chicken_roost");
	public static final DeferredRegister.Items ITEMSS = DeferredRegister.createItems("chicken_roost");
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChickenRoostMod.MODID);

	public static final DeferredItem<Item> INGOT_ELECTRUM = ITEMS.register("ingot_electrum", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_electrum"));
	public static final DeferredItem<Item> INGOT_SILVER = ITEMS.register("ingot_silver", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_silver"));
	public static final DeferredItem<Item> INGOT_ZINC = ITEMS.register("ingot_zinc", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_zinc"));

	public static final DeferredItem<Item> INGOT_BRONZE = ITEMS.register("ingot_bronze", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_bronze"));
	public static final DeferredItem<Item> INGOT_LEAD = ITEMS.register("ingot_lead", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_lead"));
	public static final DeferredItem<Item> INGOT_STEEL = ITEMS.register("ingot_steel", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_steel"));
	public static final DeferredItem<Item> INGOT_TIN = ITEMS.register("ingot_tin", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_tin"));
	public static final DeferredItem<Item> INGOT_URANIUM = ITEMS.register("ingot_uranium", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_uranium"));


	public static final DeferredItem<Item> INGOT_ALUMINUM = ITEMS.register("ingot_aluminum", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_aluminum"));
	public static final DeferredItem<Item> INGOT_CHROME = ITEMS.register("ingot_chrome", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_chrome"));

	public static final DeferredItem<Item> INGOT_INVAR = ITEMS.register("ingot_invar", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_invar"));
	public static final DeferredItem<Item> INGOT_IRIDIUM = ITEMS.register("ingot_iridium", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_iridium"));

	public static final DeferredItem<Item> INGOT_NICKEL = ITEMS.register("ingot_nickel", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_nickel"));
	public static final DeferredItem<Item> INGOT_PLATINUM = ITEMS.register("ingot_platinum", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_platinum"));

	public static final DeferredItem<Item> INGOT_TITANUM = ITEMS.register("ingot_titanum", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_titanum"));
	public static final DeferredItem<Item> INGOT_TUNGSTEN = ITEMS.register("ingot_tungsten", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_tungsten"));
	public static final DeferredItem<Item> INGOT_TUNGSTENSTEEL = ITEMS.register("ingot_tungstensteel", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_tungstensteel"));


	public static final DeferredItem<Item> INGOT_ENDERIUM = ITEMS.register("ingot_enderium", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_enderium"));
	public static final DeferredItem<Item> INGOT_ADAMANTIUM = ITEMS.register("ingot_adamantium", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_adamantium"));
	public static final DeferredItem<Item> INGOT_LUMIUM = ITEMS.register("ingot_lumium", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_lumium"));
	public static final DeferredItem<Item> INGOT_SIGNALUM = ITEMS.register("ingot_signalum", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_signalum"));

	public static final DeferredItem<Item> LAVA_EGG = ITEMS.register("lava_egg", () -> new myownbucket( new myownbucket.Properties()
			.durability(0)
			.upperCrackingTemperature(0)
			.burningTemperature(99999)
			.burningFluids(Tags.Fluids.LAVA)
			.freezingTemperature(0)
			.stacksTo(64)
			.disableBlockObtaining()
			.disableMilking()
			.disableEntityObtaining()
			.lowerCrackingTemperature(0)
	));
	public static final DeferredItem<Item> WATER_EGG = ITEMS.register("water_egg", () -> new myownbucket( new myownbucket.Properties()
			.durability(0)
			.upperCrackingTemperature(0)
			.burningTemperature(99999)
			.burningFluids(Tags.Fluids.LAVA)
			.freezingTemperature(0)
			.stacksTo(64)
			.disableBlockObtaining()
			.disableMilking()
			.disableEntityObtaining()
			.lowerCrackingTemperature(0)
	));
	public static final DeferredItem<Item> STONE_ESSENCE = ITEMS.register("stone_essence", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> WOOD_ESSENCE = ITEMS.register("wood_essence", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));

	//region Color Eggs
//public static final DeferredItem<Item> RED_EGG = ITEMS.register("red_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_RED.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_RED = ITEMS.register("c_red", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "redchicken", 0));
//public static final DeferredItem<Item> BLUE_EGG = ITEMS.register("blue_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_LAPIS.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
////public static final DeferredItem<Item> E_CHICKEN_BLUE = ITEMS.register("c_cobble", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "graychicken", 0));
//public static final DeferredItem<Item> YELLOW_EGG = ITEMS.register("yellow_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_YELLOW.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_YELLOW = ITEMS.register("c_yellow", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "yellowchicken", 0));
//public static final DeferredItem<Item> WHITE_EGG = ITEMS.register("white_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_BONE.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_WHITE = ITEMS.register("c_white", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "whitechicken", 0));
//public static final DeferredItem<Item> ORANGE_EGG = ITEMS.register("orange_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_ORANGE.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_ORANGE = ITEMS.register("c_orange", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "orangechicken", 0));
//public static final DeferredItem<Item> MAGENTA_EGG = ITEMS.register("magenta_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_MAGENTA.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_MAGENTA = ITEMS.register("c_magenta", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "magentachicken", 0));
//public static final DeferredItem<Item> LIGHT_BLUE_EGG = ITEMS.register("light_blue_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_LIGHT_BLUE.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_LIGHT_BLUE = ITEMS.register("c_light_blue", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "lightbluechicken", 0));
//public static final DeferredItem<Item> LIME_EGG = ITEMS.register("lime_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_LIME.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_LIME = ITEMS.register("c_lime", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "limechicken", 0));
//public static final DeferredItem<Item> PINK_EGG = ITEMS.register("pink_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_PINK.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_PINK = ITEMS.register("c_pink", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "pinkchicken", 0));
//public static final DeferredItem<Item> GRAY_EGG = ITEMS.register("gray_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_GRAY.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_GRAY = ITEMS.register("c_gray", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "graychicken", 0));
//public static final DeferredItem<Item> LIGHT_GRAY_EGG = ITEMS.register("light_gray_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_LIGHT_GRAY.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_LIGHT_GRAY = ITEMS.register("c_light_gray", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "graychicken", 0));
//public static final DeferredItem<Item> CYAN_EGG = ITEMS.register("cyan_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_CYAN.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_CYAN = ITEMS.register("c_cyan", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "cyanchicken", 0));
//public static final DeferredItem<Item> PURPLE_EGG = ITEMS.register("purple_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_PURPLE.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_PURPLE = ITEMS.register("c_purple", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "purplechicken", 0));
//public static final DeferredItem<Item> BROWN_EGG = ITEMS.register("brown_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_BROWN.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_BROWN = ITEMS.register("c_brown", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "brownchicken", 0));
//public static final DeferredItem<Item> GREEN_EGG = ITEMS.register("green_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_GREEN.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_GREEN = ITEMS.register("c_green", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "greenchicken", 0));
//public static final DeferredItem<Item> BLACK_EGG = ITEMS.register("black_egg", () -> new RoostEgg(ModEntities.A_CHICKEN_INK.get(), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
//public static final DeferredItem<Item> E_CHICKEN_BLACK = ITEMS.register("c_black", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "blackchicken", 0));


	public static final DeferredItem<Item> RED_EGG = ITEMS.register("red_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_red"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> BLUE_EGG = ITEMS.register("blue_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_lapis"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> YELLOW_EGG = ITEMS.register("yellow_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_yellow"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> WHITE_EGG = ITEMS.register("white_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_white"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> ORANGE_EGG = ITEMS.register("orange_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_orange"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> MAGENTA_EGG = ITEMS.register("magenta_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_magenta"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> LIGHT_BLUE_EGG = ITEMS.register("light_blue_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_light_blue"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> LIME_EGG = ITEMS.register("lime_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_lime"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> PINK_EGG = ITEMS.register("pink_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_pink"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> GRAY_EGG = ITEMS.register("gray_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_gray"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> LIGHT_GRAY_EGG = ITEMS.register("light_gray_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_light_gray"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> CYAN_EGG = ITEMS.register("cyan_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_cyan"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> PURPLE_EGG = ITEMS.register("purple_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_purple"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> BROWN_EGG = ITEMS.register("brown_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_brown"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> GREEN_EGG = ITEMS.register("green_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_green"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> BLACK_EGG = ITEMS.register("black_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_black"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	//endregion


	//region Chicken - Tier 1
//	public static final DeferredItem<Item> E_CHICKEN_COBBLE = ITEMS.register("c_cobble", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "graychicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_SAND = ITEMS.register("c_sand", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "sandchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_GRAVEL = ITEMS.register("c_gravel", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "graychicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_GRANIT = ITEMS.register("c_granit", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "garnetchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_ANDESITE = ITEMS.register("c_andesite", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "blackchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_BIRCHWOOD = ITEMS.register("c_birchwood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "whitechicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_OAKWOOD = ITEMS.register("c_oakwood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "brownchicken", 0));
//	public static final DeferredItem<Item> ECHICKENQUEENSLIME = ITEMS.register("c_queenslime", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "yellowgarnetchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_TINTEDGLASS = ITEMS.register("c_tintedglass", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "glasschicken", 0));
//	public static final DeferredItem<Item> ECHICKENFEATHER = ITEMS.register("c_feather", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "ghastchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_PRISMARINE_SHARD = ITEMS.register("c_prismarineshard", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "saltchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_NETHER_BRICK = ITEMS.register("c_netherbrick", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "brownchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_DARK_OAK = ITEMS.register("c_darkoak", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "brownchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_ACACIAWOOD = ITEMS.register("c_acaciawood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "brownchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_JUNGLEWOOD = ITEMS.register("c_junglewood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "pulsatingironchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_NAUTILUS_SHELL = ITEMS.register("c_nautilusshell", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "conductiveironchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_HONEYCOMB = ITEMS.register("c_honeycomb", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "yellowchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_DIORITE = ITEMS.register("c_diorite", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "iridiumchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_STONE = ITEMS.register("c_stone", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "stoneburntchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_WOOL = ITEMS.register("c_wool", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "enoricrystalchicken", 0));
//	public static final DeferredItem<Item> E_CHICKEN_SPRUCEWOOD = ITEMS.register("c_sprucewood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "brownchicken", 0));
//	public static final DeferredItem<Item> CHICKENCHICKEN = ITEMS.register("c_vanilla", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "saltchicken", 0));
//	//endregion
//
//	//region Chicken - Tier 2
//	public static final DeferredItem<Item> E_CHICKEN_FLINT = ITEMS.register("c_flint", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "flintchicken", 1));
	public static final DeferredItem<Item> E_CHICKEN_LAVA = ITEMS.register("c_lava", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "lavachicken", 1));
	public static final DeferredItem<Item> E_CHICKEN_WATER = ITEMS.register("c_water", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "waterchicken", 1));
//	public static final DeferredItem<Item> E_CHICKEN_CRIMSTON_STEM = ITEMS.register("c_crimstonstem", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "brownchicken", 1));
//	public static final DeferredItem<Item> E_CHICKEN_WARPED_STEM = ITEMS.register("c_warpedstem", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "brownchicken", 1));
//	public static final DeferredItem<Item> E_CHICKEN_GLASS = ITEMS.register("c_glass", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "glasschicken", 1));
//	public static final DeferredItem<Item> E_CHICKEN_NETHERRACK = ITEMS.register("c_netherrack", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "netherwartchicken", 1));
//	public static final DeferredItem<Item> E_CHICKEN_INK = ITEMS.register("c_ink", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "blackchicken", 1));
//	public static final DeferredItem<Item> E_CHICKEN_PAPER = ITEMS.register("c_paper", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "platinumchicken", 1));
//	public static final DeferredItem<Item> E_CHICKEN_SUGAR = ITEMS.register("c_sugar", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "enoricrystalchicken", 1));
//	public static final DeferredItem<Item> E_CHICKEN_BONE_MEAL = ITEMS.register("c_bonemeal", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "whitechicken", 1));
//	public static final DeferredItem<Item> E_CHICKEN_BONE = ITEMS.register("c_bone", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "whitechicken", 1));
//	public static final DeferredItem<Item> E_CHICKEN_COAL = ITEMS.register("c_coal", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "coalchicken", 1));
//	public static final DeferredItem<Item> E_CHICKEN_CHAR_COAL = ITEMS.register("c_charcoal", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "coalchicken", 1));
//	public static final DeferredItem<Item> ECHICKENSNOW = ITEMS.register("c_snow", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "snowballchicken", 1));
//	public static final DeferredItem<Item> ECHICKENAPPLE = ITEMS.register("c_apple", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "restoniacrystalchicken", 1));
//	public static final DeferredItem<Item> E_CHICKEN_MELON = ITEMS.register("c_melon", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "greenchicken", 1));
//	public static final DeferredItem<Item> E_CHICKENGLOWBERRIES = ITEMS.register("c_glowberries", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "glowstonechicken", 1));
//	public static final DeferredItem<Item> E_CHICKEN_SWEETBERRIES = ITEMS.register("c_sweetberries", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "redstonecrystalchicken", 1));
//	public static final DeferredItem<Item> E_CHICKENBEETROOT = ITEMS.register("c_beetroot", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "redstonealloychicken", 1));
//	public static final DeferredItem<Item> E_CHICKENCARROT = ITEMS.register("c_carrot", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "energeticalloychicken", 1));
//	//endregion
//
//	//region Chicken - Tier 3
//
//	public static final DeferredItem<Item> E_CHICKEN_COPPER = ITEMS.register("c_copper", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "copperchicken", 2));
//	public static final DeferredItem<Item> E_CHICKEN_BLOOD = ITEMS.register("c_blood", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "bloodchicken", 2));
//	public static final DeferredItem<Item> E_CHICKEN_BOTANIA_LIVINGROCK = ITEMS.register("c_livingrock", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "moonstonechicken", 2));
//	public static final DeferredItem<Item> E_CHICKEN_BOTANIA_LIVINGWOOD = ITEMS.register("c_livingwood", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "magicalwoodchicken", 2));
//	public static final DeferredItem<Item> E_CHICKEN_SOUL_SAND = ITEMS.register("c_soulsand", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "soulsandchicken", 2));
//	public static final DeferredItem<Item> E_CHICKEN_SOUL_SOIL = ITEMS.register("c_soulsoil", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "soulariumchicken", 2));
//	public static final DeferredItem<Item> E_CHICKEN_BASALT = ITEMS.register("c_basalt", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "basalzrodchicken", 2));
//	public static final DeferredItem<Item> E_CHICKEN_CLAY = ITEMS.register("c_clay", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "claychicken", 2));
//	public static final DeferredItem<Item> E_CHICKEN_RABBIT_HIDE = ITEMS.register("c_rabbithide", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "bronzechicken", 2));
//	public static final DeferredItem<Item> E_CHICKEN_LEATHER = ITEMS.register("c_leather", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "bronzechicken", 2));
//	public static final DeferredItem<Item> E_CHICKEN_STRING = ITEMS.register("c_string", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "destructioncorechicken", 2));
//	public static final DeferredItem<Item> ECHICKENSPONGE = ITEMS.register("c_sponge", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "brasschicken", 2));
//	public static final DeferredItem<Item> E_CHICKEN_SPIDEREYE = ITEMS.register("c_spidereye", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "redstonealloychicken", 2));
//	//endregion
//
//	//region Chicken - Tier 4
//	public static final DeferredItem<Item> E_CHICKEN_IRON = ITEMS.register("c_iron", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "ironchicken", 3));
//	public static final DeferredItem<Item> E_CHICKEN_REDSTONE = ITEMS.register("c_redstone", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "redchicken", 3));
//	public static final DeferredItem<Item> E_CHICKEN_LAPIS = ITEMS.register("c_lapis", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "lightbluechicken", 3));
//	public static final DeferredItem<Item> E_CHICKEN_OBSIDIAN = ITEMS.register("c_obsidian", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "obsidianchicken", 3));
//	public static final DeferredItem<Item> E_CHICKEN_SLIME = ITEMS.register("c_slime", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "slimechicken", 3));
//	public static final DeferredItem<Item> E_CHICKEN_MEKANISM_TIN = ITEMS.register("c_tin", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "quartzenrichedironchicken", 3));
//	public static final DeferredItem<Item> E_CHICKEN_MEKANISM_LEAD = ITEMS.register("c_lead", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "leadchicken", 3));
//	public static final DeferredItem<Item> E_CHICKEN_NETHER_WART = ITEMS.register("c_netherwart", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "netherwartchicken", 3));
//	public static final DeferredItem<Item> E_CHICKEN_MAGMACREAM = ITEMS.register("c_magmacream", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "magmachicken", 3));
//	public static final DeferredItem<Item> E_CHICKEN_ROTTEN = ITEMS.register("c_rotten", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "richslagchicken", 3));
//	public static final DeferredItem<Item> E_CHICKEN_ZINC = ITEMS.register("c_zinc", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "zincchicken", 3));
//	public static final DeferredItem<Item> E_CHICKEN_ALUMINIUM = ITEMS.register("c_aluminium", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "aluminumchicken", 3));
//	public static final DeferredItem<Item> ECHICKENNITER = ITEMS.register("c_niter", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "vinteumchicken", 3));
//	public static final DeferredItem<Item> ECHICKENCINNABAR = ITEMS.register("c_cinnabar", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "cinnabarchicken", 3));
//	public static final DeferredItem<Item> ECHICKENCOKE = ITEMS.register("c_coke", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "coalchicken", 3));
//	public static final DeferredItem<Item> E_CHICKEN_TAR = ITEMS.register("c_tar", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "blackquartzchicken", 3));
//	public static final DeferredItem<Item> E_CHICKEN_SULFUR = ITEMS.register("c_sulfur", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "sulfurchicken", 3));
//	public static final DeferredItem<Item> ECHICKENAPATITE = ITEMS.register("c_apatite", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "sapphirechicken", 3));
//	public static final DeferredItem<Item> ECHICKENBASALZ = ITEMS.register("c_basalz", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "basalzrodchicken", 3));
//	public static final DeferredItem<Item> ECHICKENBITUMEN = ITEMS.register("c_bitumen", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "coalchicken", 3));
//	//endregion
//
//	//region Chicken - Tier 5
//	public static final DeferredItem<Item> E_CHICKEN_BLITZ = ITEMS.register("c_blitz", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "blitzrodchicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_BLIZZ = ITEMS.register("c_blizz", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "blizzrodchicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_CONSTANTAN = ITEMS.register("c_constantan", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "constantanchicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_ENDERIUM = ITEMS.register("c_enderium", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "enderiumchicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_LUMIUM = ITEMS.register("c_lumium", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "lumiumchicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_QUARTZENRICHEDIRON = ITEMS.register("c_quartzenrichediron", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "quartzenrichedironchicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_BLAZEPOWDER = ITEMS.register("c_blazepowder", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "blazechicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_AE_SILICON = ITEMS.register("c_silicon", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "siliconchicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_BLAZE_ROD = ITEMS.register("c_blazerod", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "blazechicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_BOTANIA_MANASTEEL = ITEMS.register("c_manasteel", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "manasteelchicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_BREEZE = ITEMS.register("c_breeze", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "breeze", 4));
//	public static final DeferredItem<Item> E_CHICKEN_CHORUS_FRUIT = ITEMS.register("c_chorusfruit", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "magentachicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_CHROME = ITEMS.register("c_chrome", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "chromechicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_ENDER_PEARL = ITEMS.register("c_enderpearl", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "enderchicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_ENDSTONE = ITEMS.register("c_endstone", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "bulletchicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_GLOWSTONE = ITEMS.register("c_glowstone", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "glowstonechicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_GOLD = ITEMS.register("c_gold", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "goldchicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_QUARTZ = ITEMS.register("c_quartz", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "quartzchicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_REFINED_IRON = ITEMS.register("c_refinediron", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "refinedironchicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_SIGNALUM = ITEMS.register("c_signalum", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "signalumchicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_SILVER = ITEMS.register("c_silver", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "silverorechicken", 4));
//	public static final DeferredItem<Item> E_CHICKEN_TNT = ITEMS.register("c_tnt", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "gunpowderchicken", 4));
//	//endregion
//
//	//region Chicken - Tier 6
//	public static final DeferredItem<Item> ECHICKENAMETHYSTBRONZE = ITEMS.register("c_amethystbronze", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "purplechicken", 5));
//	public static final DeferredItem<Item> ECHICKENHEPATIZON = ITEMS.register("c_hepatizon", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "tanzanitechicken", 5));
//	public static final DeferredItem<Item> ECHICKENKNIGHTSLIME = ITEMS.register("c_knightslime", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "knightslimechicken", 5));
//	public static final DeferredItem<Item> ECHICKENPIGIRON = ITEMS.register("c_pigiron", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "pigironchicken", 5));
//	public static final DeferredItem<Item> ECHICKENROSEGOLD = ITEMS.register("c_rosegold", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "pinkslimechicken", 5));
//	public static final DeferredItem<Item> ECHICKENRUBY = ITEMS.register("c_ruby", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "rubychicken", 5));
//	public static final DeferredItem<Item> ECHICKENSAPPHIRE = ITEMS.register("c_sapphire", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "sapphirechicken", 5));
//	public static final DeferredItem<Item> ECHICKENSLIMESTEEL = ITEMS.register("c_slimesteel", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "sapphirechicken", 5));
//	public static final DeferredItem<Item> E_CHICKEN_BRASS = ITEMS.register("c_brass", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "brasschicken", 5));
//	public static final DeferredItem<Item> E_CHICKEN_CERTUSQ = ITEMS.register("c_certusquartz", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "quartzenrichedironchicken", 5));
//	public static final DeferredItem<Item> E_CHICKEN_EMERALD = ITEMS.register("c_emerald", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "emeradiccrystalchicken", 5));
//	public static final DeferredItem<Item> E_CHICKEN_ENDER_EYE = ITEMS.register("c_endereye", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "enderchicken", 5));
//	public static final DeferredItem<Item> E_CHICKEN_GHASTTEAR = ITEMS.register("c_ghasttear", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "ghastchicken", 5));
//	public static final DeferredItem<Item> E_CHICKEN_MEKANISM_BIO_FUEL = ITEMS.register("c_biofuel", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "enderiumchicken", 5));
//	public static final DeferredItem<Item> E_CHICKEN_MEKANISM_BRONZE = ITEMS.register("c_bronze", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "bronzechicken", 5));
//	public static final DeferredItem<Item> E_CHICKEN_MEKANISM_STEEL = ITEMS.register("c_steel", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "steelchicken", 5));
//	public static final DeferredItem<Item> E_CHICKEN_MEKANISM_URANIUM = ITEMS.register("c_uranium", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "uraniumchicken", 5));
//	public static final DeferredItem<Item> E_CHICKEN_TUNGSTEN = ITEMS.register("c_tungsten", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "tungstenchicken", 5));
//	public static final DeferredItem<Item> E_CHICKEN_YELLORIUM = ITEMS.register("c_yellorium", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "yelloriumchicken", 5));
//	public static final DeferredItem<Item> E_OSMIUM_CHICKEN = ITEMS.register("c_osmium", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "osmiumchicken", 5));
//	//endregion
//
//	//region Chicken - Tier 7
//	public static final DeferredItem<Item> ECHICKENCOBALD = ITEMS.register("c_cobald", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "cobaltchicken", 6));
//	public static final DeferredItem<Item> ECHICKENMANYULLYN = ITEMS.register("c_manyullyn", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "manyullynchicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_AE_CHARGED_CERTUS = ITEMS.register("c_chargedcertus", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "quartzchicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_AE_FLUIX_CRYSTAL = ITEMS.register("c_fluixcrystal", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "lunarreactivedustchicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_ALLTHEMODIUM = ITEMS.register("c_allthemodium", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "energeticalloychicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_AMETHYST_SHARD = ITEMS.register("c_amethystshard", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "amethystchicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_BLUTONIUM = ITEMS.register("c_blutonium", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "blutoniumchicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_BOTANIA_ELEMENTIUM = ITEMS.register("c_elementium", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "elementiumchicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_BOTANIA_TERRASTEEL = ITEMS.register("c_terrasteel", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "terrasteelchicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_DIAMOND = ITEMS.register("c_diamond", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "diamondchicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_ELECTRUM = ITEMS.register("c_electrum", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "electrumchicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_HOT_TUNGSTEN_STEEL = ITEMS.register("c_hottungstensteel", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "silverorechicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_INVAR = ITEMS.register("c_invar", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "invarchicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_NETHERITE = ITEMS.register("c_netherite", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "netherwartchicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_NETHER_STAR = ITEMS.register("c_netherstar", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "blitzrodchicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_NICKEL = ITEMS.register("c_nickel", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "nickelchicken", 6));
//	public static final DeferredItem<Item> E_CHICKEN_TUNGSTENSTEEL = ITEMS.register("c_tungstensteel", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "tungstensteelchicken", 6));
//	//endregion
//
//	//region Chicken - Tier 8
//	public static final DeferredItem<Item> E_CHICKEN_ADAMANTIUM = ITEMS.register("c_adamantium", () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "emeradiccrystalchicken", 7));
//	public static final DeferredItem<Item> E_CHICKEN_IRIDIUM = ITEMS.register("c_iridium", () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "iridiumchicken", 7));
//	public static final DeferredItem<Item> E_CHICKEN_PLATINUM = ITEMS.register("c_platinum", () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "platinumchicken", 7));
//	public static final DeferredItem<Item> E_CHICKEN_VIBRANIUM = ITEMS.register("c_vibranium", () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "vibrantalloychicken", 7));
//	//endregion
//
//	//region Chicken - Tier 9
//	public static final DeferredItem<Item> E_CHICKEN_TITANIUM = ITEMS.register("c_titanium", () -> new AnimatedChicken_9(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "titaniumchicken", 8));
//	public static final DeferredItem<Item> E_CHICKEN_UNOBTAINIUM = ITEMS.register("c_unobtainium", () -> new AnimatedChicken_9(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "vinteumchicken", 8));
	//endregion

	//public static final DeferredItem<ModBucketItem> TESTITEM = ITEMS.register("testitem", () -> new FluidStackItem(new Item.Properties()));
	//region Tools
	public static final DeferredItem<Item> CHICKEN_SCANNER = ITEMS.register("chicken_scanner", () -> new ChickenScannerItem());
	public static final DeferredItem<Item> CHICKEN_STICK = ITEMS.register("chicken_stick", () -> new AnimatedChickenStick(new Item.Properties()));
	//endregion

	//region Essence
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_1 = ITEMS.register("chicken_essence_tier_1", () -> new Essence_Soul());
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_2 = ITEMS.register("chicken_essence_tier_2", () -> new Essence_Soul());
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_3 = ITEMS.register("chicken_essence_tier_3", () -> new Essence_Soul());
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_4 = ITEMS.register("chicken_essence_tier_4", () -> new Essence_Soul());
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_5 = ITEMS.register("chicken_essence_tier_5", () -> new Essence_Soul());
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_6 = ITEMS.register("chicken_essence_tier_6", () -> new Essence_Soul());
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_7 = ITEMS.register("chicken_essence_tier_7", () -> new Essence_Soul());
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_8 = ITEMS.register("chicken_essence_tier_8", () -> new Essence_Soul());
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_9 = ITEMS.register("chicken_essence_tier_9", () -> new Essence_Soul());
	//endregion

	//region SEEDS
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_1 = ITEMS.register("chicken_food_tier_1", () -> new ModItemNameBlockItem_1(ModBlocks.SEED_CROP_1.get(), new Item.Properties(), 0));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_2 = ITEMS.register("chicken_food_tier_2", () -> new ModItemNameBlockItem_2(ModBlocks.SEED_CROP_2.get(), new Item.Properties(), 1));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_3 = ITEMS.register("chicken_food_tier_3", () -> new ModItemNameBlockItem_3(ModBlocks.SEED_CROP_3.get(), new Item.Properties(), 2));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_4 = ITEMS.register("chicken_food_tier_4", () -> new ModItemNameBlockItem_4(ModBlocks.SEED_CROP_4.get(), new Item.Properties(), 3));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_5 = ITEMS.register("chicken_food_tier_5", () -> new ModItemNameBlockItem_5(ModBlocks.SEED_CROP_5.get(), new Item.Properties(), 4));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_6 = ITEMS.register("chicken_food_tier_6", () -> new ModItemNameBlockItem_6(ModBlocks.SEED_CROP_6.get(), new Item.Properties(), 5));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_7 = ITEMS.register("chicken_food_tier_7", () -> new ModItemNameBlockItem_7(ModBlocks.SEED_CROP_7.get(), new Item.Properties(), 6));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_8 = ITEMS.register("chicken_food_tier_8", () -> new ModItemNameBlockItem_8(ModBlocks.SEED_CROP_8.get(), new Item.Properties(), 7));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_9 = ITEMS.register("chicken_food_tier_9", () -> new ModItemNameBlockItem_9(ModBlocks.SEED_CROP_9.get(), new Item.Properties(), 8));
	//endregion

	public static final DeferredHolder<Item, BlockItem> SOUL_BREEDER = ITEMSS.register("soul_breeder",
			() -> new AnimatedSoulBreederBlockItem(ModBlocks.SOUL_BREEDER.get(),
					new Item.Properties()));

	public static final DeferredHolder<Item, BlockItem> TRAINER = ITEMSS.register("trainer",
			() -> new AnimatedTrainerBlockItem(ModBlocks.TRAINER.get(),
					new Item.Properties()){

				//DecimalFormat formatter = new DecimalFormat("#,###,00");
				@Override
				public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
					super.appendHoverText(itemstack, context, list, flag);
					if(itemstack.getComponents().has(DataComponents.CONTAINER)) {
						ItemContainerContents itemContainerContents = itemstack.get(DataComponents.CONTAINER);
						if(!itemContainerContents.getStackInSlot(0).isEmpty()) {
							list.add(Component.nullToEmpty(("\u00A7e") + "Chicken: " + ("\u00A7a") +  itemContainerContents.getStackInSlot(0).getItem().getDefaultInstance().getDisplayName().getString()));
						}
					}

				}
			});

	public static final DeferredHolder<Item, BlockItem> CHICKENSTORAGE = ITEMSS.register("chickenstorage",
			() -> new BlockItem(ModBlocks.CHICKENSTORAGE.get(),
					new Item.Properties().rarity(Rarity.EPIC)){

				DecimalFormat formatter = new DecimalFormat("#,###,00");
				@Override
				public void appendHoverText(ItemStack itemstack, TooltipContext context, List<Component> list, TooltipFlag flag) {
					super.appendHoverText(itemstack, context, list, flag);
					list.add(Component.nullToEmpty("Very Big Storage"));
					list.add(Component.nullToEmpty("For Chickens and Seeds"));
					list.add(Component.nullToEmpty("Works only with AE2, RS, Simple Storage Network"));
					list.add(Component.nullToEmpty("or other Mods who access Block Inventories via Interfaces"));
					list.add(Component.nullToEmpty("Its harder than Obsidian and got a 10x explosion Resist"));
					if(itemstack.getComponents().has(ModDataComponents.STORAGEAMOUNT.value())) {
						list.add(Component.nullToEmpty(("\u00A7e") + "Stored Items: " + ("\u00A7a") + String.format("%,d", itemstack.get(ModDataComponents.STORAGEAMOUNT))));
					}
				}
			});

//	public static int mycount;
//	public static int tier = 0;
//	public static String name = "";
//	public static String id = "";
//	public static String texture = "";
//
//	public static void readthis() {
//
//		try (Stream<Path> files = Files.list(Paths.get((FMLPaths.GAMEDIR.get().toString() + "/roostultimate/chickens")))) {
//			mycount = (int) files.count();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		File file = new File("");
//		com.google.gson.JsonObject mainObj = new com.google.gson.JsonObject();
//		com.google.gson.JsonObject subObj = new com.google.gson.JsonObject();
//		com.google.gson.JsonObject othersubObj = new com.google.gson.JsonObject();
//		if (mycount > 0) {
//			{
//				//Creating a File object for directory
//				File directoryPath = new File(FMLPaths.GAMEDIR.get().toString() + "/roostultimate/chickens");
//				//List of all files and directories
//				File filesList[] = directoryPath.listFiles();
//				for(File filee : filesList) {
//					try {
//						file = filee;
//						BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//						StringBuilder jsonstringbuilder = new StringBuilder();
//						String line;
//						while ((line = bufferedReader.readLine()) != null) {
//							jsonstringbuilder.append(line);
//						}
//						bufferedReader.close();
//						subObj = new Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
//						subObj = subObj.get("chicken").getAsJsonObject();
//						id = subObj.get("id").getAsString();
//						texture = subObj.get("texture").getAsString();
//						tier = subObj.get("tier").getAsInt();
//						extrachickens(id, texture, tier);
//
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//
//				}
//			}
//		}
//	}



	public static void readthis() {

		List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
		if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){

				String id = etherItem.getId();
				String itemtexture = etherItem.getItemtexture();
				String mobtexture = etherItem.getMobtexture();
				String dropitem = etherItem.getDropitem();
				float eggtime = etherItem.getEggtime();
				int tierr = etherItem.getTier();

				extrachickens(id, itemtexture, tierr);
			}
		}
	}

	private static DeferredItem<Item> extrachickens(String idd, String texturee, int tierr) {
		return switch (tierr) {
			case 1 -> ITEMS.register(idd, () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), texturee, 0));
			case 2 -> ITEMS.register(idd, () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), texturee,1));
			case 3 -> ITEMS.register(idd, () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), texturee,2));
			case 4 -> ITEMS.register(idd, () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), texturee,3));
			case 5 -> ITEMS.register(idd, () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), texturee,4));
			case 6 -> ITEMS.register(idd, () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), texturee,5));
			case 7 -> ITEMS.register(idd, () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), texturee,6));
			case 8 -> ITEMS.register(idd, () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), texturee,7));
			case 9 -> ITEMS.register(idd, () -> new AnimatedChicken_9(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), texturee,8));
			default -> ITEMS.register(idd, () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), texturee,0));
		};
	}

	public static void register(IEventBus eventBus) {
		readthis();
		ITEMS.register(eventBus);
		ITEMSS.register(eventBus);
	}
}
