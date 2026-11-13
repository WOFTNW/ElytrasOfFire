package org.woftnw.eof.io;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.Tribe;
import org.woftnw.eof.exception.TribeNotFoundException;

import java.util.UUID;

/**
 * A {@link PersistentDataType} that represents a {@link Tribe}.
 */
public class TribePersistentDataType implements PersistentDataType<String, Tribe> {
	private static final Class<String> PRIMITIVE_TYPE = String.class;
	private static final Class<Tribe> COMPLEX_TYPE = Tribe.class;

	@Override
	public @NotNull Class<String> getPrimitiveType() {
		return PRIMITIVE_TYPE;
	}

	@Override
	public @NotNull Class<Tribe> getComplexType() {
		return COMPLEX_TYPE;
	}

	@Override
	public @NonNull String toPrimitive(@NonNull Tribe complex, @NotNull PersistentDataAdapterContext context) {
		return complex.getPDCValue();
	}

	/**
	 * Creates a complex object based of the passed primitive value.
	 * <p>
	 * This will throw a {@link TribeNotFoundException} if the tribe can not be found.
	 * </p>
	 *
	 * @param primitive the primitive value
	 * @param context the context this operation is running in
	 * @return the complex object instance
	 */
	@Override
	public @NonNull Tribe fromPrimitive(@NonNull String primitive, @NotNull PersistentDataAdapterContext context) {
		final ElytrasOfFire PLUGIN = ElytrasOfFire.getPlugin(ElytrasOfFire.class);
		final Tribe TRIBE = PLUGIN.getTribeManager().getTribeFromPDCValue(primitive);
		if (TRIBE == null) {
			throw new TribeNotFoundException(primitive);
		}
		return TRIBE;
	}
}
