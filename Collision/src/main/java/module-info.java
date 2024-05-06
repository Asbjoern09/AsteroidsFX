import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonAsteroid;
    requires CommonBullet;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionControlSystem;
}