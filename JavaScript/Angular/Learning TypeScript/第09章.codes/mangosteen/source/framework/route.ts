/// <reference path="./interfaces.ts"/>

class Route implements IRoute {
    controllerName: string;
    actionName: string;
    args: Object[];

    constructor(controllerName: string, actionName: string, args: Object[]) {
        this.controllerName = controllerName;
        this.actionName = actionName;
        this.args = args;
    }

    public serialize(): string {
        var s, sargs;
        sargs = this.args.map(a=>a.toString()).join("/");
        s = `${this.controllerName}/${this.actionName}/${sargs}`;
        return s;
    };
}

export { Route };