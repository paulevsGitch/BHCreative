package paulevs.bhcreative.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.block.BlockState;
import paulevs.bhcreative.listeners.CommonInitListener;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class BlockSelectAPI {
	private static final Map<Block, BiFunction<BlockState, Integer, ItemStack>> CONVERTERS = new HashMap<>();
	private static final BiFunction<BlockState, Integer, ItemStack> DEFAULT = (state, meta) -> new ItemStack(state.getBlock().asItem());
	
	public static void registerConverter(Block block, BiFunction<BlockState, Integer, ItemStack> stateToItem) {
		CONVERTERS.put(block, stateToItem);
	}
	
	public static void registerConverter(Block block, Function<BlockState, ItemStack> stateToItem) {
		CONVERTERS.put(block, (state, meta) -> stateToItem.apply(state));
	}
	
	public static void registerConverter(Block block, Item convertTo) {
		registerConverter(block, state -> new ItemStack(convertTo));
	}
	
	public static void registerConverter(Block block, Block convertTo) {
		registerConverter(block, state ->new ItemStack(convertTo));
	}
	
	public static ItemStack convert(BlockState state, int meta) {
		BiFunction<BlockState, Integer, ItemStack> converter = CONVERTERS.getOrDefault(state.getBlock(), DEFAULT);
		return converter.apply(state, meta);
	}
	
	static {
		registerConverter(Block.SUGAR_CANES, Item.sugarCanes);
		registerConverter(Block.WOOD_DOOR, Item.woodDoor);
		registerConverter(Block.IRON_DOOR, Item.ironDoor);
		registerConverter(Block.REDSTONE_TORCH, Block.REDSTONE_TORCH_LIT);
		registerConverter(Block.REDSTONE_ORE_LIT, Block.REDSTONE_ORE);
		registerConverter(Block.FURNACE_LIT, Block.FURNACE);
		registerConverter(Block.STANDING_SIGN, Item.sign);
		registerConverter(Block.WALL_SIGN, Item.sign);
		registerConverter(Block.DOUBLE_STONE_SLAB, (state, meta) -> {
			Item item = Block.STONE_SLAB.asItem();
			return new ItemStack(item, 1, meta & 3);
		});
		registerConverter(Block.LEAVES, (state, meta) -> {
			Item item = Block.LEAVES.asItem();
			return new ItemStack(item, 1, meta & 3);
		});
		registerConverter(Block.LOG, (state, meta) -> {
			Item item = Block.LOG.asItem();
			return new ItemStack(item, 1, meta & 3);
		});
		registerConverter(Block.SAPLING, (state, meta) -> {
			Item item = Block.SAPLING.asItem();
			return new ItemStack(item, 1, meta & 3);
		});
		registerConverter(Block.TALLGRASS, (state, meta) -> {
			Item item = CommonInitListener.tallGrass;
			switch (meta & 3) {
				case 0 -> item = Block.DEADBUSH.asItem();
				case 2 -> item = CommonInitListener.fern;
			}
			return new ItemStack(item);
		});
		registerConverter(Block.WOOL, (state, meta) -> {
			Item item = Block.WOOL.asItem();
			return new ItemStack(item, 1, meta);
		});
	}
}
