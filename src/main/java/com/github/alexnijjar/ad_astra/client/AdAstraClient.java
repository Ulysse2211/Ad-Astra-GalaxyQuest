package com.github.alexnijjar.ad_astra.client;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.client.registry.*;
import com.github.alexnijjar.ad_astra.client.renderer.block.EnergizerBlockEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.SlidingDoorBlockEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.flag.FlagBlockEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.flag.FlagItemRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.globe.GlobeRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_1.RocketItemRendererTier1;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_2.RocketItemRendererTier2;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_3.RocketItemRendererTier3;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_4.RocketItemRendererTier4;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rover.RoverItemRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.spacesuit.SpaceSuitRenderer;
import com.github.alexnijjar.ad_astra.client.resourcepack.*;
import com.github.alexnijjar.ad_astra.client.screens.PlayerOverlayScreen;
import com.github.alexnijjar.ad_astra.data.Planet;
import com.github.alexnijjar.ad_astra.networking.ModS2CPackets;
import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class AdAstraClient implements ClientModInitializer {

    public static List<Planet> planets = new ArrayList<>();
    public static List<SolarSystem> solarSystems = new ArrayList<>();
    public static List<SkyRenderer> skyRenderers = new ArrayList<>();
    public static List<PlanetRing> planetRings = new ArrayList<>();
    public static List<Galaxy> galaxies = new ArrayList<>();

    @Override
    public void onInitializeClient() {

        PlanetResources.register();
        ModS2CPackets.register();
        ClientModScreens.register();
        ClientModEntities.register();
        ClientModParticles.register();
        ClientModKeybindings.register();
        ClientModFluids.register();
        HudRenderCallback.EVENT.register(PlayerOverlayScreen::render);

        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TIER_1_ROCKET, new RocketItemRendererTier1());
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TIER_2_ROCKET, new RocketItemRendererTier2());
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TIER_3_ROCKET, new RocketItemRendererTier3());
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TIER_4_ROCKET, new RocketItemRendererTier4());
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TIER_1_ROVER, new RoverItemRenderer());

        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.AERONOS_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
            BlockEntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getBlockEntityRenderDispatcher();
            dispatcher.renderEntity(new ChestBlockEntity(BlockPos.ORIGIN, ModBlocks.AERONOS_CHEST.getDefaultState()), matrices, vertexConsumers, light, overlay);
        });

        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.STROPHAR_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
            BlockEntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getBlockEntityRenderDispatcher();
            dispatcher.renderEntity(new ChestBlockEntity(BlockPos.ORIGIN, ModBlocks.STROPHAR_CHEST.getDefaultState()), matrices, vertexConsumers, light, overlay);
        });

        BlockEntityRendererRegistry.register(ModBlockEntities.FLAG_BLOCK_ENTITY, FlagBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.GLOBE_BLOCK_ENTITY, GlobeRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.ENERGIZER, EnergizerBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.SLIDING_DOOR, SlidingDoorBlockEntityRenderer::new);

        for (Item item : new Item[]{ModItems.EARTH_GLOBE, ModItems.MOON_GLOBE, ModItems.MARS_GLOBE, ModItems.MERCURY_GLOBE, ModItems.VENUS_GLOBE, ModItems.GLACIO_GLOBE}) {
            BuiltinItemRendererRegistry.INSTANCE.register(item.asItem(), new GlobeRenderer.ItemRenderer()::render);
            ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new Identifier(AdAstra.MOD_ID, "block/" + Registry.ITEM.getId(item).getPath() + "_cube")));
        }

        for (Item item : new Item[]{ModItems.WHITE_FLAG, ModItems.BLACK_FLAG, ModItems.BLUE_FLAG, ModItems.BROWN_FLAG, ModItems.CYAN_FLAG, ModItems.GRAY_FLAG, ModItems.GREEN_FLAG, ModItems.LIGHT_BLUE_FLAG, ModItems.LIGHT_GRAY_FLAG, ModItems.LIME_FLAG,
                ModItems.MAGENTA_FLAG, ModItems.ORANGE_FLAG, ModItems.PINK_FLAG, ModItems.PURPLE_FLAG, ModItems.RED_FLAG, ModItems.YELLOW_FLAG}) {
            BuiltinItemRendererRegistry.INSTANCE.register(item, new FlagItemRenderer());
            ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModIdentifier("block/flag/" + Registry.ITEM.getId(item).getPath())));
        }

        for (Identifier id : new Identifier[]{SlidingDoorBlockEntityRenderer.IRON_SLIDING_DOOR_MODEL, SlidingDoorBlockEntityRenderer.STEEL_SLIDING_DOOR_MODEL, SlidingDoorBlockEntityRenderer.DESH_SLIDING_DOOR_MODEL,
                SlidingDoorBlockEntityRenderer.OSTRUM_SLIDING_DOOR_MODEL, SlidingDoorBlockEntityRenderer.CALORITE_SLIDING_DOOR_MODEL, SlidingDoorBlockEntityRenderer.AIRLOCK_MODEL, SlidingDoorBlockEntityRenderer.REINFORCED_DOOR_MODEL,
                SlidingDoorBlockEntityRenderer.IRON_SLIDING_DOOR_MODEL_FLIPPED, SlidingDoorBlockEntityRenderer.STEEL_SLIDING_DOOR_MODEL_FLIPPED, SlidingDoorBlockEntityRenderer.DESH_SLIDING_DOOR_MODEL_FLIPPED,
                SlidingDoorBlockEntityRenderer.OSTRUM_SLIDING_DOOR_MODEL_FLIPPED, SlidingDoorBlockEntityRenderer.CALORITE_SLIDING_MODEL_FLIPPED, SlidingDoorBlockEntityRenderer.AIRLOCK_MODEL_FLIPPED,
                SlidingDoorBlockEntityRenderer.REINFORCED_DOOR_MODEL_FLIPPED}) {
            ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(id));
        }

        SpaceSuitRenderer.register();

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.WATER_PUMP, ModBlocks.ENERGIZER, ModBlocks.STEEL_DOOR, ModBlocks.STEEL_TRAPDOOR, ModBlocks.GLACIAN_DOOR, ModBlocks.GLACIAN_TRAPDOOR, ModBlocks.AERONOS_DOOR,
                ModBlocks.AERONOS_TRAPDOOR, ModBlocks.STROPHAR_DOOR, ModBlocks.STROPHAR_TRAPDOOR, ModBlocks.COAL_TORCH, ModBlocks.WALL_COAL_TORCH, ModBlocks.VENT);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), ModBlocks.COAL_LANTERN, ModBlocks.GLACIAN_LEAVES);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.NASA_WORKBENCH, ModBlocks.AERONOS_MUSHROOM, ModBlocks.STROPHAR_MUSHROOM, ModBlocks.AERONOS_LADDER, ModBlocks.STROPHAR_LADDER, ModBlocks.AERONOS_CHEST,
                ModBlocks.STROPHAR_CHEST);

        // Sign textures
        TexturedRenderLayers.WOOD_TYPE_TEXTURES.put(ModBlocks.GLACIAN, new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, new ModIdentifier("entity/signs/" + ModBlocks.GLACIAN.getName())));
    }
}
