# ODBC Healthchecker

This project builds a docker container that checks the health of an Oracle database connection. This is a Java dropwizard application that can be run in a docker container.

## Installation

### Adding the odbc jar to your project
The first step to compile this application is to add the odbc jar to your maven application. Due to licensing restrictions, this jar cannot be pulled from a public maven repository. There are many ways to achieve this goal, and I'll document one way to do this. Feel free to use whatever way that might be suitable for you. 

..* Download suitable JDBC thin driver from Oracle after accepting license: [link](http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html). For this example, I chose the thin driver for Oracle Database 12.1.0.2 JDBC Driver & UCP Download (ojdbc7.jar).
..* Add odbc7.jar to your src/lib folder
..* To your pom.xml, reference your newly added jar and your src/lib folder using the maven install plugin. (This is already done for you).
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-install-plugin</artifactId>
    <version>2.5.2</version>
	<executions>
	       <execution>
	           <id>install-oracle-jdbc</id>
	           <goals>
	               <goal>install-file</goal>
	           </goals>
	           <phase>clean</phase>
	           <configuration>
	               <groupId>com.oracle</groupId>
	               <artifactId>ojdbc7</artifactId>
	               <version>12.1.0.1</version>
	               <packaging>jar</packaging>
	               <generatePom>true</generatePom>
	               <createChecksum>true</createChecksum>
	               <file>${project.basedir}/src/lib/ojdbc7.jar</file>
	           </configuration>
	       </execution>
	</executions>
</plugin>
```
..* To your pom.xml, reference this newly added jar in your dependency tree as shown below. (This is already done for you).

```
<!-- odbc -->
<dependency>
    <groupId>com.oracle</groupId>
    <artifactId>ojdbc7</artifactId>
    <version>12.1.0.1</version>
</dependency>
```

..* Save your pom.xml changes and run a make clean. Verify that it is successful.

## Usage

..* Build the application with a `mvn clean package`
..* Add your custom database parameters to the main.yml file
..* Run the application with a `java -jar ./target/holdinarms-0.0.1.jar server main.yml`
..* Visit [localhost:9001/healthcheck](http://localhost:9001/healthcheck)

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D


## License

MIT