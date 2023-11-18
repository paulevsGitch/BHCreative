package paulevs.bhcreative.interfaces;

import net.modificationstation.stationapi.api.util.Util;

public interface CreativePlayer {
	default boolean creative_isCreative() {
		return Util.assertImpl();
	}
	
	default void creative_setCreative(boolean creative) {
		Util.assertImpl();
	}
	
	default boolean creative_isFlying() {
		return Util.assertImpl();
	}
	
	default void creative_setFlying(boolean flying) {
		Util.assertImpl();
	}
}
