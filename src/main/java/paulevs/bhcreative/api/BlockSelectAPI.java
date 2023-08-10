package paulevs.bhcreative.api;

import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.block.BlockState;
import paulevs.bhcreative.listeners.CommonInitListener;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class BlockSelectAPI {
	private static final Map<BlockBase, BiFunction<BlockState, Integer, ItemInstance>> CONVERTERS = new HashMap<>();
	private static final BiFunction<BlockState, Integer, ItemInstance> DEFAULT = (state, meta) -> new ItemInstance(state.getBlock().asItem());
	
	public static void registerConverter(BlockBase block, BiFunction<BlockState, Integer, ItemInstance> stateToItem) {
		CONVERTERS.put(block, stateToItem);
	}
	
	public static void registerConverter(BlockBase block, Function<BlockState, ItemInstance> stateToItem) {
		CONVERTERS.put(block, (state, meta) -> stateToItem.apply(state));
	}
	
	public static void registerConverter(BlockBase block, ItemBase convertTo) {
		registerConverter(block, state -> new ItemInstance(convertTo));
	}
	
	public static void registerConverter(BlockBase block, BlockBase convertTo) {
		registerConverter(block, state ->new ItemInstance(convertTo));
	}
	
	public static ItemInstance convert(BlockState state, int meta) {
		BiFunction<BlockState, Integer, ItemInstance> converter = CONVERTERS.getOrDefault(state.getBlock(), DEFAULT);
		return converter.apply(state, meta);
	}
	
	static {
		registerConverter(BlockBase.SUGAR_CANES, ItemBase.sugarCanes);
		registerConverter(BlockBase.WOOD_DOOR, ItemBase.woodDoor);
		registerConverter(BlockBase.IRON_DOOR, ItemBase.ironDoor);
		registerConverter(BlockBase.REDSTONE_TORCH, BlockBase.REDSTONE_TORCH_LIT);
		registerConverter(BlockBase.REDSTONE_ORE_LIT, BlockBase.REDSTONE_ORE);
		registerConverter(BlockBase.FURNACE_LIT, BlockBase.FURNACE);
		registerConverter(BlockBase.STANDING_SIGN, ItemBase.sign);
		registerConverter(BlockBase.WALL_SIGN, ItemBase.sign);
		registerConverter(BlockBase.DOUBLE_STONE_SLAB, (state, meta) -> {
			ItemBase item = BlockBase.STONE_SLAB.asItem();
			return new ItemInstance(item, 1, meta & 3);
		});
		registerConverter(BlockBase.LEAVES, (state, meta) -> {
			ItemBase item = BlockBase.LEAVES.asItem();
			return new ItemInstance(item, 1, meta & 3);
		});
		registerConverter(BlockBase.LOG, (state, meta) -> {
			ItemBase item = BlockBase.LOG.asItem();
			return new ItemInstance(item, 1, meta & 3);
		});
		registerConverter(BlockBase.SAPLING, (state, meta) -> {
			ItemBase item = BlockBase.SAPLING.asItem();
			return new ItemInstance(item, 1, meta & 3);
		});
		registerConverter(BlockBase.TALLGRASS, (state, meta) -> {
			ItemBase item = CommonInitListener.tallGrass;
			switch (meta & 3) {
				case 0 -> item = BlockBase.DEADBUSH.asItem();
				case 2 -> item = CommonInitListener.fern;
			}
			return new ItemInstance(item);
		});
	}
}
