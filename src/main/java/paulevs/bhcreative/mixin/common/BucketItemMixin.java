package paulevs.bhcreative.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin extends Item {
	public BucketItemMixin(int id) {
		super(id);
	}
	
	@ModifyReturnValue(method = "use", at = @At("RETURN"))
	private ItemStack bhcreative_useBucket(ItemStack original, @Local PlayerEntity player) {
		if (!player.creative_isCreative()) return original;
		original.itemId = this.id;
		return original;
	}
}
