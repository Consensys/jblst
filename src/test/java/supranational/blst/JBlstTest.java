package supranational.blst;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JBlstTest {

  @Test
  void sanityTest() {
    Assertions.assertDoesNotThrow(JBlst::loadNativeLibrary);
  }
}
