jrs-helper-scripts
==================

Helper scripts for JasperReports Server. These are quick-n-dirty examples...for something more complete see https://github.com/Jaspersoft/jrs-rest-java-client

stress-test-xmlfile.groovy
==================
Iterate through an xml file (instead of sql like stress-schedule.groovy)

schedule_util.groovy
==================
Create a "csv" file with things like memory used and number of reports scheduled

schedule_util_runner.sh
==================
Shell script to call schedule_util.groovy every XX seconds

stress-schedule.groovy
==================
Used for scheduling as many reports as you want and have them run at the same momement for concurrency testing
