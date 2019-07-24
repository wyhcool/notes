import { Controller, AppEvent } from "../../framework/framwork";
import { QuoteModel } from "../models/quote_model";
import { ChartModel } from "../models/chart_model";
import { SymbolView } from "../views/symbol_view";
import { ChartView } from "../views/chart_view";

class SymbolController extends Controller {
    private _quoteModel: IModel;
    private _chartModel: IModel;
    private _symbolView: IView;
    private _chartView: IView;

    constructor(mediator: IMediator) {
        super(mediator);
        this._quoteModel = new QuoteModel(mediator);
        this._chartModel = new ChartModel(mediator);
        this._symbolView = new SymbolView(mediator);
        this._chartView = new ChartView(mediator);
    }

    public initialize(): void {
        //订阅 controller 中所有可用方法的事件
        this.subscribeToEvents([
            new AppEvent("app.controller.symbol.quote", null, (e, symbol: string) => {
                this.quote(symbol);
            })
        ]);
        //调用 View.initialize() 方法初始化 view
        this._symbolView.initialize();
        this._chartView.initialize();
        //调用 Model.initialize() 方法初始化 model
        this._quoteModel.initialize();
        this._chartModel.initialize();
    }

    public dispose(): void {
        //销毁 controller 事件
        this.unsubscribeToEvents();

        //销毁 view 和 model 事件
        this._symbolView.dispose();
        this._chartView.dispose();
        this._quoteModel.dispose();
        this._chartModel.dispose();
    }

    public quote(symbol: string) {
        this.triggerEvent(
            new AppEvent(
                "app.model.quote.change", symbol, null
            )
        );
    }

}

export { SymbolController }