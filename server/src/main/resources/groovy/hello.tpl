yieldUnescaped '<!doctype html>'
html {
    head {
        meta(charset:"utf-8")
        meta('http-equiv':"X-UA-Compatible", content:"IE=edge")
        meta(name:"viewport", content:"width=device-width")
        title('Spring resource handling')
        link(href: linkTo.apply('/css/main.css'), type: 'text/css', rel: 'stylesheet')
        script('data-curl-run': linkTo.apply('/run.js'), src: linkTo.apply('/lib/curl/src/curl.js'), async: 'true', "")
    }
    body {
        div(class:'container') {
            div(class:'jumbotron') {
                h1(id:"greeting", "{insert greeting here}")
            }
            div(id:"logo", "")
        }
    }
}