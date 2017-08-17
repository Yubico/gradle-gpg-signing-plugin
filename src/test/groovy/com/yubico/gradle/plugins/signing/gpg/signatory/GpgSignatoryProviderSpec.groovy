package com.yubico.gradle.plugins.signing.gpg.signatory

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class GpgSignatoryProviderSpec extends Specification {

  def 'The default Signatory gets its keyId from the signing.keyId project property.'() {
    setup:
      Project project = ProjectBuilder.builder().build()
      project.ext.'signing.keyId' = 'C001C0DE'

    expect:
      new GpgSignatoryProvider().getDefaultSignatory(project).name == 'C001C0DE'
  }

  def 'Named signatories use the given name as the keyId.'() {
    expect:
      new GpgSignatoryProvider().getSignatory('C001C0DE').name == 'C001C0DE'
  }

}
