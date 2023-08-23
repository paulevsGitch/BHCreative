package paulevs.bhcreative.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.BaseItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin extends BaseItem {
	public BucketItemMixin(int id) {
		super(id);
	}
	
	@ModifyReturnValue(method = "use", at = @At("RETURN"))
	private ItemStack bhcreative_useBucket(ItemStack original) {
		original.itemId = this.id;
		return original;
	}
}
