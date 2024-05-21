package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author corfixen
 */


public interface BulletSPI {
/**
 * Creates a new bullet entity based on the given shooter entity and game data.
 *
 * Preconditions: gameData must not be null, entity must not be null
 *
 * Postconditions: A bullet is created and returned
 *
 * @param entity The entity that is shooting the bullet. This could be an enemy, player, or other entity.
 * @param gameData The current state of the game, including display dimensions, input states, and more.
 * @return The newly created bullet entity.
 */
    Entity createBullet(Entity entity, GameData gameData);
}
