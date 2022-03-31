package paulevs.bhcreative.mixin;

import net.minecraft.client.Minecraft;
import net.modificationstation.stationapi.api.StationAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.bhcreative.util.ColorHelper;
import paulevs.bhcreative.api.CreativeTabs;
import paulevs.bhcreative.registry.TabRegistry;
import paulevs.bhcreative.registry.TabRegistryEvent;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(method = "init", at = @At("TAIL"))
	public void creative_init(CallbackInfo info) {
		CreativeTabs.initVanilla();
		StationAPI.EVENT_BUS.post(new TabRegistryEvent(TabRegistry.INSTANCE::register));
		CreativeTabs.initTabs();
		ColorHelper.init();
	}
}
