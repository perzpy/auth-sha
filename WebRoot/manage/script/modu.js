jQuery(function () {
	var value = window.getParamByName("action");
	switch(value) {
		case "ADD":
			jQuery("#addOrUpdate").css("display", "block");
			queryAuthListAll();
			break;
		case "MODIFY":
			jQuery("#addOrUpdate").css("display", "block");
			break;
		case "LIST":
			jQuery("#addOrUpdate").css("display", "none");
			showListAll();
			break;
		default:
	}
});

var nameTemp = "";
var nameIsExist = false;
var moduList = [];
var pageList = null;
var linage = 10;
var tSeg;
var oldClass = "";
//删除图标
var imgD = document.createElement("IMG");
imgD.src = "../images/010.gif";
imgD.onclick = del;
imgD.style.cursor = "pointer";
//修改图标
var imgE = document.createElement("IMG");
imgE.src = "../images/037.gif";
imgE.onclick = edit;
imgE.style.cursor = "pointer";
function showListAll(){
	if(!pageList){
		pageList = new PageList("pageList", linage, "listTable");
		pageList.setHeadCSS("bgNone");
		pageList.setTRClass("trFirst");
		pageList.setTRClass("trSecond", "even");
		pageList.setHead("序号", "名称", "地址", "备注");
		pageList.setTDScale("10%", "20%", "40%", "30%");
		pageList.setProperties(["", "NAME", "URL", "DESCRIPTION"]);
		pageList.setTRNumber(0,"all");
		pageList.createBottom();
		pageList.setPageReload(0, 1, callback);
		pageList.attachTrEvent({
         onclick:function(){
         	var trs = pageList.getBody().getElementsByTagName("TR");
         	var tr;
         	for(var i=0,len=trs.length; i<len; i++) {
         		tr = trs[i];
         		if(tr.getAttribute("isclicked") != null) {
         			tr.removeAttribute("isclicked");
         			tr.className = oldClass;
         		}
         	}
         	oldClass = this.className;
         	this.setAttribute("isclicked",true);
         	this.className = "trSelected";
         	this.firstChild.appendChild(imgE);
         	this.firstChild.appendChild(imgD);
         }});
		pageList.show("list");
	} else {
	    callback(tSeg);
	}
}
function callback(seg){
	tSeg = seg;
	ModuWeb.queryPageList(nameTemp, seg * linage, linage, function(data){
		if(data){
			pageList.setDataSize(data.total);
			pageList.setData(data.subList);
			moduList = data.subList;
		}
	});
}

function addOrUpdate(){
	dwr.engine.setAsync(false);
	var id = jQuery("#ids").attr("value").trim();
	var name = jQuery("#name").attr("value").trim();
	var url = jQuery("#url").attr("value").trim();
	var description = jQuery("#description").attr("value").trim();
	var authId = getSelectValue();
	var modu = {
				id: id,
				name: name,
				url: url,
				description: description
				};
	checkName();
	if(nameIsExist){
		return;
	}
	if(id == ""){
		ModuWeb.add(modu, authId, function(data){
			if(data){
				alert("增加成功！");
				goList();
			}
		});
	}else{
		ModuWeb.update(modu, authId, function(data){
			if(data){
				alert("修改成功！");
				goList();
			}
		});
	}
	dwr.engine.setAsync(true);
}

function goAdd(){
	location.href = "modu.html?action=ADD";
}

function goList(){
	location.href = "modu.html?action=LIST";
}

function flash(){
	document.getElementById("addOrUpdate").style.display = "none";
	document.getElementById("list").style.display = "block";
	document.getElementById("list").nextSibling.style.display = "block";
	showListAll();
}

function del(){
	var id = moduList[this.parentNode.parentNode.getAttribute("index")]["ID"];
	if(window.confirm("您确定要删除该记录吗？")) {
		ModuWeb.del(id, function(data){
			if(data){
				alert("删除成功！");
				goList();
			}
		});
	}
}

var indexTemp = 0;
function edit(){
	document.getElementById("addOrUpdate").style.display = "block";
	document.getElementById("list").style.display = "none";
	document.getElementById("list").nextSibling.style.display = "none";
	var index = this.parentNode.parentNode.getAttribute("index");
	indexTemp = index;
	fill(index, true);
}

function fill(index, flag){
	jQuery("#ids").attr("value", moduList[index]["ID"]);
	jQuery("#name").attr("value", moduList[index]["NAME"]);
	jQuery("#url").attr("value", moduList[index]["URL"]);
	jQuery("#description").attr("value", moduList[index]["DESCRIPTION"]);
	if(flag){
		queryAuthListAll();
	}
	ModuWeb.queryAuthModuListByModuId(moduList[index]["ID"], function(data){
		if(data){
			for(var i = 0; i < data.length; i = i + 1){
				var value = data[i]["ID"];
				var text = data[i]["NAME"];
				jQuery("#selectAuth").get(0).options.add(new Option(text, value));
			}
		}
	});
}

function checkName(){
	var id = jQuery("#ids").attr("value").trim();
	var name = jQuery("#name").attr("value").trim();
	if(name == ""){
		nameIsExist = true;
		jQuery("#nameSpan").text("模块名不能为空！");
		return;
	}else{
		nameIsExist = false;
		jQuery("#nameSpan").text(" ");
	}
	ModuWeb.queryByName(name, function(data){
		if(data){
			//alert(dwr.util.toDescriptiveString(data,3));
			if(data["name"] == name && data["id"] != id){
				nameIsExist = true;
				jQuery("#nameSpan").text("模块名已存在！");
			}else{
				nameIsExist = false;
				jQuery("#nameSpan").text(" ");
			}
		}
	});
}
var authList = [];
function queryAuthListAll(){
	AuthWeb.queryAll(function(data){
		if(data){
			authList = data;
			for(var i = 0; i < data.length; i = i + 1){
				var value = data[i]["ID"];
				var text = data[i]["DESCRIPTION"];
				jQuery("#allAuth").get(0).options.add(new Option(text, value));
			}
		}
	});
}

function addSelect(){
	var selectAll = jQuery("#allAuth").get(0);
	var selectAuth = jQuery("#selectAuth").get(0);
	selectAuth.options.length = 0;
	for(var i = 0; i < selectAll.options.length; i = i + 1){
		if(selectAll.options[i].selected == true){
			var value = selectAll.options[i].value;
			var text = selectAll.options[i].text;
			var flag = false;
			for(var j = 0; j < selectAuth.options.length; j = j + 1){
				if(value == selectAuth.options[j].value){
					flag = true;
				}
			}
			if(!flag){
				selectAuth.options.add(new Option(text, value));
				return;
			}
		}
	}
}

function delSelect(){
	var select = jQuery("#selectAuth").get(0);
	var length = select.options.length;
	for(var i = length - 1; i >= 0; i = i - 1){
		if(select.options[i].selected == true){
			select.remove(i);
		}
	}
}

function getSelectValue(){
	var values = "";
	var select = jQuery("#selectAuth").get(0);
	for(var i = 0; i < select.options.length; i = i + 1){
		values = select.options[i].value;
	}
	return values;
}

function resetForm(){
	var id = jQuery("#ids").attr("value").trim();
	if(id == ""){
		jQuery("#selectAuth").get(0).options.length = 0;
		document.getElementById("formId").reset();
	}else{
		fill(indexTemp, false);
	}
}

