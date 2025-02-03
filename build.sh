# Specify the Maven settings file
./mvnw clean install dependency:resolve dependency:resolve-plugins dependency:go-offline -B -DskipTests=true -s .mvn/settings.xml --fail-never