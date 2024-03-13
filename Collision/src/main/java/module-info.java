import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonAsteroid;
    uses AsteroidSPI;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionControlSystem;
}