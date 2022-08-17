# HomeDex API

## Introduction

This is the back end repository which hosts the source code used for the [HomeDex](https://github.com/vuongthu/homedex-front) mobile app.

## Dependencies

- Java 17
- PostgreSQL
- Docker (optional)

## Installation

1. Install Java 17: 
    ```
    brew install openjdk@17
    ```
1. Install PostgreSQL:
    ```
    brew install postgresql
    ```
1. Install Docker:
    ```
    brew install --cask docker
    ```

## Run HomeDex Locally

1. Clone this repository.
1. Add users to PostgreSQL database.
    - If using Docker, navigate to the `docker` folder and run `docker compose up`
    - Otherwise, create the users in the location `docker/provision/001_create_users.sql`
1. Run the command:
    ```bash
    ./gradlew bootRun
    ```


## Author

This API is developed by [Thu Vuong](www.linkedin.com/in/vuongthu) as a capstone project for [Ada Developers Academy](https://adadevelopersacademy.org/).
