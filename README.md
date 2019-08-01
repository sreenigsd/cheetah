# Cheetah

<img src="https://sreenidhi-gsd.com/resources/assets/img/cheetah-dark.gif" height="200" width="480" />

Cheetah is a **test automation framework** that has been developed to help automate testing efforts across multiple technologies including Web, Mobile, WebServices, Database, Mainframe, etc..
<span style="align:center"><br/>
 <a href="https://bamboo.sreenidhi-gsd.com/browse/CHEETAH-BP2/"><img src="https://bamboo.sreenidhi-gsd.com/plugins/servlet/wittified/build-status/CHEETAH-BP2"></a><space>
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)<space>
[![Gitter](https://badges.gitter.im/cheetah-automation/community.svg)](https://gitter.im/cheetah-automation/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
 </span>
## Motivation

The motivation is to provide a capability for automated testing of softwares and products.  This is one objective to be achieved on the road to producing quality software products in an organization that is embracing the agile methodology.  The road to automation will require skillful navigation, since our route is uncharted. 

The Cheetah Automation framework interfaces with existing open-source technologies like Selenium, Appium and Cucumber to provide a single solution for all test automation needs. Technologies like Selenium and Appium are among the widely used technologies in the market for test automation. However, in order to use these technologies to its fullest capabilities, additional and sometimes extensive coding might need to be written to achieve the desired results. To overcome this drawback, the Cheetah Automation framework aims to reduce the need for unnecessary coding by providing a pre-defined set of functional libraries to execute common automation tasks – thus, leaving the developers and testers to implement the core functionality required for testing without the need to worry about initialization, reporting, screen shots, logging, or any other supporting code activities. 
The Cheetah Automation framework bring the capability of Dynamic Data handling to retrieve test data from various sources including databases, web service calls, or any flat file and also has the capability to execute your test cases across multiple platforms including Web, Mobile (be it iOS or Android), Mainframe (those that are Emulated on a Web browser) along with Database and WebService testing. The framework also has an in-built capability of executing your test scenarios on a cloud environment like saucelabs thus removing the need for complex infrastructure setup. The framework provides built-in functionality for cross-browser testing and parallel executions and provides advanced reporting capabilities including Screen Shots, Video Captures, HTML and PDF reports. All this without the need to do any additional coding. 

When you use the Cheetah Automation framework for your automation needs, all you need to do is create your Gherkin Feature files and associated Step-definition methods that use pre-defined functional libraries for execution the scenarios. Its that simple. 


## Features
* Start Automating in less than 2 minute (not kidding)
* Anyone can start writing test cases within minimal knowledge of Java or automation
* Powerful APIs and built-in functional libraries to avoid the need for unnecessary coding
* Powerful reports with Video and Screenshot results
* Database integration for reporting and logging
* Various integrated technologies to enable automation across multiple platforms in a single framework
* Pre-defined build and trigger files for ease of use


## Getting Started

To get started, you can download the playground project and run the maven project using the following command: 
```sh
mvn clean initialize test -P TAG
```

Please refer the Getting Started Documentation <a href="https://confluence.sreenidhi-gsd.com/display/CHEETAH/Getting+Started" target="_blank">here</a> for detailed instructions to create your own automation project or to modify the playground project.


## API Reference

Please refer Cheetah API JavaDoc reference <a href="https://cheetah-doc.sreenidhi-gsd.com/" target="_blank">here</a>.


## Contribution

If you would like to be involved in enhancing the capabilities of this framework, please contact the administrator at sreenidhi.gsd@gmail.com 
You can also submit your feature requests on the Cheetah JIRA Project <a href="https://jira.sreenidhi-gsd.com/secure/RapidBoard.jspa?rapidView=142&projectKey=Cheetah" target="_blank">here</a> or fork a copy of the project on GitHub and submit a pull request for your changes.

Talk to other contributors [in our Gitter room](https://gitter.im/cheetah-automation).

* Please refer to the [CONTRIBUTING.md](CONTRIBUTING.md) file.


## Versioning
We follow the [Semantic Versioning](https://semver.org/) format: {MAJOR}.{MINOR}.{BUILD} for versioning *Cheetah*

* **MAJOR**: Major version changes are changes to the *Cheetah* framework to keep in par with updates to Core *Java*, *Selenium* and *Appium* technologies and versions. Changes to Java, Selenium and Appium are extenal to the framework. A change in these technologies does not warrant a change to the Cheetah Framework.
* **MINOR**: Minor changes are changes to the  *Cheetah* framework to include feature requests, enhancements and bug fixes.
* **BUILD**: Build changes are sequential increments to the build version are recorded in Bamboo.


## Authors and Team
* @Gundlupet Sreenidhi - sreenidhi.gsd@gmail.com **Gundlupet Sreenidhi**  

