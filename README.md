Follow these steps to deploy this application on Azure App Service.
## Pre-requisites:
- Install local softwares - PostgreSQL, IDE
- Azure CLI
- Maven 3.x
- Git client
- JDK 8
- Command Line interface
- Create PostgreSQL database and table structure

## Configure appSettings

Add the **appSettings** section to the **configuration** section of **azure-webapp-maven-plugin**. Change the values based on the application requirements.

```
<plugin> 
    <groupId>com.microsoft.azure</groupId>  
    <artifactId>azure-webapp-maven-plugin</artifactId>  
    <version>1.9.1</version>  
    <configuration> 
        <schemaVersion>V2</schemaVersion>  
        <resourceGroup>BEIS</resourceGroup>  
        <appName>subsidy-search-service</appName>
        <appServicePlanResourceGroup>BEIS</appServicePlanResourceGroup>
        <appServicePlanName>transparency-app-svc-plan</appServicePlanName>
        <pricingTier>P2v2</pricingTier>  
        <region>northeurope</region>  
        <runtime> 
            <os>linux</os>  
            <javaVersion>jre8</javaVersion>  
        </runtime>  
        <appSettings> 
            <property>
                <name>PORT</name> 
                <value>8581</value>
            </property>
        </appSettings>  
        <deployment> 
            <resources> 
                <resource> 
                    <directory>${project.basedir}/target</directory>  
                    <includes> 
                        <include>*.jar</include> 
                    </includes> 
                </resource> 
            </resources> 
        </deployment> 
        
        <stopAppDuringDeployment>true</stopAppDuringDeployment>
    
    </configuration> 
</plugin> 
```

## Package the application
```
mvn clean package
```

## Start the application on local system to verify the app is working
```
mvn spring-boot:run
```

Test the webapp by browsing to http://localhost:8581/health. Ensure the application works.

## Logon to Azure:
Use ```az login``` to connect to the Azure subscription. Ensure you have contributor access on the Resource Group.

## Deploy to Azure App Service

```
mvn azure-webapp:deploy
```