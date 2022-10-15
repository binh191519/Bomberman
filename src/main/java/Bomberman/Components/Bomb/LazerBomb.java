package Bomberman.Components.Bomb;

import static Bomberman.BombermanType.BOMB;
import static Bomberman.BombermanType.FLAME;
import static Bomberman.BombermanType.PLAYER;
import static Bomberman.Constants.Constant.TILED_SIZE;
import static com.almasb.fxgl.dsl.FXGL.getGameTimer;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.almasb.fxgl.dsl.FXGL.onCollision;
import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGL.onCollisionEnd;
import static com.almasb.fxgl.dsl.FXGL.play;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import Bomberman.Components.PlayerComponent;
import Bomberman.DynamicEntityState.State;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.util.Duration;

public class LazerBomb extends BombComponent{
  private State direction;
  public LazerBomb() {
    onCollision(BOMB, FLAME, (bomb, flame) -> {
      if (bomb != null) bomb.getComponent(LazerBomb.class).explode();
    });

    direction = getGameWorld().getSingleton(PLAYER).getComponent(PlayerComponent.class).getState();
  }

  @Override
  public void explode() {
    super.explode();

    spawn("central_flame", new SpawnData(entity.getX(), entity.getY(), 1));
    switch (direction) {
      case UP:
        for (int i = 1; i < flames; i++) {
          spawn("up_flame", new SpawnData(entity.getX(), entity.getY() - TILED_SIZE, 1));
        }
        spawn("top_up_flame", new SpawnData(entity.getX(), entity.getY() - TILED_SIZE, 1));
        break;
      case RIGHT:
        for (int i = 1; i < flames; i++) {
          spawn("right_flame", new SpawnData(entity.getX() + TILED_SIZE, entity.getY(), 1));
        }
        spawn("top_right_flame", new SpawnData(entity.getX() + TILED_SIZE, entity.getY(), 1));
        break;
      case DOWN:
        for (int i = 1; i < flames; i++) {
          spawn("down_flame", new SpawnData(entity.getX(), entity.getY() + TILED_SIZE, 1));
        }
        spawn("top_down_flame", new SpawnData(entity.getX(), entity.getY() + TILED_SIZE, 1));
        break;
      case LEFT:
        for (int i = 1; i < flames; i++) {
          spawn("left_flame", new SpawnData(entity.getX() - TILED_SIZE, entity.getY(), 1));
        }
        spawn("top_left_flame", new SpawnData(entity.getX() - TILED_SIZE, entity.getY(), 1));
        break;
      case STOP:
      case DIE:
        break;
    }
  }
}
