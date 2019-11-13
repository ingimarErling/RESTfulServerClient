# RESTfulServerClient

This is the first of a few projects, testing out restful-server and java-clients calling that server. 

## proof of concept : RESTfulServerClient

1. To be able to save large files with a java-client. 
2. To be able to send some metadata with that file, i.e 'owner':'me'

## environment 

1. maven : ```mvn clean package``` creates the JerseyServer.war-file (name is set in the pom.xml)
2. deployed in Tomcat ver. 8.0.27 
3. use log4j in Tomcat, see the config below '/lib/log4j.properties'
4. [wip] client fetches an image from the '/tmp'-directory
5. [wip] server stores the image in the '/tmp/uploader'-directory
6. [wip] fileUpload.html (2019-11-13)


## Appendix

### creating large files in linux

hardcoded source and target:

1. The source for the files is '/tmp'
2. cd /tmp
3. fallocate -l 1000mb 1000mb.zip 
4. The target for the files is '/tmp/uploader'  (mkdir /tmp/uploader)

### Loogging :  log4j and tomcat

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

1. cd /home/ingimar/SERVERS/apache-tomcat-8.0.27/logs
2.  tail -f tomcat.log 