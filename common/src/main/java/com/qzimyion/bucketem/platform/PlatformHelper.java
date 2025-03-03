package com.qzimyion.bucketem.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class PlatformHelper {

    @ExpectPlatform
    public static Side getPhysicalSide() {
        throw new AssertionError();
    }

    public enum Side {
        CLIENT, SERVER;

        public boolean isClient() {
            return this == CLIENT;
        }

        public boolean isServer() {
            return this == SERVER;
        }
    }
}
