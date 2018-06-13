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

## Notes
### Technology Choices
Initially, I was going to spin up a quick node `express` app to solve this issue.  I find them very lightweight, and zero to "hello world" incredibly fast for quick apps like this.  That being said, the directions explicitly mentioned demonstrating OO principles, which I just have more experience designing and building in Java. The directions also mentioned the ability to easily modify a configuration file to swap out which 3rd party email provider is being used.  To me, that screamed polymorphism and using DI to quickly swap out the underlying implementation of some interface.  All of that being said, after a bit of deliberation, I chose Java.  I also chose Spring to handle IoC.  To be completely honest, I think Spring is a little heavyweight for a quick app like this, as is often my feeling with Spring, but using Spring Boot, I had a simple web server up and running in less than five minutes, with only a few lines of code.  Don't judge me too much for using Spring here, although I still feel a little wrong using such a heavyweight library for such a simple application 😬.
