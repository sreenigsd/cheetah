## Thank you

First off, thanks for your interest in Cheetah and for wanting to contribute!

In this guide, we'll discuss how Cheetah is built. This should give you a good sense of our process and where you might want to fit in.

## What we're trying to build
The Cheetah Automation framework is all about letting developers and testers concentrate of making their product work as intended rather than write unnecessary code to achieve automation. We're trying to minimize the MTTD and maximize the delivery frequency of products through Test Automation.

## Our product process:

The core team runs a pretty well defined product process. It is actively being tweaked, but the below is a pretty faithful description of it at the time of writing. You should have a clear idea of how we work before jumping in with a PR.

### A) Identify product needs from the community

We actively look for new feature ideas from our community, user base and our own use of Cheetah internally. We concentrate on the underlying *problem* or *need*  as opposed to requests for specific features. While sometimes suggested features are built as requested, often we find that they involve changes to existing features, and perhaps an entirely different solution to the underlying problem. These will typically be collected in a number of issues, and tagged *Proposal*

### B) Synthesize these needs into a concrete feature

We typically will collect a group of issues or suggestions into a new topline feature concept. Typically we'll create a working document that collects all "Open Questions" regarding to what the feature is meant to do, and more importantly not do. We'll chat with our users, maybe do in depth interviews and generally try to tightly define the feature. If a feature seems like it will need time to be discussed and scoped, it will be tagged *Proposal/Being Discussed* to signify that it is still actively under discussion.

### C) Design the feature

Once a feature has been defined, typically it will be taken on by a product designer. Here, they will produce low fi mocks, get feedback from our users and community, and iterate.


### D) Build the feature

Once a feature is ready to be built, a core team member (or you, awesomely helpful person that you are) can start working on it.

Once one or more people have started to work on a feature, it should be marked *In Progress* Once there is a branch+some code, a pull request is opened, linked to the feature + any issues that were pulled together to inform the feature.

### E) Verification and merging

All PRs that involve more than an insignificant change should be reviewed.

If all goes well, the feature gets coded up, verified and then the pull request gets merged! High-fives all around.

If there are tests missing, code style concerns or specific architectural issues in the pull request, they should be fixed before merging. We have a very high bar on both code and product quality and it's important that this be maintained going forward, so please be patient with us here.

## How to contribute to the CHEETAH Automation Framework


## Ways to help:

The starting point would be to get familiar with Cheetah the product, and know your way around. If you're using it at work, that's great! If not, download the jar and play around with it. Read the docs and generally get a feel for the flow of the product.

If you want to help out, there are lots of ways. In order of increasing coordination + interaction with us:

### Help with identifying needs and problems Cheetah can solve

If you want to help, try out Cheetah. Use it at your company, and report back the things you like, dislike and any problems you run into. Help us understand your data model, required metrics and common usage patterns as much as you can. This information directly affects the quality of the product. The more you tell us about the kinds of problems you're facing, the better we'll be able to address them.

### Help us triage and support other users

Spend time on https://confluence.sreenidhi-gsd.com/display/CHEETAH/Cheetah+Automation+Framework and on new issues and try to reproduce the bugs reported. For people having trouble with their databases where you have significant knowledge, help them out. Who knows, maybe they'll end up helping you with something in the future.

It is helpful if you understand our [JavaDocs](https://cheetah-doc.sreenidhi-gsd.com/) when responding.

### Tell your friends

Let your friends know about Cheetah. Start a user group in your area. Blog about how you're using Cheetah, and share what you've learned.

#### **Did you find a bug?**
* **Do not open up an issue if you are not confident that you have a bug**

* **Ensure the bug was not already reported** by searching on JIRA under [Issues](https://jira.sreenidhi-gsd.com/projects/CHEETAH/issues) or Contact the Administrator for support.

* If you're unable to find an open issue addressing the problem, [open a new one](https://jira.sreenidhi-gsd.com/projects/CHEETAH). Be sure to include a **title and clear description**, as much relevant information as possible, and a **code sample** or an **executable test case** demonstrating the expected behavior that is not occurring.

* If possible, write the algorithm or code that needs to be implemented to resolve the issue and **paste the content into the issue description**:
  

By our definition, "Bugs" are situations where the program doesn't do what it was expected to according to the design or specification. These are typically scoped to issues where there is a clearly defined correct behavior. It's usually safe to grab one of these, fix it, and submit a PR (with tests!). These will be merged without too much drama unless the PR touches a lot of code. Don't be offended if we ask you to make small modifications or add more tests. 

* For more detailed information on submitting a bug report and creating an issue, contact the Administrator.*

### Help with Documentation

Documentation! Documentation! Documentation! 

We often have difficulties keeping them up to date. If you are reading them and you notice inconsistencies, errors or outdated information, please help up keep them current!


### #YOLO JUST SUBMIT A PR

If you come up with something really cool, and want to share it with us, just submit a PR. If it hasn't gone through the above process, we probably won't merge it as is, but if it's compelling, we're more than willing to help you via code review, design review and generally OCD nitpicking so that it fits into the rest of our codebase.

* Before submitting, please follow the [Coding Conventions for Java Programming](http://www.oracle.com/technetwork/java/codeconvtoc-136057.html) to know more about coding conventions and benchmarks.

#### **Did you fix whitespace, format code, or make a purely cosmetic patch?**

Changes that are cosmetic in nature and do not add anything substantial to the stability, functionality, or testability of the framework are generally slower to be accepted.


* Do not open an issue on JIRA until you have collected positive feedback about the change. All changes to Cheetah code must be recorded in JIRA. Changes will not be accepted without a corresponding ticket in JIRA.

#### **Do you have questions about the source code?**

* Ask any question about how to use CHEETAH in our [Gitter room](https://gitter.im/cheetah-automation).

#### **Do you want to contribute to the CHEETAH documentation?**

CHEETAH is open to volunteer effort. We encourage you to pitch in and [join the team](https://confluence.sreenidhi-gsd.com/display/CHEETAH)

Thanks! :heart: :heart: :heart:

--Gundlupet Sreenidhi
