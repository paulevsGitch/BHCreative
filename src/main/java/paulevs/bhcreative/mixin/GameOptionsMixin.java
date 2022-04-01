package paulevs.bhcreative.mixin;

import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.resource.language.TranslationStorage;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.interfaces.FlyOption;

@Mixin(GameOptions.class)
public class GameOptionsMixin implements FlyOption {
	@Unique private KeyBinding creative_flyKey = new KeyBinding("key.fly", Keyboard.KEY_G);
	@Unique private static final String KEY_CREATIVE_FLY = "key.bhcreative:fly";
	@Unique private int creative_flyKeyIndex;
	
	@Shadow public KeyBinding[] keyBindings;
	
	@Inject(method = "load", at = @At("HEAD"))
	private void creative_onGameOptionsLoad(CallbackInfo info) {
		KeyBinding[] oldBindings = keyBindings;
		keyBindings = new KeyBinding[oldBindings.length + 1];
		System.arraycopy(oldBindings, 0, keyBindings, 0, oldBindings.length);
		creative_flyKeyIndex = keyBindings.length - 1;
		keyBindings[creative_flyKeyIndex] = creative_flyKey;
	}

	@Override
	public KeyBinding creative_getFlyKey() {
		return creative_flyKey;
	}
	
	@Inject(method = "getKeybindName", at = @At("HEAD"), cancellable = true)
	private void creative_getKeybindName(int index, CallbackInfoReturnable<String> info) {
		if (index == creative_flyKeyIndex) {
			TranslationStorage storage = TranslationStorage.getInstance();
			info.setReturnValue(storage.translate(KEY_CREATIVE_FLY));
			info.cancel();
		}
	}
}
