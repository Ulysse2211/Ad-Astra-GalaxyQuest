package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.SolarPanelBlockEntity;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier1;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier2;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier3;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier4;
import com.github.alexnijjar.ad_astra.items.*;
import com.github.alexnijjar.ad_astra.items.FluidContainingItem.TankStorage;
import com.github.alexnijjar.ad_astra.items.armour.JetSuit;
import com.github.alexnijjar.ad_astra.items.armour.NetheriteSpaceSuit;
import com.github.alexnijjar.ad_astra.items.armour.SpaceSuit;
import com.github.alexnijjar.ad_astra.items.vehicles.RocketItem;
import com.github.alexnijjar.ad_astra.items.vehicles.RoverItem;
import com.github.alexnijjar.ad_astra.mixin.AxeItemAccessor;
import com.github.alexnijjar.ad_astra.util.FluidUtils;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ModItems {
	public static final Set<Item> items = new HashSet<>();

	// Vehicles Items
	public static final Item TIER_1_ROCKET = register("tier_1_rocket", new RocketItem<RocketEntityTier1>(ModEntityTypes.ROCKET_TIER_1, 1, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).maxCount(1).fireproof()));
	public static final Item TIER_2_ROCKET = register("tier_2_rocket", new RocketItem<RocketEntityTier2>(ModEntityTypes.ROCKET_TIER_2, 2, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).maxCount(1).fireproof()));
	public static final Item TIER_3_ROCKET = register("tier_3_rocket", new RocketItem<RocketEntityTier3>(ModEntityTypes.ROCKET_TIER_3, 3, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).maxCount(1).fireproof()));
	public static final Item TIER_4_ROCKET = register("tier_4_rocket", new RocketItem<RocketEntityTier4>(ModEntityTypes.ROCKET_TIER_4, 4, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).maxCount(1).fireproof()));
	public static final Item TIER_1_ROVER = register("tier_1_rover", new RoverItem(new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).maxCount(1).fireproof()));

	public static final Item OXYGEN_TANK = register("oxygen_tank", new OxygenTankItem(new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).maxCount(1)));

	public static final Item ASTRODUX = register("astrodux", new AstroduxItem(new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL)));

	public static final Item SPACE_PAINTING = register("space_painting", new SpacePaintingItem(ModEntityTypes.SPACE_PAINTING, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).rarity(Rarity.UNCOMMON)));

	public static final Item CHEESE = register("cheese", new Item(new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).food(new FoodComponent.Builder().hunger(4).saturationModifier(1.0f).build())));

	public static final BlockItem LAUNCH_PAD = register("launch_pad", new HoldableOverHeadBlockItem(ModBlocks.LAUNCH_PAD, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES)));

	// Buckets
	public static final Item OIL_BUCKET = register("oil_bucket", new BucketItem(ModFluids.OIL_STILL, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).recipeRemainder(Items.BUCKET).maxCount(1)));
	public static final Item FUEL_BUCKET = register("fuel_bucket", new BucketItem(ModFluids.FUEL_STILL, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).recipeRemainder(Items.BUCKET).maxCount(1)));
	public static final Item CRYO_FUEL_BUCKET = register("cryo_fuel_bucket", new BucketItem(ModFluids.CRYO_FUEL_STILL, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).recipeRemainder(Items.BUCKET).maxCount(1)));
	public static final Item OXYGEN_BUCKET = register("oxygen_bucket", new BucketItem(ModFluids.OXYGEN_STILL, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).recipeRemainder(Items.BUCKET).maxCount(1)) {
		@Override
		public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
			ItemStack itemStack = user.getStackInHand(hand);
			return TypedActionResult.fail(itemStack);
		}
	});

	// Spacesuit
	public static final SpaceSuit SPACE_HELMET = register("space_helmet", new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL)));
	public static final SpaceSuit SPACE_SUIT = register("space_suit", new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL)));
	public static final SpaceSuit SPACE_PANTS = register("space_pants", new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL)));
	public static final SpaceSuit SPACE_BOOTS = register("space_boots", new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL)));

	// Netherite Spacesuit
	public static final NetheriteSpaceSuit NETHERITE_SPACE_HELMET = register("netherite_space_helmet",
			new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));
	public static final NetheriteSpaceSuit NETHERITE_SPACE_SUIT = register("netherite_space_suit",
			new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));
	public static final NetheriteSpaceSuit NETHERITE_SPACE_PANTS = register("netherite_space_pants",
			new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));
	public static final NetheriteSpaceSuit NETHERITE_SPACE_BOOTS = register("netherite_space_boots",
			new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));

	// Jet Suit
	public static final JetSuit JET_SUIT_HELMET = register("jet_suit_helmet", new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));
	public static final JetSuit JET_SUIT = register("jet_suit", new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));
	public static final JetSuit JET_SUIT_PANTS = register("jet_suit_pants", new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));
	public static final JetSuit JET_SUIT_BOOTS = register("jet_suit_boots", new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));

	// Machines
	public static final BlockItem COAL_GENERATOR = register("coal_generator", new MachineBlockItem(ModBlocks.COAL_GENERATOR, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			tooltip.add((new TranslatableText("item.ad_astra.generator_energy.tooltip", AdAstra.CONFIG.coalGenerator.energyPerTick).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.coal_generator.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final BlockItem COMPRESSOR = register("compressor", new MachineBlockItem(ModBlocks.COMPRESSOR, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.compressor.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final BlockItem NASA_WORKBENCH = register("nasa_workbench", new MachineBlockItem(ModBlocks.NASA_WORKBENCH, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.nasa_workbench.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final BlockItem FUEL_REFINERY = register("fuel_refinery", new MachineBlockItem(ModBlocks.FUEL_REFINERY, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.fuel_refinery.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final BlockItem OXYGEN_LOADER = register("oxygen_loader", new MachineBlockItem(ModBlocks.OXYGEN_LOADER, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.oxygen_loader.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((new TranslatableText("item.ad_astra.oxygen_loader.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final BlockItem SOLAR_PANEL = register("solar_panel", new SolarPanelBlockItem(ModBlocks.SOLAR_PANEL, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			tooltip.add((new TranslatableText("item.ad_astra.generator_energy.tooltip", SolarPanelBlockEntity.getEnergyForDimension(world)).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.solar_panel.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((new TranslatableText("item.ad_astra.solar_panel.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final BlockItem OXYGEN_DISTRIBUTOR = register("oxygen_distributor", new MachineBlockItem(ModBlocks.OXYGEN_DISTRIBUTOR, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.oxygen_distributor.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((new TranslatableText("item.ad_astra.oxygen_distributor.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final BlockItem WATER_PUMP = register("water_pump", new MachineBlockItem(ModBlocks.WATER_PUMP, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			tooltip.add((new TranslatableText("item.ad_astra.fluid_transfer_rate.tooltip", FluidUtils.dropletsToMillibuckets(AdAstra.CONFIG.waterPump.transferPerTick)).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.water_pump.tooltip[0]", FluidUtils.dropletsToMillibuckets(AdAstra.CONFIG.waterPump.transferPerTick)).setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((new TranslatableText("item.ad_astra.water_pump.tooltip[1]", FluidUtils.dropletsToMillibuckets(AdAstra.CONFIG.waterPump.transferPerTick)).setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final BlockItem ENERGIZER = register("energizer", new EnergizerBlockItem(ModBlocks.ENERGIZER, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES).maxCount(1)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			super.appendTooltip(stack, world, tooltip, context);
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.energizer.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((new TranslatableText("item.ad_astra.energizer.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final BlockItem CRYO_FREEZER = register("cryo_freezer", new MachineBlockItem(ModBlocks.CRYO_FREEZER, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES).maxCount(1)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.cryo_freezer.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final BlockItem OXYGEN_SENSOR = register("oxygen_sensor", new MachineBlockItem(ModBlocks.OXYGEN_SENSOR, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES).maxCount(1)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.oxygen_sensor.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((new TranslatableText("item.ad_astra.oxygen_sensor.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});

	public static final Item WRENCH = register("wrench", new WrenchItem(new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES).maxCount(1)));
	public static final Item HAMMER = register("hammer", new HammerItem(new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_BASICS).maxCount(1).maxDamage(AdAstra.CONFIG.general.hammerDurability)));

	public static final Item IRON_ROD = registerItem("iron_rod", ModItemGroups.ITEM_GROUP_BASICS);
	public static final Item OXYGEN_GEAR = registerItem("oxygen_gear", ModItemGroups.ITEM_GROUP_BASICS);
	public static final Item WHEEL = registerItem("wheel", ModItemGroups.ITEM_GROUP_BASICS);
	public static final Item ENGINE_FRAME = registerItem("engine_frame", ModItemGroups.ITEM_GROUP_BASICS);
	public static final Item ENGINE_FAN = registerItem("engine_fan", ModItemGroups.ITEM_GROUP_BASICS);
	public static final Item ROCKET_NOSE_CONE = registerItem("rocket_nose_cone", ModItemGroups.ITEM_GROUP_BASICS);
	public static final Item STEEL_ENGINE = registerItem("steel_engine", ModItemGroups.ITEM_GROUP_BASICS);
	public static final Item DESH_ENGINE = registerItem("desh_engine", ModItemGroups.ITEM_GROUP_BASICS);
	public static final Item OSTRUM_ENGINE = registerItem("ostrum_engine", ModItemGroups.ITEM_GROUP_BASICS);
	public static final Item CALORITE_ENGINE = registerItem("calorite_engine", ModItemGroups.ITEM_GROUP_BASICS);
	public static final Item STEEL_TANK = registerItem("steel_tank", ModItemGroups.ITEM_GROUP_BASICS);
	public static final Item DESH_TANK = registerItem("desh_tank", ModItemGroups.ITEM_GROUP_BASICS);
	public static final Item OSTRUM_TANK = registerItem("ostrum_tank", ModItemGroups.ITEM_GROUP_BASICS);
	public static final Item CALORITE_TANK = registerItem("calorite_tank", ModItemGroups.ITEM_GROUP_BASICS);
	public static final Item ROCKET_FIN = registerItem("rocket_fin", ModItemGroups.ITEM_GROUP_BASICS);

	// Torch items
	public static final Item COAL_TORCH = register("extinguished_torch", new WallStandingBlockItem(ModBlocks.COAL_TORCH, ModBlocks.WALL_COAL_TORCH, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_BASICS)));
	public static final Item COAL_LANTERN = registerBlockItem(ModBlocks.COAL_LANTERN, ModItemGroups.ITEM_GROUP_BASICS);

	public static final Item STEEL_INGOT = registerItem("steel_ingot", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final Item DESH_INGOT = registerItem("desh_ingot", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final Item OSTRUM_INGOT = registerItem("ostrum_ingot", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final Item CALORITE_INGOT = registerItem("calorite_ingot", ModItemGroups.ITEM_GROUP_MATERIALS);

	public static final Item ICE_SHARD = registerItem("ice_shard", ModItemGroups.ITEM_GROUP_MATERIALS);

	public static final Item IRON_PLATE = registerItem("iron_plate", ModItemGroups.ITEM_GROUP_MATERIALS);

	public static final Item COMPRESSED_STEEL = registerItem("steel_plate", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final Item COMPRESSED_DESH = registerItem("desh_plate", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final Item COMPRESSED_OSTRUM = registerItem("ostrum_plate", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final Item COMPRESSED_CALORITE = registerItem("calorite_plate", ModItemGroups.ITEM_GROUP_MATERIALS);

	public static final Item STEEL_NUGGET = registerItem("steel_nugget", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final Item DESH_NUGGET = registerItem("desh_nugget", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final Item OSTRUM_NUGGET = registerItem("ostrum_nugget", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final Item CALORITE_NUGGET = registerItem("calorite_nugget", ModItemGroups.ITEM_GROUP_MATERIALS);

	public static final Item RAW_DESH = registerItem("raw_desh", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final Item RAW_OSTRUM = registerItem("raw_ostrum", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final Item RAW_CALORITE = registerItem("raw_calorite", ModItemGroups.ITEM_GROUP_MATERIALS);

	public static final BlockItem STROPHAR_MUSHROOM = registerBlockItem(ModBlocks.STROPHAR_MUSHROOM, ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final BlockItem AERONOS_MUSHROOM = registerBlockItem(ModBlocks.AERONOS_MUSHROOM, ModItemGroups.ITEM_GROUP_MATERIALS);

	// Flags
	public static final BlockItem WHITE_FLAG = registerFlag(ModBlocks.WHITE_FLAG);
	public static final BlockItem ORANGE_FLAG = registerFlag(ModBlocks.ORANGE_FLAG);
	public static final BlockItem MAGENTA_FLAG = registerFlag(ModBlocks.MAGENTA_FLAG);
	public static final BlockItem LIGHT_BLUE_FLAG = registerFlag(ModBlocks.LIGHT_BLUE_FLAG);
	public static final BlockItem YELLOW_FLAG = registerFlag(ModBlocks.YELLOW_FLAG);
	public static final BlockItem LIME_FLAG = registerFlag(ModBlocks.LIME_FLAG);
	public static final BlockItem PINK_FLAG = registerFlag(ModBlocks.PINK_FLAG);
	public static final BlockItem GRAY_FLAG = registerFlag(ModBlocks.GRAY_FLAG);
	public static final BlockItem LIGHT_GRAY_FLAG = registerFlag(ModBlocks.LIGHT_GRAY_FLAG);
	public static final BlockItem CYAN_FLAG = registerFlag(ModBlocks.CYAN_FLAG);
	public static final BlockItem PURPLE_FLAG = registerFlag(ModBlocks.PURPLE_FLAG);
	public static final BlockItem BLUE_FLAG = registerFlag(ModBlocks.BLUE_FLAG);
	public static final BlockItem BROWN_FLAG = registerFlag(ModBlocks.BROWN_FLAG);
	public static final BlockItem GREEN_FLAG = registerFlag(ModBlocks.GREEN_FLAG);
	public static final BlockItem RED_FLAG = registerFlag(ModBlocks.RED_FLAG);
	public static final BlockItem BLACK_FLAG = registerFlag(ModBlocks.BLACK_FLAG);

	// Globes
	public static final BlockItem EARTH_GLOBE = registerGlobe(ModBlocks.EARTH_GLOBE);
	public static final BlockItem MOON_GLOBE = registerGlobe(ModBlocks.MOON_GLOBE);
	public static final BlockItem MARS_GLOBE = registerGlobe(ModBlocks.MARS_GLOBE);
	public static final BlockItem MERCURY_GLOBE = registerGlobe(ModBlocks.MERCURY_GLOBE);
	public static final BlockItem VENUS_GLOBE = registerGlobe(ModBlocks.VENUS_GLOBE);
	public static final BlockItem GLACIO_GLOBE = registerGlobe(ModBlocks.GLACIO_GLOBE);

	// Cables
	public static final BlockItem STEEL_CABLE = register("steel_cable", new BlockItem(ModBlocks.STEEL_CABLE, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES)));
	public static final BlockItem DESH_CABLE = register("desh_cable", new BlockItem(ModBlocks.DESH_CABLE, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES)));

	// Fluid pipes
	public static final BlockItem DESH_FLUID_PIPE = register("desh_fluid_pipe", new BlockItem(ModBlocks.DESH_FLUID_PIPE, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES)));
	public static final BlockItem OSTRUM_FLUID_PIPE = register("ostrum_fluid_pipe", new BlockItem(ModBlocks.OSTRUM_FLUID_PIPE, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_MACHINES)));

	// Blocks
	// Iron
	public static final BlockItem VENT = registerBlockItem(ModBlocks.VENT);
	public static final BlockItem IRON_PLATING = registerBlockItem(ModBlocks.IRON_PLATING);
	public static final BlockItem IRON_PLATING_STAIRS = registerBlockItem(ModBlocks.IRON_PLATING_STAIRS);
	public static final BlockItem IRON_PLATING_SLAB = registerBlockItem(ModBlocks.IRON_PLATING_SLAB);
	public static final BlockItem IRON_PILLAR = registerBlockItem(ModBlocks.IRON_PILLAR);
	public static final BlockItem IRON_PLATING_BUTTON = registerBlockItem(ModBlocks.IRON_PLATING_BUTTON);
	public static final BlockItem IRON_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.IRON_PLATING_PRESSURE_PLATE);
	public static final BlockItem MARKED_IRON_PILLAR = registerBlockItem(ModBlocks.MARKED_IRON_PILLAR);
	public static final BlockItem GLOWING_IRON_PILLAR = registerBlockItem(ModBlocks.GLOWING_IRON_PILLAR);
	public static final BlockItem IRON_SLIDING_DOOR = registerBlockItem(ModBlocks.IRON_SLIDING_DOOR);

	// Steel
	public static final BlockItem STEEL_BLOCK = registerBlockItem(ModBlocks.STEEL_BLOCK);
	public static final BlockItem STEEL_PLATING = registerBlockItem(ModBlocks.STEEL_PLATING);
	public static final BlockItem STEEL_PLATING_STAIRS = registerBlockItem(ModBlocks.STEEL_PLATING_STAIRS);
	public static final BlockItem STEEL_PLATING_SLAB = registerBlockItem(ModBlocks.STEEL_PLATING_SLAB);
	public static final BlockItem STEEL_PILLAR = registerBlockItem(ModBlocks.STEEL_PILLAR);
	public static final BlockItem STEEL_PLATING_BUTTON = registerBlockItem(ModBlocks.STEEL_PLATING_BUTTON);
	public static final BlockItem STEEL_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.STEEL_PLATING_PRESSURE_PLATE);
	public static final BlockItem GLOWING_STEEL_PILLAR = registerBlockItem(ModBlocks.GLOWING_STEEL_PILLAR);
	public static final BlockItem STEEL_SLIDING_DOOR = registerBlockItem(ModBlocks.STEEL_SLIDING_DOOR);
	public static final BlockItem AIRLOCK = registerBlockItem(ModBlocks.AIRLOCK);
	public static final BlockItem REINFORCED_DOOR = registerBlockItem(ModBlocks.REINFORCED_DOOR);
	public static final BlockItem STEEL_DOOR = registerBlockItem(ModBlocks.STEEL_DOOR);
	public static final BlockItem STEEL_TRAPDOOR = registerBlockItem(ModBlocks.STEEL_TRAPDOOR);

	// Moon
	public static final BlockItem CHEESE_BLOCK = registerBlockItem(ModBlocks.CHEESE_BLOCK);
	public static final BlockItem DESH_BLOCK = registerBlockItem(ModBlocks.DESH_BLOCK);
	public static final BlockItem RAW_DESH_BLOCK = registerBlockItem(ModBlocks.RAW_DESH_BLOCK);
	public static final BlockItem DESH_PLATING = registerBlockItem(ModBlocks.DESH_PLATING);
	public static final BlockItem DESH_PLATING_STAIRS = registerBlockItem(ModBlocks.DESH_PLATING_STAIRS);
	public static final BlockItem DESH_PLATING_SLAB = registerBlockItem(ModBlocks.DESH_PLATING_SLAB);
	public static final BlockItem DESH_PILLAR = registerBlockItem(ModBlocks.DESH_PILLAR);
	public static final BlockItem DESH_PLATING_BUTTON = registerBlockItem(ModBlocks.DESH_PLATING_BUTTON);
	public static final BlockItem DESH_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.DESH_PLATING_PRESSURE_PLATE);
	public static final BlockItem GLOWING_DESH_PILLAR = registerBlockItem(ModBlocks.GLOWING_DESH_PILLAR);
	public static final BlockItem DESH_SLIDING_DOOR = registerBlockItem(ModBlocks.DESH_SLIDING_DOOR);

	// Mars
	public static final BlockItem OSTRUM_BLOCK = registerBlockItem(ModBlocks.OSTRUM_BLOCK);
	public static final BlockItem RAW_OSTRUM_BLOCK = registerBlockItem(ModBlocks.RAW_OSTRUM_BLOCK);
	public static final BlockItem OSTRUM_PLATING = registerBlockItem(ModBlocks.OSTRUM_PLATING);
	public static final BlockItem OSTRUM_PLATING_STAIRS = registerBlockItem(ModBlocks.OSTRUM_PLATING_STAIRS);
	public static final BlockItem OSTRUM_PLATING_SLAB = registerBlockItem(ModBlocks.OSTRUM_PLATING_SLAB);
	public static final BlockItem OSTRUM_PILLAR = registerBlockItem(ModBlocks.OSTRUM_PILLAR);
	public static final BlockItem OSTRUM_PLATING_BUTTON = registerBlockItem(ModBlocks.OSTRUM_PLATING_BUTTON);
	public static final BlockItem OSTRUM_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.OSTRUM_PLATING_PRESSURE_PLATE);
	public static final BlockItem GLOWING_OSTRUM_PILLAR = registerBlockItem(ModBlocks.GLOWING_OSTRUM_PILLAR);
	public static final BlockItem OSTRUM_SLIDING_DOOR = registerBlockItem(ModBlocks.OSTRUM_SLIDING_DOOR);

	// Venus
	public static final BlockItem CALORITE_BLOCK = registerBlockItem(ModBlocks.CALORITE_BLOCK);
	public static final BlockItem RAW_CALORITE_BLOCK = registerBlockItem(ModBlocks.RAW_CALORITE_BLOCK);
	public static final BlockItem CALORITE_PLATING = registerBlockItem(ModBlocks.CALORITE_PLATING);
	public static final BlockItem CALORITE_PLATING_STAIRS = registerBlockItem(ModBlocks.CALORITE_PLATING_STAIRS);
	public static final BlockItem CALORITE_PLATING_SLAB = registerBlockItem(ModBlocks.CALORITE_PLATING_SLAB);
	public static final BlockItem CALORITE_PILLAR = registerBlockItem(ModBlocks.CALORITE_PILLAR);
	public static final BlockItem CALORITE_PLATING_BUTTON = registerBlockItem(ModBlocks.CALORITE_PLATING_BUTTON);
	public static final BlockItem CALORITE_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.CALORITE_PLATING_PRESSURE_PLATE);
	public static final BlockItem GLOWING_CALORITE_PILLAR = registerBlockItem(ModBlocks.GLOWING_CALORITE_PILLAR);
	public static final BlockItem CALORITE_SLIDING_DOOR = registerBlockItem(ModBlocks.CALORITE_SLIDING_DOOR);

	// Earth blocks
	public static final BlockItem SKY_STONE = registerBlockItem(ModBlocks.SKY_STONE);

	// Moon blocks
	public static final BlockItem MOON_SAND = registerBlockItem(ModBlocks.MOON_SAND);
	public static final BlockItem MOON_STONE = registerBlockItem(ModBlocks.MOON_STONE);
	public static final BlockItem MOON_STONE_STAIRS = registerBlockItem(ModBlocks.MOON_STONE_STAIRS);
	public static final BlockItem MOON_STONE_SLAB = registerBlockItem(ModBlocks.MOON_STONE_SLAB);
	public static final BlockItem MOON_COBBLESTONE = registerBlockItem(ModBlocks.MOON_COBBLESTONE);
	public static final BlockItem MOON_COBBLESTONE_STAIRS = registerBlockItem(ModBlocks.MOON_COBBLESTONE_STAIRS);
	public static final BlockItem MOON_COBBLESTONE_SLAB = registerBlockItem(ModBlocks.MOON_COBBLESTONE_SLAB);
	public static final BlockItem MOON_STONE_BRICKS = registerBlockItem(ModBlocks.MOON_STONE_BRICKS);
	public static final BlockItem CRACKED_MOON_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_MOON_STONE_BRICKS);
	public static final BlockItem MOON_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.MOON_STONE_BRICK_SLAB);
	public static final BlockItem MOON_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.MOON_STONE_BRICK_STAIRS);
	public static final BlockItem CHISELED_MOON_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_MOON_STONE_BRICKS);
	public static final BlockItem CHISELED_MOON_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_MOON_STONE_STAIRS);
	public static final BlockItem CHISELED_MOON_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_MOON_STONE_SLAB);
	public static final BlockItem POLISHED_MOON_STONE = registerBlockItem(ModBlocks.POLISHED_MOON_STONE);
	public static final BlockItem POLISHED_MOON_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_MOON_STONE_STAIRS);
	public static final BlockItem POLISHED_MOON_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_MOON_STONE_SLAB);
	public static final BlockItem MOON_PILLAR = registerBlockItem(ModBlocks.MOON_PILLAR);
	public static final BlockItem MOON_STONE_BRICK_WALL = registerBlockItem(ModBlocks.MOON_STONE_BRICK_WALL);

	public static final BlockItem MOON_CHEESE_ORE = registerBlockItem(ModBlocks.MOON_CHEESE_ORE);
	public static final BlockItem MOON_DESH_ORE = registerBlockItem(ModBlocks.MOON_DESH_ORE);
	public static final BlockItem DEEPSLATE_DESH_ORE = registerBlockItem(ModBlocks.DEEPSLATE_DESH_ORE);
	public static final BlockItem MOON_IRON_ORE = registerBlockItem(ModBlocks.MOON_IRON_ORE);
	public static final BlockItem MOON_ICE_SHARD_ORE = registerBlockItem(ModBlocks.MOON_ICE_SHARD_ORE);

	public static final BlockItem STROPHAR_CAP = registerBlockItem(ModBlocks.STROPHAR_CAP);
	public static final BlockItem STROPHAR_DOOR = registerBlockItem(ModBlocks.STROPHAR_DOOR);
	public static final BlockItem STROPHAR_TRAPDOOR = registerBlockItem(ModBlocks.STROPHAR_TRAPDOOR);
	public static final BlockItem STROPHAR_PLANKS = registerBlockItem(ModBlocks.STROPHAR_PLANKS);
	public static final BlockItem STROPHAR_STAIRS = registerBlockItem(ModBlocks.STROPHAR_STAIRS);
	public static final BlockItem STROPHAR_SLAB = registerBlockItem(ModBlocks.STROPHAR_SLAB);
	public static final BlockItem STROPHAR_FENCE = registerBlockItem(ModBlocks.STROPHAR_FENCE);
	public static final BlockItem STROPHAR_FENCE_GATE = registerBlockItem(ModBlocks.STROPHAR_FENCE_GATE);
	public static final BlockItem STROPHAR_STEM = registerBlockItem(ModBlocks.STROPHAR_STEM);
	public static final BlockItem STROPHAR_CHEST = registerBlockItem(ModBlocks.STROPHAR_CHEST);
	public static final BlockItem STROPHAR_LADDER = registerBlockItem(ModBlocks.STROPHAR_LADDER);

	public static final BlockItem AERONOS_CAP = registerBlockItem(ModBlocks.AERONOS_CAP);
	public static final BlockItem AERONOS_DOOR = registerBlockItem(ModBlocks.AERONOS_DOOR);
	public static final BlockItem AERONOS_TRAPDOOR = registerBlockItem(ModBlocks.AERONOS_TRAPDOOR);
	public static final BlockItem AERONOS_PLANKS = registerBlockItem(ModBlocks.AERONOS_PLANKS);
	public static final BlockItem AERONOS_STAIRS = registerBlockItem(ModBlocks.AERONOS_STAIRS);
	public static final BlockItem AERONOS_SLAB = registerBlockItem(ModBlocks.AERONOS_SLAB);
	public static final BlockItem AERONOS_FENCE = registerBlockItem(ModBlocks.AERONOS_FENCE);
	public static final BlockItem AERONOS_FENCE_GATE = registerBlockItem(ModBlocks.AERONOS_FENCE_GATE);
	public static final BlockItem AERONOS_STEM = registerBlockItem(ModBlocks.AERONOS_STEM);
	public static final BlockItem AERONOS_CHEST = registerBlockItem(ModBlocks.AERONOS_CHEST);
	public static final BlockItem AERONOS_LADDER = registerBlockItem(ModBlocks.AERONOS_LADDER);

	// Mars blocks
	public static final BlockItem MARS_SAND = registerBlockItem(ModBlocks.MARS_SAND);
	public static final BlockItem MARS_STONE = registerBlockItem(ModBlocks.MARS_STONE);
	public static final BlockItem MARS_STONE_STAIRS = registerBlockItem(ModBlocks.MARS_STONE_STAIRS);
	public static final BlockItem MARS_STONE_SLAB = registerBlockItem(ModBlocks.MARS_STONE_SLAB);
	public static final BlockItem MARS_COBBLESTONE = registerBlockItem(ModBlocks.MARS_COBBLESTONE);
	public static final BlockItem MARS_COBBLESTONE_STAIRS = registerBlockItem(ModBlocks.MARS_COBBLESTONE_STAIRS);
	public static final BlockItem MARS_COBBLESTONE_SLAB = registerBlockItem(ModBlocks.MARS_COBBLESTONE_SLAB);
	public static final BlockItem MARS_STONE_BRICKS = registerBlockItem(ModBlocks.MARS_STONE_BRICKS);
	public static final BlockItem CRACKED_MARS_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_MARS_STONE_BRICKS);
	public static final BlockItem MARS_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.MARS_STONE_BRICK_SLAB);
	public static final BlockItem MARS_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.MARS_STONE_BRICK_STAIRS);
	public static final BlockItem CHISELED_MARS_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_MARS_STONE_BRICKS);
	public static final BlockItem CHISELED_MARS_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_MARS_STONE_STAIRS);
	public static final BlockItem CHISELED_MARS_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_MARS_STONE_SLAB);
	public static final BlockItem POLISHED_MARS_STONE = registerBlockItem(ModBlocks.POLISHED_MARS_STONE);
	public static final BlockItem POLISHED_MARS_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_MARS_STONE_STAIRS);
	public static final BlockItem POLISHED_MARS_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_MARS_STONE_SLAB);
	public static final BlockItem MARS_PILLAR = registerBlockItem(ModBlocks.MARS_PILLAR);
	public static final BlockItem MARS_STONE_BRICK_WALL = registerBlockItem(ModBlocks.MARS_STONE_BRICK_WALL);

	public static final BlockItem CONGLOMERATE = registerBlockItem(ModBlocks.CONGLOMERATE);
	public static final BlockItem POLISHED_CONGLOMERATE = registerBlockItem(ModBlocks.POLISHED_CONGLOMERATE);

	public static final BlockItem DEEPSLATE_ICE_SHARD_ORE = registerBlockItem(ModBlocks.DEEPSLATE_ICE_SHARD_ORE);
	public static final BlockItem MARS_IRON_ORE = registerBlockItem(ModBlocks.MARS_IRON_ORE);
	public static final BlockItem MARS_DIAMOND_ORE = registerBlockItem(ModBlocks.MARS_DIAMOND_ORE);
	public static final BlockItem MARS_OSTRUM_ORE = registerBlockItem(ModBlocks.MARS_OSTRUM_ORE);
	public static final BlockItem DEEPSLATE_OSTRUM_ORE = registerBlockItem(ModBlocks.DEEPSLATE_OSTRUM_ORE);
	public static final BlockItem MARS_ICE_SHARD_ORE = registerBlockItem(ModBlocks.MARS_ICE_SHARD_ORE);

	// Venus blocks
	public static final BlockItem VENUS_SANDSTONE = registerBlockItem(ModBlocks.VENUS_SANDSTONE);
	public static final BlockItem VENUS_SANDSTONE_BRICKS = registerBlockItem(ModBlocks.VENUS_SANDSTONE_BRICKS);
	public static final BlockItem CRACKED_VENUS_SANDSTONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_VENUS_SANDSTONE_BRICKS);
	public static final BlockItem VENUS_SANDSTONE_BRICK_SLAB = registerBlockItem(ModBlocks.VENUS_SANDSTONE_BRICK_SLAB);
	public static final BlockItem VENUS_SANDSTONE_BRICK_STAIRS = registerBlockItem(ModBlocks.VENUS_SANDSTONE_BRICK_STAIRS);

	public static final BlockItem VENUS_SAND = registerBlockItem(ModBlocks.VENUS_SAND);
	public static final BlockItem VENUS_STONE = registerBlockItem(ModBlocks.VENUS_STONE);
	public static final BlockItem VENUS_STONE_STAIRS = registerBlockItem(ModBlocks.VENUS_STONE_STAIRS);
	public static final BlockItem VENUS_STONE_SLAB = registerBlockItem(ModBlocks.VENUS_STONE_SLAB);
	public static final BlockItem VENUS_COBBLESTONE = registerBlockItem(ModBlocks.VENUS_COBBLESTONE);
	public static final BlockItem VENUS_COBBLESTONE_STAIRS = registerBlockItem(ModBlocks.VENUS_COBBLESTONE_STAIRS);
	public static final BlockItem VENUS_COBBLESTONE_SLAB = registerBlockItem(ModBlocks.VENUS_COBBLESTONE_SLAB);
	public static final BlockItem VENUS_STONE_BRICKS = registerBlockItem(ModBlocks.VENUS_STONE_BRICKS);
	public static final BlockItem CRACKED_VENUS_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_VENUS_STONE_BRICKS);
	public static final BlockItem VENUS_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.VENUS_STONE_BRICK_SLAB);
	public static final BlockItem VENUS_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.VENUS_STONE_BRICK_STAIRS);
	public static final BlockItem CHISELED_VENUS_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_VENUS_STONE_BRICKS);
	public static final BlockItem CHISELED_VENUS_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_VENUS_STONE_STAIRS);
	public static final BlockItem CHISELED_VENUS_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_VENUS_STONE_SLAB);
	public static final BlockItem POLISHED_VENUS_STONE = registerBlockItem(ModBlocks.POLISHED_VENUS_STONE);
	public static final BlockItem POLISHED_VENUS_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_VENUS_STONE_STAIRS);
	public static final BlockItem POLISHED_VENUS_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_VENUS_STONE_SLAB);
	public static final BlockItem VENUS_PILLAR = registerBlockItem(ModBlocks.VENUS_PILLAR);
	public static final BlockItem VENUS_STONE_BRICK_WALL = registerBlockItem(ModBlocks.VENUS_STONE_BRICK_WALL);

	public static final BlockItem VENUS_COAL_ORE = registerBlockItem(ModBlocks.VENUS_COAL_ORE);
	public static final BlockItem VENUS_GOLD_ORE = registerBlockItem(ModBlocks.VENUS_GOLD_ORE);
	public static final BlockItem VENUS_DIAMOND_ORE = registerBlockItem(ModBlocks.VENUS_DIAMOND_ORE);
	public static final BlockItem VENUS_CALORITE_ORE = registerBlockItem(ModBlocks.VENUS_CALORITE_ORE);
	public static final BlockItem DEEPSLATE_CALORITE_ORE = registerBlockItem(ModBlocks.DEEPSLATE_CALORITE_ORE);

	public static final BlockItem INFERNAL_SPIRE_BLOCK = registerBlockItem(ModBlocks.INFERNAL_SPIRE_BLOCK);

	// Mercury blocks
	public static final BlockItem MERCURY_STONE = registerBlockItem(ModBlocks.MERCURY_STONE);
	public static final BlockItem MERCURY_STONE_STAIRS = registerBlockItem(ModBlocks.MERCURY_STONE_STAIRS);
	public static final BlockItem MERCURY_STONE_SLAB = registerBlockItem(ModBlocks.MERCURY_STONE_SLAB);
	public static final BlockItem MERCURY_COBBLESTONE = registerBlockItem(ModBlocks.MERCURY_COBBLESTONE);
	public static final BlockItem MERCURY_COBBLESTONE_STAIRS = registerBlockItem(ModBlocks.MERCURY_COBBLESTONE_STAIRS);
	public static final BlockItem MERCURY_COBBLESTONE_SLAB = registerBlockItem(ModBlocks.MERCURY_COBBLESTONE_SLAB);
	public static final BlockItem MERCURY_STONE_BRICKS = registerBlockItem(ModBlocks.MERCURY_STONE_BRICKS);
	public static final BlockItem CRACKED_MERCURY_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_MERCURY_STONE_BRICKS);
	public static final BlockItem MERCURY_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.MERCURY_STONE_BRICK_SLAB);
	public static final BlockItem MERCURY_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.MERCURY_STONE_BRICK_STAIRS);
	public static final BlockItem CHISELED_MERCURY_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_MERCURY_STONE_BRICKS);
	public static final BlockItem CHISELED_MERCURY_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_MERCURY_STONE_STAIRS);
	public static final BlockItem CHISELED_MERCURY_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_MERCURY_STONE_SLAB);
	public static final BlockItem POLISHED_MERCURY_STONE = registerBlockItem(ModBlocks.POLISHED_MERCURY_STONE);
	public static final BlockItem POLISHED_MERCURY_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_MERCURY_STONE_STAIRS);
	public static final BlockItem POLISHED_MERCURY_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_MERCURY_STONE_SLAB);
	public static final BlockItem MERCURY_PILLAR = registerBlockItem(ModBlocks.MERCURY_PILLAR);
	public static final BlockItem MERCURY_STONE_BRICK_WALL = registerBlockItem(ModBlocks.MERCURY_STONE_BRICK_WALL);

	public static final BlockItem MERCURY_IRON_ORE = registerBlockItem(ModBlocks.MERCURY_IRON_ORE);

	// Glacio blocks
	public static final BlockItem GLACIO_STONE = registerBlockItem(ModBlocks.GLACIO_STONE);
	public static final BlockItem GLACIO_STONE_STAIRS = registerBlockItem(ModBlocks.GLACIO_STONE_STAIRS);
	public static final BlockItem GLACIO_STONE_SLAB = registerBlockItem(ModBlocks.GLACIO_STONE_SLAB);
	public static final BlockItem GLACIO_COBBLESTONE = registerBlockItem(ModBlocks.GLACIO_COBBLESTONE);
	public static final BlockItem GLACIO_COBBLESTONE_STAIRS = registerBlockItem(ModBlocks.GLACIO_COBBLESTONE_STAIRS);
	public static final BlockItem GLACIO_COBBLESTONE_SLAB = registerBlockItem(ModBlocks.GLACIO_COBBLESTONE_SLAB);
	public static final BlockItem GLACIO_STONE_BRICKS = registerBlockItem(ModBlocks.GLACIO_STONE_BRICKS);
	public static final BlockItem CRACKED_GLACIO_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_GLACIO_STONE_BRICKS);
	public static final BlockItem GLACIO_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.GLACIO_STONE_BRICK_SLAB);
	public static final BlockItem GLACIO_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.GLACIO_STONE_BRICK_STAIRS);
	public static final BlockItem CHISELED_GLACIO_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_GLACIO_STONE_BRICKS);
	public static final BlockItem CHISELED_GLACIO_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_GLACIO_STONE_STAIRS);
	public static final BlockItem CHISELED_GLACIO_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_GLACIO_STONE_SLAB);
	public static final BlockItem POLISHED_GLACIO_STONE = registerBlockItem(ModBlocks.POLISHED_GLACIO_STONE);
	public static final BlockItem POLISHED_GLACIO_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_GLACIO_STONE_STAIRS);
	public static final BlockItem POLISHED_GLACIO_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_GLACIO_STONE_SLAB);
	public static final BlockItem GLACIO_PILLAR = registerBlockItem(ModBlocks.GLACIO_PILLAR);
	public static final BlockItem GLACIO_STONE_BRICK_WALL = registerBlockItem(ModBlocks.GLACIO_STONE_BRICK_WALL);

	public static final BlockItem GLACIO_ICE_SHARD_ORE = registerBlockItem(ModBlocks.GLACIO_ICE_SHARD_ORE);
	public static final BlockItem GLACIO_COAL_ORE = registerBlockItem(ModBlocks.GLACIO_COAL_ORE);
	public static final BlockItem GLACIO_COPPER_ORE = registerBlockItem(ModBlocks.GLACIO_COPPER_ORE);
	public static final BlockItem GLACIO_IRON_ORE = registerBlockItem(ModBlocks.GLACIO_IRON_ORE);
	public static final BlockItem GLACIO_LAPIS_ORE = registerBlockItem(ModBlocks.GLACIO_LAPIS_ORE);

	public static final BlockItem PERMAFROST = registerBlockItem(ModBlocks.PERMAFROST);
	public static final BlockItem PERMAFROST_BRICKS = registerBlockItem(ModBlocks.PERMAFROST_BRICKS);
	public static final BlockItem PERMAFROST_BRICK_STAIRS = registerBlockItem(ModBlocks.PERMAFROST_BRICK_STAIRS);
	public static final BlockItem PERMAFROST_BRICK_SLAB = registerBlockItem(ModBlocks.PERMAFROST_BRICK_SLAB);
	public static final BlockItem CRACKED_PERMAFROST_BRICKS = registerBlockItem(ModBlocks.CRACKED_PERMAFROST_BRICKS);
	public static final BlockItem PERMAFROST_TILES = registerBlockItem(ModBlocks.PERMAFROST_TILES);
	public static final BlockItem CHISELED_PERMAFROST_BRICKS = registerBlockItem(ModBlocks.CHISELED_PERMAFROST_BRICKS);
	public static final BlockItem CHISELED_PERMAFROST_BRICK_STAIRS = registerBlockItem(ModBlocks.CHISELED_PERMAFROST_BRICK_STAIRS);
	public static final BlockItem CHISELED_PERMAFROST_BRICK_SLAB = registerBlockItem(ModBlocks.CHISELED_PERMAFROST_BRICK_SLAB);
	public static final BlockItem POLISHED_PERMAFROST = registerBlockItem(ModBlocks.POLISHED_PERMAFROST);
	public static final BlockItem POLISHED_PERMAFROST_STAIRS = registerBlockItem(ModBlocks.POLISHED_PERMAFROST_STAIRS);
	public static final BlockItem POLISHED_PERMAFROST_SLAB = registerBlockItem(ModBlocks.POLISHED_PERMAFROST_SLAB);
	public static final BlockItem PERMAFROST_PILLAR = registerBlockItem(ModBlocks.PERMAFROST_PILLAR);
	public static final BlockItem PERMAFROST_BRICK_WALL = registerBlockItem(ModBlocks.PERMAFROST_BRICK_WALL);

	public static final BlockItem GLACIAN_LOG = registerBlockItem(ModBlocks.GLACIAN_LOG);
	public static final BlockItem STRIPPED_GLACIAN_LOG = registerBlockItem(ModBlocks.STRIPPED_GLACIAN_LOG);
	public static final BlockItem GLACIAN_LEAVES = registerBlockItem(ModBlocks.GLACIAN_LEAVES);
	public static final BlockItem GLACIAN_PLANKS = registerBlockItem(ModBlocks.GLACIAN_PLANKS);
	public static final BlockItem GLACIAN_STAIRS = registerBlockItem(ModBlocks.GLACIAN_STAIRS);
	public static final BlockItem GLACIAN_SLAB = registerBlockItem(ModBlocks.GLACIAN_SLAB);
	public static final BlockItem GLACIAN_DOOR = registerBlockItem(ModBlocks.GLACIAN_DOOR);
	public static final BlockItem GLACIAN_TRAPDOOR = registerBlockItem(ModBlocks.GLACIAN_TRAPDOOR);
	public static final BlockItem GLACIAN_FENCE = registerBlockItem(ModBlocks.GLACIAN_FENCE);
	public static final BlockItem GLACIAN_FENCE_GATE = registerBlockItem(ModBlocks.GLACIAN_FENCE_GATE);
	public static final BlockItem GLACIAN_BUTTON = registerBlockItem(ModBlocks.GLACIAN_BUTTON);
	public static final BlockItem GLACIAN_PRESSURE_PLATE = registerBlockItem(ModBlocks.GLACIAN_PRESSURE_PLATE);
	public static final BlockItem GLACIAN_SIGN = register("glacian_sign", new SignItem(new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_BLOCKS).maxCount(16), ModBlocks.GLACIAN_SIGN, ModBlocks.GLACIAN_WALL_SIGN));
	public static final BlockItem GLACIAN_FUR = registerBlockItem(ModBlocks.GLACIAN_FUR);

	// Spawn eggs
	// Moon
	public static final Item LUNARIAN_SPAWN_EGG = register("lunarian_spawn_egg", new SpawnEggItem(ModEntityTypes.LUNARIAN, -13382401, -11650781, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));
	public static final Item CORRUPTED_LUNARIAN_SPAWN_EGG = register("corrupted_lunarian_spawn_egg", new SpawnEggItem(ModEntityTypes.CORRUPTED_LUNARIAN, -14804199, -16740159, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));
	public static final Item STAR_CRAWLER_SPAWN_EGG = register("star_crawler_spawn_egg", new SpawnEggItem(ModEntityTypes.STAR_CRAWLER, -13421773, -16724788, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));

	// Mars
	public static final Item MARTIAN_RAPTOR_SPAWN_EGG = register("martian_raptor_spawn_egg", new SpawnEggItem(ModEntityTypes.MARTIAN_RAPTOR, 5349438, -13312, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));

	// Venus
	public static final Item PYGRO_SPAWN_EGG = register("pygro_spawn_egg", new SpawnEggItem(ModEntityTypes.PYGRO, -3381760, -6750208, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));
	public static final Item ZOMBIFIED_PYGRO_SPAWN_EGG = register("zombified_pygro_spawn_egg", new SpawnEggItem(ModEntityTypes.ZOMBIFIED_PYGRO, 8473125, 6131271, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));
	public static final Item PYGRO_BRUTE_SPAWN_EGG = register("pygro_brute_spawn_egg", new SpawnEggItem(ModEntityTypes.PYGRO_BRUTE, -3381760, -67208, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));
	public static final Item MOGLER_SPAWN_EGG = register("mogler_spawn_egg", new SpawnEggItem(ModEntityTypes.MOGLER, -13312, -3407872, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));
	public static final Item ZOMBIFIED_MOGLER_SPAWN_EGG = register("zombified_mogler_spawn_egg", new SpawnEggItem(ModEntityTypes.ZOMBIFIED_MOGLER, 12537409, 7988821, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));
	public static final Item SULFUR_CREEPER_SPAWN_EGG = register("sulfur_creeper_spawn_egg", new SpawnEggItem(ModEntityTypes.SULFUR_CREEPER, 13930288, 11303196, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));

	// Glacio
	public static final Item GLACIAN_RAM_SPAWN_EGG = register("glacian_ram_spawn_egg", new SpawnEggItem(ModEntityTypes.GLACIAN_RAM, 16770815, 4406589, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));

	// Other
	public static final Item LUNARIAN_WANDERING_TRADER_SPAWN_EGG = register("lunarian_wandering_trader_spawn_egg",
			new SpawnEggItem(ModEntityTypes.LUNARIAN_WANDERING_TRADER, 5993415, 8537301, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));

	public static void register() {
		registerTank(TIER_1_ROCKET);
		registerTank(TIER_2_ROCKET);
		registerTank(TIER_3_ROCKET);
		registerTank(TIER_4_ROCKET);
		registerTank(TIER_1_ROVER);
		registerTank(OXYGEN_TANK);

		Map<Block, Block> strippedBlocks = new HashMap<>(AxeItemAccessor.adastra_getStrippedBlocks());
		strippedBlocks.put(ModBlocks.GLACIAN_LOG, ModBlocks.STRIPPED_GLACIAN_LOG);
		AxeItemAccessor.adastra_setStrippedBlocks(strippedBlocks);
	}

	public static BlockItem registerFlag(Block flag) {
		TallBlockItem item = new TallBlockItem(flag, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_FLAGS)) {
			@Override
			public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
				tooltip.add((new TranslatableText("item.ad_astra.flag.tooltip").setStyle(Style.EMPTY.withColor(Formatting.AQUA))));
			}
		};
		register(Registry.BLOCK.getId(flag), item);
		return item;
	}

	public static BlockItem registerGlobe(Block globe) {
		BlockItem item = new BlockItem(globe, new FabricItemSettings().group(ModItemGroups.ITEM_GROUP_GLOBES).maxCount(1).rarity(Rarity.RARE));
		register(Registry.BLOCK.getId(globe), item);
		return item;
	}

	public static BlockItem registerBlockItem(Block block) {
		return registerBlockItem(block, ModItemGroups.ITEM_GROUP_BLOCKS);
	}

	public static BlockItem registerBlockItem(Block block, ItemGroup group) {
		BlockItem item = new BlockItem(block, new FabricItemSettings().group(group));
		register(Registry.BLOCK.getId(block), item);
		return item;
	}

	public static Item registerItem(String id, ItemGroup group) {
		Item item = new Item(new FabricItemSettings().group(group));
		register(id, item);
		return item;
	}

	public static <T extends Item> T register(String id, T item) {
		return register(new ModIdentifier(id), item);
	}

	public static <T extends Item> T register(Identifier id, T item) {
		Registry.register(Registry.ITEM, id, item);
		items.add(item);
		return item;
	}

	public static void registerTank(Item tank) {
		FluidStorage.ITEM.registerForItems(TankStorage::new, tank);
	}
}