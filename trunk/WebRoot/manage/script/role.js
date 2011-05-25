var type = null;// 操作类型
// 页面加载
jQuery(function() {
			var value = window.getParamByName("action");
			switch (value) {
				case "ROLEADD" :
					type = "add";
					loadCurrentAuths();
					break;
				case "ROLELIST" :
					type = "list";
					loadRoleList();
					break;
				case "ROLEEDIT" :
					curRoleId = window.getParamByName("id");
					type = "edit";
					RoleWeb.queryByPK(curRoleId, function(data) {
								curRole = data;
								resetInfo();
								loadCurrentAuths();
							});
					break;
				default :
					alert("请检查您的连接参数！");
			}
		});
// 保存信息
function saveRole() {
	var name = jQuery("#roleName").val();
	var description = jQuery("#roleDesc").val();
	dwr.engine.setAsync(false);
	if (type == "add") {
		var role = {
			name : name,
			description : description
		};
		RoleWeb.add(role, function(data) {
					saveRoleAuth(data);
				});
	} else if (type == "edit" && curRole) {
		curRole.name = name;
		curRole.description = description;

		RoleWeb.update(curRole, function(data) {
					saveRoleAuth(curRoleId);
				});
	}
	dwr.engine.setAsync(true);
	window.location = "roleList.html?action=ROLELIST";
}
// 重置表单
function resetInfo() {
	if (type == "add") {
		document.getElementById("baseForm").reset();
		dwr.util.removeAllOptions("currentAuth");
		loadCurrentAuths();
		dwr.util.removeAllOptions("roleAuth");
	} else if (type == "edit") {
		if (!curRole) {
			alert("服务器忙，请稍后再试！");
			return;
		}
		jQuery("#roleName").val(curRole["name"]);
		jQuery("#roleDesc").val(curRole["description"]);
	}
}
// 列表参数
var roles = [];
var pageList = null;// 列表类
var oldClass = ""; // 原样式
var linage = 20; // 每页显示行数
// 删除图标
var imgD = document.createElement("IMG");
imgD.src = "../images/icon/del.gif";
imgD.onclick = delRole;
imgD.style.cursor = "pointer";
// 修改图标
var imgE = document.createElement("IMG");
imgE.src = "../images/icon/edit.gif";
imgE.onclick = editRole;
imgE.style.cursor = "pointer";
// 加载列表
function loadRoleList() {
	if (!pageList) {
		pageList = new PageList("pageList", linage, "listTable");
		pageList.setHeadCSS("bgNone");
		pageList.setTDScale("10%", "30%", "30%");
		pageList.setHead("", "名称", "描述");
		pageList.setTRClass("trFirst"); // 奇数行的样式
		pageList.setTRClass("trSecond", "even"); // 偶数行的样式
		pageList.setTRNumber(0, "all"); // 在第一列显示行号
		pageList.createBottom();
		pageList.setPageReload(0, 1, callback);
		// 设置显示属性
		pageList.setProperties(["", "name", "description"]);
		pageList.setTDClass("black");// 列样式
		pageList.attachTrEvent({
					onclick : function() {
						var trs = pageList.getBody().getElementsByTagName("TR");
						var tr;
						for (var i = 0, len = trs.length; i < len; i++) {
							tr = trs[i];
							// 清除上一个被选中的行标识(样式)
							if (tr.getAttribute("isclicked") != null) {
								tr.removeAttribute("isclicked");
								tr.className = oldClass;
							}
						}
						oldClass = this.className;
						// 设置被选中的样式
						this.setAttribute("isclicked", true);
						this.className = "trSelected";
						this.firstChild.appendChild(imgE);
						this.firstChild.appendChild(imgD);
					}
				});
		pageList.show("list");
	} else {
		callback(tSeg);
	}
}
var tSeg = 0;
// 回调
function callback(seg) {
	tSeg = seg;
	RoleWeb.queryPageList(null, seg * linage, linage, function(data) {
				if (data) {
					pageList.setDataSize(data.total);
					pageList.setData(data.subList);
					roles = data.subList;
				}
			});
}
// 删除角色
function delRole() {
	if (window.confirm("您确定要删除该记录吗？")) {
		RoleWeb.del(
				roles[this.parentNode.parentNode.getAttribute("index")]["id"],
				function(data) {
					alert("删除成功!");
					callback(0);
				});
	}
}
// 修改角色
function editRole() {
	var id = roles[this.parentNode.parentNode.getAttribute("index")]["id"];
	if (window.confirm("您确定要修改该记录吗？")) {
		location = "roleInfo.html?action=ROLEEDIT&id=" + id;
	}
}
// 加载当前权限
function loadCurrentAuths() {
	AuthWeb.queryAll(function(data) {
				dwr.util.addOptions("currentAuth", data, "ID", "DESCRIPTION");
				if (type == "edit") {
					AuthWeb.queryAuthsByRoleId(curRoleId, function(data) {
								dwr.util.addOptions("roleAuth", data, "id",
										"description");
								sycAuth();
							});
				}
			});
}
// 同步权限
function sycAuth() {
	var roleAuths = document.getElementById("roleAuth").options;
	var currentAuths = document.getElementById("currentAuth").options;
	for (var i = 0; i < roleAuths.length; i++) {
		for (var j = 0; j < currentAuths.length; j++) {
			if (roleAuths[i].value == currentAuths[j].value) {
				document.getElementById("currentAuth").remove(j);
				break;
			}
		}
	}
}
// 保存角色权限
function saveRoleAuth(roleId) {
	var auths = [];
	var roleAuths = document.getElementById("roleAuth").options;
	for (var i = 0; i < roleAuths.length; i++) {
		auths.push(roleAuths[i].value);
	}
	AuthWeb.updateRoleAuth(roleId, auths, function(data) {
				alert("提交成功");
				resetInfo();
			});
}
// 添加角色权限
function moveAuth(add) {
	var sorSelect;
	var aimSelect;
	if (add) {
		sorSelect = "currentAuth";
		aimSelect = "roleAuth";
	} else {
		sorSelect = "roleAuth";
		aimSelect = "currentAuth";
	}
	var sorAuths = document.getElementById(sorSelect).options;
	for (var i = sorAuths.length - 1; i >= 0; i--) {
		if (sorAuths[i].selected) {
			dwr.util.addOptions(aimSelect, [{
								id : sorAuths[i].value,
								description : sorAuths[i].text
							}], "id", "description");
			document.getElementById(sorSelect).remove(i);
		}
	}
}
//分配菜单
function go2MenuManage() {
	var roleId = null;
	var trs = pageList.getBody().getElementsByTagName("TR");
	if (!trs)
		return;
	for (var i = 0, j = trs.length; i < j; ++i) {
		if (trs[i].getAttribute("isclicked")) {
			var index = trs[i].getAttribute("index");
			if (!roles[index])
				return;
			roleId = roles[index]["id"];
			break;
		}
	}
	if (roleId != null) {
		window.location = "menuManage.html?action=MENUMANAGE&roleId="
				+ roleId;
	}
}
// 生成菜单
function createMenu() {
	dwr.engine.setAsync(false);
	var roleId = null;
	var trs = pageList.getBody().getElementsByTagName("TR");
	if (!trs) {
		return;
	} else {
		//$("all").disabled = true;
		for (var i = 0; i < trs.length; i++) {
			if (trs[i].getAttribute("isclicked")) {
				var index = trs[i].getAttribute("index");
				if (!roles[index])
					return;
				roleId = roles[index]["id"];
				break;
			}
		}
		if (roleId != null) {
			RoleWeb.createMenu(roleId, function(data) {
						if (data) {
							if (data == true) {
								alert("菜单生成完成！");
							} else {
								alert("菜单生成失败！");
							}
						}
					});
		}
		//$("all").disabled = false;
	}
	dwr.engine.setAsync(true);
}