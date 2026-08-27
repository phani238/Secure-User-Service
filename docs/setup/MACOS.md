# macOS Setup (Intel)

Verified Environment

- macOS 15
- Java 21
- Maven 3.9
- Docker Desktop
- Spring Tools for Eclipse
- Keycloak 26.7.2

## Important Fixes

### Java

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

### Verify

```bash
java --version
javac --version
mvn --version
```

### Lombok

Install Lombok into Spring Tools for Eclipse by pointing the installer to the Eclipse folder inside the STS application bundle.

### Maven

```bash
mvn clean compile
```

Expected result:

```text
BUILD SUCCESS
```