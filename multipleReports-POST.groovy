@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7')
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovy.xml.*


def baseDir = '/tmp/reports/'
def jobService = 'jasperserver-pro/rest_v2/jobs/'

def http = new HTTPBuilder( 'http://localhost:8080/' )
http.auth.basic 'jasperadmin', 'jasperadmin'



def schedule = http.request(PUT, XML) {
uri.path = jobService  

body = {
    job {
        baseOutputFilename 'Top5ReportDEMO'
        repositoryDestination {
            folderURI '/public'
            outputDescription 'some'
            overwriteFiles 'false'
            sequentialFilenames 'true'
            version '0'
         }
         description 'some'
         label 'ExampleJob'
         outputFormats {
             outputFormat 'PDF'
             outputFormat 'XLS'
          }
          outputLocale 'en_US'
          source {
              reportUnitURI '/public/Samples/Reports/TopFivesReport'
              parameters {
                  entry {
                      key 'startMonth'
                      value '6'
                  }
                   entry {
                      key 'endMonth'
                      value '10'
                  }
               }
            }
            simpleTrigger {
                startType '1'
                timezone 'Europe/Dublin'
                occurrenceCount '1'
             }
        }
  }

response.success = {resp, xml ->
       println("Success! Schedule ID " + xml.id)
       println("HTTP Code: " + resp.getStatus())
    }


response.failure = {resp, xml ->
       println("Failure! Schedule ID" + xml)
       println("HTTP Code: " + resp.getStatus())
    }

}