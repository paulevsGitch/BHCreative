package paulevs.bhcreative.util;

import net.minecraft.block.Block;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.item.BlockStateItem;
import net.modificationstation.stationapi.api.util.math.Direction;

public class GrassPlacerItem extends BlockStateItem {
	private final int meta;
	
	public GrassPlacerItem(Identifier identifier, int meta) {
		super(identifier, Block.TALLGRASS.getDefaultState());
		setTranslationKey(identifier.toString());
		this.meta = meta;
	}
	
	@Override
	public boolean useOnBlock(ItemStack item, PlayerEntity player, Level level, int x, int y, int z, int side) {
		boolean result = super.useOnBlock(item, player, level, x, y, z, side);
		if (result) {
			if (!level.getBlockState(x, y, z).isOf(Block.SNOW)) {
				Direction direction = Direction.byId(side);
				x += direction.getOffsetX();
				y += direction.getOffsetY();
				z += direction.getOffsetZ();
			}
			level.setBlockMeta(x, y, z, meta);
		}
		return result;
	}
}
