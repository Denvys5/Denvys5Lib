# SimpleConfigLib

Simple library to create configuration files for your software

Usage:

Initialize config
```java
Config config = ConfigFactory.getConfig(String <filename>);
```

Add configurable variable. This will load value from config, if it exists. And it will setup configuration with default value.
```java
String <variable> = config.addPropertyString(String <propertyName>, String <propertyDefault>);
```

## NEW!
Added support for JSON based configuration! 
Now, you can save and read JSONs from files.

Usage:
Importing object from file
```java
<T> object = getJsonObject(String <absolutePath>, Class<T> <target>);
```
Saving object to file
```java
ConfigFactory.saveJsonObject(String <absolutePath>, Object <object>);
```
Where <T> is your object class type.

Under the hood you will find GSON implementation for JSON serialization and deserialization.