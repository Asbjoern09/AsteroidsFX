package dk.sdu.mmmi.cbse.common.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.Random;

/**
 *
 * @author Emil
 */
public class Asteroid extends Entity {

    int splitCounter = 2;

    public int getSplitCounter() {
        return splitCounter;
    }

    public void setSplitCounter(int splitCounter) {
        this.splitCounter = splitCounter;
    }

    Random random = new Random();

    @Override
    public void handleCollision(Entity entity, World world){
        if(splitCounter <= 0){
            destroyBigAsteroid(world);
        }else{
            destroyBigAsteroid(world);
            for (int i = 0; i < 2; i++) {
                Asteroid newAsteroid = new Asteroid();
                newAsteroid.setSplitCounter(splitCounter - 1);
                newAsteroid.setEnabled(true);
                newAsteroid.setEntityType(EntityType.asteroid);
                double[] reducedPolygonCoords = reduceAsteroidSize(entity.getPolygonCoordinates());
                newAsteroid.setPolygonCoordinates(reducedPolygonCoords);
                if(i== 0){
                    newAsteroid.setX(entity.getX()+5);
                    newAsteroid.setY(entity.getY()+5);
                }else {
                    newAsteroid.setX(entity.getX()+5);
                    newAsteroid.setY(entity.getY()-5);
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

    public void destroyBigAsteroid(World world){
        world.removeEntity(this);
    }
}
