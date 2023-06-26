package paulevs.bhcreative.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.client.gui.screen.container.ContainerBase;
import net.minecraft.client.gui.screen.container.PlayerInventory;
import net.minecraft.client.render.RenderHelper;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.maths.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.bhcreative.api.CreativeTab;
import paulevs.bhcreative.interfaces.CreativePlayer;
import paulevs.bhcreative.registry.TabRegistry;
import paulevs.bhcreative.util.MHelper;

import java.util.List;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin extends ContainerBase {
	@Unique private static final int CREATIVE_COLOR_FILLER = MHelper.getColor(198, 198, 198, 128);
	@Unique private static final String KEY_CREATIVE = "title.selectGame.creative";
	@Unique private static final String KEY_INVENTORY = "title.selectGame.inventory";
	
	@Unique private static final ItemRenderer creative_itemRenderer = new ItemRenderer();
	@Unique private List<ItemInstance> creative_items;
	@Unique private boolean creative_normalGUI;
	@Unique private int creative_mouseDelta;
	@Unique private String creative_tabKey;
	@Unique private int creative_rowIndex;
	@Unique private int creative_maxIndex;
	@Unique private float creative_slider;
	@Unique private boolean creative_drag;
	
	@Unique private int creative_maxTabIndex;
	@Unique private int creative_pagesCount;
	@Unique private int creative_tabIndex;
	@Unique private int creative_tabPage;
	
	@Unique private ItemInstance creative_creativeIcon;
	@Unique private ItemInstance creative_survivalIcon;
	
	public PlayerInventoryMixin(net.minecraft.container.ContainerBase container) {
		super(container);
	}
	
	@Shadow private float mouseX;
	@Shadow private float mouseY;
	
	@Inject(method = "<init>(Lnet/minecraft/entity/player/PlayerBase;)V", at = @At("TAIL"))
	private void creative_initPlayerInventory(PlayerBase player, CallbackInfo info) {
		creative_creativeIcon = new ItemInstance(ItemBase.diamond);
		creative_survivalIcon = new ItemInstance(BlockBase.WORKBENCH);
		CreativeTab tab = TabRegistry.getTabByIndex(0);
		creative_tabKey = tab.getTranslationKey();
		creative_items = tab.getItems();
		creative_maxIndex = creative_getMaxIndex();
		creative_rowIndex = 0;
		creative_tabIndex = 0;
		creative_tabPage = 0;
		creative_updateMaxIndex();
		creative_pagesCount = (int) Math.ceil(TabRegistry.getTabsCount() / 7.0F);
	}
	
	@Inject(method = "renderContainerBackground(F)V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glDisable(I)V", shift = Shift.AFTER))
	private void creative_renderBackgroundEnd(float f, CallbackInfo info) {
		if (!(creative_isInCreative() && creative_normalGUI)) {
			return;
		}
		
		int texture = this.minecraft.textureManager.getTextureId("/assets/bhcreative/textures/gui/creative_list.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.textureManager.bindTexture(texture);
		
		int posX = (this.width - this.containerWidth) / 2;
		int posY = (this.height - this.containerHeight) / 2;
		this.blit(posX + 173, posY + 138, 176, 32, 25, 24);
		
		GL11.glPushMatrix();
		GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
		RenderHelper.enableLighting();
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(32826);
		
		creative_renderItem(creative_creativeIcon, posX + 173 + 4, posY + 114 + 4);
		creative_renderItem(creative_survivalIcon, posX + 173 + 4, posY + 138 + 4);
		
		RenderHelper.disableLighting();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		int tabX = (int) mouseX - posX - 173;
		int tabY = (int) mouseY - posY - 114;
		if (tabX >= 0 && tabX < 25 && tabY >= 0 && tabY < 24) {
			String translated = TranslationStorage.getInstance().method_995(KEY_CREATIVE);
			creative_renderString(translated);
		}
		
		tabY = (int) mouseY - posY - 138;
		if (tabX >= 0 && tabX < 25 && tabY >= 0 && tabY < 24) {
			String translated = TranslationStorage.getInstance().method_995(KEY_INVENTORY);
			creative_renderString(translated);
		}
	}
	
	@Inject(method = "renderContainerBackground", at = @At("HEAD"), cancellable = true)
	private void creative_renderBackgroundStart(float f, CallbackInfo info) {
		if (!creative_isInCreative()) {
			return;
		}
		
		int posX = (this.width - this.containerWidth) / 2;
		int posY = (this.height - this.containerHeight) / 2;
		
		int texture = this.minecraft.textureManager.getTextureId("/assets/bhcreative/textures/gui/creative_list.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.textureManager.bindTexture(texture);
		
		if (creative_normalGUI) {
			this.blit(posX + 173, posY + 114, 176, 32, 25, 24); // Survival
		}
		else {
			net.minecraft.entity.player.PlayerInventory inventory = this.minecraft.player.inventory;
			
			for (int i = 0; i < creative_maxTabIndex; i++) {
				if (i != creative_tabIndex) {
					this.blit(posX + 4 + i * 24, posY - 21, 176, 0, 24, 24);
				}
			}
			
			this.blit(posX + 173, posY + 138, 176, 32, 25, 24);
			this.blit(posX, posY, 0, 0, this.containerWidth, this.containerHeight);
			this.blit(posX + 173, posY + 114, 176, 32, 25, 24);
			
			this.blit(posX + 150, posY + 4, 208, 0, 9, 8);
			if (creative_tabPage == 0) {
				this.fill(posX + 150, posY + 4, posX + 150 + 9, posY + 4 + 8, CREATIVE_COLOR_FILLER);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
			this.blit(posX + 160, posY + 4, 208, 8, 9, 8);
			if (creative_tabPage >= creative_pagesCount - 1) {
				this.fill(posX + 160, posY + 4, posX + 160 + 9, posY + 4 + 8, CREATIVE_COLOR_FILLER);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
			
			int sliderX = posX + 154;
			int sliderY = posY + 14 + MathHelper.floor(creative_slider * 109);
			this.blit(sliderX, sliderY, 240, 1, 14, 15);
			
			this.blit(posX + 4 + creative_tabIndex * 24, posY - 21, 176, 0, 24, 24);
			
			GL11.glPushMatrix();
			GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
			RenderHelper.enableLighting();
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(32826);
			
			creative_renderItem(creative_creativeIcon, posX + 173 + 4, posY + 114 + 4);
			creative_renderItem(creative_survivalIcon, posX + 173 + 4, posY + 138 + 4);
			
			for (int i = 0; i < creative_maxTabIndex; i++) {
				CreativeTab tab = creative_getTab(creative_tabPage, i);
				creative_renderItem(tab.getIcon(), posX + 8 + i * 24, posY - 17);
			}
			
			for (int i = 0; i < 56; i++) {
				int index = creative_rowIndex + i;
				if (index >= 0 && index < creative_items.size()) {
					ItemInstance instance = creative_items.get(index);
					int x = posX + (i & 7) * 18 + 8;
					int y = posY + (i / 8) * 18 + 14;
					creative_renderItem(instance, x, y);
				}
			}
			
			for (int i = 0; i < 9; i++) {
				ItemInstance item = inventory.main[i];
				int x = posX + i * 18 + 8;
				int y = posY + 142;
				creative_renderItem(item, x, y);
			}
			
			RenderHelper.disableLighting();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			String translated = TranslationStorage.getInstance().method_995(creative_tabKey);
			this.textManager.drawText(translated, posX + 8, posY + 5, 0x373737);
			
			int slotX = MathHelper.floor((mouseX - posX - 8) / 18);
			if (slotX >= 0) {
				int slotY = MathHelper.floor((mouseY - posY - 14) / 18);
				if (slotX < 8 && slotY >= 0 && slotY < 7) {
					int x = slotX * 18 + posX + 8;
					int y = slotY * 18 + posY + 14;
					creative_renderSlotOverlay(x, y);
					
					int index = slotY * 8 + slotX + creative_rowIndex;
					ItemInstance item = index < creative_items.size() ? creative_items.get(index) : null;
					RenderHelper.disableLighting();
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					creative_renderName(item);
				}
				slotY = MathHelper.floor((mouseY - posY - 142) / 18);
				if (slotX < 9 && slotY == 0) {
					int x = slotX * 18 + posX + 8;
					int y = slotY * 18 + posY + 142;
					creative_renderSlotOverlay(x, y);
					RenderHelper.disableLighting();
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					creative_renderName(inventory.main[slotX]);
				}
			}
			
			int tabX = ((int) mouseX - posX - 4) / 24;
			int tabY = (int) mouseY - posY + 21;
			if (tabX >= 0 && tabX < creative_maxTabIndex && tabY >= 0 && tabY < 24) {
				CreativeTab tab = creative_getTab(creative_tabPage, tabX);
				translated = TranslationStorage.getInstance().method_995(tab.getTranslationKey());
				creative_renderString(translated);
			}
			
			tabX = (int) mouseX - posX - 173;
			tabY = (int) mouseY - posY - 114;
			if (tabX >= 0 && tabX < 25 && tabY >= 0 && tabY < 24) {
				translated = TranslationStorage.getInstance().method_995(KEY_CREATIVE);
				creative_renderString(translated);
			}
			
			tabY = (int) mouseY - posY - 138;
			if (tabX >= 0 && tabX < 25 && tabY >= 0 && tabY < 24) {
				translated = TranslationStorage.getInstance().method_995(KEY_INVENTORY);
				creative_renderString(translated);
			}
			
			info.cancel();
		}
	}
	
	@Unique
	private void creative_renderName(ItemInstance item) {
		if (item == null) {
			return;
		}
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		String key = item.getTranslationKey();
		String translated = TranslationStorage.getInstance().method_995(key);
		creative_renderString(translated.isEmpty() ? key : translated);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	@Unique
	private void creative_renderString(String string) {
		if (string == null) {
			return;
		}
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		if (string.length() > 0) {
			int var9 = (int) mouseX + 12;
			int var10 = (int) mouseY - 12;
			int var11 = this.textManager.getTextWidth(string);
			this.fillGradient(var9 - 3, var10 - 3, var9 + var11 + 3, var10 + 8 + 3, -1073741824, -1073741824);
			this.textManager.drawTextWithShadow(string, var9, var10, -1);
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	@Inject(method = "renderForeground", at = @At("HEAD"), cancellable = true)
	private void creative_renderForeground(CallbackInfo info) {
		if (creative_isInCreative() && !creative_normalGUI) {
			info.cancel();
		}
	}
	
	@Unique
	private boolean creative_isInCreative() {
		return ((CreativePlayer) minecraft.player).creative_isCreative();
	}
	
	@Unique
	private void creative_renderItem(ItemInstance instance, int x, int y) {
		if (instance == null) {
			return;
		}
		creative_itemRenderer.method_1488(this.textManager, this.minecraft.textureManager, instance, x, y);
		creative_itemRenderer.method_1487(this.textManager, this.minecraft.textureManager, instance, x, y);
	}
	
	@Unique
	private void creative_renderSlotOverlay(int x, int y) {
		GL11.glDisable(2896);
		GL11.glDisable(2929);
		this.fillGradient(x, y, x + 16, y + 16, -2130706433, -2130706433);
		GL11.glEnable(2896);
		GL11.glEnable(2929);
	}
	
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void creative_render(int mouseX, int mouseY, float delta, CallbackInfo info) {
		if (creative_isInCreative() && !creative_normalGUI) {
			creative_mouseScroll();
			
			this.renderBackground();
			int posX = (this.width - this.containerWidth) / 2;
			int posY = (this.height - this.containerHeight) / 2;
			this.renderContainerBackground(delta);
			
			GL11.glPushMatrix();
			GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
			RenderHelper.enableLighting();
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
			GL11.glTranslatef((float)posX, (float)posY, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(32826);
	
			net.minecraft.entity.player.PlayerInventory inventory = this.minecraft.player.inventory;
			if (inventory.getCursorItem() != null) {
				GL11.glTranslatef(0.0F, 0.0F, 32.0F);
				creative_itemRenderer.method_1488(this.textManager, this.minecraft.textureManager, inventory.getCursorItem(), mouseX - posX - 8, mouseY - posY - 8);
				creative_itemRenderer.method_1487(this.textManager, this.minecraft.textureManager, inventory.getCursorItem(), mouseX - posX - 8, mouseY - posY - 8);
			}
	
			GL11.glDisable(32826);
			RenderHelper.disableLighting();
			GL11.glDisable(2896);
			GL11.glDisable(2929);
			this.renderForeground();
	
			GL11.glPopMatrix();
			GL11.glEnable(2896);
			GL11.glEnable(2929);
			
			this.mouseX = (float) mouseX;
			this.mouseY = (float) mouseY;
			info.cancel();
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		if (creative_isInCreative()) {
			int posX = (this.width - this.containerWidth) / 2;
			int posY = (this.height - this.containerHeight) / 2;
			
			int tabX = (int) mouseX - posX - 173;
			int tabY = (int) mouseY - posY - 114;
			if (tabX >= 0 && tabX < 25 && tabY >= 0 && tabY < 24) {
				creative_normalGUI = false;
				creative_playSound();
			}
			
			tabY = (int) mouseY - posY - 138;
			if (tabX >= 0 && tabX < 25 && tabY >= 0 && tabY < 24) {
				creative_normalGUI = true;
				creative_playSound();
			}
			
			if (creative_normalGUI) {
				super.mouseClicked(mouseX, mouseY, button);
				return;
			}
			
			tabX = ((int) mouseX - posX - 4) / 24;
			tabY = (int) mouseY - posY + 21;
			if (tabX >= 0 && tabX < 7 && tabY >= 0 && tabY < 24) {
				creative_tabIndex = tabX;
				
				CreativeTab tab = creative_getTab(creative_tabPage, creative_tabIndex);
				creative_tabKey = tab.getTranslationKey();
				creative_items = tab.getItems();
				creative_maxIndex = creative_getMaxIndex();
				creative_rowIndex = 0;
				creative_slider = 0F;
				
				creative_playSound();
				
				return;
			}
			
			int buttonY = (int) mouseY - posY - 4;
			if (buttonY > 0 && buttonY < 8) {
				int buttonX = (int) mouseX - posX - 150;
				if (creative_tabPage > 0 && buttonX >= 0 && buttonX < 9) {
					creative_tabIndex = 0;
					creative_tabPage--;
					creative_updateMaxIndex();
					
					creative_playSound();
					CreativeTab tab = creative_getTab(creative_tabPage, creative_tabIndex);
					creative_rowIndex = 0;
					creative_slider = 0F;
					if (tab == null) {
						return;
					}
					creative_tabKey = tab.getTranslationKey();
					creative_items = tab.getItems();
					creative_maxIndex = creative_getMaxIndex();
					
					return;
				}
				
				buttonX = (int) mouseX - posX - 160;
				if ((creative_tabPage < (creative_pagesCount - 1)) && buttonX >= 0 && buttonX < 9) {
					creative_tabIndex = 0;
					creative_tabPage++;
					creative_updateMaxIndex();
					
					creative_playSound();
					CreativeTab tab = creative_getTab(creative_tabPage, creative_tabIndex);
					creative_rowIndex = 0;
					creative_slider = 0F;
					if (tab == null) {
						return;
					}
					creative_tabKey = tab.getTranslationKey();
					creative_items = tab.getItems();
					creative_maxIndex = creative_getMaxIndex();
					
					return;
				}
			}
			
			int sliderX = (int) mouseX - posX - 154;
			int sliderY = (int) mouseY - posY - 14 - MathHelper.floor(creative_slider * 109);
			if (sliderX > 0 && sliderX < 14 && sliderY > 0 && sliderY < 15) {
				creative_mouseDelta = posY + 14 + sliderY;
				creative_drag = true;
				return;
			}
			
			int slotX = MathHelper.floor((mouseX - posX - 8) / 18F);
			int slotY = MathHelper.floor((mouseY - posY - 14) / 18F);
			
			net.minecraft.entity.player.PlayerInventory inventory = this.minecraft.player.inventory;
			if (slotY >= 0 && slotY < 7 && slotX >= 0 && slotX < 8) {
				int index = slotY * 8 + slotX + creative_rowIndex;
				if (index < creative_items.size()) {
					ItemInstance item = creative_items.get(index);
					boolean isSame = inventory.getCursorItem() != null && inventory.getCursorItem().isDamageAndIDIdentical(item);
					if (inventory.getCursorItem() == null || isSame) {
						if (item != null) {
							if (isSame) {
								inventory.getCursorItem().count++;
							}
							else {
								inventory.setCursorItem(item.copy());
							}
							if (button == 2) {
								inventory.getCursorItem().count = inventory.getCursorItem().getMaxStackSize();
							}
							return;
						}
					}
				}
				if (button == 1 && inventory.getCursorItem().count > 1) {
					inventory.getCursorItem().count--;
				}
				else {
					inventory.setCursorItem(null);
				}
				return;
			}
			
			slotY = MathHelper.floor((mouseY - posY - 142) / 18);
			if (slotY == 0 && slotX >= 0 && slotX < 9) {
				if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					inventory.main[slotX] = null;
				}
				super.mouseClicked(mouseX, mouseY, button);
			}
		}
		else {
			super.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int button) {
		super.mouseReleased(mouseX, mouseY, button);
		if (button > -1) {
			creative_drag = false;
		}
	}
	
	@Unique
	private void creative_mouseScroll() {
		if (creative_items.size() < 56) {
			return;
		}
		if (creative_drag) {
			int mousePos = (int) mouseY - creative_mouseDelta;
			creative_slider = (float) mousePos / 109;
			creative_slider = creative_slider < 0 ? 0 : creative_slider > 1 ? 1 : creative_slider;
			creative_rowIndex = ((int) (creative_slider * creative_maxIndex) >> 3) << 3;
			if (creative_rowIndex > creative_maxIndex) {
				creative_rowIndex = creative_maxIndex;
			}
			creative_slider = (float) creative_rowIndex / creative_maxIndex;
			return;
		}
		int wheel = Mouse.getDWheel();
		if (wheel > 0) {
			creative_rowIndex -= 8;
			if (creative_rowIndex < 0) {
				creative_rowIndex = 0;
			}
			creative_slider = (float) creative_rowIndex / creative_maxIndex;
		}
		else if (wheel < 0) {
			creative_rowIndex += 8;
			if (creative_rowIndex > creative_maxIndex) {
				creative_rowIndex = creative_maxIndex;
			}
			creative_slider = (float) creative_rowIndex / creative_maxIndex;
		}
	}
	
	@Unique
	private int creative_getMaxIndex() {
		return (((creative_items.size() - 48) >> 3)) << 3;
	}
	
	@Unique
	private void creative_playSound() {
		this.minecraft.soundHelper.playSound("random.click", 1.0F, 1.0F);
	}
	
	private void creative_updateMaxIndex() {
		creative_maxTabIndex = TabRegistry.getTabsCount() - creative_tabPage * 7;
		if (creative_maxTabIndex > 7) creative_maxTabIndex = 7;
	}
	
	private CreativeTab creative_getTab(int page, int index) {
		return TabRegistry.getTabByIndex(page * 7 + index);
	}
}
