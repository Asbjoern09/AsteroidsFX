package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Entity.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world, double dt) {
        double bulletSpeed = 100;
        for (Entity bullet : world.getEntities(Bullet.class)) {
            double directionX = Math.cos(Math.toRadians(bullet.getRotation()));
            double directionY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + directionX * 3 * bulletSpeed * dt);
            bullet.setY(bullet.getY() + directionY * 3 * bulletSpeed * dt);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Bullet bulletFigure = new Bullet(shooter.getID());
        bulletFigure.setEnabled(true);
        bulletFigure.setParentID(shooter.getID());
        bulletFigure.setEntityType(EntityType.bullet);
        bulletFigure.setPolygonCoordinates(2, -2, 2, 2, -2, 2, -2, -2);
        bulletFigure.setX(shooter.getX());
        bulletFigure.setY(shooter.getY());
        bulletFigure.setRotation(shooter.getRotation());
        return bulletFigure;
    }


}
