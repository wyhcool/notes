/// <reference path="./interfaces.ts"/>

class EventEmittor implements IEventEmittor {
    protected _mediator: IMediator;
    protected _events: Array<IAppEvent>;

    constructor(mediator: IMediator) {
        this._mediator = mediator;
    }

    triggerEvent(event: IAppEvent) {
        this._mediator.publish(event);
    };

    //调用 subscribeToEvents, _events 属性被用来存储组件订阅的事件
    subscribeToEvents(events: Array<IAppEvent>) {
        this._events = events;
        for (var i = 0; i < this._events.length; i++) {
            this._mediator.subscribe(this._events[i]);
        }
    };
    //调用 unsubscribeToEvents 移除事件时, 不需要再传入整个事件列表
    unsubscribeToEvents() {
        for (var i = 0; i < this._events.length; i++) {
            this._mediator.unsubscribe(this._events[i]);
        }
    };
}

export { EventEmittor };