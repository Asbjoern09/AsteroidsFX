package dk.sdu.mmmi.cbse.collisionsystem;


import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.Entity.EntityType;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

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

    public void playerAsteroidCollisionHandler(Entity entity, Entity entity1,World world){
        if(entity.getEntityType() == EntityType.player){
            entity.setEnabled(false);
            world.removeEntity(entity);
        } else if (entity1.getEntityType() == EntityType.player) {
            entity.setEnabled(false);
            world.removeEntity(entity1);
        }
    }
//    public void bulletAsteroidCollisionHandler(Entity entity, Entity entity1, World world){
//        if(entity.getEntityType() == EntityType.asteroid){
//            asteroidSplit(entity, world);
//            world.removeEntity(entity1);
//        } else if (entity1.getEntityType() == EntityType.asteroid) {
//            asteroidSplit(entity1, world);
//            world.removeEntity(entity);
//        }
//    }

//
//    private void asteroidSplit(Entity entity, World world){
//        getAsteroidSPIs().stream().findFirst().ifPresent(spi -> {
//            spi.handleAsteroidSplit(entity, world);
//        });
//    }

    private Collection<? extends AsteroidSPI> getAsteroidSPIs() {
        return ServiceLoader.load(AsteroidSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
