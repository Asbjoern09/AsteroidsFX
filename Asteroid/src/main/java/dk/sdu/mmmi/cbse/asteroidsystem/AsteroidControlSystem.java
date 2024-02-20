package dk.sdu.mmmi.cbse.asteroidsystem;

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


public class AsteroidControlSystem implements IEntityProcessingService {


    Random random = new Random();

    @Override
    public void process(GameData gameData, World world, double dt) {
        double rotationSpeed = 50;
        double movementSpeed = 50;
        for (Entity enemy : world.getEntities(Asteroid.class)) {
            int randomNumber = random.nextInt(150);

            System.out.println("enemy y = " + enemy.getY());
            System.out.println("enemy x = " + enemy.getX());
                if(enemy.getY() >= gameData.getDisplayHeight() -5 || enemy.getY() <= 5){
                    enemy.setRotation(enemy.getRotation() - rotationSpeed*2 * dt);
                }
                else if(enemy.getX() >= gameData.getDisplayWidth() -5 || enemy.getX() <= 5){
                    enemy.setRotation(enemy.getRotation() - rotationSpeed*2 * dt);
                }
                else{
                    if (randomNumber == 1 || randomNumber == 39 || randomNumber == 42) {
                        enemy.setRotation(enemy.getRotation() - (rotationSpeed*random.nextDouble()*(20-10)) * dt);
                    }
                    if (randomNumber == 5 || randomNumber == 30 || randomNumber == 28) {
                        enemy.setRotation(enemy.getRotation() + (rotationSpeed*random.nextDouble()*(20-10)) * dt);
                    }
                }
                if (randomNumber >= 25) {
                    double enemyRadians = Math.toRadians(enemy.getRotation());
                    double changeX = Math.cos(enemyRadians) * movementSpeed * dt;
                    double changeY = Math.sin(enemyRadians) * movementSpeed * dt;
                    enemy.setX(enemy.getX() + changeX);
                    enemy.setY(enemy.getY() + changeY);
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

        }}


    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
