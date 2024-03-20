package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author Emil
 */
public class Enemy extends Entity {

    int hp = 5;
    @Override
    public void handleCollision(Entity entity, World world){
        if(entity instanceof Asteroid){
            world.removeEntity(this);
        }else if(entity instanceof Bullet){
            Bullet bullet = (Bullet) entity;
            if(!bullet.getParentID().equals(this.getID())){
                if(hp == 0){
                    world.removeEntity(this);
                }else{
                    hp -=1;
                }
            }
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
