package dk.sdu.mmmi.cbse.collisionsystem;


import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionControlSystem implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world, double dt) {
        for (Entity entity : world.getEntities()) {
            for (Entity entity1 : world.getEntities()) {

                if (entity == entity1) {
                    continue;
                } else {
                    collisionCheck(entity, entity1, world);
                }
            }
        }
    }

    public void collisionCheck(Entity entity, Entity entity1, World world) {

        double distance = Math.sqrt(Math.pow(entity1.getX() - entity.getX(), 2) + Math.pow(entity1.getY() - entity.getY(), 2));

        if(distance < 10){
                entity.handleCollision(entity1,world);
                entity1.handleCollision(entity,world);
        }
    }
}
