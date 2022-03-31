package paulevs.bhcreative;

import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;

public class CreativeMod {
	public static final ModID MOD_ID = ModID.of("bhcreative");
	
	public static final Identifier id(String name) {
		return MOD_ID.id(name);
	}
}
