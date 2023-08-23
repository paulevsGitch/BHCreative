package paulevs.bhcreative.api;

import net.minecraft.block.BaseBlock;
import net.minecraft.item.BaseItem;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.block.BlockState;
import paulevs.bhcreative.listeners.CommonInitListener;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class BlockSelectAPI {
	private static final Map<BaseBlock, BiFunction<BlockState, Integer, ItemStack>> CONVERTERS = new HashMap<>();
	private static final BiFunction<BlockState, Integer, ItemStack> DEFAULT = (state, meta) -> new ItemStack(state.getBlock().asItem());
	
	public static void registerConverter(BaseBlock block, BiFunction<BlockState, Integer, ItemStack> stateToItem) {
		CONVERTERS.put(block, stateToItem);
	}
	
	public static void registerConverter(BaseBlock block, Function<BlockState, ItemStack> stateToItem) {
		CONVERTERS.put(block, (state, meta) -> stateToItem.apply(state));
	}
	
	public static void registerConverter(BaseBlock block, BaseItem convertTo) {
		registerConverter(block, state -> new ItemStack(convertTo));
	}
	
	public static void registerConverter(BaseBlock block, BaseBlock convertTo) {
		registerConverter(block, state ->new ItemStack(convertTo));
	}
	
	public static ItemStack convert(BlockState state, int meta) {
		BiFunction<BlockState, Integer, ItemStack> converter = CONVERTERS.getOrDefault(state.getBlock(), DEFAULT);
		return converter.apply(state, meta);
	}
	
	static {
		registerConverter(BaseBlock.SUGAR_CANES, BaseItem.sugarCanes);
		registerConverter(BaseBlock.WOOD_DOOR, BaseItem.woodDoor);
		registerConverter(BaseBlock.IRON_DOOR, BaseItem.ironDoor);
		registerConverter(BaseBlock.REDSTONE_TORCH, BaseBlock.REDSTONE_TORCH_LIT);
		registerConverter(BaseBlock.REDSTONE_ORE_LIT, BaseBlock.REDSTONE_ORE);
		registerConverter(BaseBlock.FURNACE_LIT, BaseBlock.FURNACE);
		registerConverter(BaseBlock.STANDING_SIGN, BaseItem.sign);
		registerConverter(BaseBlock.WALL_SIGN, BaseItem.sign);
		registerConverter(BaseBlock.DOUBLE_STONE_SLAB, (state, meta) -> {
			BaseItem item = BaseBlock.STONE_SLAB.asItem();
			return new ItemStack(item, 1, meta & 3);
		});
		registerConverter(BaseBlock.LEAVES, (state, meta) -> {
			BaseItem item = BaseBlock.LEAVES.asItem();
			return new ItemStack(item, 1, meta & 3);
		});
		registerConverter(BaseBlock.LOG, (state, meta) -> {
			BaseItem item = BaseBlock.LOG.asItem();
			return new ItemStack(item, 1, meta & 3);
		});
		registerConverter(BaseBlock.SAPLING, (state, meta) -> {
			BaseItem item = BaseBlock.SAPLING.asItem();
			return new ItemStack(item, 1, meta & 3);
		});
		registerConverter(BaseBlock.TALLGRASS, (state, meta) -> {
			BaseItem item = CommonInitListener.tallGrass;
			switch (meta & 3) {
				case 0 -> item = BaseBlock.DEADBUSH.asItem();
				case 2 -> item = CommonInitListener.fern;
			}
			return new ItemStack(item);
		});
	}
}
