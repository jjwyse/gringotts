# gringotts <sub><sup>| Email Delivery, Made Easy </sup></sub>

--------------------------------------------------------------------------------

[![version](http://img.shields.io/badge/version-v0.0.1-blue.svg)](#) [![versioning](http://img.shields.io/badge/versioning-semver-blue.svg)](http://semver.org/) [![branching](http://img.shields.io/badge/branching-github%20flow-blue.svg)](https://guides.github.com/introduction/flow/)
[![Circle CI](https://circleci.com/gh/jjwyse/gringotts.svg?style=shield)](https://circleci.com/gh/jjwyse/gringotts)
[![Coverage Status](https://coveralls.io/repos/github/jjwyse/gringotts/badge.svg)](https://coveralls.io/github/jjwyse/gringotts)

## Overview

## Installation

## Configuration

## Running

## Notes
### Technology Choices
Initially, I was going to spin up a quick node `express` app to solve this issue, however the directions explicitly mentioned demonstrating OO principles, which I find easier to do in Java, with a statically typed language that, IMHO, is more suited for OO.  The directions also mentioned the ability to easily modify a configuration file to swap out which 3rd party email provider is being used.  To me, that was pointing me heavily towards using a Java `Interface` and using DI to quickly swap out the underlying implementation of said `Interface`.  All of that being said, I chose Java.  TBH, I think Spring is a little heavyweight for a quick app like this, as is often my feeling with Spring, but using Spring Boot, I had a simple web server up and running in less than five minutes, with only a few lines of code.
