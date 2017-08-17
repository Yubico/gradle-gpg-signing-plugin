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

}
