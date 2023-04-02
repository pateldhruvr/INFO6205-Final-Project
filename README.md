# INFO6205-Final-Project

## Instructions

* Project was run on java for following JDK Version
	* java -version
		* java version "18.0.2.1" 2022-08-18
		* Java(TM) SE Runtime Environment (build 18.0.2.1+1-1)
	 	* Java HotSpot(TM) 64-Bit Server VM (build 18.0.2.1+1-1, mixed mode, sharing)
	 * javac -version
		* javac 18.0.2.1

## Commands

* Maven command to run main method of some other class
	* mvn clean compile org.codehaus.mojo:exec-maven-plugin:1.5.0:java -Dexec.mainClass="**class-full-qualified-name**" -Dexec.args="**command-line-args**"

* Maven command to clean, compile, pass external logging configuration file and run JavaFX:
	* mvn clean compile javafx:run -Dlog4j.configurationFile=**log-file-location**
	* mvn clean compile javafx:run -Dlog4j.configurationFile=config-log-files/log4j2.xml

* System paramter to set the log4j configuration File:
	* log4j.configurationFile

* Git commands
	* Git log graph check
		* git log --decorate --graph
	* Git sync remote branches:
		* git remote update origin --prune

## References

* Creating JavaFX Project using maven
	*  https://openjfx.io/openjfx-docs/#maven
