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


## Loogging :  log4j and tomcat

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


# Settings for Wildfly.

## before starting Wildfly, increase Java Heap Size 

**(A)**

1. edit standalone.conf (PRESERVE_JAVA_OPTS=false) 
2. from command-line : export JAVA_OPTS="-Xms1024M -Xmx40968M -XX:MaxHeapSize=4096M -Djava.net.preferIPv4Stack=true"
3. from command-line : ./standalone.sh

```
~/SERVERS/wildfly-10.1.0.Final/bin  export JAVA_OPTS="-Xms1024M -Xmx4096M -XX:MaxHeapSize=4096M -Djava.net.preferIPv4Stack=true"  
 ~/SERVERS/wildfly-10.1.0.Final/bin  ./standalone.sh                                                                               
JAVA_OPTS already set in environment; overriding default settings with values: -Xms1024M -Xmx4096M -XX:MaxHeapSize=4096M -Djava.net.preferIPv4Stack=true
=========================================================================
```

**(B)**

1. jboss-cli.sh --connect 
2. /subsystem=undertow/server=default-server/http-listener=default/:read-attribute(name=max-post-size)
3. :reload



