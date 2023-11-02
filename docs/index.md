---
title: Overview
description: "Project proposal or summary of in-progress/completed project."
menu: Overview
order: 0
---

{% include ddc-abbreviations.md %}

## Summary
This app offers users a simulation of how objects would behave under varying gravitational conditions. 
However, I've added a fun twist to make it an interactive experience. Players take command of a spaceship, 
maneuvering it up and down to evade oncoming meteors. They'll also confront the task of eliminating enemy ships 
that appear randomly on the screen. To achieve this, users must select both velocity and angle to launch a projectile 
with the aim of striking the enemy ships. This dynamic blend of gravity simulation and action-packed gameplay 
ensures an engaging and entertaining experience.


[//]: # (This app lets users experience a simulation of how objects would behave if it was falling in different gravities, but I'm trying to do it )

[//]: # (in a more fun way, so the user will be able to play with that idea. The user will be able to control a spaceship moving it )

[//]: # (up and down trying to avoid the meteors that will be flying in the opposite direction, also the user will have to destroy)

[//]: # (enemy's ship that will be display in random position in the screen, to destroy the enemy's ship the user will have to select)

[//]: # (the velocity and an angle to shoot a projectile with the intention of hit the enemy's ships.)


## Intended users & user stories
{: menu="Users" }

* Kids trying to understand physics in a fun way. 

> As a kid that is trying to understand Physics I want to use the game to understand better the way an object is affected by the gravity in a more fun way. 


* People who like space movies.

    > As someone who enjoys movies like Star Wars, I like to have an app that lets me control a spaceship, so I can pretend I'm fighting against the Imperials to have fun.

## Functionality

List (using a bullet list---or ordered list, if order is relevant) the key functional aspects that will be provided by the app---i.e. tell us what the user will be able to do using the app. This should not simply be a re-statement of the [summary](#summary), but should instead provide a more specific articulation of the functionality and user experience.
* Create an account.
* See higher scores.
* Control a spaceship.

## Persistent data
{: menu="Persistence" }

[//]: # (Using a bullet list, list what content will be stored on the Android device. This should include any information that users of your app would expect to be maintained &#40;i.e. without connection to a server&#41; across multiple sessions of use.)

[//]: # ()
[//]: # (For example, this starter app already includes the necessary data model elements and data-access code to store & retrieve the following )

* User
    * Display name.
    * Display scores.
    * Timestamp of each score.
    
## Device/external services
{: menu="Services" }

[//]: # (If the client component will need to access special services of the device &#40;e.g. sensors, contacts, messaging&#41;, list them here using a bullet list. Also, if the client component will need to access already-existing external services &#40;e.g. real-time weather data, Open Movie Database, Open Trivia Database&#41;, those should also be listed here; any such references to external services should include links to the main page or API description page for the service.)
* Internet.

## Stretch goals and possible enhancements 
{: menu="Stretch goals" }
* Enemies will have a shield to will protect them from projectiles that will be activated randomly.
* Projectiles can destroy meteors.

[//]: # (If you can identify functional elements of the software that you think might not be achievable in the scope of the project, but which would nonetheless add significant value if you were able to include them, list them here. For now, we recommend listing them in order of complexity/amount of work, from the least to the most.)
