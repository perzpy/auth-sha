var nodeMap = {};// 树节点MAP
var rNode;// 树根节点
var modus;
var curRoleId = 1;
var curAction = "";
var curRoleAuth;
var moduMap = null;
jQuery(function() {
			moduMap = new Array();
			webFXTreeConfig.usePersistence = false;// 禁用Cookie
			// 取RoleId
			curRoleId = window.getParamByName("roleId");

			if (!curRoleId) {
				alert("参数错误!");
				return;
			}
			rNode = new WebFXTree('根菜单');
			menu_loadMenusByCurrentRoleId();
			jQuery("#treePanel").get(0).innerHTML = rNode;
			dwr.engine.setAsync(false);
			var moduData = new Array();
			ModuWeb.queryAll(function(data) {
				// 存储模块信息
				jQuery.each(data, function(key, value) {
							moduMap[value.NAME] = value.ID;
							moduData.push(value.NAME);
						});
				AuthWeb.queryAuthsByRoleId(curRoleId, function(data) {
							curRoleAuth = data;
						});
			});
			jQuery("#moduInfo").autocomplete(moduData, {
						minChars : 0,
						width : 150,
						mustMatch : true,
						matchContains : true,
						autoFill : true
					});
			dwr.engine.setAsync(true);
		});
// 加入节点并缓存数据
function addNode(data) {
	var levelCode = data.LEVELCODE;
	var cNode = new WebFXTreeItem(data["NAME"],
			"javascript:menu_showInfoPanel()");
	cNode["DATA"] = data;
	var pNode = getParentNode(levelCode);
	if (pNode != null && levelCode.length != 10) {
		pNode.add(cNode);
	} else if (levelCode.length == 10) {
		rNode.add(cNode);
	} else {
	}
	nodeMap[levelCode] = cNode;
}
// 获得父节点
function getParentNode(levelCode) {
	return nodeMap[levelCode.substring(0, levelCode.length - 10)];
}
function addNode2() {
	if (rNode.getSelected()) {
		rNode.getSelected().add(new WebFXTreeItem('New'));
	}
}
// 提交处理
function menu_submit() {
	if (!jQuery("#menuName").val()) {
		alert("菜单名称不能为空");
		return;
	}
	if (!jQuery("#moduInfo").val()) {
		alert("模块名称不能我空");
		return;
	}
	var name, moduId;
	switch (curAction) {
		case "ADD" :
			if (!menu_checkSelect(true)) {
				alert("请选择节点!");
				return;
			}
			var menu = {
				name : jQuery("#menuName").val(),
				moduId : moduMap[jQuery("#moduInfo").val()]
			};
			var pId = (rNode.getSelected()["DATA"] != null) ? rNode
					.getSelected()["DATA"]["ID"] : "0";
			MenuWeb.addMenu(menu, pId, curRoleId, function(menuId) {
						MenuWeb.queryById(menuId, function(menu) {
									addNode(menu);
									rNode.getSelected().expand();
									alert("添加成功!");
								});
					});
			break;
		case "EDIT" :
			if (!menu_checkSelect(false)) {
				alert("请选择节点!");
				return;
			}
			var curNode = rNode.getSelected();
			var menu = {
				id : curNode["DATA"]["ID"],
				levelCode : curNode["DATA"]["LEVELCODE"],
				name : jQuery("#menuName").val(),
				moduId : moduMap[jQuery("#moduInfo").val()],
				seq : curNode["DATA"]["SEQ"]
			};
			MenuWeb.update(menu, function(data) {
						MenuWeb.queryById(curNode["DATA"]["ID"],
								function(menu) {
									jQuery("#" + curNode.id + "-anchor")
											.text(jQuery("#menuName").val());
									curNode["DATA"] = menu;
									alert("修改成功!");
								});
					});
			break;
		default :
			alert("错误操作!");
	}
}
// 删除菜单
function menu_deleteMenu() {
	if (!menu_checkSelect(false)) {
		alert("请选择节点!");
		return;
	}
	var curNode = rNode.getSelected();
	var id = curNode["DATA"]["ID"];
	if (confirm("确定删除该菜单与其子菜单吗?")) {
		MenuWeb.removeMenuWithChildsById(id, curRoleId, function(data) {
					curNode.remove();
					alert("删除成功");
				});
	}
}
function menu_reset() {
}
// 显示添加面板
function menu_showAddPanel() {

	if (!menu_checkSelect(true)) {
		alert("请选择节点!");
		return;
	}

	curAction = "ADD";
	menu_showTotalPanel(true);
	menu_showSubmitPanel(true);

	jQuery("#submitPanel").css("display", "block");
	jQuery("#menuName").val("");
	jQuery("#authBoxesTD").html("");
	menu_lockInput(false);
}

/**
 * 显示修改面板
 */
function menu_showEditPanel() {
	if (!menu_checkSelect(false)) {
		alert("请选择节点!");
		return;
	}

	curAction = "EDIT";
	menu_showTotalPanel(true);
	menu_showSubmitPanel(true);

	var curMenu = rNode.getSelected()["DATA"];
	jQuery("#menuName").val(curMenu["NAME"]);
	if (curMenu["MODU_ID"] != null) {
		jQuery("#moduInfo").val(curMenu["MODU_NAME"]);
	} else {
		jQuery("#moduInfo").val("");
	}
	menu_showAuths(curMenu["MODU_ID"], false);

	menu_lockInput(false);
}

/**
 * 显示模块信息
 */
function menu_showInfoPanel() {
	if (!menu_checkSelect(false)) {
		alert("请选择节点!");
		return;
	}

	curAction = "VIEW";
	menu_showTotalPanel(true);
	menu_showSubmitPanel(false);

	var curMenu = rNode.getSelected()["DATA"];
	jQuery("#menuName").val(curMenu["NAME"]);
	if (curMenu["MODU_ID"] != null) {
		jQuery("#moduInfo").val(curMenu["MODU_NAME"]);
	} else {
		jQuery("#moduInfo").val("");
	}
	menu_showAuths(curMenu["MODU_ID"], true);

	menu_lockInput(true);
}

/**
 * 显示模块相关权限Boxes
 * 
 * @param moduId
 *            模块ID
 */
function menu_showAuths(moduId, ifLock) {
	AuthWeb.queryAuthsByModuId(moduId, function(data) {
		jQuery("#authBoxesTD").html("");
		for (var key in data) {
			var auth = data[key];
			jQuery("#authBoxesTD").get(0).innerHTML += ("<input type=\"checkbox\" name=\"authBox\" "
					+ ((menu_ifRoleHaveAuth(auth["name"])) ? " checked " : "")
					+ " "
					+ (ifLock ? " disabled=\"true\" readOnly=\"true\" " : "")
					+ " value=\""
					+ auth["id"]
					+ "\" onclick=\"menu_changeRoleAuth(this.value,this.checked)\"\>" + auth["description"]);
		}
	});
}

/**
 * 更改角色相关权限
 * 
 * @param authId
 *            权限ID
 * @param changeFlag
 *            改变标志
 */
function menu_changeRoleAuth(authId, changeFlag) {
	if (changeFlag) {
		AuthWeb.addRoleAuth(curRoleId, authId, function(data) {
			AuthWeb.queryAuthsByRoleId(curRoleId, function(data) {
						curRoleAuth = data;
					});
				// success
			});
	} else {
		AuthWeb.removeRoleAuth(curRoleId, authId, function(data) {
			AuthWeb.queryAuthsByRoleId(curRoleId, function(data) {
						curRoleAuth = data;
					});
				// success
			});
	}

}

/**
 * 显示总面板
 * 
 * @param ifShow
 *            是否显示
 */
function menu_showTotalPanel(ifShow) {
	if (ifShow) {
		jQuery("#totalPanel").css("display", "block");
	} else {
		jQuery("#totalPanel").css("display", "none");
	}
}

/**
 * 显示提交按钮面板
 * 
 * @param ifShow
 *            是否显示
 */
function menu_showSubmitPanel(ifShow) {
	if (ifShow) {
		jQuery("#submitPanel").css("display", "block");
	} else {
		jQuery("#submitPanel").css("display", "none");
	}
}

/**
 * 检测选择节点
 * 
 * @param ifEmpty
 *            是否可以为空节点
 */
function menu_checkSelect(ifEmpty) {
	if (!rNode.getSelected()) {
		return false;
	}

	if (!ifEmpty) {
		if (!rNode.getSelected()["DATA"]) {
			return false;
		}
	}

	return true;
}

/**
 * 锁定提交面板的INPUT
 * 
 * @param ifLock
 *            是否锁定
 */
function menu_lockInput(ifLock) {
	jQuery("#menuName").each(function() {
				this.disabled = this.readOnly = ifLock;
			});
	jQuery("#moduInfo").each(function() {
				this.disabled = this.readOnly = ifLock;
			});
}

/**
 * 判断是否有权限
 * 
 * @param authName
 *            权限名称
 */
function menu_ifRoleHaveAuth(authName) {
	for (var key in curRoleAuth) {
		if (curRoleAuth[key]["name"] == authName) {
			return true;
		}
	}
	return false;
}

/**
 * 单步移动
 */
function menu_stepmove(forward) {

	if (!menu_checkSelect(false)) {
		alert("请选择节点!");
		return;
	}

	var curNode = rNode.getSelected();
	var curMenu = curNode["DATA"];
	var levelCode = curMenu["LEVELCODE"];
	var pNode = getParentNode(curMenu["LEVELCODE"]);
	if (pNode == null) {
		pNode = rNode;
	}

	if (forward == "UP") {
		MenuWeb.stepMoveMenu(curRoleId, curMenu["ID"], true, function(data) {
					refreshNode(pNode);
					selectNode(levelCode);
				});
	} else if (forward = "DOWN") {
		MenuWeb.stepMoveMenu(curRoleId, curMenu["ID"], false, function(data) {
					refreshNode(pNode);
					selectNode(levelCode);
				});
	}
}

/**
 * 加载菜单
 */
function menu_loadMenusByParentMenuId(pId) {
	dwr.engine.setAsync(false);
	MenuWeb.queryMenusByPId(pId, function(data) {
				for (var key in data) {
					addNode(data[key]);
				}
			});
	dwr.engine.setAsync(true);
}

/**
 * 加载菜单
 */
function menu_loadMenusByCurrentRoleId() {
	dwr.engine.setAsync(false);
	MenuWeb.queryMenusByRoleId(curRoleId, function(data) {
				for (var key in data) {
					addNode(data[key]);
				}
			});
	dwr.engine.setAsync(true);
}

/**
 * 刷新节点
 */
function refreshNode(pNode) {
	menu_clearChildNodes(pNode);
	if (pNode["DATA"]) {
		menu_loadMenusByParentMenuId(pNode["DATA"]["ID"]);
	} else {
		menu_loadMenusByCurrentRoleId();
	}
	pNode.expand();
}

function selectNode(levelCode) {
	var node = nodeMap[levelCode];

	var pNode = getParentNode(levelCode);

	if (pNode) {
		pNode.deSelect();
	} else {
		rNode.deSelect();
	}

	if (!node) {
		node = rNode;
	}
	// window.blur();
	node.select();
	node.focus()
	// node.deSelect();
	// node.blur();
}

/**
 * 清理childeNodes
 */
function menu_clearChildNodes(pNode) {
	try {
		var childNodes = pNode.childNodes;
		while (childNodes && childNodes.length > 0) {
			childNodes[0].remove();
		}
	} catch (e) {
		// alert(e.description);
	}
}