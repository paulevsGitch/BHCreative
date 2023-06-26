package paulevs.bhcreative.interfaces;

import net.minecraft.entity.player.PlayerBase;

public interface CreativePlayer {
	boolean creative_isCreative();
	void creative_setCreative(boolean creative);
	boolean creative_isFlying();
	void creative_setFlying(boolean flying);
	
	public static CreativePlayer cast(PlayerBase player) {
		return (CreativePlayer) player;
	}
}
