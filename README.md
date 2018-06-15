# gringotts <sub><sup>| Email Delivery, Made Easy </sup></sub>

---

[![version](http://img.shields.io/badge/version-v0.0.1-blue.svg)](#) [![versioning](http://img.shields.io/badge/versioning-semver-blue.svg)](http://semver.org/) [![branching](http://img.shields.io/badge/branching-github%20flow-blue.svg)](https://guides.github.com/introduction/flow/)

## Overview

Gringotts is an email delivery API, that supports multiple third party email delivery services with a simple
configuration change.

## Installation

Install [Java](https://java.com/en/download/).

> **NOTE:** `Java` version must be >= `1.8.0_40`

Install [Maven](https://maven.apache.org/download.cgi).

> **NOTE:** `Maven` version must be >= `3.1.1`

## Configuration

In the base directory, copy the `application.properties.tpl` file to `application.properties`:

```
$ cp application.properties.tpl application.properties
```

You'll then need to populate any of the empty properties with your own API keys, or service-specific URLs.
> __NOTE:__ If you skip this step, emails will *not* be able to be delivered.

## Compiling and Running

First, compile the code using maven:

```
$ mvn package
```

If this compiles successfully, fire that bad boy up:

```
$ java -jar target/gs-spring-boot-0.1.0.jar
```

At this point, you should be able to send an email!  Try the following:
```
curl --request POST \
  --url http://localhost:8080/v1/email \
  --header 'content-type: application/json' \
  --data '{
	"to": "YOUR_EMAIL",
	"to_name": "Foo Bar",
	"from": "YOUR_EMAIL",
	"from_name": "Foo Bar",
	"subject": "Hello, world!",
	"body": "<h1>i am the body!</h1>"
}'
```

> **PROTIP**: You can also set this up in the IDE of your choice (if you're into IDEs) by importing it as a new Maven
> project.

To just run unit tests, run:

```
$ mvn test
```


## Technology Choices

Initially, I was going to spin up a quick node `express` app to solve this issue. I find them very lightweight,
and zero to "hello world" incredibly fast for quick apps like this. That being said, the directions explicitly
mentioned demonstrating OO principles, which I just have more experience designing and building in Java,
and with statically typed languages, in general. The directions also mentioned the ability to easily modify a
configuration file to swap out which 3rd party email provider is being used. To me, that screamed polymorphism and
using dependency injection to quickly swap out the underlying implementation of some interface. All of that being said,
after a bit of deliberation, I chose Java. I also chose Spring to handle IoC. To be completely honest, I think Spring
is a little heavyweight for a quick app like this, as is often my feeling with Spring, but using Spring Boot, I had a
simple web server up and running in less than five minutes, with only a few lines of code. Don't judge me too much for
using Spring here, although I still feel a little wrong using such a heavyweight library for such a simple
application.  I also justified it, as the

## Design Decisions/Compromises
### HTTP Client Code
Given more time, I would abstract out the Unirest HTTP client code that is used into a wrapper. I started down this
route, but for the sake of time stopped. The direction I was going, was to create an `Http` interface and then have
a `UnirestHttpImpl`. This would allow gringotts to be more loosely coupled with any specific HTTP implementation.

### Interfaces FTW
To handle the different email services, it felt like the perfect fit for polymorphism. This kept the API
controller  agnostic of what service it's actually  using, since it doesn't need to know.  It just gets the `EmailService` injected into it,
and knows how to call the `sendEmail` API on it.  This keeps the controller and service layers loosely coupled from
each other.  I also wrapped all `UnirestException` in the service layer and am throwing our generic
`EmailServiceException` so that the unirest code doesn't leak up into the controller layer.  This loose coupling
allows us to swap out the email service implementation with zero code changes, and loose coupling in general leads to
 less fragile code and code that is easier to refactor at a later date.

### POST /email API
I wanted to keep the response from gringotts consistent back to the client, regardless of what the underlying email
service returned. I started with a normalized `id` and `message` field for Mandrill and Mailgun,
but then Sendgrid just returns a `202`, and no fields at all. I debated on what was best here,
as Sendgrid isn't even in the requirements, so I didn't want to compromise the design because of that. All of that
being said, I ended up going with an empty response and just returning a `202` for all `POST /email` APIs (which I
guess contradicts my previous sentence), but I thought that was sufficient for this API. As a general rule,
when I'm designing API, I like to return the fully hydrated created resource with a `201`. I do understand sending
an email is a little different than creating a new server-side RESTful resource though.

### Project Management
I leveraged Github's issues functionality in order to ensure I listed out all of the requirements that were given for
 this application.  I also included a handful of other items that weren't requirements and were more things I would
 do if I were to take more time on this.  Please visit the "Issues" tab in Github to see some of those outstanding
 issues.  Some of those need to be fleshed out quite a bit more.

### Error Handling
Currently, the error handling in gringotts is pretty poor.  After making an API call to each service,
I just check to see if the response was a 20x response, and if it's not, I serialize the entire JSON response into a
`String` and send it back in an error.  Given more time, I'd try to clean this up a bit,
and do more testing to ensure I'm handling errors from each service appropriately.

### Testing
Currently, I'm not testing any of the `EmailService` implementations at all.  The way I would do this,
is mock the underlying HTTP endpoints using something like WireMock.  This would make it easy to test all of the
service code itself, while not depending on the third party email service itself.  All in all,
I don't have very great test coverage.  I'm very passionate about testing, and don't feel like I did a great job
communicating that in this app.  Given more time, I would spruce up the current tests,
as well as create a CI/CD pipeline where we depend on these tests to 1) merge code into Github and 2) before the code
 gets deployed to production.

### Deployment/Infrastructure
The instructions said to "organize, design, document and test your code as if it were going into production".  There
are quite a few things I would focus on right away if this _were_ to be deployed in production,
and most are around deployment and infrastructure.  I would not use an embedded tomcat servlet container,
but use Tomcat itself.  I would also centralize all logging, and look into containerized deployments in order to
easily scale.

## Random Notes
### Mandrill Account Setup
As part of the Mandrill account setup, they require you to setup a "sending domain" before you can properly deliver
emails.  I had two of the three steps done to verify this process, however the email verification isn't working as
I'm not receiving the verification email from them.  I reached out to their support to get some help,
and for some reason my personal domain email (joshua@joshuawyse.com) had been blacklisted as deliveries had failed
there in the past.  They reset that and I immediately got my verification email.  After finishing the setup for my
"sending domain" (DNS entries and email verification), I realized I needed to purchase monthly plan and a minimum of
25k transactions ($30 cost).  Spoke with Neil, and he verified I didn't need to go this far.  So all that being said,
 I wasted quite a bit of time on that and I apologize for the delay in turning this in!

## Bonus

As someone who wants to support local business, as well as the best email SaaS provider out there,
I also included an integration with Sendgrid to send emails as well. #SupportLocalBusiness :smile:
