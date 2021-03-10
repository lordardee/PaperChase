# QRChase - PA1456_Project
An Android game which requires you to find and scan QR-codes. Created in Android studio with Java programming language and XML code for the UI.

## Table of contents
* [General info](#General-info)
* [Technologies](#Technologies)
* [Setup](#Setup)
* [How to Play](#How-to-Play)

## General info
This game was made as a project for a course in school and is intended to be used for that.
If anyone wants to try out the game it's fine to do so but it's a very rough app and not very optimized.

The game is a simple treasure hunt esque game where the player can create their own routes and then play the routes trying to finish the routes as fast as possible.
The app generates QR-codes witch will have to be printed out on paper if they want to play the game as intended.
You place the QR-codes in hidden locations and when a QR-code is scanned it reveals where the next QR-code is hidden.
When all QR-codes have been scanned the time taken will be saved as a highscore and the lowest time will be first placed.

## Technologies
The program used to create the project and the used libraries/dependencies:
* Android studio, version: 4.1.2
* [QRGenerator](https://github.com/androidmads/QRGenerator), version: 1.0.4
* [code scanner](https://github.com/yuriy-budiyev/code-scanner), version: 2.1.0
* [Permission requester](https://github.com/Karumi/Dexter), version: 6.2.2
* [Gson](https://github.com/google/gson), version: 2.8.6

## Setup
There are two ways of getting the game to your phone and the prerequisite is that the phone is an android phone.\
1: Download the APK from the release on github and then plug in your phone to the computer and transfer the APK to your phone.
This requires you to allow your phone to install from unknown sources.\
2: Install Android studio on your computer and on startup of the program you select "Get from Version Control" and paste in the URL to this project and clone the repository. When you have all the files in Android studio you need to enable developer mode on your phone and then plug it in to the computer with a USB and select the option on the phone to transmit photos (this option should pop up on you phone when you have enabled developer mode). When you choose to run the program on your device via Android studio it will install the app on your phone.

## How to Play
When you first start the app there will be no courses available so you have to create the courses first.\
Create: You type your clue for the next QR-code att the top where it says "Enter Value" generate the QR and then press ADD QR. When you have created all the QR-codes you want you give the course a name and then press SAVE COURSE. To get to the main menu again press the back button on your phone.\
Play: Here you see all the created courses you select which one to play or remove. When you start a course the screen changes to show a timer att the top and some white boxes. The white boxes will display the messages from the scanned QR-codes and the check button is for after you've scanned a QR-code to check if it's correct. When you press the scan button you need to allow the usage of you camera. When you've scanned a QR-code you need to press the button that's under the text. It will take you back to the timer screen and when you press check for the first time the timer starts and wont stop untill you have scanned all the QR-codes. When you have found them all the reset button will clear all the scanned QR-codes and reset the timer and send you to the main menu.\
HighScore: Here you select what course's highscore list you'd like to see.
