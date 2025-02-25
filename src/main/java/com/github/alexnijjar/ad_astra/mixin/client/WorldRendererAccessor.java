package com.github.alexnijjar.ad_astra.mixin.client;

import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WorldRenderer.class)
public interface WorldRendererAccessor {

	@Accessor("lightSkyBuffer")
	VertexBuffer getLightSkyBuffer();

	@Accessor("darkSkyBuffer")
	VertexBuffer getDarkSkyBuffer();

	@Accessor("cloudsDirty")
	boolean getCloudsDirty();

	@Accessor("cloudsDirty")
	void setCloudsDirty(boolean value);

	@Accessor("cloudsBuffer")
	VertexBuffer getCloudsBuffer();

	@Accessor("cloudsBuffer")
	void setCloudsBuffer(VertexBuffer value);

	@Accessor("lastCloudsRenderMode")
	CloudRenderMode getLastCloudsRenderMode();

	@Accessor("lastCloudsRenderMode")
	void setLastCloudsRenderMode(CloudRenderMode value);

	@Accessor("lastCloudsColor")
	Vec3d getLastCloudsColor();

	@Accessor("lastCloudsColor")
	void setLastCloudsColor(Vec3d value);

	@Accessor("ticks")
	int getTicks();

	@Accessor("lastCloudsBlockX")
	int getLastCloudsBlockX();

	@Accessor("lastCloudsBlockX")
	void setLastCloudsBlockX(int value);

	@Accessor("lastCloudsBlockY")
	int getLastCloudsBlockY();

	@Accessor("lastCloudsBlockY")
	void setLastCloudsBlockY(int value);

	@Accessor("lastCloudsBlockZ")
	int getLastCloudsBlockZ();

	@Accessor("lastCloudsBlockZ")
	void setLastCloudsBlockZ(int value);

	@Accessor("field_20794")
	float[] getField_20794();

	@Accessor("field_20795")
	float[] getField_20795();

	@Accessor("field_20793")
	int getField_20793();

	@Accessor("field_20793")
	void setField_20793(int value);

	@Invoker("renderClouds")
	void invokeRenderClouds(BufferBuilder builder, double x, double y, double z, Vec3d color);
}