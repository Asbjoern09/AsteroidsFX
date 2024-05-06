package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.UUID;

/**
 *
 * @author corfixen
 */
public class Bullet extends Entity {


    private String parentID;

    public Bullet(String parentID){
        this.parentID = parentID;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String ID) {
        this.parentID = ID;
    }
}
