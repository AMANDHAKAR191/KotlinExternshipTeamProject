# KotlinExternshipTeamProject
Kotlin Externship Team Project

## Description

Welcome to the Kotlin Externship Team Project. This is a robust and modern application, designed and built as part of an externship program. Our team is using latest Jetpack Compose, using Kotlin programming language.

The application is an exciting project that serves users in managing a unique entity known as "expanse". This could be viewed as a learning project to explore modern Android development techniques, libraries, and best practices.


## Features

### User Authentication

The application offers a system for user authentication. It provides users with the ability to sign in using their Google account. The authentication process is streamlined to provide a seamless user experience.

### Profile Management

Once users are authenticated, they have access to their profiles where they can view and manage their information. The profile management feature is designed to be intuitive and user-friendly.

### Expanse Management

The core functionality of the application revolves around managing 'expanse' items. Users have the ability to add new expanses, edit existing ones, and delete any they no longer need. To aid in tracking and management, the application also offers functionality to fetch all expanses and calculate the sum of the expanses for the current month.


## Project Structure

Our project is organized into several packages to ensure a clean architecture(used MVVM architecture):

### Core

This package contains the base code used throughout the application. `Constants.kt` is where you'll find all constants used in the application, including those for authentication, screen names, and messaging. The `Utils.kt` file is home to utility functions, including a function for logging exceptions which aids greatly in debugging and development.

### Data

This package contains the code related to data handling and database operations. This includes our Room database and the `ExpanseDao.kt` file. The `ExpanseDao` interface includes methods for getting all expanses, getting an expanse by ID, getting the sum of current month's expanses, inserting an expanse, and deleting an expanse.

### di

This package contains the model `AppModel.kt` dependency injection

### domain

This package contains busniss logic classes, data classes and use-case classes.

### presentation

This package contains UI logic. Include `Auth` module, `home` module, `profile` module and `navigation` module for navigation


## Upcoming Features

As the application is still under active development, we are continually working on adding more features and improving user experience. We have exciting plans for future updates and we will keep the community updated as we progress.

### Feature 1

Get User Bank Balance and transation history in app, so that user can track offline so that user do not need to add transation mannually. 


## How to Contribute

We are always open to contributions from the developer community. If you'd like to contribute to this project, please fork the repository and make your changes, then open a pull request against this repository. We appreciate your efforts and look forward to seeing your innovative ideas!


## Acknowledgments

We would like to express our gratitude to the Kotlin Externship Program for providing us with this opportunity to develop our skills and contribute to the developer community. 

Thank you for your interest in the Kotlin Externship Team Project!
