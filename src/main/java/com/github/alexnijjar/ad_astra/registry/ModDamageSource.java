package com.github.alexnijjar.ad_astra.registry;

import net.minecraft.entity.damage.DamageSource;

public class ModDamageSource extends DamageSource {

	// Custom death sources
	public static final DamageSource OXYGEN = new ModDamageSource("oxygen").setBypassesArmor();
	public static final DamageSource ROCKET_FLAMES = new ModDamageSource("rocket_flames").setFire();
	public static final DamageSource CRYO_FUEL = new ModDamageSource("cryo_fuel");
	public static final DamageSource ACID_RAIN = new ModDamageSource("acid_rain");

	public ModDamageSource(String name) {
		super(name);
	}
}
