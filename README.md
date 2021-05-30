
# Buddy-Backend-UoM-GAPT-2021

Table of Contents:

 1. About the Project
	 a. Built with
 2. Getting Started
	 a. Prerequisites
	 b. Running the Solution
 3. Usage
 4.  Acknowledgements & Contacts

## About the Project

This solution was built as a University Group Project Prototype by students of University of Malta. The solution is an ASP .NET Core Web API with multiple API endpoints exposed to the database server hosted on the Azure Cloud. It is the backend half of an intended solution providing the functionality of the APIs mentioned. 

This solution is aimed to be used a tool for users to navigate unfamiliar indoor spaces by providing Navigation functionality as well as providing information of said venue and events happening within the venue. 

The test case used for this project was using the University of Malta Faculty of ICT.

### Built with

 - Azure Server
	 - Which hosts the database & SQL Server
 - Microsoft SQL  Server Management Studio 
 - Visual Studio 2019
	 - ASP .NET Core Web API
	 - Swagger
	 - C#
	 - Javascript 
	 - Html
 - Google Map API

## Getting Started
### Prerequisites :

 - #### Database

Kindly note by default the project is linked directly to the Azure Server managed by the developers. Contact the developers if the Server is not running as none of the API calls would work.

Else the project has a backup default of the database as BuddyDb.bacpac file,  kindly import db into Microsoft SQL Server Management Studio. Deploy the database appropriately from your own server and  adapt the connection string which can be found in the appsettings.json 
 

        "ConnectionStrings": {
        "BuddyAPIContext": "Insert_Your_Own_Connection_String"
      }

 - #### Google Maps AP

For this solution to work to view the test backend map, follow the link below to setup your own API key [Google Map API Key Setup ](https://developers.google.com/maps/documentation/maps-static/get-api-key)

Paste the Google Map API key in the script tag of  wwwrootfolder , index.html as shown below
  

      <script src="https://maps.googleapis.com/maps/api/js?key=INSERT_YOUR_OWN_KEY&callback=initMap&libraries=&v=weekly"

 - #### Visual Studio

Kindly make sure to have at least Visual Studio 2019 or newer

### Running the Solution

 - Download and extract the zip folder
	 - Open the BuddyAPI Folder
 - Open project solution from Visual Studio 
	 - BuddyAPI.sln
	 - Build solution, then execute it


## Usage

 - API calls
	 - navigate to url: localhost:{port}/swagger/index.html
	 - Here you can find the full list of available API calls can be accessed. 

![Swagger Index](https://raw.githubusercontent.com/LiamCurmideGray/Buddy-Wiki/master/Github%20Wiki%20Images/Swagger%20index.png)

 - Test Map
	 - navigate to url: localhost:{port}/index.html
	 - Select level using the radio buttons found below the map.
	 - Left-click or right-click a pinpoint to set it as the start or end point respectively.
	 - Click the "Get Navigation" button to retrieve the path.
	 - Alternatively, click the "Present QR Code" button to present a QR code.
	 - Click "Update Navigation" to get the new path.
	 - Click "Reset Navigation" to reset all the fields.

![Google Map Faculty ICT Image](https://raw.githubusercontent.com/LiamCurmideGray/Buddy-Wiki/master/Github%20Wiki%20Images/Google%20Faculty%20ICT%20Map.png)

For more information, please refer to the Documentation



## Acknowledgements & Contacts
The following developers were responsible for creating this backend solution:
 - Andrew Magri
	 - andrew.magri.19@um.edu.mt
 - Liam Curmi de Gray
	 - liam.curmidegray.17@um.edu.mt
 - Nina Musumeci
	 - nina.musumeci.19@um.edu.mt
 - Stefano Schembri
	 - stefano.schembri.19@um.edu.mt


