'use strict';

var baseUrl;
(function () {
    var cjsConfig = {
        loader: 'curl/loader/cjsm11'
    };

    curl.config({
        baseUrl: baseUrl || '',
        packages: {
            hello: { name: 'hello',
                location: 'js',
                main: 'hello.js',
                config: cjsConfig },
            curl: { location: 'lib/curl/src/curl/' }
        },
        paths: {
            jquery: 'lib/jquery/jquery.min'
        }
    });

    curl(['hello', 'jquery']).then(start, fail);

    function start(hello, $) {
        $(function(){
            hello.greet();
        });
    }

    function fail(ex) {
        throw ex;
    }

}());