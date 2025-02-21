# Folder Creator Plugin

## Purpose and Features

The **Folder Creator Plugin** is designed to help you quickly set up a well-organized project structure based on Test Driven Development (TDD) principles in IntelliJ IDEA, Android Studio, and other IntelliJ-based IDEs. Not only does the plugin create the essential folder hierarchy, but it also automatically generates boilerplate Dart code files for each architectural layer.

### What It Does

When you invoke the plugin, it generates a folder structure and code files based on the folder name you provide. For example, if you enter "auth" as the folder name, the plugin will create:

- **data/datasources/auth_datasource.dart**  
  Contains an abstract datasource class and its implementation.
- **data/repositories/auth_repository_impl.dart**  
  Contains a concrete repository implementation.
- **data/models/auth_model.dart**  
  Contains a model class with JSON serialization stubs.
- **domain/repositories/auth_repository.dart**  
  Contains an abstract repository interface.
- **domain/entities/auth_entity.dart**  
  Defines the domain entity.
- **presentation/auth_screen.dart**  
  Contains a basic stateful widget screen.

## How It Works

1. **Select a Folder and Enter a Name:**  
   Right-click on the desired folder in your project, choose **New > Create TDD Folders**, and then enter a folder name (e.g., `auth`).

2. **Automatic Generation:**  
   
   It then generates boilerplate Dart code files in the respective folders, customized to the provided folder name.

## Example Structure and Code

If you input "auth" as your folder name, the generated project structure will look like:



<img width="513" alt="Screenshot 2025-02-21 at 23 45 48" src="https://github.com/user-attachments/assets/2b53f299-dccd-4359-8e04-1a6f431d709d" />



## Installation and Usage

### Installation

#### Build the Plugin
- **Build the Plugin:**  
  Use the Gradle `buildPlugin` task to generate a ZIP file containing your plugin.

#### Install the Plugin
- **Open IntelliJ IDEA or Android Studio.**
- **Go to:**  
  Settings/Preferences > Plugins.
- **Select:**  
  Click the gear icon and choose **Install Plugin from Disk…**.
- **Choose the ZIP file:**  
  Select the generated ZIP file for installation.

### Usage

#### Right-Click on a Folder
- **In your project:**  
  Right-click on the folder where you want to create the new structure.

#### Select "Create TDD Folders"
- **From the context menu:**  
  Choose **New > Create TDD Folders**.

#### Enter Folder Name
- **Provide a folder name:**  
  For example, type `auth` to generate the corresponding folder structure and boilerplate Dart files.
