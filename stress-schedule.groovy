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

def totalExecutions = 500 //this will be multiplied by 4 so 400 = 1600 reports
def reportsPath = "/tmp/reports"
def reportURI = "/public/Samples/DeutscheBankPoc/Reports/9.CustomerDetailReport"
def ProdFamilyList = [ "Food", "Drink", "All", "Non-Consumable"]

def folder = new File(reportsPath)
assert folder.exists(), "The report folder ${reportsPath} does not exist!" 
assert folder.list().length <=0, "The report folder ${reportsPath} is not empty!"

//get input controls
def inputControls = new RESTClient('http://localhost:8080/jasperserver-pro/rest_v2/')
inputControls.auth.basic 'jasperadmin', 'jasperadmin'
def resp = inputControls.get( path : 'reports/public/Samples/Reports/9.CustomerDetailReport/inputControls/values/', contentType: XML, headers : [Accept : 'application/xml'] ) 
    assert resp.status == 200

//set up schedule
def jrs = new HTTPBuilder('http://localhost:8080/jasperserver-pro/rest_v2/jobs/')
jrs.auth.basic 'jasperadmin', 'jasperadmin'

def executionNum = 1
def totalExecutionNum = 1
def timeStart = new Date() //start timer

for (int i = 0; i < 4; i++) {
executionNum = 1
    while (executionNum <= totalExecutions) {
        jrs.request(PUT, XML) {
        body = """
        <job>
            <baseOutputFilename>StressReport"""  +  resp.data.inputControlState.options.option.value[executionNum] + ProdFamilyList[i] + """</baseOutputFilename>
            <repositoryDestination>
                <folderURI>/public/</folderURI>
                <saveToRepository>false</saveToRepository>
                <outputLocalFolder>""" + reportsPath + """</outputLocalFolder>
                <overwriteFiles>true</overwriteFiles>
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
               <key>Product_Family</key><value xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xsi:type="xs:string">""" +  ProdFamilyList[i] + """</value>
             </entry>
             <entry>
               <key>customerId</key><value xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xsi:type="xs:int">""" + new Integer(resp.data.inputControlState.options.option.value[executionNum].toString()) + """</value>
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
        println("Scheduled " + totalExecutionNum + " jobs. Customer ID " + resp.data.inputControlState.options.option.value[executionNum] + " | Prod. Family " + ProdFamilyList[i])
        executionNum++
        totalExecutionNum++
    } 
}

def list = []

while (true) {
	list = []
    //def dir = new File(reportsPath)
    folder.eachFileRecurse (FileType.FILES) { file ->
    list << file
    }
    def timeStop = new Date()
    TimeDuration duration = TimeCategory.minus(timeStop, timeStart)
    println list.size() + " reports executed in " + duration  
    sleep(10000)
    if (list.size()+1 == totalExecutionNum) { break }
}    

