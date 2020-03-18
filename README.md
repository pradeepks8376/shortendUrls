# shortendUrls
> 

## About

The shortendUrls is an API for short URL creation.  

The main requirements that guided the design and implementation of little-url:
*	Design and implement an API for short URL creation
*	Implement forwarding of short URLs to the original ones
*	Add an API for gathering different statistics
*	There should be some form of persistent storage
*	The application should be distributed as one or more Docker images
*	It should be readable, maintainable, and extensible where appropriate
*	The implementation should preferably be in Java

#### Tech stack
* [Spring Boot](http://spring.io/projects/spring-boot) for creating the RESTful Web Services
* [MockMVC](https://spring.io/guides/gs/testing-web/) for testing the Web Layer
* [Mockito](https://site.mockito.org/) for testing the Services Layer
* [H2](https://www.h2database.com/html/main.html) as database
* [Maven](https://maven.apache.org/) for managing the project's build
* [Docker](https://www.docker.com/) for building and managing the application distribution using containers 

## Install
#### Download the repository
```sh
$ git clone https://github.com/pradeepks8376/shortendUrls.git
```
#### With docker and docker-compose installed
```sh
$ cd shortendUrls && docker-compose up
```
## Usage

Request Method | URI | Body (JSON) | Description |  
:---: | :--- | :---: | :--- |
GET | http://localhost/url/{code} | - | Find long url and redirect | 
GET | http://localhost/urlstats/summary | - | Get the statistics summary |
POST | http://localhost/url/shortener?longurl | { "longUrl": "[http...]" } | create url and return its shorten url in response headers | 
