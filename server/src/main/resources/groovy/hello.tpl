yieldUnescaped '<!DOCTYPE html>'
html {
    head {
        meta('http-equiv':'"Content-Type", content="text/html; charset=utf-8"')
        title('Spring resource handling')
        css("/css/main.css")
    }
    body {
        div(class:'container') {
            div(class:'jumbotron') {
                h1(id:'hello', "Hello")
            }
        }
    }
}