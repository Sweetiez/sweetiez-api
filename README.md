# Sweetiez API

Is an application which manage administration and purchases for FI-Sweet company.

## Architecture
    sweetiez-api
    ├── launcher
    │   ├── src          
    │   │   ├── main          
    │   │   └── test          
    │   └── pom.xml             
    ├── products
    │   ├── src          
    │   │   ├── main          
    │   │   └── test          
    │   └── pom.xml          
    ├── .gitignore          
    ├── Dockerfile          
    ├── pom.xml          
    └── README.md          

## Get project

```
> git clone https://github.com/Sweetiez/sweetiez-api.git
  (or git clone git@github.com:Sweetiez/sweetiez-api.git)
> cd sweetiez-api
```

## Build & Run

Make sure you are at the root project before executing the following commands. 

### Command line: 
    
```
> mvn clean package
> cd launcher/target
> java -jar launcher-0.0.1-SNAPSHOT.jar
```

### Docker:

```
> docker build -t sweetiez-api .
> docker run -dp 8050:8080 sweetiez-api
```
