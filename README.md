The goal of this project is to find a **good approximation** to the best planning for the workers in a factory, given certain rules and preferences.

[![Build Status](https://travis-ci.org/adarrivi/factory-planning.png)](https://travis-ci.org/adarrivi/factory-planning)


## Problem description
Given a factory with multiple workers and production lines, try organize the shifts and holidays as good as possible.
Each working line require different skills, so the workers are specialized in some of them, being unable to work in others.
There are only two working shifts per day: _Early_ and _Late_ (I know, 12 hour days are a bit _draconian_...)

To consider a planning **correct**, some _correctness_ rules must be followed:
* All workers must have 4 holidays
* Workers can be assigned only to the production lines they know

Also there are some other _satisfaction_ rules that will add, or remove, points to the total score:
* Do not work more than 3 consecutive Late shifts
* All production line shifts must be covered
* Three or more holidays in a row are much appreciated
* Etc...

And finally: **preferences** everywhere! Some workers prefer to do some _Early_ shifts, get some holidays here and there... For each of these preferences met, we will also add extra points to the final score.

## Show me pictures! And videos!
As you command master!
This is how the app looks like:
![screenshot](https://raw.githubusercontent.com/adarrivi/factory-planning/master/wiki/images/screenshot.PNG)

And [here](http://youtu.be/pDGEOAqjo-0) to see it in action :)


## Solution
For these kind of problems, like the [Travelling salesman problem](http://en.wikipedia.org/wiki/Travelling_salesman_problem), it is very hard to find **the optimal** result when they grow in size. As an alternative, we can try to get a slightly **less optimal** result with much less effort.

First I tried to use [Genetic Algorithms](http://en.wikipedia.org/wiki/Genetic_algorithm) to solve it, but **it was a huge mistake** because the combination of the best plannings (after being split, recombine and mutate them) almost never produced a valid sibling. Please check other of [another of my projects](https://github.com/adarrivi/tsp-genetic) to see and example of how to solve TSP with Genetic Algorithms 

The second option was to use [Simulated Annealing](http://en.wikipedia.org/wiki/Simulated_annealing) to randomize the Planning days.
The **temperature** indicates the number of days to be randomized at every step (starting from all of them, and finishing with only one).

Another thing to take into consideration is to try to limit the **number of combinations** when randomizing the planning. [At the bottom of this page ](http://www.quintiq.com/optimization/technology.html) there is a very nice video about it.


## Basic Architecture
### Package Structure
The project basic package structure is:
* _auditor_: This package contains the entities that apply correctness/satisfaction rules (auditors) to a given planning, stating if it is correct and its score. It is divided in correctness (mandatory rules) and satisfaction (add/remove points to the global score).
* _planning_: All the objects that define the model (planning, shifts, workers, days...) are here.
* _problem_: Contains the objects that define the properties of a planning problem (number of days, preferred days, worker skills, etc...)
* _view_: Has the visual representation of the planning, so we can see some colors and movement going on...
* _annealing_: Package containing all the logic to solve the problem using Simulated Annealing

### Relationships
The way the different parts interact between them is pretty simple:
![Relationships](https://raw.githubusercontent.com/adarrivi/factory-planning/master/wiki/images/planningRelationships.PNG)

Unfortunately there aren't many exciting architectural challenges in this project, but for:
* The use of the [Observer Pattern](http://en.wikipedia.org/wiki/Observer_pattern) to notify the view each time the Annealing Solver finds a better solution. A kind of [Model View Controller](http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller) but without the Controller, due to the small size of the project.


## TODO List
Here is the list of things that can be improved or are missing:
- [ ] More Unit tests: I wasn't very sure about the approach to take to solve this problem, so I left them aside (yes, I feel ashamed)
- [x] [SonarQube](http://www.sonarqube.org/) analysis: to detect cycles and things I am missing in the code
- [x] Use Travis CI to set the nice build status icon at the beginning
- [ ] Dynamic number of Shifts per day: currently there are only two, and they are fixed.
- [ ] Review the randomization of the plannings: I still have the feeling that many more combinations can be removed.
- [ ] Not so fast as I would like to: also I have notice that the algorithm finds too many local peaks without - moving forward. Increasing the number of retries fixes it, but it has a high performance impact. 
- [ ] Deep investigation on resources consumption and bottlenecks: the application needs a round of Jvisualvm to monitor a bit where the time is going, as well as the resources.
- [ ] Multi-threading: after the previous task, create and process multiple plannings at the same time might improve the speed too.
