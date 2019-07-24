/// <reference path="./interfaces.ts"/>

import { App } from "./app";
import { Route } from "./route";
import { AppEvent } from "./app_event";
import { Controller } from "./controller";
import { View, ViewSettings } from "./view";
import { Model, ModelSettings } from "./model";
import { Injectable, classFactory } from "./ioc";

//提供从一个文件导入框架的访问入口
export { App, AppEvent, Controller, View, ViewSettings, Model, ModelSettings, 
    Route, Injectable, classFactory};