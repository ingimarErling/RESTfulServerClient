# RESTfulServerClient

This is the first of a few projects, testing out restful-server and java-clients calling that server. 

1. maven
2. deployed in Tomcat 8.


# log4j and tomcat

create the file /lib/log4j.properties

```
 log4j.rootLogger=INFO, R 


  log4j.appender.R=org.apache.log4j.RollingFileAppender


  log4j.appender.R.File=${catalina.base}/logs/tomcat.log


  log4j.appender.R.MaxFileSize=10MB


  log4j.appender.R.MaxBackupIndex=10 


  log4j.appender.R.layout=org.apache.log4j.PatternLayout

                                                                                                                                                              
  log4j.appender.R.layout.ConversionPattern=%p %t %c - %m%n
```
