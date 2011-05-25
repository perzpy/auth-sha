function load(){
	var value = window.getParamByName("action");
	switch(value) {
		case "ADD":
			document.getElementById("addOrUpdate").style.display = "block";
			queryRoleListAll();
			break;
		case "MODIFY":
			document.getElementById("addOrUpdate").style.display = "block";
			break;
		case "LIST":
			document.getElementById("addOrUpdate").style.display = "none";
			showListAll();
			break;
		default:
	}
}

window.onload = load;

var nameTemp = "";
var userIsExist = false;
var userList = [];
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
		pageList.setHead("序号", "名称", "备注");
		pageList.setTDScale("10%", "45%", "45%");
		pageList.setProperties(["", "name", "description"]);
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
	UserWeb.queryPageList(nameTemp, seg * linage, linage, function(data){
		if(data){
			pageList.setDataSize(data.total);
			pageList.setData(data.subList);
			userList = data.subList;
		}
	});
}

function addOrUpdateUser(){
	dwr.engine.setAsync(false);
	var id = jQuery("#ids").attr("value").trim();
	var name = jQuery("#name").attr("value").trim();
	var tel = jQuery("#tel").attr("value").trim();
	var mail = jQuery("#mail").attr("value").trim();
	var department = jQuery("#department").attr("value").trim();
	var password = jQuery("#password").attr("value").trim();
	var re_password = jQuery("#re_password").attr("value").trim();
	var description = jQuery("#description").attr("value").trim();
	var roleIds = getSelectValue();
	var user = {
				id: id,
				name: name,
				passwd: password,
				description: description,
				mail: mail,
				tel: tel,
				department: department
				};
	if(!checkPassword()){
		return;
	}
	checkUser();
	if(userIsExist){
		return;
	}
	if(id == ""){
		UserWeb.add(user, roleIds, function(data){
			if(data){
				alert("增加成功！");
				//flash();
				goList();
			}
		});
	}else{
		UserWeb.update(user, roleIds, function(data){
			if(data){
				alert("修改成功！");
				//flash();
				goList();
			}
		});
	}
	dwr.engine.setAsync(true);
}

function goAdd(){
	location.href = "user.html?action=ADD";
}

function goList(){
	location.href = "user.html?action=LIST";
}

function flash(){
	document.getElementById("addOrUpdate").style.display = "none";
	document.getElementById("list").style.display = "block";
	document.getElementById("list").nextSibling.style.display = "block";
	showListAll();
}

function del(){
	var id = userList[this.parentNode.parentNode.getAttribute("index")]["id"];
	if(window.confirm("您确定要删除该记录吗？")) {
		UserWeb.del(id, function(data){
			if(data){
				alert("删除成功！");
				//flash();
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
	jQuery("#ids").attr("value", userList[index]["id"]);
	jQuery("#name").attr("value", userList[index]["name"]);
	jQuery("#tel").attr("value", userList[index]["tel"]);
	jQuery("#mail").attr("value", userList[index]["mail"]);
	jQuery("#department").attr("value", userList[index]["department"]);
	jQuery("#password").attr("value", userList[index]["passwd"]);
	jQuery("#re_password").attr("value", userList[index]["passwd"]);
	jQuery("#description").attr("value", userList[index]["description"]);
	if(flag){
		queryRoleListAll();
	}
	jQuery("#selectRole").get(0).options.length = 0;
	UserWeb.queryUserRoleListByUserId(userList[index]["id"], function(data){
		if(data){
			for(var i = 0; i < data.length; i = i + 1){
				var value = data[i]["ID"];
				var text = data[i]["NAME"];
				jQuery("#selectRole").get(0).options.add(new Option(text, value));
			}
		}
	});
}

function checkUser(){
	var id = jQuery("#ids").attr("value").trim();
	var name = jQuery("#name").attr("value").trim();
	if(name == ""){
		userIsExist = true;
		jQuery("#nameSpan").text("用户名不能为空！");
		return;
	}else{
		userIsExist = false;
		jQuery("#nameSpan").text(" ");
	}
	UserWeb.queryByName(name, function(data){
		if(data){
			//alert(dwr.util.toDescriptiveString(data,3));
			if(data["name"] == name && data["id"] != id){
				userIsExist = true;
				jQuery("#nameSpan").text("用户名已存在！");
			}else{
				userIsExist = false;
				jQuery("#nameSpan").text(" ");
			}
		}
	});
}

function checkPassword(){
	var password = jQuery("#password").attr("value").trim();
	var re_password = jQuery("#re_password").attr("value").trim();
	if(password != re_password){
		jQuery("#re_passwordSpan").text("输入密码不同！");
		return false;
	}else{
		jQuery("#re_passwordSpan").text(" ");
	}
	return true;
}

var roleList = [];
function queryRoleListAll(){
	RoleWeb.queryListAll(function(data){
		if(data){
			roleList = data;
			for(var i = 0; i < data.length; i = i + 1){
				var value = data[i]["id"];
				var text = data[i]["name"];
				jQuery("#allRole").get(0).options.add(new Option(text, value));
			}
		}
	});
}

function addSelect(){
	var selectAll = jQuery("#allRole").get(0);
	var selectRole = jQuery("#selectRole").get(0);
	for(var i = 0; i < selectAll.options.length; i = i + 1){
		if(selectAll.options[i].selected == true){
			var value = selectAll.options[i].value;
			var text = selectAll.options[i].text;
			var flag = false;
			for(var j = 0; j < selectRole.options.length; j = j + 1){
				if(value == selectRole.options[j].value){
					flag = true;
				}
			}
			if(!flag){
				selectRole.options.add(new Option(text, value));
			}
		}
	}
}

function delSelect(){
	var select = jQuery("#selectRole").get(0);
	var length = select.options.length;
	for(var i = length - 1; i >= 0; i = i - 1){
		if(select.options[i].selected == true){
			select.remove(i);
		}
	}
}

function getSelectValue(){
	var values = "";
	var select = jQuery("#selectRole").get(0);
	for(var i = 0; i < select.options.length; i = i + 1){
		values = values + select.options[i].value + ",";
	}
	return values;
}

function resetForm(){
	var id = jQuery("#ids").attr("value").trim();
	if(id == ""){
		jQuery("#selectRole").get(0).options.length = 0;
		document.getElementById("formId").reset();
	}else{
		fill(indexTemp, false);
	}
}
