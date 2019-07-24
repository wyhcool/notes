/// <reference path="./interfaces.ts"/>

import { Dispatcher } from "./dispatcher";
import { Mediator } from "./mediator";
import { AppEvent } from "./app_event";
import { Router } from "./router";

class App {
    private _dispatcher: IDispatcher;
    private _mediator: IMediator;
    private _router: IRouter;
    private _controllers: IControllerDetails[];
    private _onErrorHandler: (o: Object) => void;

    //初始化调度器, 中介器, 路由, 控制器和全局错误处理函数
    constructor(appSettings: IAppSettings) {
        this._controllers = appSettings.controllers;
        //当新建一个程序时, 它将自动创建一个新的中介器, 并将其传入路由和调度器
        //这意味着唯一的中介器实例被程序所有组件共享, 即单例
        //它将在程序的生命周期内一直存在内存中
        this._mediator = new Mediator(appSettings.isDebug || false);
        this._router = new Router(this._mediator, appSettings.defaultController,
            appSettings.defaultAction);
        this._dispatcher = new Dispatcher(this._mediator, this._controllers);
        this._onErrorHandler = appSettings.onErrorHandler;
    }

    //创建实例后, 必须调用 initialize 方法
    public initialize() {
        this._router.initialize();
        this._dispatcher.initialize();
        this._mediator.subscribe(new AppEvent("app.error", null, (e: any, data?: any) => {
            this._onErrorHandler(data);
        }));
        this._mediator.publish(new AppEvent("app.initialize", null, null));
    }
}

export { App };