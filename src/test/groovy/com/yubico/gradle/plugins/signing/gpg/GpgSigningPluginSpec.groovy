package com.yubico.gradle.plugins.signing.gpg.signatory

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class GpgSigningPluginSpec extends Specification {

  def 'The plugin sets project.signing.signatories to a GpgSignatoryProvider.'() {
    when:
      Project project = ProjectBuilder.builder().build()
      project.pluginManager.apply 'com.yubico.signing.gpg'

    then:
      project.signing.signatories.getClass().isAssignableFrom(GpgSignatoryProvider)
  }

}
