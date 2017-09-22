package com.yubico.gradle.plugins.signing.gpg;

import com.yubico.gradle.plugins.signing.gpg.signatory.GpgSignatoryProvider;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.plugins.signing.Sign;
import org.gradle.plugins.signing.SigningExtension;
import org.gradle.plugins.signing.SigningPlugin;
import org.gradle.plugins.signing.signatory.Signatory;

import java.util.concurrent.Callable;

public class GpgSigningPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    project.getPluginManager().apply(SigningPlugin.class);

    project.getExtensions().getByType(SigningExtension.class).setSignatories(new GpgSignatoryProvider());
    project.getTasks().withType(Sign.class, new Action<Sign>() {
      @Override
      public void execute(Sign sign) {
        // Workaround bug that prevents using custom signatories
        sign.getInputs().property("signatory", new Callable<String>() {
          @Override
          public String call() throws Exception {
            return sign.getSignatory().getName();
          }
        });
      }
    });
  }

}
