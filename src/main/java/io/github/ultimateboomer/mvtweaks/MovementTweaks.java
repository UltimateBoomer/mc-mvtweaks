package io.github.ultimateboomer.mvtweaks;

import io.github.ultimateboomer.mvtweaks.config.MovementTweaksConfig;
import io.github.ultimateboomer.mvtweaks.mixin.ClientPlayerEntityMixin;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MovementTweaks implements ModInitializer {

	public static final String MODID = "mvtweaks";
	public static final String MODNAME = "Movement Tweaks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MovementTweaks.MODID);

	private static MovementTweaks INSTANCE;

	private MovementTweaksConfig config;

	public final KeyBinding toggleSneakKey = new KeyBinding("key.mvtweaks.toggleSneak", GLFW.GLFW_KEY_X, "key.categories.mvtweaks");

	public boolean sneakEnabled = false;

	public static MovementTweaks getInstance() {
		return INSTANCE;
	}

	public MovementTweaksConfig getConfig() {
		return config;
	}

	@Override
	public void onInitialize() {
		INSTANCE = this;

		var configHolder = AutoConfig.register(MovementTweaksConfig.class, GsonConfigSerializer::new);
		this.config = configHolder.getConfig();

		KeyBindingHelper.registerKeyBinding(toggleSneakKey);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleSneakKey.wasPressed()) {
				sneakEnabled ^= true;
			}
		});

		LOGGER.info("{} initialized", MODNAME);
	}
}
