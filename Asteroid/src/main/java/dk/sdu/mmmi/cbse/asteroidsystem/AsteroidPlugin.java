package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private Entity asteroid;

    List<Entity> asteroidList = new ArrayList<>();


    Random random = new Random();

    public AsteroidPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        for (int i = 0; i < 20; i++) {
            asteroid = createAsteroid(gameData);
            asteroidList.add(asteroid);
            world.addEntity(asteroid);

        }
    }

    private Entity createAsteroid(GameData gameData) {

        Entity asteroid = new Asteroid();
        asteroid.setEnabled(true);
        asteroid.setHp(2);
        asteroid.setPolygonCoordinates(10, 0, 5, 8.66, -5, 8.66, -10, 0, -5, -8.66, 5, -8.66);
        asteroid.setX(gameData.getDisplayHeight()/random.nextDouble(5));
        asteroid.setY(gameData.getDisplayWidth()/random.nextDouble(5));
        asteroid.setRotation(random.nextDouble(361));
        return asteroid;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (int i = 0; i < asteroidList.size(); i++) {
            world.removeEntity(asteroidList.get(i));

        }

    }

}
