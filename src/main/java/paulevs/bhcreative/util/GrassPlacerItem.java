package paulevs.bhcreative.util;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.BlockStateItem;
import net.modificationstation.stationapi.api.util.math.Direction;

public class GrassPlacerItem extends BlockStateItem {
	private final int meta;
	
	public GrassPlacerItem(Identifier identifier, int meta) {
		super(identifier, BlockBase.TALLGRASS.getDefaultState());
		this.meta = meta;
	}
	
	@Override
	public boolean useOnTile(ItemInstance item, PlayerBase player, Level level, int x, int y, int z, int side) {
		boolean result = super.useOnTile(item, player, level, x, y, z, side);
		if (result) {
			if (!level.getBlockState(x, y, z).isOf(BlockBase.SNOW)) {
				Direction direction = Direction.byId(side);
				x += direction.getOffsetX();
				y += direction.getOffsetY();
				z += direction.getOffsetZ();
			}
			level.setTileMeta(x, y, z, meta);
		}
		return result;
	}
}
