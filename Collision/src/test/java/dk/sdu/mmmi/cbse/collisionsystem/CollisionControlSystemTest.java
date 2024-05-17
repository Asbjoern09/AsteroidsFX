package dk.sdu.mmmi.cbse.collisionsystem;


import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CollisionControlSystemTest {

    private CollisionControlSystem collisionControlSystem;

    @Test
    public void testBulletShooterCollision() {
        collisionControlSystem = new CollisionControlSystem();

        Bullet bullet = mock(Bullet.class);
        Entity shooter = mock(Entity.class);

        when(bullet.getParentID()).thenReturn("123");
        when(shooter.getID()).thenReturn("123");

        assertTrue(collisionControlSystem.bulletShooterCollision(bullet, shooter));
    }

    @Test
    public void testBulletNotShooterCollision() {
        collisionControlSystem = new CollisionControlSystem();
        Bullet bullet = mock(Bullet.class);
        Entity shooter = mock(Entity.class);

        when(bullet.getParentID()).thenReturn("123");
        when(shooter.getID()).thenReturn("222");

        assertFalse(collisionControlSystem.bulletShooterCollision(bullet, shooter));
    }
}
