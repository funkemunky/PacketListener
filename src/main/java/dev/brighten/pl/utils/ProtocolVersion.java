package dev.brighten.pl.utils;

import dev.brighten.pl.utils.reflection.Reflection;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ProtocolVersion {
    v1_7_10(5, "v1_7_R4"),
    v1_8(45, "v1_8_R1"),
    v1_8_3(46, "v1_8_R2"),
    v1_8_9(47, "v1_8_R3"),
    v1_9(107, "v1_9_R1"),
    v1_9_1(108, "v1_9_R1"),
    v1_9_2(109, "v1_9_R2"),
    v1_9_4(110, "v1_9_R2"),
    v1_10(210, "v1_10_R1"),
    v1_10_2(210, "v1_10_R1"),
    v1_11(316, "v1_11_R1"),
    v1_12(335, "v1_12_R1"),
    v1_12_1(338, "v1_12_R1"),
    v1_12_2(340, "v1_12_R1"),
    UNKNOWN(-1, "N/A");
    
    
    private int versionNumber;
    private String serverVersion;
    public static ProtocolVersion VERSION = getVersionByString(Reflection.VERSION);

    public static ProtocolVersion getVersionByString(String serverVersion) {
        return Arrays.stream(values()).filter(val -> val.serverVersion.equals(serverVersion))
                .findFirst()
                .orElse(ProtocolVersion.UNKNOWN);
    }

    public static ProtocolVersion getVersionByNumber(int number) {
        return Arrays.stream(values()).filter(val -> val.versionNumber == number)
                .findFirst()
                .orElse(ProtocolVersion.UNKNOWN);
    }

    public boolean isAbove(ProtocolVersion version) {
        return versionNumber > version.versionNumber;
    }

    public boolean isOrAbove(ProtocolVersion version) {
        return versionNumber >= version.versionNumber;
    }

    public boolean isBelow(ProtocolVersion version) {
        return versionNumber < version.versionNumber;
    }

    public boolean isOrBelow(ProtocolVersion version) {
        return versionNumber <= version.versionNumber;
    }
}
