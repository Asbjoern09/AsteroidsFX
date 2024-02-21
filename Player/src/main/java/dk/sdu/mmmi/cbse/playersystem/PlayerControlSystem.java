package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class PlayerControlSystem implements IEntityProcessingService {
    static int shootingInterval = 20;
    static int shootingCounter = 0;

    @Override
    public void process(GameData gameData, World world, double dt) {
        double rotationSpeed = 200;
        double movementSpeed = 200;

        for (Entity player : world.getEntities(Player.class)) {
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - rotationSpeed * dt);
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + rotationSpeed * dt);
            }
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double playerRadians = Math.toRadians(player.getRotation());
                double changeX = Math.cos(playerRadians) * movementSpeed * dt;
                double changeY = Math.sin(playerRadians) * movementSpeed * dt;
                player.setX(player.getX() + changeX);
                player.setY(player.getY() + changeY);
            }
            if(gameData.getKeys().isDown(GameKeys.SPACE)){
                ArrayList<BulletSPI> bulletSPIArrayList = (ArrayList<BulletSPI>) getBulletSPIs();
                for (int i = 0; i < bulletSPIArrayList.size(); i++) {
                    if(shootingCounter > shootingInterval) {
                        world.addEntity(bulletSPIArrayList.get(i).createBullet(player, gameData));
                        shootingCounter = 0;
                    } else{
                        shootingCounter++;
                    }
                }
            }
            
        if (player.getX() < 0) {
            player.setX(1);
        }

        if (player.getX() > gameData.getDisplayWidth()) {
            player.setX(gameData.getDisplayWidth()-1);
        }

        if (player.getY() < 0) {
            player.setY(1);
        }

        if (player.getY() > gameData.getDisplayHeight()) {
            player.setY(gameData.getDisplayHeight()-1);
        }
            
                                        
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
