## Using Cheetah

#### **Maven**
Cheetah is a java project that uses the maven architecture. In order to use Cheetah, you need to setup your system to use maven. 

Please follow the steps in <a href="https://confluence.sreenidhi-gsd.com/download/attachments/11207032/Maven%20setup.docx?version=1&modificationDate=1529949461710&api=v2">this document</a> to setup your machine to use Maven.

#### **Project Structure**
Cheetah requires your test project to follow a specific structure. You can download the default project structure skeleton from <a href=https://confluence.sreenidhi-gsd.com/display/Cheetah/Cheetah+Versions?preview=%2F14156098%2F14156113%2FStructure.7z>this link</a>.

#### **Cheetah version**
You need to identify the latest Release version of Cheetah. You can identify the version from <a href="https://confluence.sreenidhi-gsd/display/Cheetah/Cheetah+Versions">this link</a>.
Once the required version has been identified, you need to use the corresponding maven **pom.xml** file in your application.

* Major Release of Cheetah, there is a new pom.xml released. 
* Minor Release of Cheetah does not require a change in the pom.xml file - just update the *<Cheetah.version>* in your pom file with the version you want to use.

To learn more about versions, please refer to the [README.md](README.md) file


#### **Cheetah variables, properties and configurations**
Cheetah supports the use of different types of properties.
* Environment variables
* Application Properties
* Global Configurations

* **Environment Variables**:
Global Properties are properties that applies to all test scenarios in your application. These properties are all mandatory properties and applies in general to all applications that use Cheetah. These properties are controlled by the Framework Admin and is used for scheduling the Automated Testing in Bamboo. 

* **Cheetah Properties**:
Application properties are properties that apply to only the application under test. There are multiple properties that Cheetah supports and not all of them are mandatory.

* **Global Configurations**:
You need to complete all global configurations in order for the test suite to run successfully.

For a list of properties that Cheetah supports, please follow <a href="https://confluence.sreenidhi-gsd.com/display/Cheetah/Variables+and+Properties" target="_blank">this link</a>.


#### **Executables**
Cheetah supports automated testing for multiple technologies, including - Web, Mobile (Android & iOS), Mainframe (Emulated on Web), WebService, Database.
In order to accomplish this, Cheetah uses the Web Browsers and Emulators and Simulators installed on your computer. 
However, Cheetah cannot directly handshake with your Browsers or Emulators and Simulators.
* For Web Automation, Cheetah takes the help of WebDrivers to perform this handshake. WebDrivers provide the capability of interacting with the Web Browsers using an object-oriented API. 
The WebDrivers need to be downloaded and placed in your application project for Cheetah to use them. Use <a href="https://confluence.sreenidhi-gsd.com/display/Cheetah/Drivers">this link</a> to download the latest version of the WebDrivers for each browser.
* For Mobile automation, Cheetah takes the help of <a href="http://appium.io/">Appium</a> to perform the handshake.
Appium Desktop must be installed on your machine to enable Cheetah for mobile automation.

