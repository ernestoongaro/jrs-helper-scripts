define(function(require) {
    "use strict";

    var $ = require("jquery"),
        _ = require("underscore");

    return {
        events: {
            click: function(event, linkData) {
                if (window.location.href.indexOf("dashboard/viewer") > -1) {
                    var hashParts = window.location.hash.split("&"),
                        reportUri = hashParts[0],
                        params = {};

                    for (var i = 1; i < hashParts.length; i++) {
                        if (hashParts[i].indexOf("=") > 0) {
                            var key = hashParts[i].split("=")[0],
                                value = hashParts[i].split("=")[1];

                            if (key in params) {
                                params[key].push(value);
                            } else {
                                params[key] = [value];
                            }
                        }
                    }

                    location.replace(reportUri + "&" + $.param(_.extend(params, linkData.parameters), true));
                }
            }
        }
    }
});