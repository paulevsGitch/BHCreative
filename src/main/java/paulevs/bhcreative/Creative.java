package paulevs.bhcreative;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.entity.player.PlayerBase;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import paulevs.bhcreative.interfaces.CreativePlayer;

import static net.modificationstation.stationapi.api.registry.Identifier.of;

public class Creative implements PreLaunchEntrypoint {
	public static final ModID MOD_ID = ModID.of("bhcreative");
	
	/**
	 * Will return creative tab {@link Identifier} based on internal {@link ModID}.
	 * @param name {@link String} name to construct ID.
	 * @return created {@link Identifier}.
	 */
	public static final Identifier id(String name) {
		return MOD_ID.id(name);
	}
	
	/**
	 * Check if player is in creative mode or not.
	 * @param player {@link PlayerBase} to check.
	 * @return {@code true} if player is in creative.
	 */
	public static boolean isInCreative(PlayerBase player) {
		return CreativePlayer.class.cast(player).creative_isCreative();
	}
	
	/**
	 * Set creative mod for specified player.
	 * @param player {@link PlayerBase} to set creative mod.
	 * @param creative {@code boolean} mode state.
	 */
	public static void setCreative(PlayerBase player, boolean creative) {
		CreativePlayer.class.cast(player).creative_setCreative(creative);
	}
	
	/**
	 * Check if player is flying or not.
	 * @param player {@link PlayerBase} to check.
	 * @return {@code true} if player is flying.
	 */
	public static boolean isFlying(PlayerBase player) {
		return CreativePlayer.class.cast(player).creative_isFlying();
	}
	
	/**
	 * Set flying mod for specified player.
	 * @param player {@link PlayerBase} to set flying.
	 * @param flying {@code boolean} state.
	 */
	public static void setFlying(PlayerBase player, boolean flying) {
		CreativePlayer.class.cast(player).creative_setFlying(flying);
	}
	
	@Override
	public void onPreLaunch() {
		setupEntrypoint(id("event_bus"));
	}
	
	private void setupEntrypoint(Identifier entrypoint) {
		FabricLoader.getInstance().getEntrypointContainers(entrypoint.toString(), Object.class).forEach(entrypointContainer -> {
			EntrypointManager.setup(entrypointContainer);
		});
	}
}
