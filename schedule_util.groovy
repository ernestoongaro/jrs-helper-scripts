#!/usr/bin/env groovy


@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7')
import groovyx.net.http.RESTClient
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovy.time.*

def jobStart = new Date() 
totalReports = new Double(10281.0)

	def scheduledJobs = new RESTClient('http://SERVER:8080/jasperserver-pro/rest_v2/resources/?folderUri=/public/SESamples/LargeXMLReport/Resources/PDF&type=file&ForceTotalCount=true&limit=1')
	scheduledJobs.auth.basic 'superuser', 'superuser'
	def resp = scheduledJobs.get( path : '', contentType: XML, headers : [Accept : 'application/xml'] )
	def counter = 0
	def items = 0


import javax.management.ObjectName
import javax.management.remote.JMXConnectorFactory as JmxFactory
import javax.management.remote.JMXServiceURL as JmxUrl

def serverUrl = 'service:jmx:rmi:///jndi/rmi://localhost:9004/jmxrmi'
def server = JmxFactory.connect(new JmxUrl(serverUrl)).MBeanServerConnection
def serverInfo = new GroovyMBean(server, 'Catalina:type=Server').serverInfo
def HeapMemoryUsage = new GroovyMBean(server, 'java.lang:type=Memory').HeapMemoryUsage.used
def NonHeapMemoryUsage = new GroovyMBean(server, 'java.lang:type=Memory').NonHeapMemoryUsage.used
def threads = new GroovyMBean(server, 'Catalina:type=ThreadPool,name="http-bio-8080"').currentThreadCount
items = new Double(resp.headers.'Total-Count')	

println new Date().toString() +  ","  + items + "," + HeapMemoryUsage + "," + NonHeapMemoryUsage + "," + threads 
