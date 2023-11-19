package paulevs.bhcreative;

import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BHCreative {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Namespace NAMESPACE = Namespace.of("bhcreative");
	public static boolean serverIsCreative;
	
	public static final int IS_CREATIVE_ID = 17;
	public static final int IS_FLYING_ID = 18;
	
	public static Identifier id(String name) {
		return NAMESPACE.id(name);
	}
	
	public static byte toByte(boolean value) {
		return (byte) (value ? 1 : 0);
	}
	
	public static boolean toBool(byte value) {
		return value > 0;
	}
}
