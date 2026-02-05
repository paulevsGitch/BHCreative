package paulevs.bhcreative.mixin.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.BHCreative;
import paulevs.bhcreative.interfaces.CreativePlayer;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements CreativePlayer {
	@Unique private final Vec3d creative_flightSpeed = Vec3d.create(0, 0, 0);
	
	@Shadow public abstract void tickRiding();
	
	public PlayerEntityMixin(World arg) {
		super(arg);
	}
	
	@Override
	public boolean creative_isCreative() {
		return BHCreative.toBool(dataTracker.getByte(BHCreative.IS_CREATIVE_ID));
	}
	
	@Override
	public void creative_setCreative(boolean creative) {
		this.dataTracker.set(BHCreative.IS_CREATIVE_ID, BHCreative.toByte(creative));
		this.fireImmune = creative;
	}
	
	@Override
	public boolean creative_isFlying() {
		return BHCreative.toBool(dataTracker.getByte(BHCreative.IS_FLYING_ID));
	}
	
	@Override
	public void creative_setFlying(boolean flying) {
		boolean selfFly = this.creative_isFlying();
		if (flying && !selfFly) {
			creative_flightSpeed.x = velocityX;
			creative_flightSpeed.y = velocityY;
			creative_flightSpeed.z = velocityZ;
		}
		else if (!flying && selfFly) {
			creative_flightSpeed.x = 0;
			creative_flightSpeed.y = 0;
			creative_flightSpeed.z = 0;
		}
		this.dataTracker.set(BHCreative.IS_FLYING_ID, BHCreative.toByte(flying));
		scheduleVelocityUpdate();
	}
	
	@Inject(method = "damage(Lnet/minecraft/entity/Entity;I)Z", at = @At("HEAD"), cancellable = true)
	private void creative_damage(Entity target, int amount, CallbackInfoReturnable<Boolean> info) {
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
	
	@Inject(method = "writeNbt", at = @At("HEAD"))
	private void creative_writeCustomDataToTag(NbtCompound tag, CallbackInfo info) {
		tag.putBoolean("Creative", creative_isCreative());
		tag.putBoolean("Flying", creative_isFlying());
	}

	@Inject(method = "readNbt", at = @At("HEAD"))
	private void creative_readCustomDataFromTag(NbtCompound tag, CallbackInfo info) {
		creative_setCreative(tag.getBoolean("Creative"));
		creative_setFlying(tag.getBoolean("Flying"));
	}
	
	@Inject(method = "tick", at = @At("TAIL"))
	private void creative_tick(CallbackInfo info) {
		if (!this.creative_isCreative()) return;
		if (!this.creative_isFlying()) return;
		
		if (this.isSleeping() || this.vehicle != null) {
			this.creative_setFlying(false);
			return;
		}
		
		if (this.onGround) {
			this.creative_setFlying(false);
			return;
		}
		
		LivingEntityAccessor entity = (LivingEntityAccessor) this;
		
		float front = entity.creative_getFrontMovement();
		float right = entity.creative_getRightMovement();
		double angle = Math.toRadians(this.yaw);
		float sin = (float) Math.sin(angle);
		float cos = (float) Math.cos(angle);
		float dx = (front * cos - right * sin);
		float dz = (right * cos + front * sin);
		
		creative_flightSpeed.x = MathHelper.lerp(0.15, creative_flightSpeed.x, dx * 0.4);
		creative_flightSpeed.z = MathHelper.lerp(0.15, creative_flightSpeed.z, dz * 0.4);
		
		boolean sneaking = this.isSneaking();
		
		dx = 0;
		if (jumping) dx += 0.4F;
		if (sneaking) dx -= 0.4F;
		
		creative_flightSpeed.y = MathHelper.lerp(0.2, creative_flightSpeed.y, dx);
		
		this.velocityX = creative_flightSpeed.x;
		this.velocityY = creative_flightSpeed.y;
		this.velocityZ = creative_flightSpeed.z;
	}
	
	@Inject(method = "initDataTracker", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/entity/LivingEntity;initDataTracker()V",
		shift = Shift.AFTER
	))
	private void creative_trackData(CallbackInfo info) {
		this.dataTracker.startTracking(BHCreative.IS_CREATIVE_ID, (byte) 0);
		this.dataTracker.startTracking(BHCreative.IS_FLYING_ID, (byte) 0);
	}
}
