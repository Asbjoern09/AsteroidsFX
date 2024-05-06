//package dk.sdu.mmmi.cbse.enemysystem;
//
//import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
//import dk.sdu.mmmi.cbse.common.bullet.Bullet;
//import dk.sdu.mmmi.cbse.common.data.World;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.UUID;
//
//import static org.junit.Assert.*;
//
//public class EnemyTest {
//
//    private Enemy enemy;
//    private World world;
//
//    @Before
//    public void setUp() {
//        enemy = new Enemy();
//        world = new World();
//        world.addEntity(enemy);
//
//    }
//
//
//    // tests collision with asteroid
//    @Test
//    public void testHandleCollisionWithAsteroid() {
//        Asteroid asteroid = new Asteroid();
//
//        world.addEntity(asteroid);
//        enemy.handleCollision(asteroid, world);
//
//        assertFalse(world.getEntities().contains(enemy));
//    }
//
//
//    // tests collision with bullets from different ship
//    @Test
//    public void testHandleCollisionWithBulletFromDifferentParent() {
//        int oldHp = enemy.getHp();
//        Bullet bullet = new Bullet(UUID.randomUUID().toString());
//
//        enemy.handleCollision(bullet, world);
//        assertTrue(enemy.getHp() < oldHp);
//
//        for (int i = 0; i < 5; i++) {
//            enemy.handleCollision(bullet, world);
//        }
//        assertFalse(world.getEntities().contains(enemy));
//    }
//
//
//    // tests collision with bullets from parent ship
//    @Test
//    public void testHandleCollisionWithBulletFromSameParent() {
//        Bullet bullet = new Bullet(enemy.getID());
//        bullet.setParentID(enemy.getID());
//        int oldHp = enemy.getHp();
//
//        enemy.handleCollision(bullet, world);
//
//        assertEquals(oldHp, enemy.getHp());
//
//        for (int i = 0; i < 5; i++) {
//            enemy.handleCollision(bullet, world);
//        }
//
//        assertTrue(world.getEntities().contains(enemy));
//    }
//}
