package dk.sdu.mmmi.cbse.common.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.Random;

/**
 *
 * @author Emil
 */
public class Asteroid extends Entity {

    private String parentID;

    int splitCounter = 2;

    int collisionCounter = 0;

    public int getSplitCounter() {
        return splitCounter;
    }

    public void setSplitCounter(int splitCounter) {
        this.splitCounter = splitCounter;
    }

    Random random = new Random();

    public Asteroid(String parentID){
        this.parentID = parentID;
    }

    public Asteroid(){

    }
    @Override
    public void handleCollision(Entity entity, World world){
        if(entity.getEntityType() != EntityType.asteroid) {
            if (splitCounter <= 0) {
                destroyBigAsteroid(world);
            } else {
                for (int i = 0; i < 2; i++) {
                    Asteroid newAsteroid = new Asteroid(this.getID());
                    newAsteroid.setSplitCounter(splitCounter - 1);
                    newAsteroid.setEnabled(true);
                    newAsteroid.setEntityType(EntityType.asteroid);
                    double[] reducedPolygonCoords = reduceAsteroidSize(this.getPolygonCoordinates());
                    newAsteroid.setPolygonCoordinates(reducedPolygonCoords);
                    if (i == 0) {
                        newAsteroid.setX(entity.getX() + 10);
                        newAsteroid.setY(entity.getY() + 10);
                    } else {
                        newAsteroid.setX(entity.getX() - 10);
                        newAsteroid.setY(entity.getY() - 10);
                    }
                    newAsteroid.setRotation(random.nextDouble(361));
                    world.addEntity(newAsteroid);
                }
                destroyBigAsteroid(world);

            }
        } else if(collisionCounter < 100){
            this.setRotation(this.getRotation()+5);
            entity.setRotation(entity.getRotation() +5);
            collisionCounter++;
        } else if(collisionCounter > 100 && collisionCounter < 200){
            collisionCounter++;
        }else{
            collisionCounter = 0;
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

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }
}
