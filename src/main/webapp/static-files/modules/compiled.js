!function e(t,n,r){function i(o,s){if(!n[o]){if(!t[o]){var l="function"==typeof require&&require;if(!s&&l)return l(o,!0);if(a)return a(o,!0);var u=new Error("Cannot find module '"+o+"'");throw u.code="MODULE_NOT_FOUND",u}var f=n[o]={exports:{}};t[o][0].call(f.exports,function(e){var n=t[o][1][e];return i(n?n:e)},f,f.exports,e,t,n,r)}return n[o].exports}for(var a="function"==typeof require&&require,o=0;o<r.length;o++)i(r[o]);return i}({1:[function(e,t,n){e("ng-file-upload")},{"ng-file-upload":3}],2:[function(e,t,n){!function(){function e(e,t){window.XMLHttpRequest.prototype[e]=t(window.XMLHttpRequest.prototype[e])}function t(e,t,n){try{Object.defineProperty(e,t,{get:n})}catch(r){}}if(window.FileAPI||(window.FileAPI={}),!window.XMLHttpRequest)throw"AJAX is not supported. XMLHttpRequest is not defined.";if(FileAPI.shouldLoad=!window.FormData||FileAPI.forceLoad,FileAPI.shouldLoad){var n=function(e){if(!e.__listeners){e.upload||(e.upload={}),e.__listeners=[];var t=e.upload.addEventListener;e.upload.addEventListener=function(n,r){e.__listeners[n]=r,t&&t.apply(this,arguments)}}};e("open",function(e){return function(t,r,i){n(this),this.__url=r;try{e.apply(this,[t,r,i])}catch(a){a.message.indexOf("Access is denied")>-1&&(this.__origError=a,e.apply(this,[t,"_fix_for_ie_crossdomain__",i]))}}}),e("getResponseHeader",function(e){return function(t){return this.__fileApiXHR&&this.__fileApiXHR.getResponseHeader?this.__fileApiXHR.getResponseHeader(t):null==e?null:e.apply(this,[t])}}),e("getAllResponseHeaders",function(e){return function(){return this.__fileApiXHR&&this.__fileApiXHR.getAllResponseHeaders?this.__fileApiXHR.getAllResponseHeaders():null==e?null:e.apply(this)}}),e("abort",function(e){return function(){return this.__fileApiXHR&&this.__fileApiXHR.abort?this.__fileApiXHR.abort():null==e?null:e.apply(this)}}),e("setRequestHeader",function(e){return function(t,r){if("__setXHR_"===t){n(this);var i=r(this);i instanceof Function&&i(this)}else this.__requestHeaders=this.__requestHeaders||{},this.__requestHeaders[t]=r,e.apply(this,arguments)}}),e("send",function(e){return function(){var n=this;if(arguments[0]&&arguments[0].__isFileAPIShim){var r=arguments[0],i={url:n.__url,jsonp:!1,cache:!0,complete:function(e,r){e&&angular.isString(e)&&-1!==e.indexOf("#2174")&&(e=null),n.__completed=!0,!e&&n.__listeners.load&&n.__listeners.load({type:"load",loaded:n.__loaded,total:n.__total,target:n,lengthComputable:!0}),!e&&n.__listeners.loadend&&n.__listeners.loadend({type:"loadend",loaded:n.__loaded,total:n.__total,target:n,lengthComputable:!0}),"abort"===e&&n.__listeners.abort&&n.__listeners.abort({type:"abort",loaded:n.__loaded,total:n.__total,target:n,lengthComputable:!0}),void 0!==r.status&&t(n,"status",function(){return 0===r.status&&e&&"abort"!==e?500:r.status}),void 0!==r.statusText&&t(n,"statusText",function(){return r.statusText}),t(n,"readyState",function(){return 4}),void 0!==r.response&&t(n,"response",function(){return r.response});var i=r.responseText||(e&&0===r.status&&"abort"!==e?e:void 0);t(n,"responseText",function(){return i}),t(n,"response",function(){return i}),e&&t(n,"err",function(){return e}),n.__fileApiXHR=r,n.onreadystatechange&&n.onreadystatechange(),n.onload&&n.onload()},progress:function(e){if(e.target=n,n.__listeners.progress&&n.__listeners.progress(e),n.__total=e.total,n.__loaded=e.loaded,e.total===e.loaded){var t=this;setTimeout(function(){n.__completed||(n.getAllResponseHeaders=function(){},t.complete(null,{status:204,statusText:"No Content"}))},FileAPI.noContentTimeout||1e4)}},headers:n.__requestHeaders};i.data={},i.files={};for(var a=0;a<r.data.length;a++){var o=r.data[a];null!=o.val&&null!=o.val.name&&null!=o.val.size&&null!=o.val.type?i.files[o.key]=o.val:i.data[o.key]=o.val}setTimeout(function(){if(!FileAPI.hasFlash)throw'Adode Flash Player need to be installed. To check ahead use "FileAPI.hasFlash"';n.__fileApiXHR=FileAPI.upload(i)},1)}else{if(this.__origError)throw this.__origError;e.apply(n,arguments)}}}),window.XMLHttpRequest.__isFileAPIShim=!0,window.FormData=FormData=function(){return{append:function(e,t,n){t.__isFileAPIBlobShim&&(t=t.data[0]),this.data.push({key:e,val:t,name:n})},data:[],__isFileAPIShim:!0}},window.Blob=Blob=function(e){return{data:e,__isFileAPIBlobShim:!0}}}}(),function(){function e(e){return"input"===e[0].tagName.toLowerCase()&&e.attr("type")&&"file"===e.attr("type").toLowerCase()}function t(){try{var e=new ActiveXObject("ShockwaveFlash.ShockwaveFlash");if(e)return!0}catch(t){if(void 0!==navigator.mimeTypes["application/x-shockwave-flash"])return!0}return!1}function n(e){var t=0,n=0;if(window.jQuery)return jQuery(e).offset();if(e.offsetParent)do t+=e.offsetLeft-e.scrollLeft,n+=e.offsetTop-e.scrollTop,e=e.offsetParent;while(e);return{left:t,top:n}}if(FileAPI.shouldLoad){if(FileAPI.hasFlash=t(),FileAPI.forceLoad&&(FileAPI.html5=!1),!FileAPI.upload){var r,i,a,o,s,l=document.createElement("script"),u=document.getElementsByTagName("script");if(window.FileAPI.jsUrl)r=window.FileAPI.jsUrl;else if(window.FileAPI.jsPath)i=window.FileAPI.jsPath;else for(a=0;a<u.length;a++)if(s=u[a].src,o=s.search(/\/ng\-file\-upload[\-a-zA-z0-9\.]*\.js/),o>-1){i=s.substring(0,o+1);break}null==FileAPI.staticPath&&(FileAPI.staticPath=i),l.setAttribute("src",r||i+"FileAPI.js"),document.getElementsByTagName("head")[0].appendChild(l)}FileAPI.ngfFixIE=function(r,i,a){if(!t())throw'Adode Flash Player need to be installed. To check ahead use "FileAPI.hasFlash"';var o=function(){r.attr("disabled")?i&&i.removeClass("js-fileapi-wrapper"):(i.attr("__ngf_flash_")||(i.unbind("change"),i.unbind("click"),i.bind("change",function(e){s.apply(this,[e]),a.apply(this,[e])}),i.attr("__ngf_flash_","true")),i.addClass("js-fileapi-wrapper"),e(r)||i.css("position","absolute").css("top",n(r[0]).top+"px").css("left",n(r[0]).left+"px").css("width",r[0].offsetWidth+"px").css("height",r[0].offsetHeight+"px").css("filter","alpha(opacity=0)").css("display",r.css("display")).css("overflow","hidden").css("z-index","900000").css("visibility","visible"))};r.bind("mouseenter",o);var s=function(e){for(var t=FileAPI.getFiles(e),n=0;n<t.length;n++)void 0===t[n].size&&(t[n].size=0),void 0===t[n].name&&(t[n].name="file"),void 0===t[n].type&&(t[n].type="undefined");e.target||(e.target={}),e.target.files=t,e.target.files!==t&&(e.__files_=t),(e.__files_||e.target.files).item=function(t){return(e.__files_||e.target.files)[t]||null}}},FileAPI.disableFileInput=function(e,t){t?e.removeClass("js-fileapi-wrapper"):e.addClass("js-fileapi-wrapper")}}}(),window.FileReader||(window.FileReader=function(){var e=this,t=!1;this.listeners={},this.addEventListener=function(t,n){e.listeners[t]=e.listeners[t]||[],e.listeners[t].push(n)},this.removeEventListener=function(t,n){e.listeners[t]&&e.listeners[t].splice(e.listeners[t].indexOf(n),1)},this.dispatchEvent=function(t){var n=e.listeners[t.type];if(n)for(var r=0;r<n.length;r++)n[r].call(e,t)},this.onabort=this.onerror=this.onload=this.onloadstart=this.onloadend=this.onprogress=null;var n=function(t,n){var r={type:t,target:e,loaded:n.loaded,total:n.total,error:n.error};return null!=n.result&&(r.target.result=n.result),r},r=function(r){t||(t=!0,e.onloadstart&&e.onloadstart(n("loadstart",r)));var i;"load"===r.type?(e.onloadend&&e.onloadend(n("loadend",r)),i=n("load",r),e.onload&&e.onload(i),e.dispatchEvent(i)):"progress"===r.type?(i=n("progress",r),e.onprogress&&e.onprogress(i),e.dispatchEvent(i)):(i=n("error",r),e.onerror&&e.onerror(i),e.dispatchEvent(i))};this.readAsDataURL=function(e){FileAPI.readAsDataURL(e,r)},this.readAsText=function(e){FileAPI.readAsText(e,r)}}),!window.XMLHttpRequest||window.FileAPI&&FileAPI.shouldLoad||(window.XMLHttpRequest.prototype.setRequestHeader=function(e){return function(t,n){if("__setXHR_"===t){var r=n(this);r instanceof Function&&r(this)}else e.apply(this,arguments)}}(window.XMLHttpRequest.prototype.setRequestHeader));var r=angular.module("ngFileUpload",[]);r.version="10.0.2",r.service("UploadBase",["$http","$q","$timeout",function(e,t,n){function i(r){function i(e){u.notify&&u.notify(e),f.progressFunc&&n(function(){f.progressFunc(e)})}function s(e){return null!=r._start&&o?{loaded:e.loaded+r._start,total:r._file.size,type:e.type,config:r,lengthComputable:!0,target:e.target}:e}function l(){e(r).then(function(e){o&&r._chunkSize&&!r._finished?(i({loaded:r._end,total:r._file.size,config:r,type:"progress"}),a.upload(r)):(r._finished&&delete r._finished,u.resolve(e))},function(e){u.reject(e)},function(e){u.notify(e)})}r.method=r.method||"POST",r.headers=r.headers||{};var u=r._deferred=r._deferred||t.defer(),f=u.promise;return r.disableProgress||(r.headers.__setXHR_=function(){return function(e){e&&e instanceof XMLHttpRequest&&(r.__XHR=e,r.xhrFn&&r.xhrFn(e),e.upload.addEventListener("progress",function(e){e.config=r,i(s(e))},!1),e.upload.addEventListener("load",function(e){e.lengthComputable&&(e.config=r,i(s(e)))},!1))}}),o?r._chunkSize&&r._end&&!r._finished?(r._start=r._end,r._end+=r._chunkSize,l()):r.resumeSizeUrl?e.get(r.resumeSizeUrl).then(function(e){r.resumeSizeResponseReader?r._start=r.resumeSizeResponseReader(e.data):r._start=parseInt((null==e.data.size?e.data:e.data.size).toString()),r._chunkSize&&(r._end=r._start+r._chunkSize),l()},function(e){throw e}):r.resumeSize?r.resumeSize().then(function(e){r._start=e,l()},function(e){throw e}):l():l(),f.success=function(e){return f.then(function(t){e(t.data,t.status,t.headers,r)}),f},f.error=function(e){return f.then(null,function(t){e(t.data,t.status,t.headers,r)}),f},f.progress=function(e){return f.progressFunc=e,f.then(null,null,function(t){e(t)}),f},f.abort=f.pause=function(){return r.__XHR&&n(function(){r.__XHR.abort()}),f},f.xhr=function(e){return r.xhrFn=function(t){return function(){t&&t.apply(f,arguments),e.apply(f,arguments)}}(r.xhrFn),f},f}var a=this;this.isResumeSupported=function(){return window.Blob&&Blob instanceof Function&&(new Blob).slice};var o=this.isResumeSupported();this.rename=function(e,t){return e.ngfName=t,e},this.jsonBlob=function(e){null==e||angular.isString(e)||(e=JSON.stringify(e));var t=new Blob([e],{type:"application/json"});return t._ngfBlob=!0,t},this.json=function(e){return angular.toJson(e)},this.upload=function(e){function t(e){return null!=e&&(e instanceof Blob||e.flashId&&e.name&&e.size)}function n(t,n){if(t._ngfBlob)return t;if(e._file=e._file||t,null!=e._start&&o){e._end&&e._end>=t.size&&(e._finished=!0,e._end=t.size);var r=t.slice(e._start,e._end||t.size);return r.name=t.name,r.ngfName=t.ngfName,e._chunkSize&&(n.append("_chunkSize",e._end-e._start),n.append("_chunkNumber",Math.floor(e._start/e._chunkSize)),n.append("_totalSize",e._file.size)),r}return t}function r(i,a,o){if(void 0!==a)if(angular.isDate(a)&&(a=a.toISOString()),angular.isString(a))i.append(o,a);else if(t(a)){var s=n(a,i),l=o.split(",");l[1]&&(s.ngfName=l[1].replace(/^\s+|\s+$/g,""),o=l[0]),e._fileKey=e._fileKey||o,i.append(o,s,s.ngfName||s.name)}else if(angular.isObject(a)){if(a.$$ngfCircularDetection)throw"ngFileUpload: Circular reference in config.data. Make sure specified data for Upload.upload() has no circular reference: "+o;a.$$ngfCircularDetection=!0;try{for(var u in a)if(a.hasOwnProperty(u)&&"$$ngfCircularDetection"!==u){var f=null==e.objectKey?"[i]":e.objectKey;a.length&&parseInt(u)>-1&&(f=null==e.arrayKey?f:e.arrayKey),r(i,a[u],o+f.replace(/[ik]/g,u))}}finally{delete a.$$ngfCircularDetection}}else i.append(o,a)}function s(){e._chunkSize=a.translateScalars(e.resumeChunkSize),e._chunkSize=e._chunkSize?parseInt(e._chunkSize.toString()):null,e.headers=e.headers||{},e.headers["Content-Type"]=void 0,e.transformRequest=e.transformRequest?angular.isArray(e.transformRequest)?e.transformRequest:[e.transformRequest]:[],e.transformRequest.push(function(t){var n,i=new FormData;t=t||e.fields||{},e.file&&(t.file=e.file);for(n in t)if(t.hasOwnProperty(n)){var a=t[n];e.formDataAppender?e.formDataAppender(i,n,a):r(i,a,n)}return i})}return e._isDigested||(e._isDigested=!0,s()),i(e)},this.http=function(t){return t.transformRequest=t.transformRequest||function(t){return window.ArrayBuffer&&t instanceof window.ArrayBuffer||t instanceof Blob?t:e.defaults.transformRequest[0].apply(this,arguments)},t._chunkSize=a.translateScalars(t.resumeChunkSize),t._chunkSize=t._chunkSize?parseInt(t._chunkSize.toString()):null,i(t)},this.translateScalars=function(e){if(angular.isString(e)){if(e.search(/kb/i)===e.length-2)return parseFloat(1e3*e.substring(0,e.length-2));if(e.search(/mb/i)===e.length-2)return parseFloat(1e6*e.substring(0,e.length-2));if(e.search(/gb/i)===e.length-2)return parseFloat(1e9*e.substring(0,e.length-2));if(e.search(/b/i)===e.length-1)return parseFloat(e.substring(0,e.length-1));if(e.search(/s/i)===e.length-1)return parseFloat(e.substring(0,e.length-1));if(e.search(/m/i)===e.length-1)return parseFloat(60*e.substring(0,e.length-1));if(e.search(/h/i)===e.length-1)return parseFloat(3600*e.substring(0,e.length-1))}return e},this.setDefaults=function(e){this.defaults=e||{}},this.defaults={},this.version=r.version}]),r.service("Upload",["$parse","$timeout","$compile","$q","UploadExif",function(e,t,n,r,i){function a(e,t,n){var i=[l.emptyPromise()];return angular.forEach(e,function(r,a){0===r.type.indexOf("image/jpeg")&&l.attrGetter("ngfFixOrientation",t,n,{$file:r})&&i.push(l.happyPromise(l.applyExifRotation(r),r).then(function(t){e.splice(a,1,t)}))}),r.all(i)}function o(e,t,n){var i=l.attrGetter("ngfResize",t,n);if(!i||!l.isResizeSupported()||!e.length)return l.emptyPromise();var a=[l.emptyPromise()];return angular.forEach(e,function(t,n){if(0===t.type.indexOf("image")){var r=l.resize(t,i.width,i.height,i.quality,i.type);a.push(r),r.then(function(t){e.splice(n,1,t)},function(e){t.$error="resize",t.$errorParam=(e?(e.message?e.message:e)+": ":"")+(t&&t.name)})}}),r.all(a)}function s(e,t,n,r){var i=[],a=l.attrGetter("ngfKeep",n,r);if(a){var o=!1;if("distinct"===a||l.attrGetter("ngfKeepDistinct",n,r)===!0){var s=t.length;if(e)for(var u=0;u<e.length;u++){for(var f=0;s>f;f++)if(e[u].name===t[f].name){i.push(e[u]);break}f===s&&(t.push(e[u]),o=!0)}e=t}else e=t.concat(e||[])}return{files:e,dupFiles:i,keep:a}}var l=i;return l.getAttrWithDefaults=function(e,t){if(null!=e[t])return e[t];var n=l.defaults[t];return null==n?n:angular.isString(n)?n:JSON.stringify(n)},l.attrGetter=function(t,n,r,i){var a=this.getAttrWithDefaults(n,t);if(!r)return a;try{return i?e(a)(r,i):e(a)(r)}catch(o){if(t.search(/min|max|pattern/i))return a;throw o}},l.shouldUpdateOn=function(e,t,n){var r=l.attrGetter("ngModelOptions",t,n);return r&&r.updateOn?r.updateOn.split(" ").indexOf(e)>-1:!0},l.emptyPromise=function(){var e=r.defer(),n=arguments;return t(function(){e.resolve.apply(e,n)}),e.promise},l.happyPromise=function(e,n){var i=r.defer();return e.then(function(e){i.resolve(e)},function(e){t(function(){throw e}),i.resolve(n)}),i.promise},l.updateModel=function(n,r,i,u,f,c,d){function p(a,o,s,f,d){var p=a&&a.length?a[0]:null;n&&(l.applyModelValidation(n,a),n.$ngfModelChange=!0,n.$setViewValue(d?p:a)),u&&e(u)(i,{$files:a,$file:p,$newFiles:s,$duplicateFiles:f,$invalidFiles:o,$event:c});var g=l.attrGetter("ngfModelInvalid",r);g&&t(function(){e(g).assign(i,o)}),t(function(){})}var g=f,h=(n&&n.$modelValue||r.$$ngfPrevFiles||[]).slice(0),m=s(f,h,r,i);f=m.files;var v=m.dupFiles,_=!l.attrGetter("ngfMultiple",r,i)&&!l.attrGetter("multiple",r)&&!m.keep;if(r.$$ngfPrevFiles=f,!m.keep||g&&g.length){l.validate(g,n,r,i).then(function(){if(d)p(f,[],g,v,_);else{var e=l.attrGetter("ngModelOptions",r,i);if(!e||!e.allowInvalid){var n=[],s=[];angular.forEach(f,function(e){e.$error?s.push(e):n.push(e)}),f=n}var u=l.emptyPromise(f);l.attrGetter("ngfFixOrientation",r,i)&&l.isExifSupported()&&(u=a(f,r,i)),u.then(function(){o(f,r,i).then(function(){t(function(){p(f,s,g,v,_)},e&&e.debounce?e.debounce.change||e.debounce:0)},function(e){throw"Could not resize files "+e})})}});for(var y=h.length;y--;){var b=h[y];window.URL&&b.blobUrl&&(URL.revokeObjectURL(b.blobUrl),delete b.blobUrl)}}},l}]),r.directive("ngfSelect",["$parse","$timeout","$compile","Upload",function(e,t,n,r){function i(e){var t=e.match(/Android[^\d]*(\d+)\.(\d+)/);if(t&&t.length>2){var n=r.defaults.androidFixMinorVersion||4;return parseInt(t[1])<4||parseInt(t[1])===n&&parseInt(t[2])<n}return-1===e.indexOf("Chrome")&&/.*Windows.*Safari.*/.test(e)}function a(e,t,n,r,a,s,l,u){function f(){return"input"===t[0].tagName.toLowerCase()&&n.type&&"file"===n.type.toLowerCase()}function c(){return y("ngfChange")||y("ngfSelect")}function d(t){if(u.shouldUpdateOn("change",n,e)){for(var i=t.__files_||t.target&&t.target.files,a=[],o=0;o<i.length;o++)a.push(i[o]);u.updateModel(r,n,e,c(),a.length?a:null,t)}}function p(e){if(t!==e)for(var n=0;n<t[0].attributes.length;n++){var r=t[0].attributes[n];"type"!==r.name&&"class"!==r.name&&"id"!==r.name&&"style"!==r.name&&((null==r.value||""===r.value)&&("required"===r.name&&(r.value="required"),"multiple"===r.name&&(r.value="multiple")),e.attr(r.name,r.value))}}function g(){if(f())return t;var e=angular.element('<input type="file">');return p(e),e.css("visibility","hidden").css("position","absolute").css("overflow","hidden").css("width","0px").css("height","0px").css("border","none").css("margin","0px").css("padding","0px").attr("tabindex","-1"),o.push({el:t,ref:e}),document.body.appendChild(e[0]),e}function h(n){if(t.attr("disabled")||y("ngfSelectDisabled",e))return!1;var r=m(n);if(null!=r)return r;v(n);try{f()||document.body.contains($[0])||(o.push({el:t,ref:$}),document.body.appendChild($[0]),$.bind("change",d))}catch(a){}return i(navigator.userAgent)?setTimeout(function(){$[0].click()},0):$[0].click(),!1}function m(e){var t=e.changedTouches||e.originalEvent&&e.originalEvent.changedTouches;if("touchstart"===e.type)return w=t?t[0].clientY:0,!0;if(e.stopPropagation(),e.preventDefault(),"touchend"===e.type){var n=t?t[0].clientY:0;if(Math.abs(n-w)>20)return!1}}function v(t){u.shouldUpdateOn("click",n,e)&&$.val()&&($.val(null),u.updateModel(r,n,e,c(),null,t,!0))}function _(e){if($&&!$.attr("__ngf_ie10_Fix_")){if(!$[0].parentNode)return void($=null);e.preventDefault(),e.stopPropagation(),$.unbind("click");var t=$.clone();return $.replaceWith(t),$=t,$.attr("__ngf_ie10_Fix_","true"),$.bind("change",d),$.bind("click",_),$[0].click(),!1}$.removeAttr("__ngf_ie10_Fix_")}var y=function(e,t){return u.attrGetter(e,n,t)};u.registerModelChangeValidator(r,n,e);var b=[];b.push(e.$watch(y("ngfMultiple"),function(){$.attr("multiple",y("ngfMultiple",e))})),b.push(e.$watch(y("ngfCapture"),function(){$.attr("capture",y("ngfCapture",e))})),n.$observe("accept",function(){$.attr("accept",y("accept"))}),b.push(function(){n.$$observers&&delete n.$$observers.accept});var w=0,$=t;f()||($=g()),$.bind("change",d),f()?t.bind("click",v):t.bind("click touchstart touchend",h),-1!==navigator.appVersion.indexOf("MSIE 10")&&$.bind("click",_),r&&r.$formatters.push(function(e){return(null==e||0===e.length)&&$.val()&&$.val(null),e}),e.$on("$destroy",function(){f()||$.remove(),angular.forEach(b,function(e){e()})}),s(function(){for(var e=0;e<o.length;e++){var t=o[e];document.body.contains(t.el[0])||(o.splice(e,1),t.ref.remove())}}),window.FileAPI&&window.FileAPI.ngfFixIE&&window.FileAPI.ngfFixIE(t,$,d)}var o=[];return{restrict:"AEC",require:"?ngModel",link:function(i,o,s,l){a(i,o,s,l,e,t,n,r)}}}]),function(){function e(e){return"img"===e.tagName.toLowerCase()?"image":"audio"===e.tagName.toLowerCase()?"audio":"video"===e.tagName.toLowerCase()?"video":/./}function t(t,n,r,i,a,o,s,l){function u(e){var o=t.attrGetter("ngfNoObjectUrl",a,r);t.dataUrl(e,o)["finally"](function(){n(function(){var t=(o?e.$ngfDataUrl:e.$ngfBlobUrl)||e.$ngfDataUrl;l?i.css("background-image","url('"+(t||"")+"')"):i.attr("src",t),t?i.removeClass("ng-hide"):i.addClass("ng-hide")})})}n(function(){var n=r.$watch(a[o],function(n){var r=s;if("ngfThumbnail"===o&&(r||(r={width:i[0].clientWidth,height:i[0].clientHeight}),0===r.width&&window.getComputedStyle)){var a=getComputedStyle(i[0]);r={width:parseInt(a.width.slice(0,-2)),height:parseInt(a.height.slice(0,-2))}}return angular.isString(n)?(i.removeClass("ng-hide"),l?i.css("background-image","url('"+n+"')"):i.attr("src",n)):void(!n||!n.type||0!==n.type.search(e(i[0]))||l&&0!==n.type.indexOf("image")?i.addClass("ng-hide"):r&&t.isResizeSupported()?t.resize(n,r.width,r.height,r.quality).then(function(e){u(e)},function(e){throw e}):u(n))});r.$on("$destroy",function(){n()})})}r.service("UploadDataUrl",["UploadBase","$timeout","$q",function(e,t,n){var r=e;return r.base64DataUrl=function(e){if(angular.isArray(e)){var t=n.defer(),i=0;return angular.forEach(e,function(n){r.dataUrl(n,!0)["finally"](function(){if(i++,i===e.length){var n=[];angular.forEach(e,function(e){n.push(e.$ngfDataUrl)}),t.resolve(n,e)}})}),t.promise}return r.dataUrl(e,!0)},r.dataUrl=function(e,i){if(!e)return r.emptyPromise(e,e);if(i&&null!=e.$ngfDataUrl||!i&&null!=e.$ngfBlobUrl)return r.emptyPromise(i?e.$ngfDataUrl:e.$ngfBlobUrl,e);var a=i?e.$$ngfDataUrlPromise:e.$$ngfBlobUrlPromise;if(a)return a;var o=n.defer();return t(function(){if(window.FileReader&&e&&(!window.FileAPI||-1===navigator.userAgent.indexOf("MSIE 8")||e.size<2e4)&&(!window.FileAPI||-1===navigator.userAgent.indexOf("MSIE 9")||e.size<4e6)){var n=window.URL||window.webkitURL;if(n&&n.createObjectURL&&!i){var r;try{r=n.createObjectURL(e)}catch(a){return void t(function(){e.$ngfBlobUrl="",o.reject()})}t(function(){e.$ngfBlobUrl=r,r&&o.resolve(r,e)})}else{var s=new FileReader;s.onload=function(n){t(function(){e.$ngfDataUrl=n.target.result,o.resolve(n.target.result,e)})},s.onerror=function(){t(function(){e.$ngfDataUrl="",o.reject()})},s.readAsDataURL(e)}}else t(function(){e[i?"dataUrl":"blobUrl"]="",o.reject()})}),a=i?e.$$ngfDataUrlPromise=o.promise:e.$$ngfBlobUrlPromise=o.promise,a["finally"](function(){delete e[i?"$$ngfDataUrlPromise":"$$ngfBlobUrlPromise"]}),a},r}]),r.directive("ngfSrc",["Upload","$timeout",function(e,n){return{restrict:"AE",link:function(r,i,a){t(e,n,r,i,a,"ngfSrc",e.attrGetter("ngfResize",a,r),!1)}}}]),r.directive("ngfBackground",["Upload","$timeout",function(e,n){return{restrict:"AE",link:function(r,i,a){t(e,n,r,i,a,"ngfBackground",e.attrGetter("ngfResize",a,r),!0)}}}]),r.directive("ngfThumbnail",["Upload","$timeout",function(e,n){return{restrict:"AE",link:function(r,i,a){var o=e.attrGetter("ngfSize",a,r);t(e,n,r,i,a,"ngfThumbnail",o,e.attrGetter("ngfAsBackground",a,r))}}}]),r.config(["$compileProvider",function(e){e.imgSrcSanitizationWhitelist&&e.imgSrcSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|local|file|data|blob):/),e.aHrefSanitizationWhitelist&&e.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|local|file|data|blob):/)}]),r.filter("ngfDataUrl",["UploadDataUrl","$sce",function(e,t){return function(n,r,i){if(angular.isString(n))return t.trustAsResourceUrl(n);var a=n&&((r?n.$ngfDataUrl:n.$ngfBlobUrl)||n.$ngfDataUrl);return n&&!a?(!n.$ngfDataUrlFilterInProgress&&angular.isObject(n)&&(n.$ngfDataUrlFilterInProgress=!0,e.dataUrl(n,r)),""):(n&&delete n.$ngfDataUrlFilterInProgress,(n&&a?i?t.trustAsResourceUrl(a):a:n)||"")}}])}(),r.service("UploadValidate",["UploadDataUrl","$q","$timeout",function(e,t,n){function r(e){var t="",n=[];if(e.length>2&&"/"===e[0]&&"/"===e[e.length-1])t=e.substring(1,e.length-1);else{var i=e.split(",");if(i.length>1)for(var a=0;a<i.length;a++){var o=r(i[a]);o.regexp?(t+="("+o.regexp+")",a<i.length-1&&(t+="|")):n=n.concat(o.excludes)}else 0===e.indexOf("!")?n.push("^((?!"+r(e.substring(1)).regexp+").)*$"):(0===e.indexOf(".")&&(e="*"+e),t="^"+e.replace(new RegExp("[.\\\\+*?\\[\\^\\]$(){}=!<>|:\\-]","g"),"\\$&")+"$",t=t.replace(/\\\*/g,".*").replace(/\\\?/g,"."))}return{regexp:t,excludes:n}}function i(e,t){null==t||e.$dirty||(e.$setDirty?e.$setDirty():e.$dirty=!0)}var a=e;return a.validatePattern=function(e,t){if(!t)return!0;var n=r(t),i=!0;if(n.regexp&&n.regexp.length){var a=new RegExp(n.regexp,"i");i=null!=e.type&&a.test(e.type)||null!=e.name&&a.test(e.name)}for(var o=n.excludes.length;o--;){var s=new RegExp(n.excludes[o],"i");i=i&&(null==e.type||s.test(e.type))&&(null==e.name||s.test(e.name))}return i},a.registerModelChangeValidator=function(e,t,n){e&&e.$formatters.push(function(r){e.$ngfModelChange?e.$ngfModelChange=!1:a.validate(r,e,t,n,function(){a.applyModelValidation(e,r)})})},a.applyModelValidation=function(e,t){i(e,t),angular.forEach(e.$ngfValidations,function(t){e.$setValidity(t.name,t.valid)})},a.validate=function(e,n,r,i){function o(t,r,i){if(e){for(var a="ngf"+t[0].toUpperCase()+t.substr(1),o=e.length,s=null;o--;){var u=e[o];if(u){var f=l(a,{$file:u});null==f&&(f=r(l("ngfValidate")||{}),s=null==s?!0:s),null!=f&&(i(u,f)||(u.$error=t,u.$errorParam=f,e.splice(o,1),s=!1))}}null!==s&&n.$ngfValidations.push({name:t,valid:s})}}function s(r,i,o,s,u){var f=[a.emptyPromise()];if(e){var c="ngf"+r[0].toUpperCase()+r.substr(1);return e=void 0===e.length?[e]:e,angular.forEach(e,function(e){var n=t.defer();if(f.push(n.promise),o&&(null==e.type||0!==e.type.search(o)))return void n.resolve();var a=l(c,{$file:e})||i(l("ngfValidate",{$file:e})||{});a?s(e,a).then(function(t){u(t,a)?n.resolve():(e.$error=r,e.$errorParam=a,n.reject())},function(){l("ngfValidateForce",{$file:e})?(e.$error=r,e.$errorParam=a,n.reject()):n.resolve()}):n.resolve()}),t.all(f).then(function(){n.$ngfValidations.push({name:r,valid:!0})},function(){n.$ngfValidations.push({name:r,valid:!1})})}}n=n||{},n.$ngfValidations=n.$ngfValidations||[],angular.forEach(n.$ngfValidations,function(e){e.valid=!0});var l=function(e,t){return a.attrGetter(e,r,i,t)};if(null==e||0===e.length)return a.emptyPromise(n);e=void 0===e.length?[e]:e.slice(0),o("pattern",function(e){return e.pattern},a.validatePattern),o("minSize",function(e){return e.size&&e.size.min},function(e,t){return e.size>=a.translateScalars(t)}),o("maxSize",function(e){return e.size&&e.size.max},function(e,t){return e.size<=a.translateScalars(t)});var u=0;if(o("maxTotalSize",function(e){return e.maxTotalSize&&e.maxTotalSize},function(t,n){return u+=t.size,u>a.translateScalars(n)?(e.splice(0,e.length),!1):!0}),o("validateFn",function(){return null},function(e,t){return t===!0||null===t||""===t}),!e.length)return a.emptyPromise(n,n.$ngfValidations);var f=t.defer(),c=[];return c.push(a.happyPromise(s("maxHeight",function(e){return e.height&&e.height.max},/image/,this.imageDimensions,function(e,t){return e.height<=t}))),c.push(a.happyPromise(s("minHeight",function(e){return e.height&&e.height.min},/image/,this.imageDimensions,function(e,t){return e.height>=t}))),c.push(a.happyPromise(s("maxWidth",function(e){return e.width&&e.width.max},/image/,this.imageDimensions,function(e,t){return e.width<=t}))),c.push(a.happyPromise(s("minWidth",function(e){return e.width&&e.width.min},/image/,this.imageDimensions,function(e,t){return e.width>=t}))),c.push(a.happyPromise(s("ratio",function(e){return e.ratio},/image/,this.imageDimensions,function(e,t){for(var n=t.toString().split(","),r=!1,i=0;i<n.length;i++){var a=n[i],o=a.search(/x/i);a=o>-1?parseFloat(a.substring(0,o))/parseFloat(a.substring(o+1)):parseFloat(a),Math.abs(e.width/e.height-a)<1e-4&&(r=!0)}return r}))),c.push(a.happyPromise(s("maxDuration",function(e){return e.duration&&e.duration.max},/audio|video/,this.mediaDuration,function(e,t){return e<=a.translateScalars(t)}))),c.push(a.happyPromise(s("minDuration",function(e){return e.duration&&e.duration.min},/audio|video/,this.mediaDuration,function(e,t){return e>=a.translateScalars(t)}))),c.push(a.happyPromise(s("validateAsyncFn",function(){return null},null,function(e,t){return t},function(e){return e===!0||null===e||""===e}))),t.all(c).then(function(){f.resolve(n,n.$ngfValidations)})},a.imageDimensions=function(e){if(e.$ngfWidth&&e.$ngfHeight){var r=t.defer();return n(function(){r.resolve({width:e.$ngfWidth,height:e.$ngfHeight})}),r.promise}if(e.$ngfDimensionPromise)return e.$ngfDimensionPromise;var i=t.defer();return n(function(){return 0!==e.type.indexOf("image")?void i.reject("not image"):void a.dataUrl(e).then(function(t){function r(){var t=s[0].clientWidth,n=s[0].clientHeight;s.remove(),e.$ngfWidth=t,e.$ngfHeight=n,i.resolve({width:t,height:n})}function a(){s.remove(),i.reject("load error")}function o(){n(function(){s[0].parentNode&&(s[0].clientWidth?r():l>10?a():o())},1e3)}var s=angular.element("<img>").attr("src",t).css("visibility","hidden").css("position","fixed");s.on("load",r),s.on("error",a);var l=0;o(),angular.element(document.getElementsByTagName("body")[0]).append(s)},function(){i.reject("load error")})}),e.$ngfDimensionPromise=i.promise,e.$ngfDimensionPromise["finally"](function(){delete e.$ngfDimensionPromise}),e.$ngfDimensionPromise},a.mediaDuration=function(e){if(e.$ngfDuration){var r=t.defer();return n(function(){r.resolve(e.$ngfDuration)}),r.promise}if(e.$ngfDurationPromise)return e.$ngfDurationPromise;var i=t.defer();return n(function(){return 0!==e.type.indexOf("audio")&&0!==e.type.indexOf("video")?void i.reject("not media"):void a.dataUrl(e).then(function(t){function r(){var t=s[0].duration;e.$ngfDuration=t,s.remove(),i.resolve(t)}function a(){s.remove(),i.reject("load error")}function o(){n(function(){s[0].parentNode&&(s[0].duration?r():l>10?a():o())},1e3)}var s=angular.element(0===e.type.indexOf("audio")?"<audio>":"<video>").attr("src",t).css("visibility","none").css("position","fixed");s.on("loadedmetadata",r),s.on("error",a);var l=0;o(),angular.element(document.body).append(s)},function(){i.reject("load error")})}),e.$ngfDurationPromise=i.promise,e.$ngfDurationPromise["finally"](function(){delete e.$ngfDurationPromise}),e.$ngfDurationPromise},a}]),r.service("UploadResize",["UploadValidate","$q",function(e,t){var n=e,r=function(e,t,n,r){var i=Math.min(n/e,r/t);return{width:e*i,height:t*i}},i=function(e,n,i,a,o){var s=t.defer(),l=document.createElement("canvas"),u=document.createElement("img");return u.onload=function(){try{n||(n=u.width),i||(i=u.height);var e=r(u.width,u.height,n,i);l.width=e.width,l.height=e.height;var t=l.getContext("2d");t.drawImage(u,0,0,e.width,e.height),s.resolve(l.toDataURL(o||"image/WebP",a||1))}catch(f){s.reject(f)}},u.onerror=function(){s.reject()},u.src=e,s.promise};return n.dataUrltoBlob=function(e,t){for(var n=e.split(","),r=n[0].match(/:(.*?);/)[1],i=atob(n[1]),a=i.length,o=new Uint8Array(a);a--;)o[a]=i.charCodeAt(a);var s=new Blob([o],{type:r});return s.name=t,s},n.isResizeSupported=function(){var e=document.createElement("canvas");return window.atob&&e.getContext&&e.getContext("2d")},n.isResizeSupported()&&Object.defineProperty(Blob.prototype,"name",{get:function(){return this.$ngfName},set:function(e){this.$ngfName=e},configurable:!0}),n.resize=function(e,r,a,o,s){if(0!==e.type.indexOf("image"))return n.emptyPromise(e);var l=t.defer();return n.dataUrl(e,!0).then(function(t){i(t,r,a,o,s||e.type).then(function(t){l.resolve(n.dataUrltoBlob(t,e.name))},function(){l.reject()})},function(){l.reject()}),l.promise},n}]),function(){function e(e,n,r,i,a,o,s,l,u){function f(){return n.attr("disabled")||g("ngfDropDisabled",e)}function c(e,t,n,r){var i=g("ngfDragOverClass",e,{$event:n}),a="dragover";if(angular.isString(i))a=i;else if(i&&(i.delay&&(_=i.delay),i.accept||i.reject)){var o=n.dataTransfer.items;if(null!=o&&o.length)for(var s=i.pattern||g("ngfPattern",e,{$event:n}),u=o.length;u--;){if(!l.validatePattern(o[u],s)){a=i.reject;break}a=i.accept}else a=i.accept}r(a)}function d(e,t,n,r){function i(e,t,n){if(null!=t)if(t.isDirectory){var r=(n||"")+t.name;e.push({name:t.name,type:"directory",path:r});var a=t.createReader(),o=[];l++;var s=function(){a.readEntries(function(r){try{if(r.length)o=o.concat(Array.prototype.slice.call(r||[],0)),s();else{for(var a=0;a<o.length;a++)i(e,o[a],(n?n:"")+t.name+"/");l--}}catch(u){l--,console.error(u)}},function(){l--})};s()}else l++,t.file(function(t){try{l--,t.path=(n?n:"")+t.name,e.push(t)}catch(r){l--,console.error(r)}},function(){l--})}var a=[],l=0,u=e.dataTransfer.items;if(u&&u.length>0&&"file"!==s.protocol())for(var f=0;f<u.length;f++){if(u[f].webkitGetAsEntry&&u[f].webkitGetAsEntry()&&u[f].webkitGetAsEntry().isDirectory){
var c=u[f].webkitGetAsEntry();if(c.isDirectory&&!n)continue;null!=c&&i(a,c)}else{var d=u[f].getAsFile();null!=d&&a.push(d)}if(!r&&a.length>0)break}else{var p=e.dataTransfer.files;if(null!=p)for(var g=0;g<p.length&&(a.push(p.item(g)),r||!(a.length>0));g++);}var h=0;!function m(e){o(function(){if(l)10*h++<2e4&&m(10);else{if(!r&&a.length>1){for(f=0;"directory"===a[f].type;)f++;a=[a[f]]}t(a)}},e||0)}()}var p=t(),g=function(e,t,n){return l.attrGetter(e,r,t,n)};if(g("dropAvailable")&&o(function(){e[g("dropAvailable")]?e[g("dropAvailable")].value=p:e[g("dropAvailable")]=p}),!p)return void(g("ngfHideOnDropNotAvailable",e)===!0&&n.css("display","none"));null==g("ngfSelect")&&l.registerModelChangeValidator(i,r,e);var h,m=null,v=a(g("ngfStopPropagation")),_=1;n[0].addEventListener("dragover",function(t){if(!f()){if(t.preventDefault(),v(e)&&t.stopPropagation(),navigator.userAgent.indexOf("Chrome")>-1){var i=t.dataTransfer.effectAllowed;t.dataTransfer.dropEffect="move"===i||"linkMove"===i?"move":"copy"}o.cancel(m),h||(h="C",c(e,r,t,function(r){h=r,n.addClass(h),g("ngfDrag",e,{$isDragging:!0,$class:h,$event:t})}))}},!1),n[0].addEventListener("dragenter",function(t){f()||(t.preventDefault(),v(e)&&t.stopPropagation())},!1),n[0].addEventListener("dragleave",function(t){f()||(t.preventDefault(),v(e)&&t.stopPropagation(),m=o(function(){h&&n.removeClass(h),h=null,g("ngfDrag",e,{$isDragging:!1,$event:t})},_||100))},!1),n[0].addEventListener("drop",function(t){if(!f()&&l.shouldUpdateOn("drop",r,e)){t.preventDefault(),v(e)&&t.stopPropagation(),h&&n.removeClass(h),h=null;var a;try{a=t.dataTransfer&&t.dataTransfer.getData&&t.dataTransfer.getData("text/html")}catch(o){}if(a){var s;a.replace(/<img .*src *=\"([^\"]*)\"/,function(e,t){s=t}),s&&u({url:s,method:"get",responseType:"arraybuffer"}).then(function(n){var a=new Uint8Array(n.data),o=n.headers("content-type")||"image/WebP",s=new Blob([a],{type:o});l.updateModel(i,r,e,g("ngfChange")||g("ngfDrop"),[s],t)})}else d(t,function(n){l.updateModel(i,r,e,g("ngfChange")||g("ngfDrop"),n,t)},g("ngfAllowDir",e)!==!1,g("multiple")||g("ngfMultiple",e))}},!1),n[0].addEventListener("paste",function(t){if(!f()&&l.shouldUpdateOn("paste",r,e)){var n=[],a=t.clipboardData||t.originalEvent.clipboardData;if(a&&a.items){for(var o=0;o<a.items.length;o++)-1!==a.items[o].type.indexOf("image")&&n.push(a.items[o].getAsFile());l.updateModel(i,r,e,g("ngfChange")||g("ngfDrop"),n,t)}}},!1)}function t(){var e=document.createElement("div");return"draggable"in e&&"ondrop"in e&&!/Edge\/12./i.test(navigator.userAgent)}r.directive("ngfDrop",["$parse","$timeout","$location","Upload","$http",function(t,n,r,i,a){return{restrict:"AEC",require:"?ngModel",link:function(o,s,l,u){e(o,s,l,u,t,n,r,i,a)}}}]),r.directive("ngfNoFileDrop",function(){return function(e,n){t()&&n.css("display","none")}}),r.directive("ngfDropAvailable",["$parse","$timeout","Upload",function(e,n,r){return function(i,a,o){if(t()){var s=e(r.attrGetter("ngfDropAvailable",o));n(function(){s(i),s.assign&&s.assign(i,!0)})}}}])}(),r.service("UploadExif",["UploadResize","$q",function(e,t){function n(e){var t=new DataView(e);if(255!==t.getUint8(0)||216!==t.getUint8(1))return"Not a valid JPEG";for(var n,r=2,i=e.byteLength;i>r;){if(255!==t.getUint8(r))return"Not a valid marker at offset "+r+", found: "+t.getUint8(r);if(n=t.getUint8(r+1),225===n)return o(t,r+4,t.getUint16(r+2)-2);r+=2+t.getUint16(r+2)}}function r(e,t,n,r){var a,o,s=e.getUint16(n,!r);for(o=0;s>o;o++){a=n+12*o+2;var l=e.getUint16(a,!r);if(274===l)return i(e,a,t,r)}return null}function i(e,t,n,r){var i,a,o,s=e.getUint32(t+4,!r),l=e.getUint32(t+8,!r)+n;if(1===s)return e.getUint16(t+8,!r);for(i=s>2?l:t+8,a=[],o=0;s>o;o++)a[o]=e.getUint16(i+2*o,!r);return a}function a(e,t,n){for(var r="",i=t;t+n>i;i++)r+=String.fromCharCode(e.getUint8(i));return r}function o(e,t){if("Exif"!==a(e,t,4))return"Not valid EXIF data! "+a(e,t,4);var n,i=t+6;if(18761===e.getUint16(i))n=!1;else{if(19789!==e.getUint16(i))return"Not valid TIFF data! (no 0x4949 or 0x4D4D)";n=!0}if(42!==e.getUint16(i+2,!n))return"Not valid TIFF data! (no 0x002A)";var o=e.getUint32(i+4,!n);return 8>o?e.getUint32(i+4,!n):r(e,i,i+o,n)}function s(e,t,n,r){switch(t){case 2:return e.transform(-1,0,0,1,n,0);case 3:return e.transform(-1,0,0,-1,n,r);case 4:return e.transform(1,0,0,-1,0,r);case 5:return e.transform(0,1,1,0,0,0);case 6:return e.transform(0,1,-1,0,r,0);case 7:return e.transform(0,-1,-1,0,r,n);case 8:return e.transform(0,-1,1,0,0,n)}}var l=e;return l.isExifSupported=function(){return window.FileReader&&(new FileReader).readAsArrayBuffer&&l.isResizeSupported()},l.orientation=function(e){if(null!=e.$ngfOrientation)return l.emptyPromise(e.$ngfOrientation);var r=t.defer(),i=new FileReader;return i.onload=function(t){var i;try{i=n(t.target.result)}catch(t){return void r.reject(t)}angular.isString(i)?r.reject(i):(e.$ngfOrientation=i,r.resolve(i))},i.onerror=function(e){r.reject(e)},i.readAsArrayBuffer(e),r.promise},l.applyExifRotation=function(e){if(0!==e.type.indexOf("image/jpeg"))return l.emptyPromise(e);var n=t.defer();return l.orientation(e).then(function(t){(!t||2>t||t>8)&&n.resolve(e),l.dataUrl(e,!0).then(function(r){var i=document.createElement("canvas"),a=document.createElement("img");a.onload=function(){try{i.width=t>4?a.height:a.width,i.height=t>4?a.width:a.height;var r=i.getContext("2d");s(r,t,a.width,a.height),r.drawImage(a,0,0);var o=i.toDataURL(e.type||"image/WebP",1),u=l.dataUrltoBlob(o,e.name);n.resolve(u)}catch(f){n.reject(f)}},a.onerror=function(){n.reject()},a.src=r},function(e){n.reject(e)})},function(e){n.reject(e)}),n.promise},l}])},{}],3:[function(e,t,n){e("./dist/ng-file-upload-all"),t.exports="ngFileUpload"},{"./dist/ng-file-upload-all":2}]},{},[1]);