# SimpleConfigLib

Simple library to create configuration files for your software

Usage:

Initialize config
```java
Config config = ConfigFactory.getConfig(filename);
```

Add configurable variable. This will load value from config, if it exists. And it will setup configuration with default value.
```java
String <variable> = config.addPropertyString(String <propertyName>, String <propertyDefault>);
```