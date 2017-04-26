<h1>Wobble Beat!</h1>

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/aFbuj2brN7U/0.jpg)](https://youtu.be/aFbuj2brN7U)

This project for Object Oriented Programming at DIT is the final OOP project for the semester. The Wobble Beat team is David Burton, Ronan Connolly and Philip Butler. For this project we chose to make a rythem game based on older beat and rythem based games such as Guitar Hero and Dance, Dance Revolution. The project was quite ambitious based on our ability but we feel we have delivered a fun and enjoyable game. 

<h2>Controls</h2>
Menu
  - 'w' to change choice to Start game
  - 's' to change choice to bring you to Score screen
  - 'x' to change choice to Exit game
  - ' ' to confirm choice
  
Score Screen
  - 'x' to return to main menu
  
Gameplay
  - Use the mouse to click on the notes
  - Hold the left mouse button to continuously press notes
  
Game Over screen
  - Press any key to return to main menu
  
<h2>Functionality</h2>
This project takes advantage of many Object Orientated concepts. Most of the game is built around polymorphism for example,as most objects in the game are all of type gameObject with theirown subclasses. The game, in essence, is these different objects, all in one ArrayList, interacting with each other in different ways.

There are also many other things going on behind the scenes such as database score recording, note detection using fast-Fourier Transforms and keeping track of game state variables to ensure the program is animating the correct thing at the correct time.

<h2>Things to Note</h2>
David; My favourite part of the project was getting the animation as well as the UI working. In particular, I liked the instructions and the way they are animated to twist and zoom in and out. Also, during this project I finally understood how the Lerp function worked and put it to use in the way the notes during gameplay will get bigger as they move towards the bottom of the screen.

Ronan; I really enjoyed learning how to integrate Java and MySQL together to form a database of scores. This project also allowed me to learn much more about Eclipse as an IDE and about Git and how to use it effectively as part of a team.

Philip; I'm really happy with the Audio system I developed (see video below). This project was a great opportunity to learn about audio and audio systems/algorithms and I am excited to develope this project further over the summer.

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/DWT-iL_DINM/0.jpg)](https://youtu.be/DWT-iL_DINM)
