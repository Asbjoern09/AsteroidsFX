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
//    private Entity enemy1;
//    private Entity enemy2;
List<Entity> enemyList = new ArrayList<>();


    Random random = new Random();

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        for (int i = 0; i < 8; i++) {
            enemy = createEnemyShip(gameData);
            enemyList.add(enemy);
            world.addEntity(enemy);

        }
//        // Add entities to the world
//        enemy = createEnemyShip(gameData);
//        enemy1 = createEnemyShip(gameData);
//        enemy2 = createEnemyShip(gameData);
//        world.addEntity(enemy);
//        world.addEntity(enemy1);
//        world.addEntity(enemy2);
    }

    private Entity createEnemyShip(GameData gameData) {

        Entity enemyShip = new Enemy();
        enemyShip.setEnabled(true);
        enemyShip.setPolygonCoordinates(-5,-5,10,0,-5,5);
        enemyShip.setX(gameData.getDisplayHeight()/random.nextDouble(5));
        enemyShip.setY(gameData.getDisplayWidth()/random.nextDouble(5));
        enemyShip.setRotation(random.nextDouble(361));
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (int i = 0; i < enemyList.size(); i++) {
            world.removeEntity(enemyList.get(i));

        }
    }
}
