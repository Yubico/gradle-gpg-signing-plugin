gradle-gpg-signing-plugin
=========================

This plugin sets up Gradle's [Signing plugin][signing] to use `gpg` as the
signing backend. Thus key management is delegated to `gpg`, which unlocks
features such as supporting keys stored on smartcards.

NOTE: This is currently a work in progress, and is not released to public
repositories yet.


[signing]: https://docs.gradle.org/current/userguide/signing_plugin.html


Usage
-----

The plugin is currently pre-alpha and not available in public repositories just
yet. For now, you can install it in your build by cloning and building this
repository, and including the jar in your build classpath like so:

```gradle
buildscript {
  dependencies {
    classpath files('gradle-gpg-signing-plugin-0.1.0.jar')
  }
}

apply plugin: com.yubico.gradle.plugins.signing.gpg.GpgSigningPlugin
```

The plugin has no settings. If you need to use another signing backend, don't
apply this plugin.

Set the `signing.keyId` in your user `gradle.properties` as described in the
[Signing plugin manual][signing-credentials]:

    signing.keyId=5FF0B636

This value is passed through to the `gpg` command-line, and can therefore be
appended with a `!` to force `gpg` to use a specific subkey if needed.

That's it! Now just follow the instructions for the [Signing plugin][signing],
except ignoring setting `signing.password` and `signing.secretKeyRingFile`, and
you should be good to go.


[signing-credentials]: https://docs.gradle.org/current/userguide/signing_plugin.html#sec:signatory_credentials