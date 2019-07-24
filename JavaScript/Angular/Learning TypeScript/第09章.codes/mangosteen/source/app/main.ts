import { App, Injectable, classFactory } from "../framework/framwork";

// import { MarketController } from "./controllers/market_controller";

// var appSettings: IAppSettings = {
//     isDebug: true,
//     defaultController: "market",
//     defaultAction: "nasdaq",
//     controllers: [
//         { controllerName: "market", controller: MarketController },
//     ],
//     onErrorHandler: function(o: Object) {
//         alert("Sorry! there has been an error please check out the console for more info!");
//     }
// };


// var myApp = new App(appSettings);
// myApp.initialize();

// IoC Test
@Injectable()
class C {
    constructor() {

    }

    sayHello() {
        console.log("hello C")
    }
}

@Injectable()
class B {
    constructor(private c: C) {

    }

    sayHello() {
        this.c.sayHello();
    }
}

@Injectable()
class A {
    constructor(private b: B){
        b.sayHello();
    }

    sayHello() {
        console.log("hello A");
    }
}


let a: A = classFactory(A);


//Typescript 如何对 window 扩展
//(window as any).xxx = a;