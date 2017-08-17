package com.yubico.gradle.plugins.signing.gpg.signatory;

import groovy.lang.Closure;
import org.gradle.api.Project;
import org.gradle.plugins.signing.SigningExtension;
import org.gradle.plugins.signing.signatory.SignatoryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GpgSignatoryProvider implements SignatoryProvider<GpgSignatory> {

    private static final Logger logger = LoggerFactory.getLogger(GpgSignatoryProvider.class);

    @Override
    public void configure(SigningExtension settings, Closure closure) {
        logger.debug("configure(${settings}, ${closure}");
    }

    @Override
    public GpgSignatory getDefaultSignatory(Project project) {
        return new GpgSignatory((String) project.getProperties().get("signing.keyId"));
    }

    @Override
    public GpgSignatory getSignatory(String name) {
        return new GpgSignatory(name);
    }

}
