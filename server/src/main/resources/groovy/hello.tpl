yieldUnescaped '<!doctype html>'
html {
    head {
        meta(charset:"utf-8")
        meta('http-equiv':"X-UA-Compatible", content:"IE=edge")
        meta(name:"viewport", content:"width=device-width")
        title('Spring resource handling')
        link(href: linkTo.apply('/css/main.css'), type: 'text/css', rel: 'stylesheet')
        script(src: linkTo.apply('/lib/es6-module-loader/dist/es6-module-loader.js'), "")
        script(src: linkTo.apply('/lib/systemjs/dist/system.js'), "")
        script("""
        	System.map['jquery'] = '/lib/jquery/dist/jquery.min';
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