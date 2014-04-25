'use strict';

var baseUrl;
(function () {
    curl.config({
        baseUrl: baseUrl || '',
        packages: {
            curl: { location: 'lib/curl/src/curl/' }
        },
        paths: {
            jquery: 'lib/jquery/jquery.min'
        }
    });

    curl(['app/hello', 'jquery']).then(start, fail);

    function start(hello, $) {
        $(function(){
            hello.greet();
        });
    }

    function fail(ex) {
        throw ex;
    }

}());