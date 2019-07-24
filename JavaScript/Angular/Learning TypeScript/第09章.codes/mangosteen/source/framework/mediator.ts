/// <reference path="./interfaces.ts"/>

import * as $ from "jquery";

class Mediator implements IMediator {
    private _$: any;
    private _isDebug: boolean;

    constructor(isDebug: boolean = false) {
        //在内存中维护的 jQuery 对象
        this._$ = $({});
        this._isDebug = isDebug;
    }

    publish(e: IAppEvent): void {
        if (this._isDebug === true) {
            console.log(new Date().getTime(), "PUBLISH", e.topic, e.data);
        }
        this._$.trigger(e.topic, e.data);
    };
    subscribe(e: IAppEvent): void {
        if (this._isDebug === true) {
            console.log(new Date().getTime(), "SUBSCRIBE", e.topic, e.data);
        }
        this._$.on(e.topic, e.handler);
    };
    unsubscribe(e: IAppEvent): void {
        if (this._isDebug === true) {
            console.log(new Date().getTime(), "UNSUBSCRIBE", e.topic, e.data);
        }
        this._$.off(e.topic);
    };
}

export { Mediator };