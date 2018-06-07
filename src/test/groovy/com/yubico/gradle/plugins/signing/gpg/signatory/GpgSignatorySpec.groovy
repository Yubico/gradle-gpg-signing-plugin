package com.yubico.gradle.plugins.signing.gpg.signatory

import spock.lang.Specification

class GpgSignatorySpec extends Specification {

  def 'Constructor requires a nonempty keyId.'() {
    when:
      new GpgSignatory(null)

    then:
      thrown(IllegalArgumentException)

    when:
      new GpgSignatory("")

    then:
      thrown(IllegalArgumentException)
  }

  def 'getName() returns the keyId.'() {
    expect:
      new GpgSignatory('C001C0DE').name == 'C001C0DE'
  }

  def 'getKeyId() returns the keyId.'() {
    expect:
    new GpgSignatory('C001C0DE').keyId == 'C001C0DE'
  }

}
