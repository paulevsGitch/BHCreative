package paulevs.bhcreative.util;

import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.block.BlockState;

import java.util.function.Function;

@Deprecated(forRemoval = true)
public class BlockSelectAPI {
	/**
	 * Use paulevs.bhcreative.api.BlockSelectAPI instead
	 */
	@Deprecated(forRemoval = true)
	public static void registerConverter(BlockBase block, Function<BlockState, ItemBase> stateToItem) {
		paulevs.bhcreative.api.BlockSelectAPI.registerConverter(block, state -> new ItemInstance(stateToItem.apply(state)));
	}
}
