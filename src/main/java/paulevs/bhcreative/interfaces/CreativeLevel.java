package paulevs.bhcreative.interfaces;

import net.modificationstation.stationapi.api.util.Util;

public interface CreativeLevel {
	default boolean creative_isCreative() {
		return Util.assertImpl();
	}
	
	default void creative_setCreative(boolean creative) {
		Util.assertImpl();
	}
}
