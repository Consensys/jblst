package tech.pegasys.teku.bls.impl.blst;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.pegasys.teku.bls.impl.blst.swig.BLST_ERROR;
import tech.pegasys.teku.bls.impl.blst.swig.blst;
import tech.pegasys.teku.bls.impl.blst.swig.p1;
import tech.pegasys.teku.bls.impl.blst.swig.p1_affine;
import tech.pegasys.teku.bls.impl.blst.swig.p2;
import tech.pegasys.teku.bls.impl.blst.swig.p2_affine;
import tech.pegasys.teku.bls.impl.blst.swig.scalar;

public class JBlstTest {

  @Test
  void sanityTest() {
    JBlst.loadNativeLibrary();
    scalar sk = new scalar();
    blst.keygen(sk, new byte[] {11}, null);
    p1 pk = new p1();
    blst.sk_to_pk_in_g1(pk, sk);
    p2 sig = new p2();
    p2 msgHash = new p2();

    byte[] msg = new byte[16];
    byte[] dst = new byte[4];
    blst.hash_to_g2(msgHash, msg, dst, null);
    blst.sign_pk_in_g1(sig, msgHash, sk);

    p1_affine pkAff = new p1_affine();
    blst.p1_to_affine(pkAff, pk);
    p2_affine sigAff = new p2_affine();
    blst.p2_to_affine(sigAff, sig);
    BLST_ERROR blst_error = blst.core_verify_pk_in_g1(pkAff, sigAff, 1, msg, dst, null);
    Assertions.assertEquals(BLST_ERROR.BLST_SUCCESS, blst_error);
  }
}
