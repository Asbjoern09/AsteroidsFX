package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author Emil
 */
public class Player extends Entity {

    int hp = 10;

    @Override
    public void handleCollision(Entity entity, World world){
        if(entity instanceof Asteroid){
            world.removeEntity(this);
        }else if(entity instanceof Asteroid){
            Asteroid bullet = (Asteroid) entity;
            if(!bullet.getParentID().equals(this.getID())){
                if(hp == 0){
                    world.removeEntity(this);
                }else{
                    hp -=1;
                }
            }
        }
    }
}
