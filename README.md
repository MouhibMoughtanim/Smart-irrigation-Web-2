# TO TEST THE APP:

1. Run Docker Desktop
2. Build the Docker images with the command: `docker-compose build`
3. Start the application with the command: `docker-compose up`
4. **ADMIN LOGIN:** Username: `admin@admin` / Password: `admin@admin`
5. **FARMER LOGIN:** Username: `lachgar` / Password: `lachgar`

# Demonstration :

https://github.com/MouhibMoughtanim/Smart-irrigation-Web-2/assets/101585977/3994c80f-abee-461e-be59-94737cb30053

# Smart-irrigation-Web-2
A- WEB APPLICATION CONFIGURATION

To import and run backend and frontend web applications using Spring Tools, follow these steps:

1- Install Spring Tools: Download and install Spring Tools Suite or Spring Tool Suite 4 from the official website (https://spring.io/tools).

2- Import Backend Project (smartwatering_api_lastV): Launch Spring Tools and select "File" from the top menu, then choose "Import." In the Import dialog, expand the "Maven" folder and select "Existing Maven Projects." Click "Next." Browse to the location where your backend project is stored and select the project folder. Ensure that the backend project's pom.xml file is selected, and click "Finish" to import the project.

3- Resolve Dependencies: Spring Tools will automatically resolve and download the necessary dependencies specified in the backend project's pom.xml file. Wait for the dependencies to be downloaded.

4- Run Backend Application: Once the dependencies are resolved, navigate to the main class of your backend application (typically annotated with @SpringBootApplication) in the project structure. Right-click on the main class and select "Run As" > "Spring Boot App" or "Java Application" to start the backend server.

5- Import Frontend Project (smartwateringV2-1): If your frontend application is a separate project, you can import it into Spring Tools as well. Follow a similar process to step 2, but choose the appropriate import option based on your frontend project structure (e.g., "Import Existing Maven/Gradle Projects," "Import Existing Project," etc.).

6- Serve Frontend Application: Depending on your frontend framework (e.g., Angular, React, Vue.js), you may need to serve the frontend application separately. Refer to the documentation of your frontend framework for instructions on serving the application. Typically, this involves running commands like npm install to install dependencies and npm start or a similar command to serve the application.

7- Access Web Application: Once both the backend and frontend applications are running, you can access the web application by opening a web browser and navigating to the appropriate URL. This URL will depend on the server and port configuration specified in the backend application and the frontend application's configuration.

B- MOBILE APP CONFIGURATION


https://github.com/MouhibMoughtanim/Smart-irrigation-Web-2/assets/119877992/f052ba89-c47f-427b-8180-0e4949160bfd


To import and run the mobile application using Android Studio, follow these steps:

1- Install Android Studio: Download and install the latest version of Android Studio from the official website (https://developer.android.com/studio).

Open Android Studio: Launch Android Studio once the installation is complete.

2- Import Project (smart_irrigation_mobile-main): Click on "Open an Existing Project" or select "File" from the top menu, then choose "Open" or "Open Project." Browse to the location where your project is stored and select the project folder. Click "OK" to import the project.

3- Gradle Build: After importing the project, Android Studio will initiate a Gradle build process. This process downloads any necessary dependencies and sets up the project structure. Wait for the build to complete.

4- Connect a Device or Start an Emulator: Connect your Android device to the computer using a USB cable or start an emulator through the Android Virtual Device (AVD) Manager in Android Studio. Ensure that the device/emulator is detected by Android Studio.

5- Build and Run the Application: From the toolbar at the top, select the target device/emulator and click on the "Run" button (typically a green triangle). Android Studio will build the project, install the app on the device/emulator, and launch it.

6- Test and Debug: Once the app is running, you can interact with it on the connected device/emulator. Use the debugging tools provided by Android Studio to analyze the app's behavior, set breakpoints, and track variables.

C- AI APP CONFIGURATION
To run the IPython (.ipynb) app in Jupyter Notebook, follow these steps:

1- Install Jupyter Notebook: Ensure that Jupyter Notebook is installed on your system. If it's not installed, you can install it using Anaconda Navigator or by running the following command in your terminal:

pip install jupyter

2- Launch Jupyter Notebook: Open a terminal (command prompt) and navigate to the directory where your IPython notebook (*.ipynb) file is located. Run the following command:

This will start the Jupyter Notebook server and open a new tab in your web browser.

3- Open the IPython Notebook: In the Jupyter Notebook interface in your web browser, navigate to the directory where your IPython notebook file is located. Click on the file to open it.

4- Run the Notebook Cells: Once the IPython notebook is opened, you'll see individual cells containing code and markdown text. To run a code cell, click on it to select it, then either press the "Run" button in the toolbar at the top or press Shift + Enter. The code in the cell will be executed, and the output (if any) will be displayed below the cell.

5- Continue Running Cells: You can proceed to run the remaining cells in the notebook one by one. If there are any dependencies or cells that need to be run in a specific order, make sure to follow those instructions within the notebook.



6- Modify and Re-run Cells: You can modify the code or text within the cells as needed. After making changes, re-run the modified cells to see the updated output or results.

7- Save and Quit: Remember to save your changes periodically by clicking on the "Save" button or selecting "File" > "Save and Checkpoint" from the menu. To exit Jupyter Notebook, close the browser tab, and you can also stop the server by going back to the terminal and pressing Ctrl + C.

By following these steps, you'll be able to run and interact with your IPython notebook in Jupyter Notebook, executing the code cells and viewing their outputs in real-time.
