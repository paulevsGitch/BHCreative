package paulevs.bhcreative.mixin;

import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.screen.menu.CreateLevel;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.resource.language.TranslationStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.bhcreative.interfaces.CreativePlayer;

@Mixin(CreateLevel.class)
public abstract class CreateLevelMixin extends ScreenBase {
	@Unique private boolean creative = false;
	
	@SuppressWarnings("unchecked")
	@Inject(method = "init", at = @At("TAIL"))
	private void creative_addButtons(CallbackInfo info) {
		Button cancelButton = (Button) this.buttons.get(1);
		this.buttons.add(1, new Button(2, this.width / 2 - 100, cancelButton.y, creative_getButtonName()));
		cancelButton.y = this.height / 4 + 144 + 12;
	}
	
	@Inject(method = "buttonClicked", at = @At("TAIL"))
	protected void creative_buttonClicked(Button button, CallbackInfo info) {
		if (button.id == 2) {
			creative = !creative;
			button.text = creative_getButtonName();
		}
		else if (button.id == 0) {
			if (minecraft.player != null) {
				((CreativePlayer) minecraft.player).creative_setCreative(creative);
			}
		}
	}
	
	@Unique
	private String creative_getButtonName() {
		TranslationStorage storage = TranslationStorage.getInstance();
		return storage.translate(creative ? "title.bhcreative:selectWorld.creative": "title.bhcreative:selectWorld.survival");
	}
}
