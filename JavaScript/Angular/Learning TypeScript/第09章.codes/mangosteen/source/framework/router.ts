/// <reference path="./interfaces.ts"/>

import { EventEmittor } from "./event_emitter";
import { Route } from "./route";
import { AppEvent } from "./app_event";

class Router extends EventEmittor implements IRouter {

    private _defaultController: string;
    private _defaultAction: string;

    constructor(mediator: IMediator, defaultController: string, 
        defaultAction: string) {
        super(mediator);
        this._defaultController = defaultController || "home";
        this._defaultAction = defaultAction || "index";
    }

    public initialize(): void {

        //检测 URL 被用户改变
        $(window).on('hashchange', () => {
            var r = this.getRoute();
            this.onRouteChange(r);
        });

        //拥有改变 URL 的能力
        this.subscribeToEvents([
            //用于在应用启动时触发路由
            new AppEvent("app.initialize", null, (e:any, data?: any) => {
                this.onRouteChange(this.getRoute());
            }),

            //用于从其他组件改变URL
            new AppEvent("app.route", null, (e:any, data?: any) => {
                this.setRoute(data);
            })
        ]);
    }

    //读取 URL
    private getRoute() {
        var h = window.location.hash;
        return this.parseRoute(h);
    }

    //改变 URL
    private setRoute(route: Route) {
        var s = route.serialize();
        window.location.hash = s;
    }

    //解析 URL
    private parseRoute(hash: string) {
        var comp, controller, action, args, i;
        if (hash[hash.length - 1] === "/") {
            hash = hash.substring(0, hash.length - 1);
        }
        comp = hash.replace("#", "").split("/");
        controller = comp[0] || this._defaultController;
        action = comp[1] || this._defaultAction;

        args = [];
        for (i = 2; i < comp.length; i++) {
            args.push(comp[i]);
        }
        return new Route(controller, action, args);
    }

    //通过中介器将控制权转移给调度器
    private onRouteChange(route: Route) {
        this.triggerEvent(new AppEvent("app.dispatch", route, null));
    }
}

export { Router };