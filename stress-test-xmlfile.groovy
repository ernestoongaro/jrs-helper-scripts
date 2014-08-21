/* 
JRS Stress Scheduler 
*/

@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7')
import groovyx.net.http.RESTClient
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovy.io.FileType
import groovy.time.*

def reportsPath = "/tmp/reports"
def reportURI = "/public/SESamples/LargeXMLReport/Resources/ImageReport/CustomersReportparametrized"

//set up schedule
def jrs = new HTTPBuilder('http://localhost:8080/jasperserver-pro/rest_v2/jobs/')
jrs.auth.basic 'jasperadmin', 'jasperadmin'

def executionNum = 1
def totalExecutionNum = 1
def timeStart = new Date() //start timer

//get input controls
def xmlFile = "/opt/jasperreports-server-5.6/apache-tomcat/webapps/docs/customers.xml"
def xml = new XmlSlurper().parse(xmlFile)
    xml.customer.customer_id.each {    customer ->            // iterate through customer file
        jrs.request(PUT, XML) {
        body = """
        <job>
            <baseOutputFilename>StressReport"""  +  customer.text() + """</baseOutputFilename>
            <repositoryDestination>
                <folderURI>/public/SESamples/LargeXMLReport/Resources/PDF</folderURI>
                <saveToRepository>true</saveToRepository>
            </repositoryDestination>
            <description/>
            <label>MyNewJob""" + executionNum + """</label>
            <outputFormats>
                <outputFormat>PDF</outputFormat>
            </outputFormats>
            <outputLocale/>
         <source> 
          <reportUnitURI>""" + reportURI + """</reportUnitURI>
           <parameters>
           <parameterValues>
             <entry>
               <key>customerID</key><value xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xsi:type="xs:string">""" +  customer.text() + """</value>
             </entry>
             </parameterValues>
           </parameters>
          </source>
  
           <simpleTrigger>
                <startType>1</startType>
                <timezone>Europe/Dublin</timezone>
                <version>0</version>
                <occurrenceCount>1</occurrenceCount>
            </simpleTrigger>
        </job>
        """
        headers.'Content-Type' = 'application/xml'
        }
        println("Scheduled " + totalExecutionNum + " jobs. Customer ID " +  customer.text())
        executionNum++
        totalExecutionNum++
	sleep(10)
    } 
prinln("Finished! Scheduled " + totalExecutionNum + "jobs.")
