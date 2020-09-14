<div align="left">
  <img src=jblst.png>
</div>

# jblst

Java wrapper around Blst BLS native library from Supranational
https://github.com/supranational/blst

# Dependency 

```xml
<dependency>
  <groupId>tech.pegasys</groupId>
  <artifactId>jblst</artifactId>
  <version>0.2.0-RELEASE</version>
  <type>pom</type>
</dependency>
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
- ARMv8
  - Linux

