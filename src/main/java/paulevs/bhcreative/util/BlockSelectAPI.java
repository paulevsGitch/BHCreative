package paulevs.bhcreative.util;

import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.modificationstation.stationapi.api.block.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BlockSelectAPI {
	private static final Map<BlockBase, Function<BlockState, ItemBase>> CONVERTERS = new HashMap<>();
	private static final Function<BlockState, ItemBase> DEFAULT = state -> state.getBlock().asItem();
	
	public static void registerConverter(BlockBase block, Function<BlockState, ItemBase> stateToItem) {
		CONVERTERS.put(block, stateToItem);
	}
	
	public static ItemBase convert(BlockState state) {
		Function<BlockState, ItemBase> converter = CONVERTERS.getOrDefault(state.getBlock(), DEFAULT);
		return converter.apply(state);
	}
}
