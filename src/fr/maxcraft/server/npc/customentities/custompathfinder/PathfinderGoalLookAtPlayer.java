package fr.maxcraft.server.npc.customentities.custompathfinder;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoal;

/**
 * Created by Crevebedaine on 26/01/2016.
 */
public class PathfinderGoalLookAtPlayer extends PathfinderGoal {
    protected EntityInsentient a;
    protected Entity b;
    protected float c;
    private int e;
    private float f;
    protected Class<? extends Entity> d;

    public PathfinderGoalLookAtPlayer(EntityInsentient var1, Class<? extends Entity> var2, float var3) {
        this.a = var1;
        this.d = var2;
        this.c = var3;
        this.f = 0.02F;
        this.a(2);
    }

    public PathfinderGoalLookAtPlayer(EntityInsentient var1, Class<? extends Entity> var2, float var3, float var4) {
        this.a = var1;
        this.d = var2;
        this.c = var3;
        this.f = var4;
        this.a(2);
    }

    public boolean a() {
        if(this.a.bc().nextFloat() >= this.f) {
            return false;
        } else {
            if(this.a.getGoalTarget() != null) {
                this.b = this.a.getGoalTarget();
            }

            if(this.d == EntityHuman.class) {
                this.b = this.a.world.findNearbyPlayer(this.a, (double)this.c);
            } else {
                this.b = this.a.world.a(this.d, this.a.getBoundingBox().grow((double)this.c, 3.0D, (double)this.c), this.a);
            }

            return this.b != null;
        }
    }

    public boolean b() {
        return !this.b.isAlive()?false:(this.a.h(this.b) > (double)(this.c * this.c)?false:this.e > 0);
    }

    public void c() {
        this.e = 40 + this.a.bc().nextInt(40);
    }

    public void d() {
        this.b = null;
    }

    public void e() {
        this.a.getControllerLook().a(this.b.locX, this.b.locY, this.b.locZ, 10.0F, (float)this.a.bQ());
        --this.e;
    }
}
