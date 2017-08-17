package com.yubico.gradle.plugins.signing.gpg;

import com.yubico.gradle.plugins.signing.gpg.signatory.GpgSignatoryProvider;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.plugins.signing.SigningExtension;
import org.gradle.plugins.signing.SigningPlugin;

public class GpgSigningPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    project.getPluginManager().apply(SigningPlugin.class);

    project.getExtensions().getByType(SigningExtension.class).setSignatories(new GpgSignatoryProvider());
  }

}
