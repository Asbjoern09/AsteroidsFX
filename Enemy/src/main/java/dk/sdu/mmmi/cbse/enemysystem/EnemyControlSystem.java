package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class EnemyControlSystem implements IEntityProcessingService {
    static int shootingInterval = 100;
    static int shootingCounter = 0;

    Random random = new Random();

    @Override
    public void process(GameData gameData, World world, double dt) {
        double rotationSpeed = 200;
        double movementSpeed = 200;
        for (Entity enemy : world.getEntities(Enemy.class)) {
            int randomNumber = random.nextInt(30);

//            if (shootingCounter > shootingInterval) {
                if (randomNumber == 1) {
                    enemy.setRotation(enemy.getRotation() - rotationSpeed * dt);
                }
                if (randomNumber == 5) {
                    enemy.setRotation(enemy.getRotation() + rotationSpeed * dt);
                }
                if (randomNumber ==9) {
                    double enemyRadians = Math.toRadians(enemy.getRotation());
                    double changeX = Math.cos(enemyRadians) * movementSpeed * dt;
                    double changeY = Math.sin(enemyRadians) * movementSpeed * dt;
                    enemy.setX(enemy.getX() + changeX);
                    enemy.setY(enemy.getY() + changeY);
                }
                if (randomNumber == 25) {
                    ArrayList<BulletSPI> bulletSPIArrayList = (ArrayList<BulletSPI>) getBulletSPIs();
                    for (int i = 0; i < bulletSPIArrayList.size(); i++) {
                            world.addEntity(bulletSPIArrayList.get(i).createBullet(enemy, gameData));
                    }
                }

                if (enemy.getX() < 0) {
                    enemy.setX(1);
                }

                if (enemy.getX() > gameData.getDisplayWidth()) {
                    enemy.setX(gameData.getDisplayWidth() - 1);
                }

                if (enemy.getY() < 0) {
                    enemy.setY(1);
                }

                if (enemy.getY() > gameData.getDisplayHeight()) {
                    enemy.setY(gameData.getDisplayHeight() - 1);
                }

//                shootingCounter = 0;
//            } else{
//                shootingCounter++;
//            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
