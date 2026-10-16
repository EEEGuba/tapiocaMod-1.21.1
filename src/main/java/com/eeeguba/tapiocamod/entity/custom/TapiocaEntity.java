package com.eeeguba.tapiocamod.entity.custom;

import io.netty.channel.unix.GenericUnixChannelOption;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import javax.annotation.Nullable;
import net.minecraft.network.syncher.EntityDataSerializers;

import com.eeeguba.tapiocamod.entity.ModEntities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;

import static com.eeeguba.tapiocamod.Config.STARE_EVENT_CHANCE;

public class TapiocaEntity extends Animal{

    private static final EntityDataAccessor<Integer> DATA_VARIANT =
            SynchedEntityData.defineId(TapiocaEntity.class, EntityDataSerializers.INT);
    public static int getVariant(TapiocaEntity tapiocaEntity){
        return tapiocaEntity.entityData.get(DATA_VARIANT);
    };
    private static void setVariant(TapiocaEntity tapiocaEntity, int variant){
        tapiocaEntity.entityData.set(DATA_VARIANT, Mth.clamp(variant, 1, 6));
    }
    private static final EntityDataAccessor<Optional<BlockState>> DATA_CARRY_STATE =
            SynchedEntityData.defineId(
            TapiocaEntity.class,
            EntityDataSerializers.OPTIONAL_BLOCK_STATE
    );

    
    public TapiocaEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        if(!level.isClientSide()) {
            int min = 1;
            int max = 6;
            int range = max - min + 1;
            setVariant(this, (int) (Math.random() * range) + min);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_CARRY_STATE, Optional.empty());
        builder.define(DATA_VARIANT,1);
    }
    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.BREAD);
    }

    @Override
    public @NotNull ItemStack equipItemIfPossible(ItemStack stack) {
        EquipmentSlot equipmentslot = this.getEquipmentSlotForItem(stack);
        ItemStack itemstack = this.getItemBySlot(equipmentslot);
        boolean flag = this.canReplaceCurrentItem(stack, itemstack);

        if (flag && this.canHoldItem(stack)&&equipmentslot == EquipmentSlot.HEAD) {
            double d0 = (double)this.getEquipmentDropChance(equipmentslot);
            if (!itemstack.isEmpty() && (double)Math.max(this.random.nextFloat() - 0.1F, 0.0F) < d0) {
                this.spawnAtLocation(itemstack);
            }

            ItemStack itemstack1 = equipmentslot.limit(stack);
            this.setItemSlotAndDropWhenKilled(equipmentslot, itemstack1);
            return itemstack1;
        } else {
            return ItemStack.EMPTY;
        }
    }
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        tag.putInt("variant", getVariant(this));

        BlockState carried = this.getCarriedBlock();
        if (carried != null) {
            tag.put("CarriedBlock", NbtUtils.writeBlockState(carried));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains("variant", Tag.TAG_INT)) {
            setVariant(this, tag.getInt("variant"));
        }

        if (tag.contains("CarriedBlock", Tag.TAG_COMPOUND)) {
            BlockState state = NbtUtils.readBlockState(
                    this.level().registryAccess().lookupOrThrow(Registries.BLOCK),
                    tag.getCompound("CarriedBlock")
            );
            this.setCarriedBlock(state);
        }
    }
    protected void registerGoals(){
        this.goalSelector.addGoal(0,new FloatGoal(this));
        this.goalSelector.addGoal(0,new TapiocaStareAtPlayerGoal(this));
        this.goalSelector.addGoal(1,new BreedGoal(this, 1.5d));
        this.goalSelector.addGoal(1,new TapiocaEatFlowerGoal(this));
        this.goalSelector.addGoal(1,new TapiocaPickFlowerGoal(this));
        this.goalSelector.addGoal(2,new RandomStrollGoal(this, 1));
        //this.goalSelector.addGoal(2,new EatBlockGoal(this));
        //eating grass looks weird on these guys, nvm
    }
    public void setCarriedBlock(@Nullable BlockState state) {
        this.entityData.set(DATA_CARRY_STATE, Optional.ofNullable(state));
    }
    @Nullable
    public BlockState getCarriedBlock() {
        return (BlockState)((Optional)this.entityData.get(DATA_CARRY_STATE)).orElse((Object)null);
    }

    static class TapiocaStareAtPlayerGoal extends Goal{
        private final TapiocaEntity tapioca;

        public TapiocaStareAtPlayerGoal(TapiocaEntity tapiocaEntity) {
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
            this.tapioca = tapiocaEntity;
        }
        //global sync
        private boolean isGlobalEventWindow() {
            if (!(tapioca.level() instanceof ServerLevel level)) return false;

            ServerLevel clock = level.getServer().overworld();

            long periodTicks = 20L * 60L; // one-minute windows
            long window = clock.getGameTime() / periodTicks;
            //bitwise XOR on a golden ratio hex so it feels more random
            long seed = clock.getSeed() ^ (window * 0x9E3779B97F4A7C15L);

            return new Random(seed).nextDouble() < STARE_EVENT_CHANCE.get();
        }

        @Override
        public boolean canUse() {
            return isGlobalEventWindow(); //&& isTimeoutActive();
        }

        @Override
        public void tick() {
            super.start();
            Player player = this.tapioca.level().getNearestPlayer(this.tapioca, 16.0);
            if (player != null){tapioca.getLookControl().setLookAt(player, 10.0F, 10.0F);}
        }
    }

    static class TapiocaEatFlowerGoal extends Goal {
        private final TapiocaEntity tapioca;

        public TapiocaEatFlowerGoal(TapiocaEntity tapiocaEntity) {
            this.tapioca = tapiocaEntity;
        }

        @Override
        public boolean canUse() {
            Player player = this.tapioca.level().getNearestPlayer(this.tapioca, 16.0);
            if (player != null && this.tapioca.getCarriedBlock() != null){
                Vec3 toTapioca = this.tapioca.getEyePosition()
                        .subtract(player.getEyePosition())
                        .normalize();

                boolean lookingAtTapioca = player.getLookAngle().dot(toTapioca) > 0.5;
                boolean canSeeTapioca = player.hasLineOfSight(this.tapioca) && lookingAtTapioca;

                return !canSeeTapioca||!player.hasLineOfSight(tapioca);
            }
            else{return false;}
        }
        @Override
        public void start() {
            this.tapioca.setCarriedBlock(null);
            this.tapioca.heal(20.0F);
            this.tapioca.playSound(SoundEvents.FOX_EAT, 1.0F, 1.0F);
        }
    }
    static class TapiocaPickFlowerGoal extends Goal {
        private final TapiocaEntity tapioca;

    //this below is to grab random flowers and hold them
        public TapiocaPickFlowerGoal(TapiocaEntity tapiocaEntity) {
            this.tapioca = tapiocaEntity;
        }

            public boolean canUse() {
                if (this.tapioca.getCarriedBlock() != null) {
                    return false;
                } else {
                    return !EventHooks.canEntityGrief(this.tapioca.level(), this.tapioca) ? false : this.tapioca.getRandom().nextInt(reducedTickDelay(20)) == 0;
                }
            }
            public void tick() {
                RandomSource randomsource = this.tapioca.getRandom();
                Level level = this.tapioca.level();
                int i = Mth.floor(this.tapioca.getX() - (double)2.0F + randomsource.nextDouble() * (double)4.0F);
                int j = Mth.floor(this.tapioca.getY() + randomsource.nextDouble() * (double)3.0F);
                int k = Mth.floor(this.tapioca.getZ() - (double)2.0F + randomsource.nextDouble() * (double)4.0F);
                BlockPos blockpos = new BlockPos(i, j, k);
                BlockState blockstate = level.getBlockState(blockpos);
                Vec3 vec3 = new Vec3((double)this.tapioca.getBlockX() + (double)0.5F, (double)j + (double)0.5F, (double)this.tapioca.getBlockZ() + (double)0.5F);
                Vec3 vec31 = new Vec3((double)i + (double)0.5F, (double)j + (double)0.5F, (double)k + (double)0.5F);
                BlockHitResult blockhitresult = level.clip(new ClipContext(vec3, vec31, net.minecraft.world.level.ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, this.tapioca));
                boolean flag = blockhitresult.getBlockPos().equals(blockpos);
                if (blockstate.is(BlockTags.SMALL_FLOWERS) && flag) {
                    level.removeBlock(blockpos, false);
                    if (!level.isClientSide) {
                        this.tapioca.playSound(SoundEvents.GRASS_BREAK, 1.0F, 1.0F);
                    }
                    level.gameEvent(GameEvent.BLOCK_DESTROY, blockpos, GameEvent.Context.of(this.tapioca, blockstate));
                    this.tapioca.setCarriedBlock(blockstate.getBlock().defaultBlockState());
                }

            }
        }

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes()
            .add(Attributes.MAX_HEALTH, 20d)
            .add(Attributes.MOVEMENT_SPEED,0.1d)
            .add(Attributes.FOLLOW_RANGE,24d);
    }
    @Override
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
       return ModEntities.TAPIOCA.get().create(level);
    }

}
