package paulevs.bhcreative.mixin.common;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.inventory.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.entity.player.StationFlatteningPlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = PlayerInventory.class, priority = 2000)
public abstract class PlayerInventoryMixin implements StationFlatteningPlayerInventory {
	@Shadow public PlayerEntity player;
	
	@Shadow public int selectedHotbarSlot;
	@Shadow public abstract ItemStack getItem(int i);
	
	@Override
	public boolean canHarvest(BlockState state) {
		if (player.creative_isCreative()) return false;
		if (state.isToolRequired()) {
			ItemStack var2 = this.getItem(this.selectedHotbarSlot);
			return var2 != null && var2.isSuitableFor(state);
		}
		else return true;
	}
}
