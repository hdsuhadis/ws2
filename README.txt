INCLUDED FILES:
* source - folder with source code of the Java project

* program.exe - main program executable for Windows computers

* program.jar - secondary program executable, for all Java supported operating systems

* members.json - the member list, already contains some data

* diagrams - folder which contains diagrams of the program
	* design_model.png - Class diagram over the entire application (does not contain all attributes and methods)
	* sequence_diagram_in.png - Sequence diagram which covers one input requirement
	* sequence_diagram_out.png - Sequence diagram which covers one output requirement


HOW TO INSTALL JAVA AND RUN THE PROGRAM:
1. If Java is not installed on the computer, or if the installed version is outdated, please visit https://www.java.com/en/download/. The "Java Download" button should redirect you to a download for your operating system.
2. If Java could not be installed, visit https://www.java.com/en/download/help/linux_x64_install.xml or https://java.com/en/download/help/mac_install.xml to see more detailed installation steps.
3. Double-click the program.exe file or the program.jar file, which should open the application in a new window
4. If the program can still not be opened, please compile the code yourself. 

HOW TO COMPILE THE PROJECT MANUALLY (with Eclipse IDE):
1. Visit https://gluonhq.com/products/javafx/ and download the version of JavaFX 11.0.2 that is compatible with your operating system, and unpack it 
2. Visit https://www.eclipse.org/downloads/ and download the Eclipse IDE 
3. Install Eclipse IDE for Java Developers. Visit https://www.eclipse.org/downloads/packages/installer for more detailed installation instructions
4. Select a workspace, meaning a directory where the projects will be stored
5. Start importing this project by clicking "File" > "Open Projects from File System"
6. Click "Directory" and browse for where this project is located. Select the folder called "source"
7. Click "Finish" and the project should be visible in the "Project Explorer" sidebar
8. If the project shows as empty, right click it and click "Refresh", all files should now be visible
9. Right click the project and click "Build Path" > "Configure Build Path"
10. If "json-simple-1.1.1.jar" is not visible under the tab labeled "Libraries", click "Add External JARs...". If it is visible, skip this step and step 11
11. Select "json-simple-1.1.1.jar", located in PATH-TO-PROJECT > source > src. It should now be visible in the "Libraries" tab
12. Click through "Add Library..." > "User Library" > "User Libraries" > "New..." and name it something like "JavaFX"
13. Select it and click "Add External JARs..." 
14. Select all .jar files in PATH-TO-JAVAFX > openjfx-11.0.2_OPERATING_SYSTEM_bin-sdk > javafx-sdk-11.0.2 > lib
15. Click "Apply and Close", select the library and click "Finish". JavaFX and json-simple should now be visible under the Libraries tab
16. Click "Apply and Close" on the "Properties" window
17. Refresh the project once more if any errors are visible in the project explorer
18. Right click the project and select "Run As" > "Java Application" > "Main - (default package)"
19. Click "Ok" and the project should compile and open in a new window