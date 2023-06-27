package paulevs.bhcreative.mixin;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.entity.player.StationFlatteningPlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import paulevs.bhcreative.Creative;

@Mixin(value = PlayerInventory.class, priority = 2000)
public abstract class PlayerInventoryMixin implements StationFlatteningPlayerInventory {
	@Shadow public PlayerBase player;
	
	@Shadow public int selectedHotbarSlot;
	@Shadow public abstract ItemInstance getInventoryItem(int i);
	
	@Override
	public boolean canHarvest(BlockState state) {
		if (Creative.isInCreative(player)) return false;
		if (state.isToolRequired()) {
			ItemInstance var2 = this.getInventoryItem(this.selectedHotbarSlot);
			return var2 != null && var2.isSuitableFor(state);
		}
		else return true;
	}
}
