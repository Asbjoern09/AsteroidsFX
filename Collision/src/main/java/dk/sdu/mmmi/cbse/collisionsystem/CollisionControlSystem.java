package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.List;

public class CollisionControlSystem implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world, double dt) {
        List<Entity> entities = new ArrayList<>(world.getEntities());
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                Entity entity = entities.get(i);
                Entity entity1 = entities.get(j);

                if (collideChecker(entity, entity1)) {
                    if (bulletShooterCollision(entity, entity1)) {
                        continue;
                    }
                    if (entity instanceof Asteroid && entity1 instanceof Asteroid) {
                        continue;
                    }
                    asteroidCollision(entity, entity1, world);
                    otherCollision(entity, entity1, world);
                }
            }
        }
    }

    protected void asteroidCollision(Entity entity, Entity entity1, World world) {
        List<Asteroid> smallerAsteroids = new ArrayList<>();
        if (entity instanceof Asteroid) {
            Asteroid asteroid = (Asteroid) entity;
            if(asteroid.getHp() <= 0){
                world.removeEntity(entity);
            }else {
                smallerAsteroids = asteroid.split(asteroid);
                world.removeEntity(asteroid);
            }
        }
        if (entity1 instanceof Asteroid) {
            Asteroid asteroid = (Asteroid) entity1;
            if(asteroid.getHp() <= 0){
                world.removeEntity(entity);
            }else {
                smallerAsteroids = asteroid.split(asteroid);
                world.removeEntity(asteroid);
            }
        }
        for (Asteroid asteroid: smallerAsteroids) {
            world.addEntity(asteroid);
        }
    }

    protected boolean bulletShooterCollision(Entity entity, Entity entity1) {
        if (entity instanceof Bullet) {
            Bullet bullet = (Bullet) entity;
            return bullet.getParentID().equals(entity1.getID());
        }
        if (entity1 instanceof Bullet) {
            Bullet bullet = (Bullet) entity1;
            return bullet.getParentID().equals(entity.getID());
        }
        return false;
    }

    protected void otherCollision(Entity entity, Entity entity1, World world) {
        entity.setHp(entity.getHp() - 1);
        entity1.setHp(entity1.getHp() - 1);

        if (entity instanceof Asteroid || entity1 instanceof Asteroid) {
            if (entity instanceof Asteroid) {
                world.removeEntity(entity1);
            }
            if (entity1 instanceof Asteroid) {
                world.removeEntity(entity);
            }
        } else {
            if (entity.getHp() <= 0) {
                world.removeEntity(entity);
            }
            if (entity1.getHp() <= 0) {
                world.removeEntity(entity1);
            }
        }
    }
    private boolean collideChecker(Entity entity, Entity entity1) {
        double distance = Math.sqrt(Math.pow(entity.getX() - entity1.getX(), 2) + Math.pow(entity.getY() - entity1.getY(), 2));
        return distance < 10;
    }



}