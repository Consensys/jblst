package supranational.blst;

import java.util.Map;

public class JBlst {

  private static final Map<String, String> ARCH_MAPPING = Map.of(
      "x86_64", "x86_64",
      "amd64", "x86_64",
      "aarch64", "aarch64"
  );

  public static void loadNativeLibrary() throws UnsupportedOperationException {
    String libLocation = "/" + getArch() + "/" + System.mapLibraryName("blst");
    try {
      NativeUtils.loadLibraryFromJar(libLocation);
    } catch (Throwable e) {
      throw new UnsupportedOperationException(
          "Couldn't load native BLS library (from resource " + libLocation + "): ", e);
    }
  }

  private static String getArch() {
    String osArch = System.getProperty("os.arch");
    String arch = ARCH_MAPPING.get(osArch);
    if (arch == null) {
      throw new UnsupportedOperationException(
          "Unknown arch value from 'os.arch' property: '" + osArch + "'");
    }
    return arch;
  }
}
