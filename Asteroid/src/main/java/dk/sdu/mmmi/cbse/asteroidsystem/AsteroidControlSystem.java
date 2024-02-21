package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Entity.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class AsteroidControlSystem implements IEntityProcessingService, AsteroidSPI {


    private int splitCounter= 2;

    Random random = new Random();

    @Override
    public void process(GameData gameData, World world, double dt) {
        double movementSpeed = 25;
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            if(asteroid.isHit()){
                if(splitCounter > 0){
                    splitCounter -= 1;
                    ArrayList<AsteroidSPI> asteroidSPIArrayList = (ArrayList<AsteroidSPI>) getAsteroidSPIs();
                    System.out.println("Asteroid spi " + asteroidSPIArrayList );
                    for (int i = 0; i < asteroidSPIArrayList.size(); i++) {
                        System.out.println("Is hit = " + asteroid.isHit());
                        world.addEntity(asteroidSPIArrayList.get(i).createSmallerAsteroid(asteroid, gameData));
                        world.addEntity(asteroidSPIArrayList.get(i).createSmallerAsteroid(asteroid, gameData));
                    }
                }
            }
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

    public Entity createSmallerAsteroid(Entity bigAsteroid, GameData gameData) {
        System.out.println("Creating smaller asteroid");
        Entity asteroid = new Asteroid();
        asteroid.setEnabled(true);
        asteroid.setEntityType(EntityType.asteroid);
        asteroid.setPolygonCoordinates(-5,-5, 10,-5, 10,0, 5,5, -5,10, -10,0);
        asteroid.setX(bigAsteroid.getX());
        asteroid.setY(bigAsteroid.getY());
        asteroid.setRotation(random.nextDouble(361));
        return asteroid;
    }

    private Collection<? extends AsteroidSPI> getAsteroidSPIs() {
        return ServiceLoader.load(AsteroidSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
