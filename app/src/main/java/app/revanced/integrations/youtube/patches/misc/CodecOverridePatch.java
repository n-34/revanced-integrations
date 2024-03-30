package app.revanced.integrations.youtube.patches.misc;

import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class CodecOverridePatch {

    public static String getManufacturer(String manufacturer) {
        if (!Settings.ENABLE_VIDEO_CODEC.get())
            return manufacturer;

        return Settings.ENABLE_VIDEO_CODEC_TYPE.get() ? "Samsung" : "Google";
    }

    public static String getBrand(String brand) {
        if (!Settings.ENABLE_VIDEO_CODEC.get())
            return brand;

        return Settings.ENABLE_VIDEO_CODEC_TYPE.get() ? "samsung" : "google";
    }

    public static String getModel(String model) {
        if (!Settings.ENABLE_VIDEO_CODEC.get())
            return model;

        return Settings.ENABLE_VIDEO_CODEC_TYPE.get() ? "SM-G955W" : "Pixel 7 Pro";
    }

    public static boolean shouldForceOpus() {
        return Settings.ENABLE_OPUS_CODEC.get();
    }

    public static boolean shouldForceCodec(boolean original) {
        return Settings.ENABLE_VIDEO_CODEC.get() || original;
    }
}
