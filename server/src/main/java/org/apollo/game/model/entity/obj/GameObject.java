package org.apollo.game.model.entity.obj;

import com.google.common.base.MoreObjects;
import nulled.runescript.data.NextLocStage;
import nulled.cache.def.LocDefinition;
import org.apollo.game.model.Direction;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.model.area.EntityUpdateType;
import org.apollo.game.model.area.Region;
import org.apollo.game.model.area.update.GroupableEntity;
import org.apollo.game.model.area.update.ObjectUpdateOperation;
import org.apollo.game.model.entity.Entity;
import org.apollo.game.model.entity.Player;

import static org.apollo.game.model.entity.obj.ObjectType.RECTANGULAR_CORNER;
import static org.apollo.game.model.entity.obj.ObjectType.TRIANGULAR_CORNER;

/**
 * Represents an object in the game world.
 *
 * @author Chris Fletcher
 * @author Major
 */
public abstract class GameObject extends Entity implements GroupableEntity {

	/**
	 * The packed value that stores this object's id, type, and orientation.
	 */
	private final int packed;

	/**
	 * Creates the GameObject.
	 *
	 * @param world The {@link World} containing the GameObject.
	 * @param type The id of the GameObject
	 * @param position The {@link Position} of the GameObject.
	 * @param shape The type of the GameObject.
	 * @param angle The orientation of the GameObject.
	 */
	public GameObject(World world, int type, Position position, int shape, int angle) {
		super(world, position);
		packed = type << 8 | (shape & 0x3F) << 2 | angle & 0x3;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameObject) {
			GameObject other = (GameObject) obj;
			return position.equals(other.position) && packed == other.packed;
		}

		return false;
	}

	/**
	 * Gets the definition of this object.
	 *
	 * @return The object's definition.
	 */
	public LocDefinition getDefinition() {
		return LocDefinition.Companion.lookup(getType());
	}

	/**
	 * Gets this object's id.
	 *
	 * @return The id.
	 */
	public int getType() {
		return packed >>> 8;
	}

	/**
	 * Gets this object's orientation.
	 *
	 * @return The orientation.
	 */
	public int getAngle() {
		return packed & 0x3;
	}

	/**
	 * Gets this object's type.
	 *
	 * @return The type.
	 */
	public int getShape() {
		return packed >> 2 & 0x3F;
	}

	@Override
	public int getLength() {
		return isRotated() ? getDefinition().getWidth() : getDefinition().getLength();
	}

	@Override
	public int getWidth() {
		return isRotated() ? getDefinition().getLength() : getDefinition().getWidth();
	}

	/**
	 * Returns whether or not this GameObject's orientation is rotated {@link Direction#WEST} or {@link Direction#EAST}.
	 *
	 * @return {@code true} iff this GameObject's orientation is rotated.
	 */
	public boolean isRotated() {
		int orientation = getAngle();
		int type = getShape();
		Direction direction = Direction.WNES[orientation];

		if (type == TRIANGULAR_CORNER.getValue() || type == RECTANGULAR_CORNER.getValue()) {
			direction = Direction.WNES_DIAGONAL[orientation];
		}

		return direction == Direction.NORTH || direction == Direction.SOUTH
			|| direction == Direction.NORTH_WEST || direction == Direction.SOUTH_EAST;
	}

	@Override
	public int hashCode() {
		return packed;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", getType()).add("type", getShape())
				.add("orientation", getAngle()).toString();
	}

	@Override
	public ObjectUpdateOperation toUpdateOperation(Region region, EntityUpdateType operation) {
		return new ObjectUpdateOperation(region, operation, this);
	}

	/**
	 * Returns whether or not this GameObject can be seen by the specified {@link Player}.
	 *
	 * @param player The Player.
	 * @param world The {@link World} containing the GameObject.
	 * @return {@code true} if the Player can see this GameObject, {@code false} if not.
	 */
	public abstract boolean viewableBy(Player player, World world);

	public String getRuneScriptName() {
		return "loc_" + getDefinition().getId();
	}

	public int getNextStage() {
		return NextLocStage.INSTANCE.get(getRuneScriptName());
	}
}