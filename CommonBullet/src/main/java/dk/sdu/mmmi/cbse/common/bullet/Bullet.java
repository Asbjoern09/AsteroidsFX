package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;


/**
 *
 * @author corfixen
 */
public class Bullet extends Entity {


    private String parentID;

    public Bullet(String parentID){
        this.parentID = parentID;
    }
    @Override
    public void handleCollision(Entity entity, World world){
        if(!entity.getID().equals(parentID)) {
            world.removeEntity(this);
        }
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String ID) {
        this.parentID = ID;
    }
}
