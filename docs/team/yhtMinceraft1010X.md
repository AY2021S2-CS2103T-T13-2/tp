---
layout: page
title: T13-2 ModuleBook Level 3.5
---

## Project: ModuleBook Level 3.5

ModuleBook - Level 3.5 is a desktop module book application used for keeping track of tasks for various NUS modules. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to mark a task as done or not done.
  * What it does: allows the user to mark a task as done. If user wishes to attempt the task again, the task can also be marked as not done.
  * Justification: This feature improves the product significantly because a user may want to re-attempt a task and the app should provide a convenient way for the user to indicate that the previously done task is actually not done yet.
  * Highlights: This enhancement affects existing commands and commands to be added in the future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.
  * Credits: *yhtMinceraft1010X ip*

* **New Feature**: Added a workload field that allows the user to rate a task's expected workload using integers in the range 1-3 inclusive.

* **Code contributed**: [RepoSense link]()

* **Project management**:
  * Managed releases `v1.2` - `v1.4` (3 releases) on GitHub

* **Enhancements to existing features**:
  * Updated the GUI color scheme (Pull requests [\#33](), [\#34]())
  * Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())

* **Documentation**:
  * User Guide:
    * Added documentation for the features `done` and `notdone` [\#72]()
    * Did cosmetic tweaks to existing documentation: [\#74]()
  * Developer Guide:
    * Added implementation details of the `done` feature.
    * Did cosmetic tweaks to existing documentation.

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
  * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
  * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
  * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

* **Tools**:
  * Integrated a third party library (Natty) to the project ([\#42]())
  * Integrated a new Github plugin (CircleCI) to the team repo

* _{you can add/remove categories in the list above}_
