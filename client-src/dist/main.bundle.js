webpackJsonp(["main"],{

/***/ "./src/$$_lazy_route_resource lazy recursive":
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncatched exception popping up in devtools
	return Promise.resolve().then(function() {
		throw new Error("Cannot find module '" + req + "'.");
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/app/add-knowledge-base-article/add.component.html":
/***/ (function(module, exports) {

module.exports = "<form  (ngSubmit)=\"onSubmit(article)\" [formGroup]=\"article\">\r\n<div>\r\n  <div>\r\n    <label>Title: </label>\r\n    <input formControlName=\"title\" type=\"text\" placeholder=\"Title\" />\r\n    <div class=\"error\" \r\n    *ngIf=\"article.get('title').touched \r\n    && article.get('title').hasError('required')\">\r\n      Title is required\r\n    </div>\r\n  </div>\r\n  <div>\r\n    <label>Description: </label>\r\n    <quill-editor #description id=\"description\" style=\"height: 300px\"\r\n    placeholder=\"Description\"\r\n  formControlName=\"description\"></quill-editor>\r\n\r\n    <div class=\"error\" \r\n    *ngIf=\"article.get('description').touched \r\n    && article.get('description').hasError('required')\">\r\n      Description is required\r\n    </div>\r\n  </div>\r\n  <div>\r\n    <button [disabled]=\"article.invalid\">Save</button>\r\n  </div>\r\n</div> \r\n<div *ngIf=\"articleTitle\" [ngClass] = \"'success'\"> {{articleTitle}} created. </div>\r\n<div *ngIf=\"errorMessage\" [ngClass] = \"'error'\"> {{errorMessage}} </div>\r\n</form>"

/***/ }),

/***/ "./src/app/add-knowledge-base-article/add.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AddArticleComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__shared_service_knowledge_base_article_knowledge_base_article_service__ = __webpack_require__("./src/shared/service/knowledge-base-article/knowledge-base-article.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var AddArticleComponent = /** @class */ (function () {
    function AddArticleComponent(kbContentService, fb) {
        this.kbContentService = kbContentService;
        this.fb = fb;
    }
    AddArticleComponent.prototype.ngOnInit = function () {
        this.article = this.fb.group({
            title: ['', [__WEBPACK_IMPORTED_MODULE_2__angular_forms__["j" /* Validators */].required, __WEBPACK_IMPORTED_MODULE_2__angular_forms__["j" /* Validators */].minLength(2)]],
            description: ['', __WEBPACK_IMPORTED_MODULE_2__angular_forms__["j" /* Validators */].required],
        });
    };
    AddArticleComponent.prototype.onSubmit = function (_a) {
        var _this = this;
        var value = _a.value, valid = _a.valid;
        this.kbContentService.createKnowledgeBaseArticle(value)
            .subscribe(function (article) {
            _this.articleTitle = article.title;
        }, function (error) { return _this.errorMessage = error; });
    };
    AddArticleComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-add-kb-article',
            template: __webpack_require__("./src/app/add-knowledge-base-article/add.component.html"),
            styleUrls: []
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__shared_service_knowledge_base_article_knowledge_base_article_service__["a" /* KnowledgeBaseArticleService */],
            __WEBPACK_IMPORTED_MODULE_2__angular_forms__["b" /* FormBuilder */]])
    ], AddArticleComponent);
    return AddArticleComponent;
}());



/***/ }),

/***/ "./src/app/add-user/add-user.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\r\n    <toaster-container></toaster-container>\r\n    <div class=\"add-heading\"><h2 class=\"heading-margin\">Add User</h2></div>\r\n    <form  (ngSubmit)=\"onSubmit(user)\" [formGroup]=\"user\">\r\n        <div class=\"lower-container\">\r\n            <div class=\"form-box\">\r\n          <mat-form-field>\r\n            <input formControlName=\"firstName\" matInput placeholder=\"First Name\">\r\n          </mat-form-field>\r\n          <div class=\"error\" \r\n            *ngIf=\"user.get('firstName').touched \r\n            && user.get('firstName').hasError('required')\">\r\n              First Name is required\r\n          </div>\r\n        \r\n          <mat-form-field>\r\n            <input formControlName=\"lastName\" matInput placeholder=\"Last Name\">\r\n          </mat-form-field>\r\n          <div class=\"error\" \r\n            *ngIf=\"user.get('lastName').touched \r\n            && user.get('lastName').hasError('required')\">\r\n              Last Name is required\r\n          </div>\r\n        \r\n          <mat-form-field>\r\n            <mat-select formControlName=\"userRole\" placeholder=\"Role\">\r\n                <mat-option value=\"\">Select</mat-option>\r\n                <mat-option value=\"user\">User</mat-option>\r\n              <mat-option value=\"manager\">Manager</mat-option>\r\n              <mat-option value=\"admin\">Admin</mat-option>\r\n            </mat-select>\r\n          </mat-form-field>\r\n          <div class=\"error\" \r\n            *ngIf=\"user.get('userRole').touched \r\n            && user.get('userRole').hasError('required')\">\r\n              Role is required\r\n          </div>\r\n        \r\n          <mat-form-field>\r\n            <input formControlName=\"email\" matInput placeholder=\"Email\">\r\n          </mat-form-field>\r\n          <div class=\"error\" \r\n            *ngIf=\"user.get('email').touched \r\n            && user.get('email').hasError('email')\">\r\n              Please provide correct email\r\n          </div>\r\n        \r\n          <mat-form-field>\r\n            <input formControlName=\"password\" type=\"password\" matInput placeholder=\"Password\">\r\n          </mat-form-field>\r\n          <div class=\"error\" \r\n            *ngIf=\"user.get('password').touched \r\n            && user.get('password').hasError('required')\">\r\n              Password is required\r\n          </div>\r\n          <span *ngIf=\"cpwd.hasError('invalid')\"> Invalid Password </span>\r\n        \r\n          <mat-form-field>\r\n            <input formControlName=\"confirmPassword\" type=\"password\" matInput placeholder=\"Confirm Password\">\r\n          </mat-form-field>\r\n          <div class=\"error\" \r\n            *ngIf=\"user.get('confirmPassword').touched \r\n            && user.get('confirmPassword').hasError('required')\">\r\n              Confirm Password is required\r\n          </div>\r\n          <div class=\"error\" \r\n            *ngIf=\"user.get('confirmPassword').touched \r\n            && user.get('confirmPassword').hasError('notEquivalent')\">\r\n              Password and Confirm Password should match\r\n          </div>\r\n            </div>\r\n        <div class=\"button-postion\">\r\n          <button type=\"button\" mat-raised-button (click)=\"onCancle()\">Cancel</button>\r\n          <button mat-raised-button type=\"submit\">Save</button>\r\n        </div>\r\n        \r\n          <div *ngIf=\"addUserName\" [ngClass] = \"'success'\"> {{addUserName}} created. </div>\r\n        \r\n        </div>\r\n        </form>\r\n</div>\r\n\r\n"

/***/ }),

/***/ "./src/app/add-user/add-user.component.scss":
/***/ (function(module, exports) {

module.exports = ".heading-margin {\n  margin: 0px; }\n\n.mat-form-field {\n  width: 45%;\n  margin-right: 26px; }\n\n.form-box {\n  float: left;\n  width: 100%; }\n\n.button-postion {\n  float: right; }\n"

/***/ }),

/***/ "./src/app/add-user/add-user.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AddUserComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__shared_service_user_user_service__ = __webpack_require__("./src/shared/service/user/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_angular5_toaster__ = __webpack_require__("./node_modules/angular5-toaster/dist/bundles/angular5-toaster.umd.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_angular5_toaster___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4_angular5_toaster__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var AddUserComponent = /** @class */ (function () {
    function AddUserComponent(userService, fb, router, toasterService) {
        this.userService = userService;
        this.fb = fb;
        this.router = router;
        this.toasterService = toasterService;
    }
    Object.defineProperty(AddUserComponent.prototype, "cpwd", {
        get: function () {
            return this.user.get('confirmPassword');
        },
        enumerable: true,
        configurable: true
    });
    AddUserComponent.prototype.ngOnInit = function () {
        this.user = this.fb.group({
            firstName: ['', [__WEBPACK_IMPORTED_MODULE_3__angular_forms__["j" /* Validators */].required, __WEBPACK_IMPORTED_MODULE_3__angular_forms__["j" /* Validators */].minLength(2)]],
            lastName: ['', __WEBPACK_IMPORTED_MODULE_3__angular_forms__["j" /* Validators */].required],
            password: ['', __WEBPACK_IMPORTED_MODULE_3__angular_forms__["j" /* Validators */].required],
            userRole: ['', __WEBPACK_IMPORTED_MODULE_3__angular_forms__["j" /* Validators */].required],
            email: ['', __WEBPACK_IMPORTED_MODULE_3__angular_forms__["j" /* Validators */].email],
            confirmPassword: ['', __WEBPACK_IMPORTED_MODULE_3__angular_forms__["j" /* Validators */].required],
        }, { validator: this.validateConfirmPassword('password', 'confirmPassword') });
    };
    AddUserComponent.prototype.validateConfirmPassword = function (passwordKey, passwordConfirmationKey) {
        return function (group) {
            var passwordInput = group.controls[passwordKey], passwordConfirmationInput = group.controls[passwordConfirmationKey];
            if (passwordInput.value !== passwordConfirmationInput.value) {
                return passwordConfirmationInput.setErrors({ notEquivalent: true });
            }
            return passwordConfirmationInput.setErrors(null);
        };
    };
    AddUserComponent.prototype.onSubmit = function (_a) {
        var _this = this;
        var value = _a.value, valid = _a.valid;
        delete value.confirmPassword;
        this.userService.createUser(value)
            .subscribe(function (data) {
            _this.toasterService.pop('success', 'Success', data.success.message);
        }, function (error) {
            _this.toasterService.pop('error', 'Error', error.failure.message);
        });
    };
    AddUserComponent.prototype.onCancle = function () {
        this.router.navigateByUrl('/userlist');
    };
    AddUserComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-add-user',
            template: __webpack_require__("./src/app/add-user/add-user.component.html"),
            styles: [__webpack_require__("./src/app/add-user/add-user.component.scss")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__shared_service_user_user_service__["a" /* UserService */],
            __WEBPACK_IMPORTED_MODULE_3__angular_forms__["b" /* FormBuilder */],
            __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* Router */], typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_4_angular5_toaster__["ToasterService"] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_angular5_toaster__["ToasterService"]) === "function" && _a || Object])
    ], AddUserComponent);
    return AddUserComponent;
    var _a;
}());



/***/ }),

/***/ "./src/app/app-routing.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppRoutingModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__dashboard_dashboard_component__ = __webpack_require__("./src/app/dashboard/dashboard.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__add_knowledge_base_article_add_component__ = __webpack_require__("./src/app/add-knowledge-base-article/add.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__edit_knowledge_base_article_edit_component__ = __webpack_require__("./src/app/edit-knowledge-base-article/edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__login_login_component__ = __webpack_require__("./src/app/login/login.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__shared_service_helper_auth_guards__ = __webpack_require__("./src/shared/service/helper/auth-guards.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__add_user_add_user_component__ = __webpack_require__("./src/app/add-user/add-user.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__user_list_user_list_component__ = __webpack_require__("./src/app/user-list/user-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__edit_user_edit_user_component__ = __webpack_require__("./src/app/edit-user/edit-user.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};










var routes = [
    {
        path: 'dashboard',
        component: __WEBPACK_IMPORTED_MODULE_2__dashboard_dashboard_component__["a" /* DashboardComponent */],
        data: { title: 'Dashboard' },
        canActivate: [__WEBPACK_IMPORTED_MODULE_6__shared_service_helper_auth_guards__["a" /* AuthGuard */]]
    },
    {
        path: 'login',
        component: __WEBPACK_IMPORTED_MODULE_5__login_login_component__["a" /* LoginComponent */],
        data: { title: 'Login' }
    },
    {
        path: 'article/add',
        component: __WEBPACK_IMPORTED_MODULE_3__add_knowledge_base_article_add_component__["a" /* AddArticleComponent */],
        data: { title: 'Add knowledge Base Article' }
    },
    {
        path: 'article/edit/:id',
        component: __WEBPACK_IMPORTED_MODULE_4__edit_knowledge_base_article_edit_component__["a" /* EditArticleComponent */],
        data: { title: 'Edit Knowledge Base Article' }
    },
    { path: 'user/add',
        component: __WEBPACK_IMPORTED_MODULE_7__add_user_add_user_component__["a" /* AddUserComponent */],
        data: { title: 'Add User' }
    },
    { path: 'userlist',
        component: __WEBPACK_IMPORTED_MODULE_8__user_list_user_list_component__["a" /* UserListComponent */],
        data: { title: 'User List' }
    },
    { path: '',
        redirectTo: '/dashboard',
        pathMatch: 'full'
    },
    { path: 'user/edit/:id',
        component: __WEBPACK_IMPORTED_MODULE_9__edit_user_edit_user_component__["a" /* EditUserComponent */],
        data: { title: 'Edit User' }
    },
];
var AppRoutingModule = /** @class */ (function () {
    function AppRoutingModule() {
    }
    AppRoutingModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["NgModule"])({
            imports: [__WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* RouterModule */].forRoot(routes, { enableTracing: true })],
            exports: [__WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* RouterModule */]]
        })
    ], AppRoutingModule);
    return AppRoutingModule;
}());



/***/ }),

/***/ "./src/app/app.component.css":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/app.component.html":
/***/ (function(module, exports) {

module.exports = "<app-left-panel></app-left-panel >\r\n<router-outlet></router-outlet>\r\n<app-footer></app-footer>  \r\n\r\n      \r\n"

/***/ }),

/***/ "./src/app/app.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var AppComponent = /** @class */ (function () {
    function AppComponent() {
        this.title = 'angula5 template editor';
        //alert("app");
    }
    AppComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-root',
            template: __webpack_require__("./src/app/app.component.html"),
            styles: [__webpack_require__("./src/app/app.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__("./node_modules/@angular/platform-browser/esm5/platform-browser.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_ngx_quill__ = __webpack_require__("./node_modules/ngx-quill/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__app_component__ = __webpack_require__("./src/app/app.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__dashboard_dashboard_component__ = __webpack_require__("./src/app/dashboard/dashboard.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__add_knowledge_base_article_add_component__ = __webpack_require__("./src/app/add-knowledge-base-article/add.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__edit_knowledge_base_article_edit_component__ = __webpack_require__("./src/app/edit-knowledge-base-article/edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__shared_shared_module__ = __webpack_require__("./src/shared/shared.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__login_login_component__ = __webpack_require__("./src/app/login/login.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__app_routing_module__ = __webpack_require__("./src/app/app-routing.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__angular_http__ = __webpack_require__("./node_modules/@angular/http/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__add_user_add_user_component__ = __webpack_require__("./src/app/add-user/add-user.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__edit_user_edit_user_component__ = __webpack_require__("./src/app/edit-user/edit-user.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__angular_platform_browser_animations__ = __webpack_require__("./node_modules/@angular/platform-browser/esm5/animations.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16__main_main_component__ = __webpack_require__("./src/app/main/main.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_17__left_panel_left_panel_component__ = __webpack_require__("./src/app/left-panel/left-panel.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_18__footer_footer_component__ = __webpack_require__("./src/app/footer/footer.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_19__content_content_component__ = __webpack_require__("./src/app/content/content.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_20__user_list_user_list_component__ = __webpack_require__("./src/app/user-list/user-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_21__shared_material_module__ = __webpack_require__("./src/shared/material.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_22_angular5_toaster__ = __webpack_require__("./node_modules/angular5-toaster/dist/bundles/angular5-toaster.umd.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_22_angular5_toaster___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_22_angular5_toaster__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};























var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_1__angular_core__["NgModule"])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* AppComponent */],
                __WEBPACK_IMPORTED_MODULE_5__dashboard_dashboard_component__["a" /* DashboardComponent */],
                __WEBPACK_IMPORTED_MODULE_9__login_login_component__["a" /* LoginComponent */],
                __WEBPACK_IMPORTED_MODULE_6__add_knowledge_base_article_add_component__["a" /* AddArticleComponent */],
                __WEBPACK_IMPORTED_MODULE_7__edit_knowledge_base_article_edit_component__["a" /* EditArticleComponent */],
                __WEBPACK_IMPORTED_MODULE_13__add_user_add_user_component__["a" /* AddUserComponent */],
                __WEBPACK_IMPORTED_MODULE_18__footer_footer_component__["a" /* FooterComponent */],
                __WEBPACK_IMPORTED_MODULE_17__left_panel_left_panel_component__["a" /* LeftPanelComponent */],
                __WEBPACK_IMPORTED_MODULE_19__content_content_component__["a" /* ContentComponent */],
                __WEBPACK_IMPORTED_MODULE_16__main_main_component__["a" /* MainComponent */],
                __WEBPACK_IMPORTED_MODULE_20__user_list_user_list_component__["a" /* UserListComponent */],
                __WEBPACK_IMPORTED_MODULE_14__edit_user_edit_user_component__["a" /* EditUserComponent */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["BrowserModule"],
                __WEBPACK_IMPORTED_MODULE_2__angular_forms__["d" /* FormsModule */],
                __WEBPACK_IMPORTED_MODULE_3_ngx_quill__["a" /* QuillModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_common_http__["c" /* HttpClientModule */],
                __WEBPACK_IMPORTED_MODULE_12__angular_http__["a" /* HttpModule */],
                __WEBPACK_IMPORTED_MODULE_8__shared_shared_module__["a" /* SharedModule */].forRoot(),
                //InMemoryWebApiModule.forRoot(ArticleData),
                __WEBPACK_IMPORTED_MODULE_10__app_routing_module__["a" /* AppRoutingModule */],
                __WEBPACK_IMPORTED_MODULE_21__shared_material_module__["a" /* MaterialModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_forms__["i" /* ReactiveFormsModule */],
                __WEBPACK_IMPORTED_MODULE_15__angular_platform_browser_animations__["a" /* BrowserAnimationsModule */],
                __WEBPACK_IMPORTED_MODULE_22_angular5_toaster__["ToasterModule"]
            ],
            providers: [],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* AppComponent */]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/content/content.component.html":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/content/content.component.scss":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/content/content.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ContentComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var ContentComponent = /** @class */ (function () {
    function ContentComponent() {
    }
    ContentComponent.prototype.ngOnInit = function () {
    };
    ContentComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-content',
            template: __webpack_require__("./src/app/content/content.component.html"),
            styles: [__webpack_require__("./src/app/content/content.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], ContentComponent);
    return ContentComponent;
}());



/***/ }),

/***/ "./src/app/dashboard/dashboard.component.css":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/dashboard/dashboard.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\r\n  <h1>Dashboard\r\n    <a [routerLink]=\"['/knowledge-base-content-create']\" class=\"btn btn-default btn-lg\">\r\n      <span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>\r\n    </a>\r\n  </h1>\r\n  <table class=\"table\">\r\n    <thead>\r\n      <tr>\r\n        <th>Title</th>\r\n        <th>Description</th>\r\n        <th>Action</th>\r\n      </tr>\r\n    </thead>\r\n    <tbody>\r\n      <tr *ngFor=\"let kb of knowledge_base_contents\">\r\n        <td>{{ kb.title }}</td>\r\n        <td>{{ kb.description }}</td>\r\n        <td><a [routerLink]=\"['/article/edit', kb.id]\">Edit</a></td>\r\n      </tr>\r\n    </tbody>\r\n  </table>\r\n  <br/>\r\n  <a [routerLink]=\"['/article/add']\">Add Knowledge Base Article</a>\r\n  <br/> <br/>\r\n  <a [routerLink]=\"['/userlist']\">User List</a>\r\n  \r\n</div>\r\n"

/***/ }),

/***/ "./src/app/dashboard/dashboard.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DashboardComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__shared_service_knowledge_base_article_knowledge_base_article_service__ = __webpack_require__("./src/shared/service/knowledge-base-article/knowledge-base-article.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var DashboardComponent = /** @class */ (function () {
    function DashboardComponent(kbContentService) {
        this.kbContentService = kbContentService;
    }
    DashboardComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.kbContentService.listKnowledgeBaseArticle().subscribe(function (result) {
            _this.knowledge_base_contents = result;
        }, function (error) {
            // To Do proper error handling at application level
            // console.log("error in api call");
        });
    };
    DashboardComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-dashboard',
            template: __webpack_require__("./src/app/dashboard/dashboard.component.html"),
            styles: [__webpack_require__("./src/app/dashboard/dashboard.component.css")],
            encapsulation: __WEBPACK_IMPORTED_MODULE_0__angular_core__["ViewEncapsulation"].None
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__shared_service_knowledge_base_article_knowledge_base_article_service__["a" /* KnowledgeBaseArticleService */]])
    ], DashboardComponent);
    return DashboardComponent;
}());



/***/ }),

/***/ "./src/app/edit-knowledge-base-article/edit.component.html":
/***/ (function(module, exports) {

module.exports = "<form  (ngSubmit)=\"onSubmit(article)\" [formGroup]=\"article\">\r\n<div>\r\n  <div>\r\n    <label>Title: </label>\r\n    <input formControlName=\"title\" type=\"text\" placeholder=\"Title\" />\r\n    <div class=\"error\" \r\n    *ngIf=\"article.get('title').touched \r\n    && article.get('title').hasError('required')\">\r\n      Title is required\r\n    </div>\r\n  </div>\r\n  <div>\r\n    <label>Description: </label>\r\n    <quill-editor #description id=\"description\" style=\"height: 300px\"\r\n    placeholder=\"Description\"\r\n  formControlName=\"description\"></quill-editor>\r\n\r\n    <div class=\"error\" \r\n    *ngIf=\"article.get('description').touched \r\n    && article.get('description').hasError('required')\">\r\n      Description is required\r\n    </div>\r\n  </div>\r\n  <div>\r\n    <button [disabled]=\"article.invalid\">Save</button>\r\n  </div>\r\n</div> \r\n<div *ngIf=\"articleTitle\" [ngClass] = \"'success'\"> {{articleTitle}} created. </div>\r\n<div *ngIf=\"errorMessage\" [ngClass] = \"'error'\"> {{errorMessage}} </div>\r\n</form>"

/***/ }),

/***/ "./src/app/edit-knowledge-base-article/edit.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return EditArticleComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__shared_service_knowledge_base_article_knowledge_base_article_service__ = __webpack_require__("./src/shared/service/knowledge-base-article/knowledge-base-article.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var EditArticleComponent = /** @class */ (function () {
    function EditArticleComponent(kbContentService, fb, activatedRoute) {
        var _this = this;
        this.kbContentService = kbContentService;
        this.fb = fb;
        this.activatedRoute = activatedRoute;
        this.activatedRoute.params.subscribe(function (params) {
            _this.articleId = params['id'];
            if (_this.articleId) {
                _this.kbContentService.reteriveKnowledgeBaseArticleById(_this.articleId).subscribe(function (article) {
                    _this.article.setValue({
                        title: article.title,
                        description: article.description
                    });
                });
            }
        });
    }
    EditArticleComponent.prototype.ngOnInit = function () {
        this.article = this.fb.group({
            title: ['', [__WEBPACK_IMPORTED_MODULE_2__angular_forms__["j" /* Validators */].required, __WEBPACK_IMPORTED_MODULE_2__angular_forms__["j" /* Validators */].minLength(2)]],
            description: ['', __WEBPACK_IMPORTED_MODULE_2__angular_forms__["j" /* Validators */].required],
        });
    };
    EditArticleComponent.prototype.onSubmit = function (_a) {
        var _this = this;
        var value = _a.value, valid = _a.valid;
        this.kbContentService.updateKnowledgeBaseArticle(this.articleId, value)
            .subscribe(function (article) {
            _this.articleTitle = article.title;
        }, function (error) { return _this.errorMessage = error; });
    };
    EditArticleComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-edit-kb-article',
            template: __webpack_require__("./src/app/edit-knowledge-base-article/edit.component.html"),
            styleUrls: []
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__shared_service_knowledge_base_article_knowledge_base_article_service__["a" /* KnowledgeBaseArticleService */],
            __WEBPACK_IMPORTED_MODULE_2__angular_forms__["b" /* FormBuilder */],
            __WEBPACK_IMPORTED_MODULE_3__angular_router__["a" /* ActivatedRoute */]])
    ], EditArticleComponent);
    return EditArticleComponent;
}());



/***/ }),

/***/ "./src/app/edit-user/edit-user.component.html":
/***/ (function(module, exports) {

module.exports = "\r\n\r\n<div class=\"container\">\r\n  <toaster-container></toaster-container>  \r\n  <div class=\"add-heading\"><h2 class=\"heading-margin\">Edit User</h2></div>\r\n\r\n<form  (ngSubmit)=\"onSubmit(user)\" [formGroup]=\"user\">\r\n\r\n<div class=\"example-container\">\r\n  <mat-form-field>\r\n    <input formControlName=\"firstName\" matInput placeholder=\"First Name\">\r\n  </mat-form-field>\r\n  <div class=\"error\" \r\n    *ngIf=\"user.get('firstName').touched \r\n    && user.get('firstName').hasError('required')\">\r\n      First Name is required\r\n  </div>\r\n\r\n  <mat-form-field>\r\n    <input formControlName=\"lastName\" matInput placeholder=\"Last Name\">\r\n  </mat-form-field>\r\n  <div class=\"error\" \r\n    *ngIf=\"user.get('lastName').touched \r\n    && user.get('lastName').hasError('required')\">\r\n      Last Name is required\r\n  </div>\r\n\r\n  <mat-form-field>\r\n    <mat-select formControlName=\"userRole\" placeholder=\"Role\">\r\n        <mat-option value=\"\">Select</mat-option>\r\n        <mat-option value=\"user\">User</mat-option>\r\n        <mat-option value=\"manager\">Manager</mat-option>\r\n        <mat-option value=\"admin\">Admin</mat-option>\r\n    </mat-select>\r\n  </mat-form-field>\r\n  <div class=\"error\" \r\n    *ngIf=\"user.get('userRole').touched \r\n    && user.get('userRole').hasError('required')\">\r\n      Role is required\r\n  </div>\r\n\r\n  <mat-form-field>\r\n    <input formControlName=\"email\" matInput placeholder=\"Email\" readonly>\r\n  </mat-form-field>\r\n  <div class=\"error\" \r\n    *ngIf=\"user.get('email').touched \r\n    && user.get('email').hasError('email')\">\r\n    Enter full email address, including the '@'.\r\n  </div>\r\n\r\n  <mat-form-field>\r\n    <input formControlName=\"password\" type=\"password\" matInput placeholder=\"Password\">\r\n  </mat-form-field>\r\n  <div class=\"error\" \r\n    *ngIf=\"user.get('password').touched \r\n    && user.get('password').hasError('required')\">\r\n      Password is required\r\n  </div>\r\n  <span *ngIf=\"cpwd.hasError('invalid')\"> Invalid Password </span>\r\n\r\n  <mat-form-field>\r\n    <input formControlName=\"confirmPassword\" type=\"password\" matInput placeholder=\"Confirm Password\">\r\n  </mat-form-field>\r\n  <div class=\"error\" \r\n    *ngIf=\"user.get('confirmPassword').touched \r\n    && user.get('confirmPassword').hasError('required')\">\r\n      Confirm Password is required\r\n  </div>\r\n  <div class=\"error\" \r\n    *ngIf=\"user.get('confirmPassword').touched \r\n    && user.get('confirmPassword').hasError('notEquivalent')\">\r\n      Password and Confirm Password should match\r\n  </div>\r\n<div class=\"button-postion\">\r\n  <button type=\"button\" mat-raised-button (click)=\"onDeactivate()\">Deactivate</button>\r\n  <button type=\"button\" mat-raised-button (click)=\"onCancle()\">Cancel</button>\r\n  <button mat-raised-button type=\"submit\">Save</button>\r\n</div>\r\n  <div *ngIf=\"userName\" [ngClass] = \"'success'\"> {{userName}} created. </div>\r\n</div>\r\n</form>\r\n</div>"

/***/ }),

/***/ "./src/app/edit-user/edit-user.component.scss":
/***/ (function(module, exports) {

module.exports = ".example-container {\n  -webkit-box-orient: vertical;\n  -webkit-box-direction: normal;\n      -ms-flex-direction: column;\n          flex-direction: column;\n  background-color: #f2f2f2;\n  padding: 20px;\n  -webkit-box-shadow: 0px 5px 8px rgba(0, 0, 0, 0.3);\n          box-shadow: 0px 5px 8px rgba(0, 0, 0, 0.3);\n  float: left; }\n\n.heading-margin {\n  margin: 0px; }\n\n.mat-form-field {\n  width: 45%;\n  margin-right: 26px; }\n\n.button-postion {\n  float: right; }\n"

/***/ }),

/***/ "./src/app/edit-user/edit-user.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return EditUserComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__shared_service_user_user_service__ = __webpack_require__("./src/shared/service/user/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_angular5_toaster__ = __webpack_require__("./node_modules/angular5-toaster/dist/bundles/angular5-toaster.umd.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_angular5_toaster___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4_angular5_toaster__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






var EditUserComponent = /** @class */ (function () {
    function EditUserComponent(userService, fb, router, activatedRoute, toasterService) {
        var _this = this;
        this.userService = userService;
        this.fb = fb;
        this.router = router;
        this.activatedRoute = activatedRoute;
        this.toasterService = toasterService;
        this.activatedRoute.params.subscribe(function (params) {
            _this.userId = params['id'];
            if (_this.userId) {
                _this.userService.reteriveUserById(_this.userId).subscribe(function (data) {
                    _this.user.setValue({
                        firstName: data.user.firstName,
                        lastName: data.user.lastName,
                        email: data.user.email,
                        password: data.user.password,
                        confirmPassword: data.user.password,
                        userRole: data.user.userRole
                    });
                });
            }
        });
    }
    Object.defineProperty(EditUserComponent.prototype, "cpwd", {
        get: function () {
            return this.user.get('confirmPassword');
        },
        enumerable: true,
        configurable: true
    });
    EditUserComponent.prototype.ngOnInit = function () {
        this.user = this.fb.group({
            firstName: ['', [__WEBPACK_IMPORTED_MODULE_3__angular_forms__["j" /* Validators */].required, __WEBPACK_IMPORTED_MODULE_3__angular_forms__["j" /* Validators */].minLength(2)]],
            lastName: ['', __WEBPACK_IMPORTED_MODULE_3__angular_forms__["j" /* Validators */].required],
            password: ['', __WEBPACK_IMPORTED_MODULE_3__angular_forms__["j" /* Validators */].required],
            userRole: ['', __WEBPACK_IMPORTED_MODULE_3__angular_forms__["j" /* Validators */].required],
            email: ['', __WEBPACK_IMPORTED_MODULE_3__angular_forms__["j" /* Validators */].email],
            confirmPassword: ['', __WEBPACK_IMPORTED_MODULE_3__angular_forms__["j" /* Validators */].required],
        }, { validator: this.validateConfirmPassword('password', 'confirmPassword') });
    };
    EditUserComponent.prototype.validateConfirmPassword = function (passwordKey, passwordConfirmationKey) {
        return function (group) {
            var passwordInput = group.controls[passwordKey], passwordConfirmationInput = group.controls[passwordConfirmationKey];
            if (passwordInput.value !== passwordConfirmationInput.value) {
                return passwordConfirmationInput.setErrors({ notEquivalent: true });
            }
            return passwordConfirmationInput.setErrors(null);
        };
    };
    EditUserComponent.prototype.onSubmit = function (_a) {
        var _this = this;
        var value = _a.value, valid = _a.valid;
        delete value.confirmPassword;
        this.userService.updateUser(this.userId, value)
            .subscribe(function (data) {
            _this.toasterService.pop('success', 'Success', data.success.message);
        }, function (error) { return _this.toasterService.pop('error', 'Error', error.failure.message); });
    };
    EditUserComponent.prototype.onCancle = function () {
        this.router.navigateByUrl('/userlist');
    };
    EditUserComponent.prototype.onDeactivate = function () {
        var _this = this;
        if (confirm('Are you sure to deactivate user ? ' + name)) {
            this.userService.deleteUser(this.userId)
                .subscribe(function (data) {
                _this.toasterService.pop('success', 'Success', data.success.message);
            }, function (error) { return _this.toasterService.pop('error', 'Error', error.failure.message); });
        }
    };
    EditUserComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-edit-user',
            template: __webpack_require__("./src/app/edit-user/edit-user.component.html"),
            styles: [__webpack_require__("./src/app/edit-user/edit-user.component.scss")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__shared_service_user_user_service__["a" /* UserService */],
            __WEBPACK_IMPORTED_MODULE_3__angular_forms__["b" /* FormBuilder */],
            __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* Router */],
            __WEBPACK_IMPORTED_MODULE_2__angular_router__["a" /* ActivatedRoute */], typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_4_angular5_toaster__["ToasterService"] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_angular5_toaster__["ToasterService"]) === "function" && _a || Object])
    ], EditUserComponent);
    return EditUserComponent;
    var _a;
}());



/***/ }),

/***/ "./src/app/footer/footer.component.html":
/***/ (function(module, exports) {

module.exports = "<!--<div class=\"footer\">\r\n    <a href=\"#\"> Term & Conditions </a>\r\n    <a href=\"#\">  Privacy policay </a><span>  @copyright 2018</span>\r\n  </div>-->  "

/***/ }),

/***/ "./src/app/footer/footer.component.scss":
/***/ (function(module, exports) {

module.exports = ".footer {\n  color: #fff;\n  background-color: #111;\n  text-align: center;\n  font-size: 12px;\n  padding: 1px;\n  position: absolute;\n  bottom: 0;\n  right: 0;\n  width: 100%; }\n\n.footer a {\n  color: white; }\n"

/***/ }),

/***/ "./src/app/footer/footer.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return FooterComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var FooterComponent = /** @class */ (function () {
    function FooterComponent() {
    }
    FooterComponent.prototype.ngOnInit = function () {
    };
    FooterComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-footer',
            template: __webpack_require__("./src/app/footer/footer.component.html"),
            styles: [__webpack_require__("./src/app/footer/footer.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], FooterComponent);
    return FooterComponent;
}());



/***/ }),

/***/ "./src/app/left-panel/left-panel.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"sidenav\" *ngIf=\"isAuthenticated()\">\r\n    <div class=\"user-profile\">\r\n      <div class=\"user-image\">\r\n     <a (click)=\"onTapNavigation('')\"> <img src=\"https://upload.wikimedia.org/wikipedia/commons/c/c3/Circle-icons-camera.svg\" class=\"circle\"></a>\r\n      </div>\r\n      <div class=\"user-information\">\r\n     <span class=\"user\"><a>{{ getUserName() }}</a></span>\r\n     <span class=\"user-logout\"><a (click)=\"logout();\"> Logout</a></span>\r\n      </div>\r\n     </div>\r\n     <div class=\"sidenav-menu\">\r\n       <ul class=\"menu-list\">\r\n         <li>\r\n             <a (click)=\"onTapNavigation('')\">\r\n             <img src=\"https://upload.wikimedia.org/wikipedia/commons/c/c3/Circle-icons-camera.svg\" class=\"menu-icon\">\r\n             <span>Search</span>\r\n             </a>\r\n         </li>\r\n         <li>\r\n             <a (click)=\"onTapNavigation('')\">\r\n             <img src=\"https://upload.wikimedia.org/wikipedia/commons/c/c3/Circle-icons-camera.svg\" class=\"menu-icon\">\r\n             <span>Knowledge Base</span>\r\n             </a>\r\n         </li>\r\n         <li *ngIf=\"userType === 'admin' || userType === 'manager'\">\r\n             <a (click)=\"onTapNavigation('')\">\r\n             <img src=\"https://upload.wikimedia.org/wikipedia/commons/c/c3/Circle-icons-camera.svg\" class=\"menu-icon\">\r\n             <span>Dashboard</span>\r\n             </a>\r\n         </li>\r\n         <li>\r\n             <a (click)=\"onTapNavigation('')\">\r\n             <img src=\"https://upload.wikimedia.org/wikipedia/commons/c/c3/Circle-icons-camera.svg\" class=\"menu-icon\">\r\n             <span>Content</span>\r\n             </a>\r\n         </li>\r\n         <li class=\"submenubar\">\r\n             <a>\r\n               <img src=\"https://upload.wikimedia.org/wikipedia/commons/c/c3/Circle-icons-camera.svg\" class=\"menu-icon\">\r\n               <span>Settings</span>\r\n             </a>\r\n             <ul class=\"menu-sub_list\">\r\n               <li *ngIf=\"userType === 'admin' || userType === 'manager'\">            \r\n                 <a (click)=\"onTapNavigation('/userlist')\">User</a>\r\n               </li>\r\n               <li>  \r\n                   <a (click)=\"onTapNavigation('')\">Profile</a>\r\n               </li>\r\n               <li> \r\n                   <a (click)=\"onTapNavigation('')\">Google Tanslater</a>\r\n               </li>     \r\n             </ul>              \r\n         </li>\r\n         <li>\r\n             <a (click)=\"onTapNavigation('')\">\r\n               <img src=\"https://upload.wikimedia.org/wikipedia/commons/c/c3/Circle-icons-camera.svg\" class=\"menu-icon\">\r\n               <span>Help</span>\r\n             </a>\r\n         </li>\r\n       </ul>\r\n     </div>\r\n   </div>\r\n   <!--<div class=\"main\">\r\n       <div class=\"content-box\">\r\n         <div class=\"some-header\">\r\n           Something\r\n         </div> \r\n        </div>\r\n      </div>-->\r\n        "

/***/ }),

/***/ "./src/app/left-panel/left-panel.component.scss":
/***/ (function(module, exports) {

module.exports = "html, body {\n  height: auto; }\n\nbody {\n  font-size: 1.7rem; }\n\n/* For the Side navigation.........................*/\n\n.sidenav {\n  height: 100%;\n  width: 20%;\n  position: fixed;\n  z-index: 1;\n  top: 0;\n  left: 0;\n  background-color: #111;\n  overflow-x: hidden; }\n\n.user-profile {\n  float: left;\n  width: 100%;\n  border-bottom: 1px solid white;\n  padding: 15px 0px;\n  background-color: #332e2e; }\n\n.user-image {\n  display: inline;\n  float: left;\n  width: 21%;\n  margin-right: 6px;\n  margin-left: 6px; }\n\n.user-information {\n  float: left;\n  width: 72%;\n  display: inline;\n  padding-top: 10px; }\n\n.circle {\n  height: 50px;\n  width: 50px; }\n\n.user {\n  display: block; }\n\n.user a {\n  color: white; }\n\n.user-logout a {\n  color: #0071bc; }\n\n/* for the menu items..................*/\n\n.menu-list {\n  margin-top: 0;\n  margin-bottom: 0;\n  list-style-type: none;\n  padding-left: 0;\n  border-bottom: 1px solid;\n  border-top: 1px solid; }\n\nul {\n  margin-top: 1em;\n  margin-bottom: 1em; }\n\n.sidenav-menu {\n  float: left;\n  width: 100%; }\n\n.sidenav-menu ul {\n  list-style-type: none;\n  margin-left: 0px; }\n\n.menu-list > li:first-child {\n  border-top: none; }\n\n.menu-list > li {\n  background-color: transparent;\n  border-top: 1px solid #5b616b;\n  font-size: 1.2rem; }\n\n.menu-list > li {\n  margin-bottom: 0; }\n\nli {\n  line-height: 1.5; }\n\n.menu-icon {\n  width: 20px;\n  height: 20px;\n  padding-right: 10px; }\n\n.menu-list a {\n  border: none;\n  color: #fff;\n  display: block;\n  line-height: 1.3;\n  padding: 0.85rem 1rem 0.85rem 1rem; }\n\n.menu-list a:hover {\n  background-color: #f1f1f1;\n  border-left: 0.4rem solid #0071bc;\n  color: #0071bc; }\n\n/* for sublist...............*/\n\n.submenubar ul {\n  display: none; }\n\n.submenubar:hover ul {\n  display: block; }\n\n.menu-sub_list {\n  margin-top: 0;\n  margin-bottom: 0;\n  list-style-type: none;\n  padding-left: 0;\n  margin: 0;\n  width: 100%; }\n\n/* ending the side navbar .......*/\n\n/* content box..............................*/\n\n.main {\n  margin-left: 250px;\n  /* Same as the width of the sidenav */ }\n\n.content-box {\n  margin: 20px; }\n\n.some-header {\n  background-color: #191919;\n  color: white;\n  padding: 20px; }\n\n.content {\n  margin: 20px; }\n\n/* media quary.............*/\n\n@media screen and (min-width: 1201px) {\n  .menu-list {\n    border-bottom: none;\n    border-top: none; } }\n\n@media screen and (max-height: 450px) {\n  .sidenav {\n    padding-top: 15px; }\n  .sidenav a {\n    font-size: 16px; } }\n\na {\n  cursor: pointer; }\n"

/***/ }),

/***/ "./src/app/left-panel/left-panel.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LeftPanelComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__shared_service_authentication_authentication_service__ = __webpack_require__("./src/shared/service/authentication/authentication.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__shared_service_message_message__ = __webpack_require__("./src/shared/service/message/message.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var LeftPanelComponent = /** @class */ (function () {
    function LeftPanelComponent(auth, router, messageService) {
        var _this = this;
        this.auth = auth;
        this.router = router;
        this.messageService = messageService;
        this.userType = this.auth.getUserType();
        this.subscription = this.messageService.getMessage().subscribe(function (message) {
            _this.userType = _this.auth.getUserType();
        });
    }
    LeftPanelComponent.prototype.ngOnDestroy = function () {
        // unsubscribe to ensure no memory leaks
        this.subscription.unsubscribe();
    };
    LeftPanelComponent.prototype.ngOnInit = function () {
    };
    LeftPanelComponent.prototype.refreshUserType = function () {
        this.userType = this.auth.getUserType();
    };
    LeftPanelComponent.prototype.onTapNavigation = function (route) {
        this.router.navigate([route]);
    };
    LeftPanelComponent.prototype.isAuthenticated = function () {
        return this.auth.isAuthenticated();
    };
    LeftPanelComponent.prototype.getUserName = function () {
        return this.auth.getUserName();
    };
    LeftPanelComponent.prototype.logout = function () {
        this.auth.logout();
        this.onTapNavigation('/login');
    };
    LeftPanelComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-left-panel',
            template: __webpack_require__("./src/app/left-panel/left-panel.component.html"),
            styles: [__webpack_require__("./src/app/left-panel/left-panel.component.scss")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__shared_service_authentication_authentication_service__["a" /* AuthenticationService */], __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* Router */], __WEBPACK_IMPORTED_MODULE_3__shared_service_message_message__["a" /* MessageService */]])
    ], LeftPanelComponent);
    return LeftPanelComponent;
}());



/***/ }),

/***/ "./src/app/login/login.component.html":
/***/ (function(module, exports) {

module.exports = "\r\n<toaster-container></toaster-container>\r\n<div class=\"app-header\">\r\n    <h1 class=\"heading \">Login</h1>\r\n</div>\r\n<!-- Application header ends -->\r\n <div class=\"login-body\">\r\n<mat-grid-list cols=\"3\">\r\n    <mat-grid-tile></mat-grid-tile>\r\n \r\n    <!-- Writing markup for login starts -->\r\n    <mat-grid-tile class='login-element'>\r\n        <div class=\"login-container\">\r\n \r\n            <!-- Template Header starts -->\r\n            <div class=\"login-box-header\">\r\n                <h2>Login </h2>\r\n                \r\n            </div>\r\n            <!-- Template Header ends -->\r\n \r\n            <!-- Template Body starts -->\r\n            <div class=\"login-box-body \">\r\n                <form class=\"example-form\" name=\"form\" >\r\n                    <mat-form-field class=\"example-full-width\">\r\n                        <label>Email</label> \r\n                        <input matInput type=\"email\" name=\"username\" [(ngModel)]=\"model.username\" #username=\"ngModel\" required>\r\n                    </mat-form-field>\r\n\r\n                    <mat-form-field class=\"example-full-width\">\r\n                        <label>Password</label>\r\n                        <input matInput type=\"password\" class=\"form-control\" name=\"password\" [(ngModel)]=\"model.password\" #password=\"ngModel\" required>\r\n                    </mat-form-field>\r\n                    <div class=\"login-forgot\">\r\n                        <a href='#'> Forgot Password</a>\r\n                    </div>\r\n                    <div class=\"login-button\" (click)=\"login()\">\r\n                        <button mat-raised-button color=\"primary\" class=\"login-button\">\r\n                          Login\r\n                         </button>\r\n                    </div>\r\n            </form> \r\n        </div>\r\n            \r\n            \r\n            <!-- Template Body ends -->\r\n            \r\n        </div>\r\n    </mat-grid-tile>\r\n    <!-- Writing markup for login ends -->\r\n    <mat-grid-tile></mat-grid-tile>\r\n \r\n</mat-grid-list>\r\n</div>\r\n\r\n<!-- Application footer starts -->\r\n<div class=\"footer\">\r\n        <a href=\"#\"> Term & Conditions </a>\r\n        <a href=\"#\">  Privacy policay </a><span>  @copyright 2018</span>\r\n      </div> \r\n<!-- Application footer ends -->\r\n\r\n"

/***/ }),

/***/ "./src/app/login/login.component.scss":
/***/ (function(module, exports) {

module.exports = "/*\r\nLogin template using Angular Material\r\n\r\n*/\n.app-header {\n  color: #fff;\n  background-color: #000000;\n  text-align: center;\n  padding: 14px; }\n.login-body {\n  margin-top: 40px; }\n.login-container {\n  width: 100%;\n  height: 450px;\n  padding: 0%;\n  background-color: #fff;\n  -webkit-box-shadow: 2px 4px 58px -20px black;\n  box-shadow: 2px 4px 58px -20px black; }\n.login-box-header {\n  width: auto;\n  height: 17%;\n  background-color: #1f8edc;\n  text-align: center;\n  vertical-align: middle;\n  font-size: 1em;\n  padding: 2%;\n  color: #fff; }\n.login-button {\n  padding: 1%;\n  font-size: 1em;\n  color: #fff; }\n.example-form {\n  padding: 10px; }\n.example-full-width {\n  width: 100%; }\n/* footer.............*/\n.footer {\n  color: #fff;\n  background-color: #111;\n  text-align: center;\n  font-size: 12px;\n  padding: 8px;\n  position: absolute;\n  bottom: 0;\n  right: 0;\n  width: 100%; }\n.footer a {\n  color: white;\n  text-decoration: none; }\n.login-box-body {\n  margin: 0px 35px 0px 20px; }\n.login-box-body input {\n  padding: 10px;\n  margin-top: 10px; }\n.login-forgot {\n  margin-bottom: 10px; }\n.login-forgot a {\n  text-decoration: none; }\n.mat-raised-button.mat-primary {\n  background-color: #1f8edc !important; }\n.heading {\n  margin: 0; }\n"

/***/ }),

/***/ "./src/app/login/login.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LoginComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__shared_service_authentication_authentication_service__ = __webpack_require__("./src/shared/service/authentication/authentication.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_angular5_toaster__ = __webpack_require__("./node_modules/angular5-toaster/dist/bundles/angular5-toaster.umd.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_angular5_toaster___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_angular5_toaster__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__shared_service_message_message__ = __webpack_require__("./src/shared/service/message/message.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var LoginComponent = /** @class */ (function () {
    function LoginComponent(router, toasterService, messageService, authenticationService) {
        this.router = router;
        this.toasterService = toasterService;
        this.messageService = messageService;
        this.authenticationService = authenticationService;
        this.model = {};
        this.loading = false;
    }
    LoginComponent.prototype.ngOnInit = function () {
    };
    LoginComponent.prototype.login = function () {
        var _this = this;
        this.loading = true;
        this.authenticationService.login(this.model.username, this.model.password)
            .subscribe(function (data) {
            console.log(data);
            _this.messageService.sendMessage('set user type');
            _this.router.navigate(['/dashboard']);
        }, function (error) {
            _this.loading = false;
            _this.toasterService.pop('error', 'Error', 'Username or password is incorrect');
        });
    };
    LoginComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-login',
            template: __webpack_require__("./src/app/login/login.component.html"),
            styles: [__webpack_require__("./src/app/login/login.component.scss")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */], typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_3_angular5_toaster__["ToasterService"] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_angular5_toaster__["ToasterService"]) === "function" && _a || Object, __WEBPACK_IMPORTED_MODULE_4__shared_service_message_message__["a" /* MessageService */],
            __WEBPACK_IMPORTED_MODULE_2__shared_service_authentication_authentication_service__["a" /* AuthenticationService */]])
    ], LoginComponent);
    return LoginComponent;
    var _a;
}());



/***/ }),

/***/ "./src/app/main/main.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\r\n  main works!\r\n</p>\r\n"

/***/ }),

/***/ "./src/app/main/main.component.scss":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/main/main.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MainComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var MainComponent = /** @class */ (function () {
    function MainComponent() {
    }
    MainComponent.prototype.ngOnInit = function () {
    };
    MainComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-main',
            template: __webpack_require__("./src/app/main/main.component.html"),
            styles: [__webpack_require__("./src/app/main/main.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], MainComponent);
    return MainComponent;
}());



/***/ }),

/***/ "./src/app/user-list/user-list.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\r\n  <div class=\"add-user-button\"><a (click)=\"onTapNavigation('/user/add')\">Add User</a></div>\r\n  <div class=\"Container-table\">\r\n    <div class=\"add-heading\"><h2 class=\"heading-margin\">User Settings</h2></div>\r\n    <div class=\"example-container\">\r\n    <!-- <table class=\"table\">\r\n    <thead>\r\n      <tr>\r\n        <th>First Name</th>\r\n        <th>Last Name</th>\r\n        <th>Email</th>\r\n        <th>Role</th>\r\n        <th>Action</th>\r\n      </tr>\r\n    </thead>\r\n    <tbody>\r\n      <tr *ngFor=\"let user of listOfUsers\">\r\n        <td> {{ user.firstName }}</td>\r\n        <td> {{ user.lastName }}</td>\r\n        <td> {{ user.email }}</td>\r\n        <td> {{ user.userRole }}</td>\r\n        <td><a [routerLink]=\"['/user/edit', user.id]\">Edit</a></td>\r\n      </tr>\r\n     </tbody> \r\n  </table>-->\r\n\r\n  <div class=\"example-header\">\r\n    <mat-form-field>\r\n      <input matInput (keyup)=\"applyFilter($event.target.value)\" placeholder=\"Filter\">\r\n    </mat-form-field>\r\n  </div>\r\n  \r\n  <div class=\"example-container-1 mat-elevation-z8\">\r\n  \r\n    <mat-table [dataSource]=\"dataSource\" matSort>\r\n  \r\n      <!-- ID Column -->\r\n      <ng-container matColumnDef=\"name\">\r\n        <mat-header-cell *matHeaderCellDef mat-sort-header> Name </mat-header-cell>\r\n        <mat-cell *matCellDef=\"let row\"> {{row.firstName}} {{row.lastName}} </mat-cell>\r\n      </ng-container>\r\n  \r\n      <!-- Progress Column -->\r\n      <ng-container matColumnDef=\"email\">\r\n        <mat-header-cell *matHeaderCellDef mat-sort-header> Email </mat-header-cell>\r\n        <mat-cell *matCellDef=\"let row\"> {{row.email}} </mat-cell>\r\n      </ng-container>\r\n  \r\n      <!-- Name Column -->\r\n      <ng-container matColumnDef=\"role\">\r\n        <mat-header-cell *matHeaderCellDef mat-sort-header> Role </mat-header-cell>\r\n        <mat-cell *matCellDef=\"let row\"> {{row.userRole}} </mat-cell>\r\n      </ng-container>\r\n\r\n       <!-- status Column -->\r\n       <ng-container matColumnDef=\"status\">\r\n          <mat-header-cell *matHeaderCellDef mat-sort-header> Status </mat-header-cell>\r\n          <mat-cell *matCellDef=\"let row\"> {{row.status}} </mat-cell>\r\n        </ng-container>\r\n  \r\n      <!-- Color Column -->\r\n      <ng-container matColumnDef=\"actions\">\r\n        <mat-header-cell *matHeaderCellDef mat-sort-header> Actions </mat-header-cell>\r\n        <mat-cell *matCellDef=\"let row\"><a [routerLink]=\"['/user/edit', row.id]\">Edit</a></mat-cell>\r\n      </ng-container>\r\n  \r\n      <mat-header-row *matHeaderRowDef=\"displayedColumns\"></mat-header-row>\r\n      <mat-row *matRowDef=\"let row; columns: displayedColumns;\">\r\n      </mat-row>\r\n    </mat-table>\r\n  \r\n    <mat-paginator [pageSizeOptions]=\"[5, 10, 25, 100]\"></mat-paginator>\r\n  </div>\r\n\r\n  </div>\r\n </div>\r\n</div>\r\n"

/***/ }),

/***/ "./src/app/user-list/user-list.component.scss":
/***/ (function(module, exports) {

module.exports = ".example-container {\n  -webkit-box-orient: vertical;\n  -webkit-box-direction: normal;\n      -ms-flex-direction: column;\n          flex-direction: column;\n  background-color: #fbfbfb;\n  padding: 20px;\n  -webkit-box-shadow: 0px 5px 8px rgba(0, 0, 0, 0.3);\n          box-shadow: 0px 5px 8px rgba(0, 0, 0, 0.3); }\n\n.heading-margin {\n  margin: 0px; }\n\n.table {\n  width: 100%;\n  text-align: left; }\n\nth, td {\n  padding: 5px; }\n\ntable, th, td {\n  border: 1px solid #cccaca;\n  border-collapse: collapse; }\n\ntable tr:nth-child(even) {\n  background-color: #eee; }\n\ntable tr:nth-child(odd) {\n  background-color: #fff; }\n\n.add-user-button {\n  float: right;\n  margin: 19px 0 10px 0; }\n\n.add-user-button a {\n  text-decoration: none;\n  padding: 10px 21px;\n  background-color: #ffa834;\n  display: block;\n  color: #fff; }\n\n.Container-table {\n  float: left;\n  width: 100%; }\n\n.example-container-1 {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-orient: vertical;\n  -webkit-box-direction: normal;\n      -ms-flex-direction: column;\n          flex-direction: column;\n  min-width: 300px; }\n\n.example-header {\n  min-height: 64px;\n  padding: 8px 24px 0; }\n\n.mat-form-field {\n  font-size: 14px;\n  width: 100%; }\n\n.mat-table {\n  overflow: auto;\n  max-height: 500px; }\n"

/***/ }),

/***/ "./src/app/user-list/user-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return UserListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__shared_service_user_user_service__ = __webpack_require__("./src/shared/service/user/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




/**
 * @title Data table with sorting, pagination, and filtering.
 */
var UserListComponent = /** @class */ (function () {
    function UserListComponent(userService, router) {
        this.userService = userService;
        this.router = router;
        this.displayedColumns = ['name', 'email', 'role', 'actions'];
    }
    UserListComponent.prototype.ngOnInit = function () {
        this.getUserList();
    };
    UserListComponent.prototype.getUserList = function () {
        var _this = this;
        this.userService.listUser()
            .subscribe(function (data) {
            _this.createData(data.users);
        }, function (error) {
            // Need to perform
        });
    };
    UserListComponent.prototype.onTapNavigation = function (route) {
        this.router.navigate([route]);
    };
    UserListComponent.prototype.createData = function (data) {
        var users = [];
        for (var i = 0; i < data.length; i++) {
            users.push(this.createNewUser(data[i]));
        }
        this.dataSource = new __WEBPACK_IMPORTED_MODULE_1__angular_material__["F" /* MatTableDataSource */](users);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    };
    UserListComponent.prototype.applyFilter = function (filterValue) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.dataSource.filter = filterValue;
    };
    UserListComponent.prototype.createNewUser = function (item) {
        return {
            id: item.id,
            firstName: item.firstName,
            lastName: item.lastName,
            email: item.email,
            password: item.password,
            userRole: item.userRole
        };
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["ViewChild"])(__WEBPACK_IMPORTED_MODULE_1__angular_material__["r" /* MatPaginator */]),
        __metadata("design:type", __WEBPACK_IMPORTED_MODULE_1__angular_material__["r" /* MatPaginator */])
    ], UserListComponent.prototype, "paginator", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["ViewChild"])(__WEBPACK_IMPORTED_MODULE_1__angular_material__["C" /* MatSort */]),
        __metadata("design:type", __WEBPACK_IMPORTED_MODULE_1__angular_material__["C" /* MatSort */])
    ], UserListComponent.prototype, "sort", void 0);
    UserListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-user-list',
            template: __webpack_require__("./src/app/user-list/user-list.component.html"),
            styles: [__webpack_require__("./src/app/user-list/user-list.component.scss")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__shared_service_user_user_service__["a" /* UserService */], __WEBPACK_IMPORTED_MODULE_3__angular_router__["b" /* Router */]])
    ], UserListComponent);
    return UserListComponent;
}());



/***/ }),

/***/ "./src/environments/environment.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
var environment = {
    API_ENDPOINT: 'http://localhost:8080/api/',
    production: false
};


/***/ }),

/***/ "./src/main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__("./node_modules/@angular/platform-browser-dynamic/esm5/platform-browser-dynamic.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__("./src/app/app.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("./src/environments/environment.ts");




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["enableProdMode"])();
}
Object(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */])
    .catch(function (err) { return console.log(err); });


/***/ }),

/***/ "./src/mock/fake-backend.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* unused harmony export FakeBackendInterceptor */
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return fakeBackendProvider; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_Observable__ = __webpack_require__("./node_modules/rxjs/_esm5/Observable.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_observable_of__ = __webpack_require__("./node_modules/rxjs/_esm5/add/observable/of.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_rxjs_add_observable_throw__ = __webpack_require__("./node_modules/rxjs/_esm5/add/observable/throw.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_rxjs_add_operator_delay__ = __webpack_require__("./node_modules/rxjs/_esm5/add/operator/delay.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_rxjs_add_operator_mergeMap__ = __webpack_require__("./node_modules/rxjs/_esm5/add/operator/mergeMap.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_rxjs_add_operator_materialize__ = __webpack_require__("./node_modules/rxjs/_esm5/add/operator/materialize.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_rxjs_add_operator_dematerialize__ = __webpack_require__("./node_modules/rxjs/_esm5/add/operator/dematerialize.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};









var FakeBackendInterceptor = /** @class */ (function () {
    function FakeBackendInterceptor() {
    }
    FakeBackendInterceptor.prototype.intercept = function (request, next) {
        // array in local storage for registered users
        var users = [{ username: 'test', password: 'test', firstName: 'Test', lastName: 'User' }];
        // wrap in delayed observable to simulate server api call
        return __WEBPACK_IMPORTED_MODULE_2_rxjs_Observable__["Observable"].of(null).mergeMap(function () {
            // authenticate
            /*if (request.url.endsWith('/mock/api/users/login') && request.method === 'POST') {
                console.log(request);
            
                let filteredUsers = users.filter(user => {
                    return user.username === request.body.username && user.password === request.body.password;
                });
 
                if (filteredUsers.length) {
                    let user = filteredUsers[0];
                    let body = {
                        id: user.id,
                        username: user.username,
                        firstName: user.firstName,
                        lastName: user.lastName,
                        token: 'fake-jwt-token'
                    };
 
                    return Observable.of(new HttpResponse({ status: 200, body: body }));
                } else {
                    return Observable.throw('Username or password is incorrect');
                }
            }*/
            // get users
            /*if (request.url.endsWith('api/users') && request.method === 'GET') {
                // check for fake auth token in header and return users if valid, this security is implemented server side in a real application
                if (request.headers.get('Authorization') === 'Bearer fake-jwt-token') {
                    return Observable.of(new HttpResponse({ status: 200, body: UserData.listOfUser() }));
                } else {
                    // return 401 not authorised if token is null or invalid
                    return Observable.throw('Unauthorised');
                }
            }*/
            // get user by id
            /*if (request.url.match(/\/api\/users\/\d+$/) && request.method === 'GET') {
                // check for fake auth token in header and return user if valid, this security is implemented server side in a real application
                if (request.headers.get('Authorization') === 'Bearer fake-jwt-token') {
                    // find user by id in users array
                    let urlParts = request.url.split('/');
                    let id = parseInt(urlParts[urlParts.length - 1]);
                    let matchedUsers = users.filter(user => { return user.id === id; });
                    let user = matchedUsers.length ? matchedUsers[0] : null;
 
                    return Observable.of(new HttpResponse({ status: 200, body: user }));
                } else {
                    // return 401 not authorised if token is null or invalid
                    return Observable.throw('Unauthorised');
                }
            }*/
            // pass through any requests not handled above
            return next.handle(request);
        })
            .materialize()
            .delay(500)
            .dematerialize();
    };
    FakeBackendInterceptor = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
        __metadata("design:paramtypes", [])
    ], FakeBackendInterceptor);
    return FakeBackendInterceptor;
}());

var fakeBackendProvider = {
    // use fake backend in place of Http service for backend-less development
    provide: __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HTTP_INTERCEPTORS */],
    useClass: FakeBackendInterceptor,
    multi: true
};


/***/ }),

/***/ "./src/mock/user-data.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return UserData; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var UserData = /** @class */ (function () {
    function UserData() {
    }
    UserData.listOfUser = function () {
        return this.users;
    };
    UserData.users = {
        "meta": {
            "current_page": 1,
            "total_pages": 10
        },
        "users": [
            {
                "id": 100,
                "date_joined": 1519363687246,
                "last_login": 1519363687246,
                "first_name": "John",
                "last_name": "Doe",
                "email": "john.doe@email.com",
                "user_role": "manager"
            },
            {
                "id": 101,
                "date_joined": 1519363687246,
                "last_login": 1519363687246,
                "first_name": "Jane",
                "last_name": "Doe",
                "email": "jane.doe@email.com",
                "user_role": "user"
            }
        ]
    };
    UserData = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
        __metadata("design:paramtypes", [])
    ], UserData);
    return UserData;
}());



/***/ }),

/***/ "./src/shared/material.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MaterialModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_cdk_table__ = __webpack_require__("./node_modules/@angular/cdk/esm5/table.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_material__ = __webpack_require__("./node_modules/@angular/material/esm5/material.es5.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var MaterialModule = /** @class */ (function () {
    function MaterialModule() {
    }
    MaterialModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_1__angular_core__["NgModule"])({
            exports: [
                __WEBPACK_IMPORTED_MODULE_0__angular_cdk_table__["m" /* CdkTableModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["a" /* MatAutocompleteModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["b" /* MatButtonModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["c" /* MatButtonToggleModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["d" /* MatCardModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["e" /* MatCheckboxModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["f" /* MatChipsModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["E" /* MatStepperModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["g" /* MatDatepickerModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["h" /* MatDialogModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["i" /* MatDividerModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["j" /* MatExpansionModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["l" /* MatGridListModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["m" /* MatIconModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["n" /* MatInputModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["o" /* MatListModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["p" /* MatMenuModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["q" /* MatNativeDateModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["s" /* MatPaginatorModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["t" /* MatProgressBarModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["u" /* MatProgressSpinnerModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["v" /* MatRadioModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["w" /* MatRippleModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["x" /* MatSelectModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["y" /* MatSidenavModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["A" /* MatSliderModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["z" /* MatSlideToggleModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["B" /* MatSnackBarModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["D" /* MatSortModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["G" /* MatTableModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["H" /* MatTabsModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["I" /* MatToolbarModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["J" /* MatTooltipModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_material__["k" /* MatFormFieldModule */]
            ],
            declarations: []
        })
    ], MaterialModule);
    return MaterialModule;
}());



/***/ }),

/***/ "./src/shared/service/authentication/authentication.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AuthenticationService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__ = __webpack_require__("./node_modules/rxjs/_esm5/add/operator/map.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("./src/environments/environment.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var AuthenticationService = /** @class */ (function () {
    function AuthenticationService(http) {
        this.http = http;
    }
    AuthenticationService.prototype.login = function (username, password) {
        return this.http.post(__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].API_ENDPOINT + 'login', { email: username, password: password })
            .map(function (user) {
            // login successful if there's a jwt token in the response
            if (user && user.accessToken) {
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem('currentUser', JSON.stringify(user));
            }
            return user;
        });
    };
    AuthenticationService.prototype.logout = function () {
        // clear token remove user from local storage to log user out
        this.token = null;
        localStorage.removeItem('currentUser');
    };
    AuthenticationService.prototype.isAuthenticated = function () {
        var currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser && currentUser.accessToken) {
            return true;
        }
        return false;
    };
    AuthenticationService.prototype.getUserName = function () {
        var currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser && currentUser.user && currentUser.accessToken) {
            return currentUser.user.firstName + ' ' + currentUser.user.lastName;
        }
        return null;
    };
    AuthenticationService.prototype.getUserType = function () {
        var currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser && currentUser.user && currentUser.user.userRole) {
            return currentUser.user.userRole;
        }
        return undefined;
    };
    AuthenticationService.prototype.getAccessToken = function () {
        var currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser && currentUser.accessToken) {
            return currentUser.accessToken;
        }
        return null;
    };
    AuthenticationService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["b" /* HttpClient */]])
    ], AuthenticationService);
    return AuthenticationService;
}());



/***/ }),

/***/ "./src/shared/service/helper/auth-guards.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AuthGuard; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var AuthGuard = /** @class */ (function () {
    function AuthGuard(router) {
        this.router = router;
    }
    AuthGuard.prototype.canActivate = function () {
        if (localStorage.getItem('currentUser')) {
            // logged in so return true
            return true;
        }
        // not logged in so redirect to login page
        this.router.navigate(['/login']);
        return false;
    };
    AuthGuard = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */]])
    ], AuthGuard);
    return AuthGuard;
}());



/***/ }),

/***/ "./src/shared/service/helper/jwt-interceptor.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return JwtInterceptor; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var JwtInterceptor = /** @class */ (function () {
    function JwtInterceptor() {
    }
    JwtInterceptor.prototype.intercept = function (request, next) {
        // add authorization header with jwt token if available
        var currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser && currentUser.accessToken) {
            request = request.clone({
                setHeaders: {
                    Authorization: currentUser.accessToken
                }
            });
        }
        return next.handle(request);
    };
    JwtInterceptor = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])()
    ], JwtInterceptor);
    return JwtInterceptor;
}());



/***/ }),

/***/ "./src/shared/service/knowledge-base-article/knowledge-base-article.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return KnowledgeBaseArticleService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_Observable__ = __webpack_require__("./node_modules/rxjs/_esm5/Observable.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_catch__ = __webpack_require__("./node_modules/rxjs/_esm5/add/operator/catch.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




/**
 * This class provides the Knowledgebase content service with methods to manage.
 */
var httpOptions = {
    headers: new __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["d" /* HttpHeaders */]({ 'Content-Type': 'application/json' })
};
var KnowledgeBaseArticleService = /** @class */ (function () {
    function KnowledgeBaseArticleService(http) {
        this.http = http;
        /**
         * Creates a new KnowledgeBaseContentervice with the injected HttpClient.
         * @param {HttpClient} http - The injected HttpClient.
         * @constructor
         */
        this.apiUrl = 'api/articles'; // URL to web api environment.API_ENDPOINT
    }
    /**
     * Returns an Observable for the HTTP GET request for the JSON resource.
     * @return {KnowledgeBaseArticle[]} The Observable for the HTTP request.
     */
    KnowledgeBaseArticleService.prototype.listKnowledgeBaseArticle = function () {
        return this.http.get(this.apiUrl)
            .catch(this.handleErrorObservable);
    };
    /**
     * Returns an Observable for the HTTP POST request for the JSON resource.
     * @return {KnowledgeBaseArticle} The Observable for the HTTP request.
     */
    KnowledgeBaseArticleService.prototype.createKnowledgeBaseArticle = function (articleInfo) {
        return this.http.post(this.apiUrl, articleInfo, httpOptions)
            .catch(this.handleErrorObservable);
    };
    /**
     * Returns an Observable for the HTTP POST request for the JSON resource.
     * @return {KnowledgeBaseArticle} The Observable for the HTTP request.
     */
    KnowledgeBaseArticleService.prototype.updateKnowledgeBaseArticle = function (id, articleInfo) {
        return this.http.put(this.apiUrl + '/' + id, articleInfo, httpOptions)
            .catch(this.handleErrorObservable);
    };
    /**
     * Returns an Observable for the HTTP POST request for the JSON resource.
     * @return {KnowledgeBaseArticle} The Observable for the HTTP request.
     */
    KnowledgeBaseArticleService.prototype.reteriveKnowledgeBaseArticleById = function (articleId) {
        return this.http.get(this.apiUrl + '/' + articleId).catch(this.handleErrorObservable);
    };
    /**
        * Handle HTTP error
        */
    KnowledgeBaseArticleService.prototype.handleErrorObservable = function (error) {
        console.error(error.message || error);
        return __WEBPACK_IMPORTED_MODULE_2_rxjs_Observable__["Observable"].throw(error.message || error);
    };
    KnowledgeBaseArticleService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["b" /* HttpClient */]])
    ], KnowledgeBaseArticleService);
    return KnowledgeBaseArticleService;
}());



/***/ }),

/***/ "./src/shared/service/message/message.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MessageService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_Subject__ = __webpack_require__("./node_modules/rxjs/_esm5/Subject.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};


var MessageService = /** @class */ (function () {
    function MessageService() {
        this.subject = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Subject__["Subject"]();
    }
    MessageService.prototype.sendMessage = function (message) {
        this.subject.next({ text: message });
    };
    MessageService.prototype.clearMessage = function () {
        this.subject.next();
    };
    MessageService.prototype.getMessage = function () {
        return this.subject.asObservable();
    };
    MessageService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])()
    ], MessageService);
    return MessageService;
}());



/***/ }),

/***/ "./src/shared/service/user/user.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return UserService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_Observable__ = __webpack_require__("./node_modules/rxjs/_esm5/Observable.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_catch__ = __webpack_require__("./node_modules/rxjs/_esm5/add/operator/catch.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__environments_environment__ = __webpack_require__("./src/environments/environment.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__authentication_authentication_service__ = __webpack_require__("./src/shared/service/authentication/authentication.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






/**
 * This class provides the User service with methods to manage.
 */
var UserService = /** @class */ (function () {
    function UserService(http, auth) {
        this.http = http;
        this.auth = auth;
        /**
         * Creates a new KnowledgeBaseContentervice with the injected HttpClient.
         * @param {HttpClient} http - The injected HttpClient.
         * @constructor
         */
        this.apiUrl = __WEBPACK_IMPORTED_MODULE_4__environments_environment__["a" /* environment */].API_ENDPOINT + 'users'; // URL to web api environment.API_ENDPOINT
        var token = auth.getAccessToken();
        this.httpOptions = {
            headers: new __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["d" /* HttpHeaders */]({ 'Content-Type': 'application/json', 'Authorization': token })
        };
    }
    /**
     * Returns an Observable for the HTTP GET request for the JSON resource.
     * @return {User[]} The Observable for the HTTP request.
     */
    UserService.prototype.listUser = function () {
        return this.http.get(this.apiUrl)
            .catch(this.handleErrorObservable);
    };
    /**
     * Returns an Observable for the HTTP POST request for the JSON resource.
     * @return {User} The Observable for the HTTP request.
     */
    UserService.prototype.createUser = function (userInfo) {
        return this.http.post(this.apiUrl, userInfo, this.httpOptions)
            .catch(this.handleErrorObservable);
    };
    /**
     * Returns an Observable for the HTTP POST request for the JSON resource.
     * @return {KnowledgeBaseArticle} The Observable for the HTTP request.
     */
    UserService.prototype.updateUser = function (id, userInfo) {
        return this.http.put(this.apiUrl + '/' + id, userInfo, this.httpOptions)
            .catch(this.handleErrorObservable);
    };
    /**
     * Returns an Observable for the HTTP POST request for the JSON resource.
     * @return {UserService} The Observable for the HTTP request.
     */
    UserService.prototype.reteriveUserById = function (userId) {
        return this.http.get(this.apiUrl + '/' + userId, this.httpOptions).catch(this.handleErrorObservable);
    };
    /**
       * Returns an Observable for the HTTP DELETE request for the JSON resource.
       * @return {UserService} The Observable for the HTTP request.
       */
    UserService.prototype.deleteUser = function (id) {
        return this.http.delete(this.apiUrl + '/' + id, this.httpOptions)
            .catch(this.handleErrorObservable);
    };
    /**
        * Handle HTTP error
        */
    UserService.prototype.handleErrorObservable = function (error) {
        console.error(error.message || error);
        return __WEBPACK_IMPORTED_MODULE_2_rxjs_Observable__["Observable"].throw(error.message || error);
    };
    UserService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["b" /* HttpClient */], __WEBPACK_IMPORTED_MODULE_5__authentication_authentication_service__["a" /* AuthenticationService */]])
    ], UserService);
    return UserService;
}());



/***/ }),

/***/ "./src/shared/shared.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SharedModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("./node_modules/@angular/common/esm5/common.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__service_knowledge_base_article_knowledge_base_article_service__ = __webpack_require__("./src/shared/service/knowledge-base-article/knowledge-base-article.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__service_authentication_authentication_service__ = __webpack_require__("./src/shared/service/authentication/authentication.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__mock_fake_backend__ = __webpack_require__("./src/mock/fake-backend.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__service_helper_auth_guards__ = __webpack_require__("./src/shared/service/helper/auth-guards.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__service_helper_jwt_interceptor__ = __webpack_require__("./src/shared/service/helper/jwt-interceptor.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__mock_user_data__ = __webpack_require__("./src/mock/user-data.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__service_user_user_service__ = __webpack_require__("./src/shared/service/user/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__service_message_message__ = __webpack_require__("./src/shared/service/message/message.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};














var SharedModule = /** @class */ (function () {
    function SharedModule() {
    }
    SharedModule_1 = SharedModule;
    SharedModule.forRoot = function () {
        return {
            ngModule: SharedModule_1,
            providers: [
                __WEBPACK_IMPORTED_MODULE_5__service_knowledge_base_article_knowledge_base_article_service__["a" /* KnowledgeBaseArticleService */],
                __WEBPACK_IMPORTED_MODULE_11__service_user_user_service__["a" /* UserService */],
                __WEBPACK_IMPORTED_MODULE_6__service_authentication_authentication_service__["a" /* AuthenticationService */],
                {
                    provide: __WEBPACK_IMPORTED_MODULE_4__angular_common_http__["a" /* HTTP_INTERCEPTORS */],
                    useClass: __WEBPACK_IMPORTED_MODULE_9__service_helper_jwt_interceptor__["a" /* JwtInterceptor */],
                    multi: true
                },
                // provider used to create fake backend
                __WEBPACK_IMPORTED_MODULE_7__mock_fake_backend__["a" /* fakeBackendProvider */],
                __WEBPACK_IMPORTED_MODULE_8__service_helper_auth_guards__["a" /* AuthGuard */],
                __WEBPACK_IMPORTED_MODULE_10__mock_user_data__["a" /* UserData */],
                __WEBPACK_IMPORTED_MODULE_11__service_user_user_service__["a" /* UserService */],
                __WEBPACK_IMPORTED_MODULE_12__service_message_message__["a" /* MessageService */]
            ]
        };
    };
    SharedModule = SharedModule_1 = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["NgModule"])({
            imports: [__WEBPACK_IMPORTED_MODULE_1__angular_common__["CommonModule"], __WEBPACK_IMPORTED_MODULE_2__angular_forms__["d" /* FormsModule */], __WEBPACK_IMPORTED_MODULE_2__angular_forms__["i" /* ReactiveFormsModule */], __WEBPACK_IMPORTED_MODULE_3__angular_router__["c" /* RouterModule */], __WEBPACK_IMPORTED_MODULE_4__angular_common_http__["c" /* HttpClientModule */]],
            declarations: [],
            exports: [__WEBPACK_IMPORTED_MODULE_1__angular_common__["CommonModule"], __WEBPACK_IMPORTED_MODULE_2__angular_forms__["d" /* FormsModule */], __WEBPACK_IMPORTED_MODULE_3__angular_router__["c" /* RouterModule */]]
        })
    ], SharedModule);
    return SharedModule;
    var SharedModule_1;
}());



/***/ }),

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("./src/main.ts");


/***/ })

},[0]);
//# sourceMappingURL=main.bundle.js.map