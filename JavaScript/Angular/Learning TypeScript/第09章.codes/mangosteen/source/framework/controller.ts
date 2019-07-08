/// <reference path="./interfaces.ts"/>

import { EventEmittor } from "./event_emitter";

class Controller extends EventEmittor implements IController {

    constructor(mediator: IMediator) {
        super(mediator);
    }

    //必须由派生类来实现
    public initialize(): void {
        throw new Error("Controller.prototype.initialize() is abstract you must implement it!");
    }

    //必须由派生类来实现
    public dispose(): void {
        throw new Error("Controller.prototype.dispose() is abstract you must implement it!");
    }
}

export { Controller };