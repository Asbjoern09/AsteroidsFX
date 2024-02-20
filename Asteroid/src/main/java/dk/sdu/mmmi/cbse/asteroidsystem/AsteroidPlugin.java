package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private Entity asteroid;
    private Entity asteroid1;
    private Entity asteroid2;


    Random random = new Random();

    public AsteroidPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        asteroid = createAsteroid(gameData);
        asteroid1 = createAsteroid(gameData);
        asteroid2 = createAsteroid(gameData);
        world.addEntity(asteroid);
        world.addEntity(asteroid1);
        world.addEntity(asteroid2);
    }

    private Entity createAsteroid(GameData gameData) {

        Entity asteroid = new Asteroid();
        asteroid.setPolygonCoordinates(-5,-5, 10,-5, 10,0, 5,5, -5,10, -10,0);
        asteroid.setX(gameData.getDisplayHeight()/random.nextDouble(5));
        asteroid.setY(gameData.getDisplayWidth()/random.nextDouble(5));
        asteroid.setRotation(random.nextDouble(361));
        return asteroid;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(asteroid);
        world.removeEntity(asteroid1);
        world.removeEntity(asteroid2);
    }

}
