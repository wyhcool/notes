import { Controller, AppEvent } from "../../framework/framwork";
import { MarketView } from "../views/market_view";
import { NasdaqModel } from "../models/nasdaq_model";
import { NyseModel } from "../models/nyse_model";


class MarketController extends Controller {
    
    private _marketView: IView;
    private _nasdaqModel: IModel;
    private _nyseModel: IModel;

    constructor(mediator: IMediator) {
        super(mediator);
        this._marketView = new MarketView(mediator);
        this._nasdaqModel = new NasdaqModel(mediator);
        this._nyseModel = new NyseModel(mediator);
    }

    public initialize(): void {
        //订阅 controller 中所有可用方法的事件
        this.subscribeToEvents([
            new AppEvent("app.controller.market.nasdaq", null, (e, args:string[]) => {
                this.nasdaq(args);
            }),
            new AppEvent("app.controller.market.nyse", null, (e, args:string[]) => {
                this.nyse(args);
            })
        ]);
        //调用 View.initialize() 方法初始化 view
        this._marketView.initialize();
        //调用 Model.initialize() 方法初始化 model
        this._nasdaqModel.initialize();
        this._nyseModel.initialize();
    }

    //销毁 view/model 并停止监听 controller 中的变化
    public dispose(): void {
        //销毁 controller 事件
        this.unsubscribeToEvents();

        //销毁 view 和 model 事件
        this._marketView.dispose();
        this._nasdaqModel.dispose();
        this._nyseModel.dispose();
    }


    public nasdaq(args: string[]) {
        this._mediator.publish(
            new AppEvent("app.model.nasdaq.change", null, null)
        );
    }

    public nyse(args: string[]) {
        this._mediator.publish(
            new AppEvent("app.model.nyse.change", null, null)
        );
    }
}

export { MarketController }