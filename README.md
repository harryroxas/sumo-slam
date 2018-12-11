# SUMO SLAM

## Installing Protobuf 3.6.1
```
curl -OL https://github.com/google/protobuf/releases/download/v3.6.1/protoc-3.6.1-linux-x86_64.zip
unzip protoc-3.6.1-linux-x86_64.zip -d protoc3
sudo mv protoc3/bin/* /usr/local/bin/
sudo mv protoc3/include/* /usr/local/include/
```

## Installing Apache Maven
```
unzip apache-maven-3.6.0-bin.zip
export PATH=/opt/apache-maven-3.6.0/bin:$PATH
```

## Running the Program
1. Install [protobuf 3.6.1](https://github.com/protocolbuffers/protobuf) and build with [maven](https://maven.apache.org/).
2. Compile the program with `mvn package`.
3. Run the application with `java -cp target/my-app-1.0-SNAPSHOT.jar com.main.app.Main`.
