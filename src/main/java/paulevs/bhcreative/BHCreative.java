package paulevs.bhcreative;

import net.minecraft.entity.living.player.PlayerEntity;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import paulevs.bhcreative.interfaces.CreativePlayer;

public class BHCreative {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Namespace NAMESPACE = Namespace.of("bhcreative");
	
	/**
	 * Will return creative tab {@link Identifier} based on internal ModID.
	 * @param name {@link String} name to construct ID.
	 * @return created {@link Identifier}.
	 */
	public static Identifier id(String name) {
		return NAMESPACE.id(name);
	}
	
	/**
	 * Check if player is in creative mode or not.
	 * @param player {@link PlayerEntity} to check.
	 * @return {@code true} if player is in creative.
	 */
	public static boolean isInCreative(PlayerEntity player) {
		return CreativePlayer.cast(player).creative_isCreative();
	}
	
	/**
	 * Set creative mod for specified player.
	 * @param player {@link PlayerEntity} to set creative mod.
	 * @param creative {@code boolean} mode state.
	 */
	public static void setCreative(PlayerEntity player, boolean creative) {
		CreativePlayer.cast(player).creative_setCreative(creative);
	}
	
	/**
	 * Check if player is flying or not.
	 * @param player {@link PlayerEntity} to check.
	 * @return {@code true} if player is flying.
	 */
	public static boolean isFlying(PlayerEntity player) {
		return CreativePlayer.cast(player).creative_isFlying();
	}
	
	/**
	 * Set flying mod for specified player.
	 * @param player {@link PlayerEntity} to set flying.
	 * @param flying {@code boolean} state.
	 */
	public static void setFlying(PlayerEntity player, boolean flying) {
		CreativePlayer.cast(player).creative_setFlying(flying);
	}
}
