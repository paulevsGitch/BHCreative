package paulevs.bhcreative.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.TranslationStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class CreateLevelScreenMixin extends Screen {
	@Unique private static final String CREATIVE_KEY_SURVIVAL = "title.bhcreative.selectWorld.survival";
	@Unique private static final String CREATIVE_KEY_CREATIVE = "title.bhcreative.selectWorld.creative";
	@Unique private boolean creative = false;
	
	@SuppressWarnings("unchecked")
	@Inject(method = "init", at = @At("TAIL"))
	private void creative_addButtons(CallbackInfo info) {
		ButtonWidget cancelButton = (ButtonWidget) this.buttons.get(1);
		this.buttons.add(1, new ButtonWidget(2, this.width / 2 - 100, cancelButton.y, creative_getButtonName()));
		cancelButton.y = this.height / 4 + 144 + 12;
	}
	
	@Inject(method = "buttonClicked", at = @At("TAIL"))
	protected void creative_buttonClicked(ButtonWidget button, CallbackInfo info) {
		if (button.id == 2) {
			creative = !creative;
			button.text = creative_getButtonName();
		}
		else if (button.id == 0) {
			if (minecraft.player != null) {
				minecraft.player.creative_setCreative(creative);
			}
		}
	}
	
	@Unique
	private String creative_getButtonName() {
		TranslationStorage storage = TranslationStorage.getInstance();
		return storage.get(creative ? CREATIVE_KEY_CREATIVE : CREATIVE_KEY_SURVIVAL);
	}
}
