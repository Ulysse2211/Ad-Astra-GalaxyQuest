package com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_1;

import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.VehicleEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class RocketItemRendererTier1 implements BuiltinItemRendererRegistry.DynamicItemRenderer {

	@Override
	public void render(ItemStack stack, Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		VehicleEntityRenderer.renderRocketItem(RocketEntityRendererTier1.TEXTURE, RocketEntityModelTier1.LAYER_LOCATION, matrices, vertexConsumers, light, overlay);
	}
}