package com.yubico.gradle.plugins.signing.gpg.signatory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.gradle.plugins.signing.signatory.SignatorySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GpgSignatory extends SignatorySupport {

    private static final Logger logger = LoggerFactory.getLogger(GpgSignatory.class);

    private final String keyId;
    private final File gpgHome;

    GpgSignatory(String keyId, File gpgHome) {
        if (keyId == null || keyId.isEmpty()) {
            throw new IllegalArgumentException("keyId must not be null.");
        }
        this.keyId = keyId;
        this.gpgHome = gpgHome;
    }

    public GpgSignatory(String keyId) {
        this(keyId, null);
    }

    @Override
    public String getName() {
        return keyId;
    }

    @Override
    public void sign(InputStream toSign, OutputStream destination) {
        try {
            ProcessBuilder gpgProcessBuilder = new ProcessBuilder("gpg", "--local-user", keyId, "--detach-sign");

            if (gpgHome != null) {
                logger.debug("Using GNUPGHOME: " + gpgHome.getAbsolutePath());
                gpgProcessBuilder.environment().put("GNUPGHOME", gpgHome.getAbsolutePath());
            }

            Process gpgProcess = gpgProcessBuilder.start();
            IOUtils.copy(toSign, gpgProcess.getOutputStream());

            try {
                gpgProcess.getOutputStream().close();
            } catch (IOException e) {
                logger.warn("Failed to close gpg process STDIN stream", e);
            }

            gpgProcess.waitFor();

            if (gpgProcess.exitValue() == 0) {
                IOUtils.copy(gpgProcess.getInputStream(), destination);
            } else {
                final String stdout = IOUtils.toString(gpgProcess.getInputStream(), "UTF-8");
                final String stderr = IOUtils.toString(gpgProcess.getErrorStream(), "UTF-8");
                logger.error("gpg process failed with exit code: {}.", gpgProcess.exitValue());
                logger.error("STDOUT: [{}]", stdout);
                logger.error("STDERR: [{}]", stderr);
                throw new IOException("gpg process failed with exit code: " + gpgProcess.exitValue() + ". STDOUT: [" + stdout + "], STDERR: [" + stderr + "]");
            }

        } catch (IOException|InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
