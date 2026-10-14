package com.eeeguba.tapiocamod.entity.custom;

import net.minecraft.core.BlockPos;
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
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TapiocaEntity extends Animal{

    final int variant;
    public static int getVariant(TapiocaEntity tapiocaEntity){
        return tapiocaEntity.variant;
    };
    private static final EntityDataAccessor<Optional<BlockState>> DATA_CARRY_STATE =
            SynchedEntityData.defineId(
            TapiocaEntity.class,
            EntityDataSerializers.OPTIONAL_BLOCK_STATE
    );

    
    public TapiocaEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        int min = 1;
        int max = 6;
        int range = max - min + 1;
        this.variant = (int)(Math.random() * range) + min;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_CARRY_STATE, Optional.empty());
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
    protected void registerGoals(){
        this.goalSelector.addGoal(0,new FloatGoal(this));
        this.goalSelector.addGoal(1,new BreedGoal(this, 1d));
        this.goalSelector.addGoal(1,new TapiocaPickFlowerGoal(this));
        this.goalSelector.addGoal(2,new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(2,new EatBlockGoal(this));
    }
    public void setCarriedBlock(@Nullable BlockState state) {
        this.entityData.set(DATA_CARRY_STATE, Optional.ofNullable(state));
    }
    @Nullable
    public BlockState getCarriedBlock() {
        return (BlockState)((Optional)this.entityData.get(DATA_CARRY_STATE)).orElse((Object)null);
    }
    static class TapiocaPickFlowerGoal extends Goal {
        private final TapiocaEntity tapioca;

        public TapiocaPickFlowerGoal(TapiocaEntity tapioca) {
            this.tapioca = tapioca;
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
