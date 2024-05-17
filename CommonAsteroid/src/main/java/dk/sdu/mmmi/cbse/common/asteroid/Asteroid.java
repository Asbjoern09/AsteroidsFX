package dk.sdu.mmmi.cbse.common.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Asteroid extends Entity {
    Random random = new Random();

    public Asteroid(){

    }

    public List<Asteroid> split(Asteroid asteroid){
        List<Asteroid> smallerAsteroids = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Asteroid newAsteroid = new Asteroid();
            newAsteroid.setHp(this.getHp()- 1);
            newAsteroid.setEnabled(true);
            double[] reducedPolygonCoords = reduceAsteroidSize(this.getPolygonCoordinates(), 0.8);;
            newAsteroid.setPolygonCoordinates(reducedPolygonCoords);
            if (i == 0) {
                newAsteroid.setX(asteroid.getX() + 10);
                newAsteroid.setY(asteroid.getY() + 10);
            } else {
                newAsteroid.setX(asteroid.getX() - 10);
                newAsteroid.setY(asteroid.getY() - 10);
            }
            newAsteroid.setRotation(random.nextDouble()*361);
            smallerAsteroids.add(newAsteroid);
        }
        return smallerAsteroids;
    }

    public double[] reduceAsteroidSize(double[] polygonCoords, double scaleFactor){
        for (int i = 0; i < polygonCoords.length; i++) {
            polygonCoords[i] *= scaleFactor;
        }
        return polygonCoords;
    }


}
