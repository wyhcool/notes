/*依赖反转意味着不应该依赖一个具体的类而是依赖它的抽象(接口) */

interface IAppEvent {
    topic: string;
    data: any;
    handler: (e: any, data: any) => void;
}

interface IMediator {
    //用来触发事件, 当发布一个事件时, 所有订阅事件的地方都会收到通知
    publish(e: IAppEvent): void;
    //订阅一个事件, 即为一个事件设置事件处理
    subscribe(e: IAppEvent): void;
    //取消订阅一个事件, 即移除一个事件的处理函数
    unsubscribe(e: IAppEvent): void;
}

//指明可用的程序设置
interface IAppSettings {
    isDebug: boolean;
    defaultController: string;
    defaultAction: string;
    controllers: Array<IControllerDetails>;
    onErrorHandler: (o: Object) => void;
}

interface IControllerDetails {
    controllerName: string;
    controller: {
        new (...arg: any[]): IController;
    };
}

interface IRoute {
    controllerName: string;
    actionName: string;
    args: Object[];
    serialize(): string;
}

//所有组件都需要使用中介器与其他组件进行通信
interface IEventEmittor {
    triggerEvent(event: IAppEvent): void;
    subscribeToEvents(events: Array<IAppEvent>): void;
    unsubscribeToEvents(events: Array<IAppEvent>): void;
}

interface IRouter extends IEventEmittor {
    initialize(): void;
}

interface IDispatcher extends IEventEmittor {
    initialize(): void;
}

interface IController extends IEventEmittor {
    initialize(): void;
    dispose(): void;
}

interface IModel extends IEventEmittor {
    initialize(): void;
    dispose(): void;
}

interface IView extends IEventEmittor {
    initialize(): void;
    dispose(): void;
}