@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7')
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

def baseDir = '/tmp/reports/'
def restService = 'jasperserver-pro/rest_v2/reports'

def http = new HTTPBuilder( 'http://localhost:8080/' )
http.auth.basic 'jasperadmin', 'jasperadmin'

new File(baseDir,'CustomerDetail.pdf') << http.request( GET ) {
  uri.path = restService + '/public/Samples/Reports/9.CustomerDetailReport.pdf'
  uri.query = [ customerId: '9163'] 
}

new File(baseDir,'TopFives.pdf') << http.request( GET ) {
  uri.path = restService + '/public/Samples/Reports/TopFivesReport.pdf'
  uri.query = [ endMonth:'7', startMonth: '6' ] 
}

new File(baseDir,'Accounts.pdf') << http.request( GET ) {
  uri.path = restService + '/public/Samples/Reports/AllAccounts.pdf'
}
