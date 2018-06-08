package com.yubico.gradle.plugins.signing.gpg.signatory

import static com.yubico.gradle.plugins.signing.gpg.signatory.GpgSignatory.copy
import static com.yubico.gradle.plugins.signing.gpg.signatory.GpgSignatory.toString

import org.gradle.plugins.signing.signatory.Signatory
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class GpgSignatoryIT extends Specification {

  private static final String KEY_ID = "test@test.org"

  @Rule
  public final TemporaryFolder tmpDir = new TemporaryFolder();

  private File gnupgHome;

  def setup() {
    gnupgHome = tmpDir.newFolder("gnupg")

    ProcessBuilder gpgProcessBuilder = new ProcessBuilder("gpg", "--batch", "--gen-key")
    gpgProcessBuilder.redirectErrorStream(true)
    gpgProcessBuilder.environment().put("GNUPGHOME", gnupgHome.absolutePath)

    Process gpgProcess = gpgProcessBuilder.start()
    copy(getClass().getResourceAsStream("/genkey.gpgbatch"), gpgProcess.getOutputStream())
    try {
      gpgProcess.getOutputStream().close()
    } catch (IOException e) {
      // Ignore
    }

    gpgProcess.waitFor()
  }

  def 'Signature is written correctly.'() {
    when:
      URL payload = getClass().getResource("/payload.txt")
      File signatureFile = tmpDir.newFile("payload.txt.sig")

      Signatory gpgSignatory = new GpgSignatory(KEY_ID, gnupgHome);

      OutputStream signatureStream = new FileOutputStream(signatureFile)

      gpgSignatory.sign(getClass().getResourceAsStream("/payload.txt"), signatureStream)

      ProcessBuilder gpgProcessBuilder = new ProcessBuilder("gpg", "--verify", signatureFile.getAbsolutePath(), payload.getPath())
        .redirectErrorStream(true)

      gpgProcessBuilder.environment().put("GNUPGHOME", gnupgHome.absolutePath)

      Process gpgProcess = gpgProcessBuilder.start()
      gpgProcess.waitFor()

      String gpgOutput = toString(gpgProcess.getInputStream())

    then:
      gpgOutput.contains('Good signature from "Test Testsson <test@test.org>"')
  }

}
