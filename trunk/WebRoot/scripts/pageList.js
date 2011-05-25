/**
 * 分页处理脚本：为异步方式提供方便的分页处理
 * @author 程志伟
 * @version 2.1.0
 * 分页类
 * @param name      声明的变量名称
 * @param className CSS样式
 * @param linage    行数(缺省为10)
 */
/**
 * 增加表格标题栏冻结
 * 增加按某字段查询排序功能
 * 增加当前显示的表格排序
 * 2011-02-28
 */
var pars;
function PageList(name,linage,className) {
	if(typeof(name) == "string") this._name = name;
	this._table = document.createElement("TABLE");
	this._table.border = "1";
	this._table.tbody = document.createElement("TBODY");
	this._head = document.createElement("TBODY");
	this._table.appendChild(this._head);
	this._table.appendChild(this._table.tbody);
	this.setTableClass(className);
	this.setLinage(linage);
	this._dataSize = 0;
	this.currentPage = 0; //当前页码
	this.pageSize = 0;    //总页码
	this._segment = -1;   //当前页码段
}
/**
 * 设置表格样式
 * @param className CSS样式
 */
PageList.prototype.setTableClass = function(className) {
 this._table.className = className;
}
/**
 * 设置表格ID
 * @param idName CSS样式
 */
PageList.prototype.setTableId = function(idName){
 this._table.id = idName;
}
/**
 * 设置行样式
 * @param className CSS样式
 * @param even 为真表示设置偶数行样式,反之为假
 */
PageList.prototype.setTRClass = function(className,even) {
 if(even) {
	 this.trEvenClassName = className;
 } else {
	 this.trOddClassName = className;
 }
}
/**
 * 设置行数
 * @param linage 行数
 */
PageList.prototype.setLinage = function(linage,flag) {
 this._linage = linage;//typeof(linage) == "number" ? linage : 10;
 this._linage2 = this._linage;
 if(!this._captureDataFunc) {
	 this.pageSize = Math.ceil(this._dataSize/this._linage);
 }
 if(flag) {
 	this._linageFlag = true;
 	this._linage = null;
 }
}
/**
 * 创建表头信息
 * @param title   表头信息
 * @param colSpan 表头占用列数
 * @see setTitle
 */
PageList.prototype.createTitle = function(colSpan,title) {
 if(!this._title) {
	 var thead = document.createElement("THEAD");
	 var tr = document.createElement("TR");
	 var th = document.createElement("TH");
	 if(colSpan) {th.colSpan = colSpan;}
	 tr.appendChild(th);
	 thead.appendChild(tr);
	 this._table.appendChild(thead);
	 this._title = th;
	 this.setTitle(title);
 }
}
//创建底部翻页工具条(可覆盖)
PageList.prototype.createBottom = function() {
 if(!this._name || this._bottom) return;
 //覆盖时该对象一定要被创建
 this._bottom = document.createElement("TABLE");
 this._bottom.className = "pageTool";
 var tbody = document.createElement("TBODY");
 var tr = document.createElement("TR");
 var td = document.createElement("TD");
 td.align = "right";
 td.innerHTML = '共<span class="green" id="' + this._name + '_pageSize">0</span>页'
			  + '&nbsp;<a href="javascript:' + this._name + '.pageFirst();">'
			  + '首页'
			  + '</a>&nbsp;<a href="javascript:' + this._name + '.pageUp();">上一页'
			  + '</a>&nbsp;<a href="javascript:' + this._name + '.pageDown();">下一页'
			  + '</a>&nbsp;<a href="javascript:' + this._name + '.pageEnd();">末页'
			  + '</a>&nbsp;&nbsp;当前第<span class="red" id="' + this._name + '_currentPage">0</span>页&nbsp;转到第'
			  + '<label><input id="'+ this._name +'GoPage" type="text" size="1"/>页</label>'
			  + '&nbsp;&nbsp;<a href="javascript:void(0)" onclick="' +this._name+'.go(document.getElementById(\''+this._name+'GoPage\').value);">跳转</a>';
 tr.appendChild(td);
 tbody.appendChild(tr);
 this._bottom.appendChild(tbody);
 //这里将其自动加载到表格显示的面板中
 //if(this._panel) this._panel.appendChild(this._bottom);
 /*
 var listBar = document.getElementById("listBar");
 if(listBar != null){
 	listBar.appendChild(this._bottom);
 }else{
 	if(this._panel) this._panel.appendChild(this._bottom);
 }
 */
}
//获得表格体
PageList.prototype.getBody = function() {
 	return this._table.tbody;
}
/**
 * 设置表头信息
 * @param title 表头信息
 * @see createTitle
 */
PageList.prototype.setTitle = function(title) {
	if(this._title) {
	 if(title) {	 
		 this._title.innerHTML = title;
		 this._title.parentNode.parentNode.style.display = "";
	 } else {
		 this._title.innerHTML = "";
	     this._title.parentNode.parentNode.style.display = "none";
	 }
	}
}
/**
 * 获取表头信息
 * @see setTitle
 */
PageList.prototype.getTitle = function() {
 if(this._title) {
	 return this._title.innerHTML;
 } else {
	 return "";
 }
}
/**
 * 设置列宽
 * 参数可为字符串数组或多个字符串
 * 如果参数为数组，将直接设置列宽为该数组所示；
 * 如果参数为一系列字符串，将按顺序设置列宽，遇到第一个不是字符串的参数时将抛弃其后参数
 * @paramType Array or String(s)
 */
PageList.prototype.setTDScale = function() {
 if(arguments[0] instanceof Array) {
	 this._tdScale = arguments[0];
 } else {
	 var tdScale = [];
	 for(var i=0; i<arguments.length;) {
		 if(typeof(arguments[i]) == "string") {
			 tdScale[i] = arguments[i++];
		 }
	 }
	 this._tdScale = tdScale.length ? tdScale : null;
 }
}
/**
 * 设置列表头样式
 * 参数可为字符串数组或多个字符串
 * 如果参数为数组，将直接设置列宽为该数组所示；
 * 如果参数为一系列字符串，将按顺序设置列宽，遇到第一个不是字符串的参数时将抛弃其后参数
 * @paramType Array or String(s)
 */
PageList.prototype.setHeadCSS = function() {
 if(arguments[0] instanceof Array) {
	 this._theadCSS = arguments[0];
 } else {
	 var hcss = [];
	 for(var i=0; i<arguments.length;) {
		 if(typeof(arguments[i]) == "string") {
			 hcss[i] = arguments[i++];
		 }
	 }
	 this._theadCSS = hcss.length ? hcss : null;
 }
}
/**
 * 设置列表头
 * 参数可为字符串数组或多个字符串
 * 如果参数为数组，将直接设置列表头为该数组所示；
 * 如果参数为一系列字符串，将按顺序设置列宽，遇到第一个不是字符串的参数时将抛弃其后参数
 * @paramType Array or String(s)
 */
PageList.prototype.setHead = function() {
	 var head;
	 if(arguments[0] instanceof Array) {
		 head = arguments[0];
	 } else {
		 var tHead = [];
		 for(var i=0; i<arguments.length;) {
			 if(typeof(arguments[i]) == "string") {
				 tHead[i] = arguments[i++];
			 }
		 }
		 head = tHead;
	 }
	 if(head && head.length) {
		 while(this._head.firstChild) {
			 this._head.removeChild(this._head.firstChild);
		 }
		 var tr = document.createElement("TR");
		 
		 tr.style.position = "relative";
		 tr.style.top = "0px";
		 tr.style.zIndex = 9;
		 
		 var th;
		 for(var i=0 ; i<head.length ; i++) {
		 	 th = document.createElement("TH");
		 	 if(this._tdScale) {
				if(this._tdScale[i]) {th.width = this._tdScale[i];}
		     }
			 if(this._theadCSS && this._theadCSS[i]) {
				 th.className = this._theadCSS[i];
			 }
			 th.innerHTML = "<A href='javascript:" + this._name + ".orderBy(" + i +");' >" + head[i] + "</A>";
			 if(this._tdScale) {
				 if(this._tdScale[i]) {th.width = this._tdScale[i];}
			 }
			 tr.appendChild(th);
		 }
		 this._head.appendChild(tr);
	 }
}
/**
 * 设置不参于排序行的数组
 * 默认是第一行
 * @paramType Array or String(s)
 */
var orderByNotNumber = [0];
PageList.prototype.setOrderByNotNumber = function() {
	if(arguments[0] instanceof Array) {
		orderByNotNumber = arguments[0];
	} else {
		var tOrderByNotNumber = [];
		for(var i=0; i<arguments.length;) {
			if(typeof(arguments[i]) == "string") {
				tOrderByNotNumber[i] = arguments[i++];
			}
		}
		orderByNotNumber = tOrderByNotNumber;
	}
}
/**
 * 初始化当前表格
 * 即删除表格中因为排序而留在表头的符号
 */
PageList.prototype.setHeadlabelInit = function(){
	for(var i = 0; i < this._table.rows.length; i = i + 1){
		for(var j = 0; j < this._table.rows[i].cells.length; j = j + 1){
			var cellTemp = this._table.rows[i].cells[j].innerHTML;
			if(cellTemp.indexOf("↑") != -1){
				this._table.rows[i].cells[j].innerHTML = cellTemp.substring(0, cellTemp.indexOf("↑"));
			}else if(cellTemp.indexOf("↓") != -1){
				this._table.rows[i].cells[j].innerHTML = cellTemp.substring(0, cellTemp.indexOf("↓"));
			}
		}
	}
}
/**
 * 按什么排序查询功能实现
 * @params number 按第几列排序
 */
PageList.prototype.orderBy = function(number){
	var object = this._table.rows[0].cells[number];
	for(var i = 0; i < object.parentNode.cells.length; i = i + 1){
		if(object.cellIndex + "" == orderByNotNumber[i] + ""){
			alert("This Column is Not Order!");
			return ;
		}
	}
	var value = object.innerHTML;
	this.setHeadlabelInit();
	if(value.indexOf("↑") != -1){
		object.innerHTML = value.substring(0, value.indexOf("↑"));
		this.order = "";
	}else if(value.indexOf("↓") != -1){
		object.innerHTML = value.substring(0, value.indexOf("↓")) + "↑";
		this.order = "ASC";
	}else{
		object.innerHTML = value + "↓";
		this.order = "DESC";
	}
	this.orderByName = this._properties[object.cellIndex];
	this._segment = 1;
	//this.go(1);
	
	this.orderTable(this._table, number, this.order);
}
/**
 * 对当前显示的表格排序功能实现
 * @params table 要排序的表格对象
 * @params number 按第几列排序
 * @params order 升序、降序、不排序
 */
var oldTable = new Array();
var index = new Array();
var value = new Array();
PageList.prototype.orderTable = function(table, number, order){
	this.setParentScroll();
	var temp = "";
	var parentTable = table.tBodies[1];
	var headRowNumber = table.tBodies[0].rows.length;
	if(order == "DESC"){
		
		if(!(oldTable.length == "0")){
			temp = parentTable.rows.length;
			for(var i = 0; i < temp; i = i + 1){
				parentTable.deleteRow(temp - i - 1);
			}
			for(var i = 0; i < oldTable.rows.length; i = i + 1){
				var tableTr = parentTable.insertRow(parentTable.rows.length);
				tableTr.setAttribute("index", oldTable.rows[i].getAttribute("index"));
				for(var j = 0; j < oldTable.rows[i].cells.length; j = j + 1){
					var td = tableTr.insertCell(tableTr.cells.length);
					td.setAttribute("width", oldTable.rows[i].cells[j].getAttribute("width"));
					td.innerHTML = oldTable.rows[i].cells[j].innerHTML;
				}
			}
		}
		
		//oldTable = new Array();
		//index = new Array();
		//value = new Array();
		oldTable = document.createElement("TABLE");
		for(var i = 0; i < parentTable.rows.length; i = i + 1){
			var oldTableTr = oldTable.insertRow(oldTable.rows.length);
			oldTableTr.setAttribute("index", parentTable.rows[i].getAttribute("index"));
			for(var j = 0; j < parentTable.rows[i].cells.length; j = j + 1){
				var td = oldTableTr.insertCell(oldTableTr.cells.length);
				td.setAttribute("width", parentTable.rows[i].cells[j].getAttribute("width"));
				td.innerHTML = parentTable.rows[i].cells[j].innerHTML;
			}
		}
		
		for(var i = 0; i < parentTable.rows.length; i = i + 1){
			index[i] = parentTable.rows[i].rowIndex - headRowNumber;
			value[i] = parentTable.rows[i].cells[number].innerHTML;
		}
		var flag = false;
		for(var i = 0; i < value.length; i = i + 1){
			if(isNaN(value[i] * 1)){
				flag = true;
			}
		}
		if(!flag){
			for(var i = 0; i < value.length; i = i + 1){
				value[i] = value[i] * 1;
			}
		}
		
		for(var i = 0; i < value.length; i = i + 1){
			for(var j = 0; j < value.length - i - 1; j = j + 1){
				if(value[j] > value[j + 1]){
					temp = value[j];
					value[j] = value[j + 1];
					value[j + 1] = temp;
					temp = index[j];
					index[j] = index[j + 1];
					index[j + 1] = temp;
				}
			}
		}
	}
	temp = parentTable.rows.length;
	for(var i = 0; i < temp; i = i + 1){
		parentTable.deleteRow(temp - i - 1);
	}
	var oldTr = new Array();
	if(order == "DESC"){
		for(var i = 0; i < index.length; i = i + 1){
			oldTr[i] = oldTable.rows[index[index.length - i - 1]];
		}
	}else if(order == "ASC"){
		for(var i = 0; i < index.length; i = i + 1){
			oldTr[i] = oldTable.rows[index[i]];
		}
	}else{
		for(var i = 0; i < index.length; i = i + 1){
			oldTr[i] = oldTable.rows[i];
		}
	}
	for(var i = 0; i < index.length; i = i + 1){
		var tr = parentTable.insertRow(parentTable.rows.length);
		tr.setAttribute("index", oldTr[i].getAttribute("index"));
		for(var j = 0; j < oldTr[i].cells.length; j = j + 1){
			var td = tr.insertCell(tr.cells.length);
			td.setAttribute("width", oldTr[i].cells[j].getAttribute("width"));
			td.innerHTML = oldTr[i].cells[j].innerHTML;
		}
	}
}
/**
 * 恢复滚动条到初始位置
 */
PageList.prototype.setParentScroll = function() {
	if(this._table.parentNode.scrollTop != "undefined"){
		this._table.parentNode.scrollTop = "0px";
	}
}
/**
 * 恢复Table到初始位置
 */
PageList.prototype.setOldTable = function() {
	oldTable = new Array();
	index = new Array();
	value = new Array();
}

/**
 * 设置列属性(数据的属性或下标)
 * 参数可为字符串数组或多个字符串
 * 如果参数为数组，将直接设置列属性为该数组所示；
 * 如果参数为一系列字符串，将按顺序设置列宽，遇到第一个不是字符串的参数时将抛弃其后参数
 * @paramType Array or String(s)
 */
PageList.prototype.setProperties = function() {
	 if(arguments[0] instanceof Array) {
		 this._properties = arguments[0];
	 } else {
		 var props = [];
		 for(var i=0; i<arguments.length;) {
			 props[i] = arguments[i++];
		 }
		 this._properties = props;
	 }
}
/**
 * 设置是否自动适应数据量
 * (比如最后一页不够每页显示的最大数，则只显示相应的数据，否则自动补白)
 * @param flag true:补白 false:自适应(默认)
 */
PageList.prototype.setAutoBlank = function(flag) {
	this._blank = flag ? true : false;
}
/**
 * 为行添加事件
 * @param Events 事件集合({"onmouseover":function(){}[,其它事件]})
 */
PageList.prototype.attachTrEvent = function(Events) {
	this._trEvent = Events;
};
/**
 * 设置TD处理方法
 * @param func 回调方法(该方法会接收到整条记录信息)
 */
PageList.prototype.setTDHandle = function(func) {
	if(arguments[0] instanceof Array) {
	    this._tdHandle = arguments[0];
	} else {
		var callback = [];
		for(var i=0; i<arguments.length;i++) {
			if(typeof(arguments[i]) == "function") {
				callback[i] = arguments[i];
			} 
		}
		this._tdHandle = callback.length ? callback : null;
		
	}
}
/**
 * 设置td的特殊样式
 * @param classNames 为td加的样式
 */
PageList.prototype.setTDClass = function() {
	if(arguments[0] instanceof Array) {
	    this._tdCSS = arguments[0];
	} else {
		var tdCSS = [];
		for(var i=0; i<arguments.length;) {
			if(typeof(arguments[i]) == "string") {
				tdCSS[i] = arguments[i++];
			}
		}
		this._tdCSS = tdCSS.length ? tdCSS : null;
	}
}
/**
 * 输入数据
 * @param data 数据
 */
PageList.prototype.setData = function(data) {
	this._data = data;
	if(this._panel) {
	    this._linage = !isNaN(parseInt(this._linage)) ? parseInt(this._linage): this._data ? this._data.length : this._dataSize;
		this.go(this.currentPage);
	} else {
		this._segment = 0;
	}
};
/**
 * 分页设置
 * @param dataSize 预期数据量
 * @param pageCount 一次读取页数
 * @param callback 回调方法
 */
PageList.prototype.setPageReload = function(dataSize,pageCount,callback) {
	if(typeof(dataSize) != "number") {
		alert("the dataSize is not a number!");
		return;
	}
	if(typeof(pageCount) != "number") {
		alert("the dataSize is not a number!");
		return;
	}
	if(typeof(callback) != "function") {
		alert("the callback is not a function!");
		return;
	}
	this._captureDataFunc = callback;
	this._pageCount = pageCount;
	this.setDataSize(dataSize);
}
/**
 * 设置数据大小(多步分页)
 * @param dataSize
 */
PageList.prototype.setDataSize = function(dataSize) {
	 if(typeof(dataSize) != "number") {
		 alert("the dataSize is not a number!");
		 return;
	 }
	 this._dataSize = dataSize;
	 this.pageSize = Math.ceil(this._dataSize/this._linage2);
};
/**
 * 设置行号显示列
 * @param cellNumber 列序(从0开始)
 */
PageList.prototype.setTRNumber = function(cellNumber,type) {
	 if(typeof(cellNumber) != "number") {
		 alert("the cellNumber is not a number!");
		 return;
	 }
	 this._cellNum = cellNumber;
	 this._cellNumType = type == "all" ? "all" : "current";
};
/**
 * 定位显示
 * @param index 页码
 */
PageList.prototype.go = function(index) {
	this.setParentScroll();
	this.setOldTable();
	this.setHeadlabelInit();
	if(!this._data && !this._captureDataFunc && !(this._captureDataFunc instanceof Function)) {
		return;
	}
	index = parseInt(index) || 1;
	this.currentPage = index = index > 0 ? (index > this.pageSize ? 1 : index) : 1;
	if(index === 0) {index = 1;}
	var start,stop;
	if(this._captureDataFunc && (this._captureDataFunc instanceof Function)) {
		this._pageCount = this._pageCount > 0 ? this._pageCount : 1;
		var segment = Math.floor((index-1)/this._pageCount);
		if(this._segment != segment) {
			//alert(this.orderByName + "," + this.order);
			this._captureDataFunc(this._segment = segment, this.orderByName, this.order);
			return; //如果是分页显示，但又没有开始读数据，调用回调方法以读取数据
		} else if(!this._data) {
			return;/*如果回调没有数据回来直接退出*/
		}
		start = (index -1) % this._pageCount * this._linage;
		var t = Math.floor((index-1)/this._pageCount);
		t = index - t * this._pageCount;
		stop  = (t * this._linage) > this._data.length ? this._data.length : (t * this._linage);
	} else if(this._dataSize != this._data.length){
		this._dataSize = this._data.length;
		this.pageSize = Math.ceil(this._dataSize/this._linage);
		this.currentPage = index = index > this.pageSize ? this.pageSize : index;
	}
	if(this._bottom) {
		var dataSize = document.getElementById(this._name+"_dataSize");
		var pageSize = document.getElementById(this._name+"_pageSize");
		if(!this._dataSize) this._dataSize = this._data.length;
		if(dataSize) dataSize.innerHTML = this._dataSize;
		if(!this.pageSize) {this.pageSize = Math.ceil(this._dataSize/this._linage);}
		if(pageSize) pageSize.innerHTML = this.pageSize;
	}
	//this._linage = !isNaN(parseInt(this._linage)) ? parseInt(this._linage): this._data ? this._data.length : 0;
	start = isNaN(start) ? (index - 1) * this._linage : start;
	stop = isNaN(stop) ? (index * this._linage) > this._dataSize ? this._dataSize : (index * this._linage) : stop;
	while(this._table.tbody.firstChild) {
		this._table.tbody.removeChild(this._table.tbody.firstChild);
	}
	if(index > 0) {
	for(var i=start ; i<stop ; i++) {
		var tr = document.createElement("TR");
		tr.setAttribute("index",i);
		if(!this.trEvenClassName && this.trOddClassName) {
			tr.className = this.trOddClassName;
		} else if(this.trEvenClassName) {
			if(i % 2) {
				tr.className = this.trEvenClassName;
			} else if(this.trOddClassName) {
				tr.className = this.trOddClassName;
			}
		}
		if(this._trEvent) {
			for(var k in this._trEvent) {
				tr[k] = this._trEvent[k];
			}
		}
		//行号增量(页)
		var inc = this._segment > 0 && this._cellNumType.toLowerCase() == "all" ? this._segment*this._pageCount*this._linage + (this.currentPage%this._pageCount == 0 ? (this._pageCount-1)*this._linage:(this.currentPage%this._pageCount-1)*this._linage) : 0;
		if(this._properties) {
			for(var j=0 ; j<this._properties.length ; j++) {
				var td = document.createElement("TD");
				if(this._tdCSS && this._tdCSS[j]) {
					td.className = this._tdCSS[j];
				}
				if(this._tdScale) {
					if(this._tdScale[j]) {td.width = this._tdScale[j];}
			    }
				var _p;
				var pre = this._cellNum == j ? i+1+inc : "";
				if(this._data[i][this._properties[j]] || !isNaN(this._data[i][this._properties[j]])) {
					_p = this._data[i][this._properties[j]];
				} else {
					_p = this._properties[j];
				}
				_p = typeof(_p) == 'number' ? _p : _p || "&nbsp;";
				if(pre != "") _p = pre +"&nbsp;"+_p;
				if(this._tdHandle) {
					if(this._tdHandle[j] && this._tdHandle[j] instanceof Function) {
						td.innerHTML = this._tdHandle[j](this._data[i]);
					} else {
						td.innerHTML = _p;
					}
				} else {
					td.innerHTML = _p;
				}
				if(pars){
					tr.className=tr.className+"_new";
					pars=null;
				}
				tr.appendChild(td);
			}
		} else {
			var num=0;
			for(var p in this._data[i]) {
				var td = document.createElement("TD");
				if(this._tdCSS && this._tdCSS[num]) {
					td.className = this._tdCSS[num];
				}
				if(this._tdScale) {
					if(this._tdScale[num]) {td.width = this._tdScale[num];}
			    }
				var _p;
				var pre = this._cellNum == num ? i+1+inc : "";
				if(this._tdHandle) {
					if(this._tdHandle[num] && this._tdHandle[num] instanceof Function) {
						_p = this._tdHandle[num](this._data[i]);
					} else {
						_p =this._data[i][p];
					}
				} else {
					if(this._tdCSS && this._tdCSS[num]) {
						td.className = this._tdCSS[num];
					}
					_p = this._data[i][p];
				}
				_p = typeof(_p) == 'number' ? _p : _p || "&nbsp;";
				if(pre != "") _p = pre +"&nbsp;"+_p;
				td.innerHTML = _p;
				tr.appendChild(td);
				num++;
			}
		}
		this._table.tbody.appendChild(tr);
	}
	if(stop-start < this._linage && this._blank) {
		for(var r=stop-start ; r<this._linage ; r++ ) {
				var tr = document.createElement("TR");
				if(this._properties) {
					for(var j=0 ; j<this._properties.length ; j++) {
						var td = document.createElement("TD");
						td.innerHTML = "&nbsp;";
						if(this._tdCSS && this._tdCSS[j]) {
							td.className = this._tdCSS[j];
						}
						tr.appendChild(td);
					}
				} else {
					var num=0;
					for(var p in this._data[i]) {
						var td = document.createElement("TD");
						td.innerHTML = "&nbsp;";
						tr.appendChild(td);
						num++;
					}
				}
				this._table.tbody.appendChild(tr);
		}
	}
	}
	var currentPage = document.getElementById(this._name+"_currentPage");
	if(currentPage) currentPage.innerHTML = this.currentPage;
	if(this._linageFlag) this._linage = null; //丢弃行数
 };
//上一页
 PageList.prototype.pageUp = function() {
	 if(this.currentPage > 1) this.go (this.currentPage - 1);
 };
//下一页
PageList.prototype.pageDown = function() {
	if(this.currentPage < this.pageSize) this.go(this.currentPage + 1);
};
//到首页
PageList.prototype.pageFirst = function() {
	if(this.currentPage != 1) this.go(1);
};
//到末页
PageList.prototype.pageEnd = function() {
	if(this.currentPage != this.pageSize) this.go(this.pageSize);
};
/**
 * 显示列表
 * @param panel 容器id
 */
PageList.prototype.show = function(panel) {
	 var _p;
	 if(typeof(panel) == "string") {
		 _p = document.getElementById(panel);
	 } else if(!panel.innerHTML) {
		 _p = null;
	 }
	 if(!_p) {
		 alert("can't find the HTML element by " + panel);
		 return;
	 }
	 this._panel = _p;
	 _p.appendChild(this._table);
	 if(this._bottom) _p.parentNode.insertBefore(this._bottom, _p.nextSibling);
	 //显示数据
	 this.go(this.currentPage);
};
PageList.prototype.toString = function() {
	 return this._table.outerHTML;
}

//设置合计的行 
PageList.prototype.setTotalCount = function(func){
	var tbody = document.createElement("tbody");
	var bottom;
	if(arguments[0] instanceof Array) {
		bottom = arguments[0];
	} else {
		 var tbottom = [];
		 for(var i=0; i<arguments.length;) {
			 if(typeof(arguments[i]) == "string") {
				 tbottom[i] = arguments[i++];
			 }
		 }
		 bottom = tbottom;
	 }
	 if(bottom && bottom.length) {
	 	 while(this._table.tbody.lastChild) {
			 this._table.tbody.removeChild(this._head.lastChild);
		 }
		 var tr = document.createElement("TR");
		 var th;
		 for(var i=0 ; i<bottom.length ; i++) {
		 	 td = document.createElement("Td");
		 	 td.id = bottom[i];
			 if(i==0){
			 	 td.innerHTML = bottom[i];
			 }else
			 {
			 	if(td.id!="-")
				 {
				 	td.innerHTML = '0';
				 }
				 else
				 {
				 	td.innerHTML = '-';
				 }
			 }
			 
			 
			 tr.appendChild(td);
		 }
		 tbody.appendChild(tr);
		 this._table.appendChild(tbody);
	 }
}
PageList.prototype.setColor = function(){
	pars="tdThree";
}
