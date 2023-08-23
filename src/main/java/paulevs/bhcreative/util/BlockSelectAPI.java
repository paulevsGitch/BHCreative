package paulevs.bhcreative.util;

import net.minecraft.block.BaseBlock;
import net.minecraft.item.BaseItem;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.block.BlockState;

import java.util.function.Function;

@Deprecated(forRemoval = true)
public class BlockSelectAPI {
	/**
	 * Use paulevs.bhcreative.api.BlockSelectAPI instead
	 */
	@Deprecated(forRemoval = true)
	public static void registerConverter(BaseBlock block, Function<BlockState, BaseItem> stateToItem) {
		paulevs.bhcreative.api.BlockSelectAPI.registerConverter(block, state -> new ItemStack(stateToItem.apply(state)));
	}
}
