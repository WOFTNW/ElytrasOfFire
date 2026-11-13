package org.woftnw.eof.io;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * A {@link PersistentDataType} that represents a {@link UUID}.
 */
public class UUIDPersistentDataType implements PersistentDataType<String, UUID> {
	private static final Class<String> PRIMITIVE_TYPE = String.class;
	private static final Class<UUID> COMPLEX_TYPE = UUID.class;

	@Override
	public @NotNull Class<String> getPrimitiveType() {
		return PRIMITIVE_TYPE;
	}

	@Override
	public @NotNull Class<UUID> getComplexType() {
		return COMPLEX_TYPE;
	}

	@Override
	public @NonNull String toPrimitive(@NonNull UUID complex, @NotNull PersistentDataAdapterContext context) {
		return complex.toString();
	}

	@Override
	public @NonNull UUID fromPrimitive(@NonNull String primitive, @NotNull PersistentDataAdapterContext context) {
		return UUID.fromString(primitive);
	}
}
