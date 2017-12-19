java -jar -Dserver.port=8888 ./ConfigServer/target/ConfigServer-0.0.1-SNAPSHOT.jar >> configserver.log &
java -jar -Dserver.port=8091 ./ServiceB/target/ServiceB-0.0.1-SNAPSHOT.jar >> serviceb.log &
java -jar -Dserver.port=8090 ./ServiceA/target/ServiceA-0.0.1-SNAPSHOT.jar >> servicea.log &
