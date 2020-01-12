FROM arm32v7/maven:3.6-jdk-8-alpine AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

# Start with a base image containing Java runtime
FROM arm32v7/openjdk

# Database persitency mount point
WORKDIR /db
WORKDIR /

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/guild-members-hero-manager-*SNAPSHOT.jar guild-members-hero-manager.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/shopping-list.jar"]