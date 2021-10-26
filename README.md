# jblst

[![Build Status](https://circleci.com/gh/ConsenSys/jblst.svg?style=svg)](https://circleci.com/gh/ConsenSys/workflows/jblst)

Java wrapper around [Blst BLS native library from Supranational](https://github.com/supranational/blst)


# Dependency 

```groovy
repositories {
  maven { url "https://artifacts.consensys.net/public/maven/maven/" }
}
dependencies {
  dependency "tech.pegasys:jblst:<version>"
}
```

# Minimal sample

```java
// Load native library for current platform
JBlst.loadNativeLibrary();
    
// Generating new private key
scalar sk = new scalar();
blst.keygen(sk, new byte[] {11}, null);
```

# Supported platforms
- x86-64
  - Windows
  - Linux
  - MacOS
- aarch64
  - Linux
  - MacOS

