#Readme
Author credits: Konstia from the Client team gracefully developed this extension.

###Features
  - Create crosslinked dashboards by clicking on one dashlet and having it drive another dashlet
  - Supports multiple widgets

###Changelog
  - May 21, 2015: 0.2 - Support multiple widgets in same dashboard
  - May 20, 2015: 0.1 - First release

###Installation
Place `dashboardHyperlinkHandlerUpdateDashboardParams.js` into:    
> <js-install>/jasperserver-pro/scripts 
>
> <js-install>/jasperserver-pro/optimized-scripts

###Usage
You will need to create a Dashboard hyperlink under the heading of "Manually Created Filters" then wire each parameter to be upated by that filter

#### For Text Elements 
```
<textField hyperlinkType="UpdateDashboardParams">
	<reportElement x="0" y="0" width="102" height="30"/>
	<textFieldExpression><![CDATA[$F{store_name}]]></textFieldExpression>
	<hyperlinkParameter name="parStoreId">
	    <hyperlinkParameterExpression><![CDATA[$F{store_id}]]></hyperlinkParameterExpression>
	</hyperlinkParameter>
</textField>
```
Hyperlink type must be *UpdateDashboardParams* and then you pass the name of the receiving parameter, in this case parStoreId

### For Charts
```
<hc:series name="Measure1">
	<hc:contributor name="SeriesItemHyperlink">
	<hc:contributorProperty name="hyperlinkType" valueType="Constant" value="UpdateDashboardParams"/>
	    <hc:contributorProperty name="state" valueType="Bucket" value="State.state"/>
	</hc:contributor>
</hc:series>
```

### In visualize.js 
See this example http://jsfiddle.net/n0r48c2p/3/

###TODO
  - Can’t handle hyperlinks in preview mode
  - Add better support for custom hyperlinks in Jaspersoft Studio
  ~~- visualize.js sample only works with one dashlet link at a time, forgets others~~
  - Not included by default :)


