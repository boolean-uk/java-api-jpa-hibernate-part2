# Java API with JPA and Hibernate

## Learning Objectives
- Implement foreign keys and other relationships in tables

## Instructions

1. Fork this repository
2. Clone your fork to your machine
3. Open the project in IntelliJ
4. Copy `application.yml.example` to `application.yml` and fill out your database connection details
5. Check that `build.gradle` contains the correct dependencies and rerun gradle sync to make it all update

## Activity

Implement an API for the following database tables

| Authors    |         |             |
|------------|---------|-------------|
| id         | SERIAL  | PRIMARY KEY |
| first_name | TEXT    |             |
| last_name  | TEXT    |             |
| email      | TEXT    |             |
| alive      | BOOLEAN |             |

| Publishers |        |             |
|------------|--------|-------------|
| id         | SERIAL | PRIMARY KEY |
| name       | TEXT   |             |
| location   | TEXT   |             |


| Books        |        |                                 |
|--------------|--------|---------------------------------|
| id           | SERIAL | PRIMARY KEY                     |
| title        | TEXT   |                                 |
| genre        | TEXT   |                                 |
| author_id    | INT    | FK -> References Authors(id)    |
| publisher_id | INT    | FK -> References Publishers(id) |

The API should allow you to do the usual CRUD activities.

The API is specified [here](https://boolean-uk.github.io/java-api-jpa-hibernate-part2/) as usual.

## Core Criteria 
All of the endpoints exist but references to the other tables are not necessarily implemented/working.

## Extension Criteria
As well as the endpoints existing, references to other tables are correctly working (ie if I view an publisher I can see details of their books etc).



