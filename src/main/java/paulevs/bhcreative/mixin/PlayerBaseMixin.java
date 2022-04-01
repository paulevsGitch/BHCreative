package paulevs.bhcreative.mixin;

import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathHelper;
import net.minecraft.util.maths.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.interfaces.CreativePlayer;
import paulevs.bhcreative.util.MHelper;

@Mixin(PlayerBase.class)
public abstract class PlayerBaseMixin extends Living implements CreativePlayer {
	@Unique private static final float CREATIVE_MAX_SPEED = 0.4F;
	@Unique private final Vec3f creative_flightSpeed = Vec3f.method_1293(0, 0, 0);
	@Unique private boolean creative_isCreative;
	@Unique private boolean creative_isFlying;
	
	public PlayerBaseMixin(Level arg) {
		super(arg);
	}
	
	@Override
	public boolean creative_isCreative() {
		return creative_isCreative;
	}
	
	@Override
	public void creative_setCreative(boolean creative) {
		this.creative_isCreative = creative;
	}
	
	@Override
	public boolean creative_isFlying() {
		return creative_isFlying;
	}
	
	@Override
	public void creative_setFlying(boolean flying) {
		this.creative_isFlying = flying;
		//this.onGround = false;
	}
	
	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	private void creative_damage(EntityBase target, int amount, CallbackInfoReturnable<Boolean> info) {
		if (this.creative_isCreative()) {
			info.setReturnValue(false);
			info.cancel();
		}
	}
	
	@Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true)
	private void creative_applyDamage(int damageAmount, CallbackInfo info) {
		if (this.creative_isCreative()) {
			info.cancel();
		}
	}
	
	@Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
	private void creative_writeCustomDataToTag(CompoundTag tag, CallbackInfo info) {
		tag.put("Creative", creative_isCreative());
		tag.put("Flying", creative_isFlying());
	}

	@Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
	private void creative_readCustomDataFromTag(CompoundTag tag, CallbackInfo info) {
		creative_setCreative(tag.getBoolean("Creative"));
		creative_setFlying(tag.getBoolean("Flying"));
	}
	
	@Inject(method = "tick", at = @At("TAIL"))
	private void creative_tick(CallbackInfo info) {
		if (!this.creative_isCreative()) {
			this.creative_setFlying(false);
		}
		else if (this.creative_isFlying() && !this.isSleeping()) {
			LivingAccessor living = LivingAccessor.class.cast(this);
			float perpen = living.getPerpendicularMovement();
			float parall = living.getParallelMovement();
			float angle = this.yaw * MHelper.PI / 180.0F;
			float sin = MathHelper.sin(angle);
			float cos = MathHelper.cos(angle);
			float dx = (perpen * cos - parall * sin);
			float dz = (parall * cos + perpen * sin);
			creative_flightSpeed.x = creative_flightSpeed.x * 0.9F + dx * 0.1F;
			creative_flightSpeed.z = creative_flightSpeed.z * 0.9F + dz * 0.1F;
			creative_flightSpeed.x = MHelper.clamp(creative_flightSpeed.x, -CREATIVE_MAX_SPEED, CREATIVE_MAX_SPEED);
			creative_flightSpeed.z = MHelper.clamp(creative_flightSpeed.z, -CREATIVE_MAX_SPEED, CREATIVE_MAX_SPEED);
			
			boolean sneaking = this.method_1373();
			
			creative_flightSpeed.y *= 0.9F;
			if (jumping) {
				creative_flightSpeed.y += 0.1F;
				if (creative_flightSpeed.y > CREATIVE_MAX_SPEED) {
					creative_flightSpeed.y = CREATIVE_MAX_SPEED;
				}
			}
			if (sneaking) {
				creative_flightSpeed.y -= 0.1F;
				if (creative_flightSpeed.y < -CREATIVE_MAX_SPEED) {
					creative_flightSpeed.y = -CREATIVE_MAX_SPEED;
				}
			}
			
			this.velocityX = creative_flightSpeed.x;
			this.velocityY = creative_flightSpeed.y;
			this.velocityZ = creative_flightSpeed.z;
			
			if (this.onGround) {
				this.creative_setFlying(false);
				this.velocityX = 0;
				this.velocityY = 0;
				this.velocityZ = 0;
				creative_flightSpeed.x = 0;
				creative_flightSpeed.y = 0;
				creative_flightSpeed.z = 0;
			}
			
			/*this.onGround = false;
			LivingAccessor living = LivingAccessor.class.cast(this);
			float perpen = living.getPerpendicularMovement();
			float parall = living.getParallelMovement();
			float angle = this.yaw * MHelper.PI / 180.0F;
			float sin = MathHelper.sin(angle);
			float cos = MathHelper.cos(angle);
			float dx = (perpen * cos - parall * sin);
			float dz = (parall * cos + perpen * sin);
			creative_flightSpeed.x = dx;
			creative_flightSpeed.z = dz;
			
			boolean sneaking = this.method_1373();
			if (this.jumping) {
				creative_flightSpeed.y = 0.6F;
			}
			else if (sneaking) {
				creative_flightSpeed.y = -0.6F;
			}
			else {
				creative_flightSpeed.y = 0;
			}
			
			this.velocityX = creative_flightSpeed.x;
			this.velocityY = creative_flightSpeed.y;
			this.velocityZ = creative_flightSpeed.z;*/
			
			//this.velocityY = 0;
			//this.y += creative_flightSpeed.y;
		}
	}
}
