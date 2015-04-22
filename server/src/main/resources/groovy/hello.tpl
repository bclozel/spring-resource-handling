yieldUnescaped '<!doctype html>'
html {
    head {
        meta(charset:"utf-8")
        meta('http-equiv':"X-UA-Compatible", content:"IE=edge")
        meta(name:"viewport", content:"width=device-width")
        title('Spring resource handling')
        link(href: linkTo.apply('/css/main.css'), type: 'text/css', rel: 'stylesheet')
        script(src: linkTo.apply('/lib/system.js'), "")
        script(src: linkTo.apply('/config.js'), "")
        script("""
            System.config({baseURL: "/${appVersion.apply()}"});
        	System.import('app/app');
        """)
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