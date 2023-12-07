# FindASeat

FindASeat is an Android application designed to help users find and book seats in various buildings around the University of Southern California campus. Users can browse different buildings, check seat availability, and make reservations with ease.


## Features
- **Interactive Campus Map**: A dynamic map view of the USC campus showcasing all buildings with available seating options. Users can interact with the map even without logging in.
- **Browse Buildings**: Explore a list of buildings, complete with details such as name, type, and operating hours.
- **Real-Time Availability**: Check the availability of seats in real-time and reserve your spot in a few simple taps.
- **Book Seats**: Select a building and book a seat for studying or attending lectures.
- **User-Friendly Interface**: An intuitive interface that ensures a seamless user experience.
- **Firebase Integration**: Real-time data synchronization with Firebase for up-to-date seat availability and bookings.


## Getting Started
To run FindASeat on your local machine for development and testing purposes, follow the steps below:


### Prerequisites
- Android Studio
- Java Development Kit (JDK)
- Android device or emulator
- Pixel 2 API 34 
- Firebase account : https://firebase.google.com/?hl=en&authuser=0


### Installation
1. Clone the repository:
2. Open the project in Android Studio.
3. Configure Firebase:
- Go to the Firebase console and create a new project.
- Add your Android app to the Firebase project and download the `google-services.json` file.
- Place `google-services.json` into your Android project's `app` directory.
4. Sync the project with Gradle files and run the project on an Android device or emulator.


## Usage
1. **Explore Campus Map**: Upon opening the app, users will be presented with an interactive map of the campus. Each building with available seating will be marked on the map.
2. **View Buildings**: Scroll through the list of buildings on the main screen.
3. **Book a Seat**: Tap on a building and choose a seat to book.
4. **View Bookings**: Access your current and past bookings through the booking interface.
5. **Manage Reservations**: By navigating to the Profile tab, users can view their current and past bookings, keep track of their reservation history, and make changes or cancellations as needed.
6. Accounts to log into
     username: csci@gmail.com, password: findaseat
               mantejkhalsa@gmail.com, password: mantej123
               testing@gmail.com, password: johnny
               usc@usc.edu, password: usctrojan
               ta@gmail.com, password: csci310


## Contact
Project Link: 

## Updates 
We have improved the functionality of our app since the 2.4 Submission. We fixed an app crash when a user clicked on a marker on the map, implemented time validation for reservations so that a user cannot book slots that have already passed for the day, enforced consecutive time slot bookings, restricted reservation duration to 2 hours, limited reservations to one active per user and finally added in real-time seat availability updates. We also added in some styling to make the app look more presentable. 



## Acknowledgements
- [Firebase](https://firebase.google.com/)
- [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview)
- [AndroidX](https://developer.android.com/jetpack/androidx)

