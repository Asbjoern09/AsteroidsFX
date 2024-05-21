package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;
    List<Entity> enemyList = new ArrayList<>();


    Random random = new Random();

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        for (int i = 0; i < 5; i++) {
            enemy = createEnemyShip(gameData);
            enemyList.add(enemy);
            world.addEntity(enemy);

        }

    }

    private Entity createEnemyShip(GameData gameData) {
        Entity enemyShip = new Enemy();
        enemyShip.setEnabled(true);
        enemyShip.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        double randomX = random.nextDouble() * gameData.getDisplayWidth();
        double randomY = random.nextDouble() * gameData.getDisplayHeight();
        enemyShip.setX(randomX);
        enemyShip.setY(randomY);
        enemyShip.setRotation(random.nextDouble() * 361);
        return enemyShip;
    }


    @Override
    public void stop(GameData gameData, World world) {
        for (int i = 0; i < enemyList.size(); i++) {
            world.removeEntity(enemyList.get(i));

        }
    }
}
