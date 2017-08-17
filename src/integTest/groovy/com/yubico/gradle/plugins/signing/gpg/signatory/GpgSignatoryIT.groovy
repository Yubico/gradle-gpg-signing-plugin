package com.yubico.gradle.plugins.signing.gpg.signatory

import org.apache.commons.io.IOUtils
import org.gradle.plugins.ide.eclipse.model.Output
import org.gradle.plugins.signing.signatory.Signatory
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class GpgSignatoryIT extends Specification {

  private static final String KEY_ID = "B84D477DA20D6764"

  @Rule
  public final TemporaryFolder tmpDir = new TemporaryFolder();

  def 'Signature is written correctly.'() {
    when:
      URL payload = getClass().getResource("/payload.txt")
      File signatureFile = tmpDir.newFile("payload.txt.sig")

      println("Writing signature to: ${signatureFile}")
      println("Payload in: ${payload.getPath()}")
      println("Payload: ${payload.openStream().text}")
      println("Using GNUPGHOME in: ${getClass().getResource("/gnupg").getPath()}")

      Signatory gpgSignatory = new GpgSignatory(KEY_ID, new File(getClass().getResource("/gnupg").toURI()));


      OutputStream signatureStream = new FileOutputStream(signatureFile)

      gpgSignatory.sign(getClass().getResourceAsStream("/payload.txt"), signatureStream)

      println("Wrote signature: ${signatureFile.text}")

      ProcessBuilder gpgProcessBuilder = new ProcessBuilder("gpg", "--verify", signatureFile.getAbsolutePath(), payload.getPath())
        .redirectErrorStream(true)

      gpgProcessBuilder.environment().put("GNUPGHOME", getClass().getResource("/gnupg").getPath())

      Process gpgProcess = gpgProcessBuilder.start()
      gpgProcess.waitFor()

      String gpgOutput = IOUtils.toString(gpgProcess.getInputStream(), "UTF-8")

    then:
      gpgOutput.contains('Good signature from "Test Testsson <test@test.org>"')
  }

}
