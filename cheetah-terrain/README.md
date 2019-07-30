# Cheetah Terrain

Cheetah Terrain a program to listen for incoming calls from Cheetah Automation API to dynamically create/destroy nodes while executing automated test cases using the Selenium GRID. 

  
## Objective

To provide a program that can intelligently create or destoy Selenium nodes on demand, thus removing the need to manually start-stop the nodes for each test suite. In order for this program to run as intended, the Selenium Grid and Terrain program should be initialized properly.

## Before you Start
Please note that this program currently works only in a Windows environment. If you are using a different Operating System, you will be unable to use the Terrain program at this time. Updates will come soon...

## Getting Started

* Download the Terrain archive file.
* Extract it in the server/machine you wish to use as part of your Selenium grid. 
* Execute the Selenium Grid batch file
* Execute the Turn batch file
 
Since the Terrain program is used with the **Selenium GRID**, ensure that the Selenium Hub has been started and start the post-listener on the required Server/machine by running the command: java -jar cheetah-terrain.jar.

**Note:** It is beneficial to ruin the Selenium Grid and Terrain programs with Admin right in order to gain complete control of your system environment.  


## Versioning

We follow the [Semantic Versioning](https://semver.org/) format: {MAJOR}.{MINOR}.{BUILD} for versioning *Cheetah*

* **MAJOR**: Major version changes are changes to the *Cheetah* framework to keep in par with updates to Core *Java*, *Selenium* and *Appium* technologies and versions. Changes to Java, Selenium and Appium are extenal to the Cheetah Framework. A change in these technologies does not warrant a change to the Cheetah Framework.
* **MINOR**: Minor changes are changes to the  *Cheetah* framework to include feature requests, enhancements and bug fixes.
* **BUILD**: Build changes are sequential increments to the build version are recorded in Bamboo.

## Authors

* **Gundlupet Sreenidhi** - sreenidhi.gsd@gmail.com - *Application Architect* 
