package com.yubico.gradle.plugins.signing.gpg;

import com.yubico.gradle.plugins.signing.gpg.signatory.GpgSignatoryProvider;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.plugins.signing.SigningExtension;
import org.gradle.plugins.signing.SigningPlugin;

/**
 * @deprecated Included in Gradle 4.5.
 */
@Deprecated
public class GpgSigningPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    project.getPluginManager().apply(SigningPlugin.class);

    Logging.getLogger(GpgSigningPlugin.class).lifecycle("WARNING: com.yubico.gradle.plugins.signing.gpg.GpgSigningPlugin is no longer maintained since its features are included in Gradle 4.5 and later. Please upgrade to Gradle 4.5 instead.");

    project.getExtensions().getByType(SigningExtension.class).setSignatories(new GpgSignatoryProvider());
  }

}
