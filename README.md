# gringotts <sub><sup>| Email Delivery, Made Easy </sup></sub>

--------------------------------------------------------------------------------

[![version](http://img.shields.io/badge/version-v0.0.1-blue.svg)](#) [![versioning](http://img.shields.io/badge/versioning-semver-blue.svg)](http://semver.org/) [![branching](http://img.shields.io/badge/branching-github%20flow-blue.svg)](https://guides.github.com/introduction/flow/)
[![Circle CI](https://circleci.com/gh/jjwyse/gringotts.svg?style=shield)](https://circleci.com/gh/jjwyse/gringotts)
[![Coverage Status](https://coveralls.io/repos/github/jjwyse/gringotts/badge.svg)](https://coveralls.io/github/jjwyse/gringotts)

## Overview

## Installation
Install [Java](https://java.com/en/download/).
> __NOTE:__ `Java` version must  be >= `1.8.0_40`

Install [Maven](https://maven.apache.org/download.cgi).
> __NOTE:__ `Maven` version must  be >= `3.1.1`

## Configuration
In the base directory, copy the `application.properties.tpl` file to `application.properties`:
```
$ cp application.properties.tpl application.properties
```

## Compiling and Running
First, compile the code using maven:
```
$ mvn package
```

If this compile successfully, fire that bad boy up:
```
$ java -jar target/gs-spring-boot-0.1.0.jar
```

> __PROTIP__: You can also set this up in the IDE of your choice (if you're into IDEs) by importing it as a new Maven
 project.
 
To just run unit tests, run:
```
$ mvn test
```

## Notes
### Technology Choices
Initially, I was going to spin up a quick node `express` app to solve this issue.  I find them very lightweight, and zero to "hello world" incredibly fast for quick apps like this.  That being said, the directions explicitly mentioned demonstrating OO principles, which I just have more experience designing and building in Java. The directions also mentioned the ability to easily modify a configuration file to swap out which 3rd party email provider is being used.  To me, that screamed polymorphism and using DI to quickly swap out the underlying implementation of some interface.  All of that being said, after a bit of deliberation, I chose Java.  I also chose Spring to handle IoC.  To be completely honest, I think Spring is a little heavyweight for a quick app like this, as is often my feeling with Spring, but using Spring Boot, I had a simple web server up and running in less than five minutes, with only a few lines of code.  Don't judge me too much for using Spring here, although I still feel a little wrong using such a heavyweight library for such a simple application 😬.

### Design Decisions/Compromises
#### HTTP Client Code
Given more time, I would abstract out the Unirest HTTP client code that is used into a wrapper.  I started down this 
route, but for the sake of time stopped.  The direction I was going, was to create an `Http` interface and then have 
a `UnirestHttpImpl`.  This would allow gringotts to be more loosely coupled with any specific HTTP implementation.  

#### POST /email API
I wanted to keep the response from gringotts consistent back to the client, regardless of what the underlying email 
service returned.  I started with a normalized `id` and `message` field for Mandrill and Mailgun, 
but then Sendgrid just returns a `202`, and no fields at all.  I debated on what was best here, 
as Sendgrid isn't even in the requirements, so I didn't want to compromise the design because of that.  All of that 
being said, I ended up going with an empty response and just returning a `202` for all `POST /email` APIs (which I 
guess contradicts my previous sentence), but I thought that was sufficient for this API.  As a general rule, 
when I'm designing API, I like to return the fully hydrated created resource with a `201`.  I do understand sending 
an email is a little different than creating a new server-side RESTful resource though. 

### Bonus
As someone who wants to support local business, as well as the best email SaaS provider out there, 
I also included an integration with Sendgrid to send emails as well. #SupportLocalBusiness :smile:
