package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Random;


public class AsteroidControlSystem implements IEntityProcessingService {


    Random random = new Random();

    @Override
    public void process(GameData gameData, World world, double dt) {
        double movementSpeed = 25;
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            int randomNumber = random.nextInt(150);
            if (asteroid.getY() >= (gameData.getDisplayHeight()- 0.01) || asteroid.getY() <= 0.01) {
                if((asteroid.getY() >= (gameData.getDisplayHeight() -0.1))){
                    asteroid.setY(0.02);
                }else{
                    asteroid.setY(gameData.getDisplayHeight()-0.02);
                }
            }

            if (asteroid.getX() >= (gameData.getDisplayWidth() -0.01) || asteroid.getX() <= 0.01) {
                if((asteroid.getX() >= (gameData.getDisplayWidth() -0.1))){
                    asteroid.setX(0.02);
                }else{
                    asteroid.setX(gameData.getDisplayWidth()-0.02);
                }
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
}
