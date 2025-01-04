# Start with the official Debian image
FROM debian:bullseye-slim

# Install OpenJDK 17 and Maven
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    maven \
    && rm -rf /var/lib/apt/lists/*  # Clean up the apt cache to reduce image size

# Set environment variables for Java and Maven
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV MAVEN_HOME=/usr/share/maven
ENV PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH

# Set the working directory
WORKDIR /app
# Declare the volume mount point inside the container
VOLUME ["/data"]

# Copy the project files into the container
COPY . .

# Command to run the Maven tests using the specified TestNG XML file
CMD ["mvn", "clean", "test", "-Dtestng.suiteXmlFiles=testng.xml"]
#CMD ["mvn", "clean", "test"]