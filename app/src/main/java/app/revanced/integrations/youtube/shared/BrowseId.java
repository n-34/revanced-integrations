package app.revanced.integrations.youtube.shared;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Objects;

import app.revanced.integrations.shared.utils.Logger;

@Deprecated
@SuppressWarnings("unused")
public final class BrowseId {
    private static final String BROWSE_ID_DEFAULT = "FEwhat_to_watch";
    private static final String BROWSE_ID_HISTORY = "FEhistory";
    private static final String BROWSE_ID_LIBRARY = "FElibrary";

    /**
     * Current browse id.
     */
    private static volatile String browseId = BROWSE_ID_DEFAULT;
    /**
     * Field where the BrowseId is saved. (type: String)
     */
    private static volatile Field browseIdField;
    /**
     * Class to handle BrowseId.
     */
    private static WeakReference<Object> browseIdRef;

    /**
     * Injection point.
     * <p>
     * Access BrowseId field using Java Reflection.
     *
     * @param browseIdClass     class to handle BrowseId.
     * @param browseIdFieldName field where the BrowseId is saved. (type: String)
     */
    public static void initialize(@NonNull Object browseIdClass, @NonNull String browseIdFieldName) {
        try {
            browseIdRef = new WeakReference<>(Objects.requireNonNull(browseIdClass));

            Field field = browseIdClass.getClass().getDeclaredField(browseIdFieldName);
            field.setAccessible(true);
            browseIdField = field;
        } catch (Exception ex) {
            Logger.printException(() -> "Failed to initialize", ex);
        }
    }

    /**
     * Injection point.
     */
    public static void setBrowseIdFromField() {
        try {
            if (browseIdField == null || browseIdRef == null) {
                return;
            }
            final Object browseIdFieldReference = browseIdRef.get();
            if (browseIdFieldReference == null) {
                return;
            }
            final Object browseIdFieldObject = browseIdField.get(browseIdFieldReference);
            if (browseIdFieldObject == null) {
                return;
            }
            final String newlyLoadedBrowseId = browseIdFieldObject.toString();
            if (newlyLoadedBrowseId.isEmpty() || Objects.equals(browseId, newlyLoadedBrowseId)) {
                return;
            }
            browseId = newlyLoadedBrowseId;
            Logger.printDebug(() -> "setBrowseIdFromField: " + browseId);
        } catch (Exception ex) {
            Logger.printException(() -> "Failed to setBrowseIdFromField", ex);
        }
    }

    /**
     * Save a new value to BrowseId field.
     */
    public static void setBrowseIdToField(@NonNull String newlyLoadedBrowseId) {
        try {
            if (browseIdField == null || browseIdRef == null) {
                return;
            }
            final Object browseIdFieldReference = browseIdRef.get();
            if (browseIdFieldReference == null) {
                return;
            }
            if (Objects.equals(browseId, newlyLoadedBrowseId)) {
                return;
            }
            browseId = newlyLoadedBrowseId;
            browseIdField.set(browseIdFieldReference, newlyLoadedBrowseId);
        } catch (Exception ex) {
            Logger.printException(() -> "Failed to setBrowseIdToField", ex);
        }
    }

    public static void setDefaultBrowseIdToField() {
        setBrowseIdToField(BROWSE_ID_DEFAULT);
    }

    public static boolean isHistory() {
        return browseId.equals(BROWSE_ID_HISTORY);
    }

    public static boolean isLibrary() {
        return browseId.equals(BROWSE_ID_LIBRARY);
    }

}


