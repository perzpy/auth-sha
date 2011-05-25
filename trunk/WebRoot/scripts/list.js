jQuery(function() {
			loadList();
		});
var operate;		
var list;
var id;
var name;
var value;
var pageList = null;//列表类
var oldClass = "";  //原样式
var linage = 15; //每页显示行数
//删除图标
var imgD = document.createElement("IMG");
imgD.src = "../images/icon/del.gif";
imgD.onclick = del;
imgD.style.cursor = "pointer";
//修改图标
var imgE = document.createElement("IMG");
imgE.src = "../images/icon/edit.gif";
imgE.onclick = edit;
imgE.style.cursor = "pointer";
function loadList() {
	if(!pageList) {
		pageList = new PageList("pageList",linage,"listTable");
		pageList.setHeadCSS("bgNone");
		pageList.setTDScale("2%", "5%", "6%", "6%");
		pageList.setHead("", "ID", "Name", "Value");
		pageList.setTRClass("trFirst");  //奇数行的样式
        pageList.setTRClass("trSecond","even");  //偶数行的样式
        pageList.setTRNumber(0,"all"); //在第一列显示行号
		pageList.createBottom();
		pageList.setPageReload(0,1,callback);
		pageList.setProperties(["","ID","NAME","VALUE"]);
        pageList.setTDClass("black");  //列样式
		pageList.setPageReload(0,1,callback);
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
    }else{
    	callback(tSeg);
    }
}
var tSeg = 0;
function callback(seg) {
	var name = dwr.util.getValue("name");
	tSeg = seg;
	ExampleWeb.queryPageList(name, seg*linage, linage, function(data){
		if(data){
			pageList.setDataSize(data.total);
			pageList.setData(data.subList);
			list = data.subList;
		}
	});
}
//查询
function search() {
	loadList();
}
//删除
function del() {
	var id = list[this.parentNode.parentNode.getAttribute("index")]["ID"];
	if(window.confirm("您确定要删除该记录吗？")) {
		ExampleWeb.del(id, function(data) {
			if(data) {
				alert("删除成功");
				loadList();
			}
		});
	}
}
function edit() {
	operate = "edit";
	id = list[this.parentNode.parentNode.getAttribute("index")]["ID"];
	name = list[this.parentNode.parentNode.getAttribute("index")]["NAME"];
	value = list[this.parentNode.parentNode.getAttribute("index")]["VALUE"];
	jQuery("#id").attr("value", id);
	jQuery("#id").attr("disabled", "disabled");
	jQuery("#uname").attr("value", name);
	jQuery("#value").attr("value", value);
}
function add(){
	var id = jQuery("#id").attr("value");
	var name = jQuery("#uname").attr("value");
	var value = jQuery("#value").attr("value");
	var exampleTable = {id: id, name: name, value: value};
	ExampleWeb.add(exampleTable, function(data){
		if(data){
			alert(data);
		}
	});
	loadList();
}
function resetForm() {
	if (operate == "edit") {
		jQuery("#id").attr("value", id);
		jQuery("#uname").attr("value", name);
		jQuery("#value").attr("value", value);
		return;
	}
	jQuery("#id").attr("disabled", "");
	document.myForm.reset();
}