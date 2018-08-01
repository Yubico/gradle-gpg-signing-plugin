gradle-gpg-signing-plugin
=========================

***UNMAINTAINED:*** _The features provided by this plugin are now included in
Gradle since [release 4.5][release-notes]. Work on this plugin has therefore
been abandoned as it is no longer needed._

To sign artifacts via `gpg-agent` in Gradle 4.5 and later, use the following
build script snippet from the [release notes][release-notes]:

```
signing {
    useGpgCmd()
    sign configurations.archives
}
```

Previous contents of this README file are available in Git history.

[release-notes]: https://docs.gradle.org/4.5/release-notes.html#signing-artifacts-with-gpg-agent
