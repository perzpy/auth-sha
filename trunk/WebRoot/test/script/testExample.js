var cellFuctions = [null,
					function(item) {return item["id"];},
					function(item) {return item["name"];},
					function(item) {return item["value"];}
			];
var cellFuctionsJdbc = [null,
					function(item) {return item["ID"];},
					function(item) {return item["NAME"];},
					function(item) {return item["VALUE"];}
			];

function load(){
	$("#testJQuery")[0].innerHTML = "Test JQuery Success!";
	var value = window.getParamByName("action");
	switch(value) {
		case "ADD":
			document.getElementById("tables").style.display = "none";
			document.getElementById("forms").style.display = "block";
			break;
		case "LISTALL":
			document.getElementById("tables").style.display = "none";
           	document.getElementById("forms").style.display = "none";
           	setTableListAll();
			break;
		default:
           	document.getElementById("tables").style.display = "block";
           	document.getElementById("forms").style.display = "none";
			setTableAllRows();
			setTableTotal();
	}
}

var utilTitle = null;
window.onload = load;

var startIndex = 0;
var linage = 28;
function setResult(startIndex, linage){
	ExampleWeb.queryPageList(startIndex, linage, function(data){
		if(data){
			startIndex = startIndex + linage;
			//alert(dwr.util.toDescriptiveString(data,3));
			dwr.util.removeAllRows("result");
			//dwr.util.addRows("result", data, cellFuctions);
			dwr.util.addRows("result", data, cellFuctionsJdbc);
		}
	});
}

function setTableAllRows(){
	ExampleWeb.queryList(function(data){
		if(data){
			//alert(dwr.util.toDescriptiveString(data,3));
			$("#total")[0].innerHTML = "Total = " + data.length;
			//DWRUtil.removeAllRows("result");
			dwr.util.removeAllRows("result");
			//DWRUtil.addRows("result", data, cellFuctions, {escapeHtml:false});
			dwr.util.addRows("result", data, cellFuctions);
			//dwr.util.addRows("result", data, cellFuctionsJdbc);
		}
	});
}

var pageList = null;
var tSeg;
function setTableListAll(){
	if(!pageList){
		pageList = new PageSearch("pageList",linage,"listTable");
		pageList.setHead("number", "id", "name", "value");
		pageList.setTDScale("10%", "30%", "30%", "30%");
		pageList.setProperties(["", "ID", "NAME", "VALUE"]);
		pageList.setTRNumber(0,"all");
		pageList.createBottom();
		pageList.setPageReload(0, 1, callback);		         
		pageList.show("list");
	} else {
	    callback(tSeg);
	}
}
function callback(seg){
	tSeg = seg;
	ExampleWeb.queryListCount(function(data){
		if(data){
			pageList.setDataSize(data);
			ExampleWeb.queryPageList(seg * linage, linage, function(data) {
					if(data){
						pageList.setData(data);
					}
				});
		}
	});
}

function setTableTotal(){
	ExampleWeb.queryListCount(function(data){
		if(data){
			//var utilTitle = new UtilTitle();
			utilTitle = new UtilTitle("utilTitleDiv");
			utilTitle.setTotal(data);
			utilTitle.setLinage(linage);
			utilTitle.setDivPage();
			//alert(utilTitle.toString());
			//$("#utilTitleDiv")[0].innerHTML = utilTitle.toString();
			//$("#utilTitleDiv").append(utilTitle.toString());
		}
	});
}

function nextPage(){//alert(startIndex + " " +linage);
	setResult(startIndex, linage);
}

function moveToGoFunction(startIndex, linage){
	//alert(startIndex + " " + linage);
	setResult(startIndex, linage);
}

function addGo(){
	location.href = "test.html?action=ADD";
}

function add(){
	var id = $("#id").attr("value");
	var name = $("#name").attr("value");
	var value = $("#value").attr("value");
	var exampleTable = {id: id, name: name, value: value};
	ExampleWeb.add(exampleTable, function(data){
		if(data){
			alert(data);
		}
	});
}