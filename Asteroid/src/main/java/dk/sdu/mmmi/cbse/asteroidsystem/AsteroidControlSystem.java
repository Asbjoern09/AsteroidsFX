package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Entity.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Random;


public class AsteroidControlSystem implements IEntityProcessingService, AsteroidSPI {


    private int splitCounter= 2;

    Random random = new Random();

    @Override
    public void process(GameData gameData, World world, double dt) {
        double movementSpeed = 25;
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            int randomNumber = random.nextInt(150);
            if (asteroid.getY() >= (gameData.getDisplayHeight()- 0.1) || asteroid.getY() <= 0.1) {
                asteroid.setRotation(asteroid.getRotation() + random.nextInt(3001 - 1000) + 1000 * dt);
            }

            if (asteroid.getX() >= (gameData.getDisplayWidth() -0.1) || asteroid.getX() <= 0.1) {
                asteroid.setRotation(asteroid.getRotation() + random.nextInt(3001 - 1000) + 1000 * dt);
            }

            double enemyRadians = Math.toRadians(asteroid.getRotation());
            double changeX = Math.cos(enemyRadians) * movementSpeed * dt;
            double changeY = Math.sin(enemyRadians) * movementSpeed * dt;
            asteroid.setX(asteroid.getX() + changeX);
            asteroid.setY(asteroid.getY() + changeY);

            if (asteroid.getX() < 0) {
                asteroid.setX(1);
            }

            if (asteroid.getX() > gameData.getDisplayWidth()) {
                asteroid.setX(gameData.getDisplayWidth() - 1);
            }

            if (asteroid.getY() < 0) {
                asteroid.setY(1);
            }

            if (asteroid.getY() > gameData.getDisplayHeight()) {
                asteroid.setY(gameData.getDisplayHeight() - 1);
            }

        }
    }

    @Override
    public void handleAsteroidSplit(Entity bigAsteroid, World world) {
        Asteroid oldAsteroid = (Asteroid) bigAsteroid;
        if(oldAsteroid.getSplitCounter() <= 0){
            destroyBigAsteroid(oldAsteroid,world);
        }else{
            int splitCounter = oldAsteroid.getSplitCounter();
            destroyBigAsteroid(oldAsteroid,world);
            for (int i = 0; i < 2; i++) {
                Asteroid newAsteroid = new Asteroid();
                newAsteroid.setSplitCounter(splitCounter - 1);
                newAsteroid.setEnabled(true);
                newAsteroid.setEntityType(EntityType.asteroid);
                double[] reducedPolygonCoords = reduceAsteroidSize(bigAsteroid.getPolygonCoordinates());
                newAsteroid.setPolygonCoordinates(reducedPolygonCoords);
                if(i== 0){
                    newAsteroid.setX(bigAsteroid.getX()+5);
                    newAsteroid.setY(bigAsteroid.getY()+5);
                }else {
                    newAsteroid.setX(bigAsteroid.getX()+5);
                    newAsteroid.setY(bigAsteroid.getY()-5);
                }
                newAsteroid.setRotation(random.nextDouble(361));
                world.addEntity(newAsteroid);
            }
        }

    }

    public double[] reduceAsteroidSize(double[] polygonCoords){
        for (int i = 0; i < polygonCoords.length; i++) {
            if(polygonCoords[i] < 0){
                polygonCoords[i] += 1;
            }else if (polygonCoords[i] > 0 ){
                polygonCoords[i] -= 1;
            }
        }
        return polygonCoords;
    }

    public void destroyBigAsteroid(Entity destroyAsteroid, World world){
        world.removeEntity(destroyAsteroid);
    }
}
