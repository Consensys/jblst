package tech.pegasys.teku.bls.impl.blst;

public class JBlst {

  public static void loadNativeLibrary() throws UnsupportedOperationException {
    String libLocation = "/" + System.getProperty("os.arch") + "/" + System.mapLibraryName("jblst");
    try {
      NativeUtils.loadLibraryFromJar(libLocation);
    } catch (Throwable e) {
      throw new UnsupportedOperationException(
          "Couldn't load native BLS library (from resource " + libLocation + "): ", e);
    }
  }
}
