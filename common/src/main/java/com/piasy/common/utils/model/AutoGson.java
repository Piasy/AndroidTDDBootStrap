package com.piasy.common.utils.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * https://gist.github.com/Piasy/fa507251da452d36b221
 *
 * Marks an {@link AutoValue @AutoValue}/{@link AutoParcel @AutoParcel}-annotated type for proper Gson serialization.
 * <p>
 * This annotation is needed because the {@linkplain Retention retention} of {@code @AutoValue}/{@code @AutoParcel}
 * does not allow reflection at runtime.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoGson {
    // A reference to the Auto*-generated class (e.g. AutoValue_MyClass/AutoParcel_MyClass). This is
    // necessary to handle obfuscation of the class names.
    Class autoClass();
}
