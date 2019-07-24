/// <reference path="./interfaces.ts"/>

import { EventEmittor } from "./event_emitter";
import { AppEvent } from "./app_event";

//调度器被用来在需要时创建与销毁 controller
class Dispatcher extends EventEmittor implements IDispatcher {

    private _controllersHashMap: any;
    private _currentController: IController;
    private _currentControllerName: string;

    constructor(mediator: IMediator, controllers: IControllerDetails[]) {
        super(mediator);
        this._controllersHashMap = this.getController(controllers);
        this._currentController = null;
        this._currentControllerName = null;
    }

    public initialize(): void {
        //监听 app.dispatch 事件
        this.subscribeToEvents([
            new AppEvent("app.dispatch", null, (e: any, data?: any) => {
                this.dispatch(data);
            })
        ]);
    }

    //生成一个 hash 表
    //使用 controller 的名字作为键，使用 controller 的构造函数作为值
    private getController(controllers: IControllerDetails[]): Object {
        var hashMap: any,
            hashMapEntry: any,
            name: string,
            controller: IControllerDetails,
            l: number;

        hashMap = {};
        l = controllers.length;

        if (l <= 0) {
            this.triggerEvent(
                new AppEvent("app.error",
                    "Cannot create an application without at least one controller.",
                    null)
            );
        }

        for (var i = 0; i < l; i++) {
            controller = controllers[i];
            name = controller.controllerName;
            hashMapEntry = hashMap[name];
            if (hashMapEntry !== null && hashMapEntry !== undefined) {
                this.triggerEvent(
                    new AppEvent("app.error",
                        "Two controller cannot use the same name.",
                        null)
                );
            }
            hashMap[name] = controller.controller;
        }

        return hashMap;
    }

    //负责创建、初始化、销毁一个 controller 的实例
    private dispatch(route: IRoute): void {
        var Controller: { new(...args: any[]): IController } = this._controllersHashMap[route.controllerName];

        //试图发现 controller
        if (Controller === null || Controller === undefined) {
            this.triggerEvent(
                new AppEvent("app.error",
                    `Controller not found: ${route.controllerName}.`,
                    null)
            );
        } else {

            //创建一个 controller 的实例
            var controller: any = new Controller(this._mediator);
            //获取 action 方法
            //NOTICE: typescript 对象用中括号引用属性报错怎么处理 -- 设置类型 any
            var action: Function = controller[route.actionName];
            if (action === null || action === undefined) {
                this.triggerEvent(
                    new AppEvent("app.error",
                        `Action not found in controller: ${route.actionName} - + ${route.controllerName}.`,
                        null)
                );
            } else {

                if (this._currentController == null) {
                    //初始化 controller
                    this._currentControllerName = route.controllerName;
                    this._currentController = controller;
                    this._currentController.initialize();
                } else {

                    //若之前的 controller 不再需要, 则销毁
                    if (this._currentControllerName !== route.controllerName) {
                        this._currentController.dispose();
                        this._currentControllerName = route.controllerName;
                        this._currentController = controller;
                        this._currentController.initialize();
                    }
                }

                //将流从调度器传递至 controller
                this.triggerEvent(
                    new AppEvent(`app.controller.${this._currentControllerName}.${route.actionName}`,
                        route.args,
                        null)
                );
            }
        }
    }
}

export { Dispatcher };