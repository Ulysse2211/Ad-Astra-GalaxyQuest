package com.github.alexnijjar.ad_astra.client.registry;

import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.*;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.*;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.lander.LanderEntityModel;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.lander.LanderEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_1.RocketEntityModelTier1;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_1.RocketEntityRendererTier1;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_2.RocketEntityModelTier2;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_2.RocketEntityRendererTier2;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_3.RocketEntityModelTier3;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_3.RocketEntityRendererTier3;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_4.RocketEntityModelTier4;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_4.RocketEntityRendererTier4;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rover.RoverEntityModel;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rover.RoverEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.spacesuit.JetSuitModel;
import com.github.alexnijjar.ad_astra.client.renderer.spacesuit.NetheriteSpaceSuitModel;
import com.github.alexnijjar.ad_astra.client.renderer.spacesuit.SpaceSuitModel;
import com.github.alexnijjar.ad_astra.registry.ModEntityTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.PaintingEntityRenderer;

@Environment(EnvType.CLIENT)
public class ClientModEntities {

	public static void register() {

		// Mobs
		EntityRendererRegistry.register(ModEntityTypes.LUNARIAN, LunarianEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.CORRUPTED_LUNARIAN, CorruptedLunarianEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.STAR_CRAWLER, StarCrawlerEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.MARTIAN_RAPTOR, MartianRaptorEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.PYGRO, PygroEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.ZOMBIFIED_PYGRO, ZombifiedPygroEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.PYGRO_BRUTE, PygroBruteEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.MOGLER, MoglerEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.ZOMBIFIED_MOGLER, ZombifiedMoglerEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.SULFUR_CREEPER, SulfurCreeperEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.LUNARIAN_WANDERING_TRADER, LunarianWanderingTraderEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.GLACIAN_RAM, GlacianRamEntityRenderer::new);

		// Machines
		EntityRendererRegistry.register(ModEntityTypes.ROCKET_TIER_1, RocketEntityRendererTier1::new);
		EntityRendererRegistry.register(ModEntityTypes.ROCKET_TIER_2, RocketEntityRendererTier2::new);
		EntityRendererRegistry.register(ModEntityTypes.ROCKET_TIER_3, RocketEntityRendererTier3::new);
		EntityRendererRegistry.register(ModEntityTypes.ROCKET_TIER_4, RocketEntityRendererTier4::new);
		EntityRendererRegistry.register(ModEntityTypes.ROVER_TIER_1, RoverEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.LANDER, LanderEntityRenderer::new);

		// Painting
		EntityRendererRegistry.register(ModEntityTypes.SPACE_PAINTING, PaintingEntityRenderer::new);

		// Projectiles
		EntityRendererRegistry.register(ModEntityTypes.ICE_SPIT_ENTITY, FlyingItemEntityRenderer::new);

		// Layers
		EntityModelLayerRegistry.registerModelLayer(LunarianEntityModel.LAYER_LOCATION, LunarianEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(CorruptedLunarianEntityModel.LAYER_LOCATION, CorruptedLunarianEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(StarCrawlerEntityModel.LAYER_LOCATION, StarCrawlerEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(MartianRaptorEntityModel.LAYER_LOCATION, MartianRaptorEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(PygroEntityModel.LAYER_LOCATION, PygroEntityModel::getModelData);
		EntityModelLayerRegistry.registerModelLayer(ZombifiedPygroEntityModel.LAYER_LOCATION, ZombifiedPygroEntityModel::getModelData);
		EntityModelLayerRegistry.registerModelLayer(PygroBruteEntityModel.LAYER_LOCATION, PygroBruteEntityModel::getModelData);
		EntityModelLayerRegistry.registerModelLayer(MoglerEntityModel.LAYER_LOCATION, MoglerEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(SulfurCreeperEntityModel.LAYER_LOCATION, SulfurCreeperEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(GlacianRamEntityModel.LAYER_LOCATION, GlacianRamEntityModel::getTexturedModelData);

		// Machine Layers
		EntityModelLayerRegistry.registerModelLayer(RocketEntityModelTier1.LAYER_LOCATION, RocketEntityModelTier1::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(RocketEntityModelTier2.LAYER_LOCATION, RocketEntityModelTier2::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(RocketEntityModelTier3.LAYER_LOCATION, RocketEntityModelTier3::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(RocketEntityModelTier4.LAYER_LOCATION, RocketEntityModelTier4::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(RoverEntityModel.LAYER_LOCATION, RoverEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(LanderEntityModel.LAYER_LOCATION, LanderEntityModel::getTexturedModelData);

		// Armour Layers
		EntityModelLayerRegistry.registerModelLayer(SpaceSuitModel.LAYER_LOCATION, SpaceSuitModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(NetheriteSpaceSuitModel.LAYER_LOCATION, NetheriteSpaceSuitModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(JetSuitModel.LAYER_LOCATION, JetSuitModel::getTexturedModelData);
	}
}