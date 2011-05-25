function load(){
	dwr.engine.setAsync(false);
	queryUserRole();
	setRoleMenu();
	dwr.engine.setAsync(true);
}

window.onload = load;

var userRoleMap = [];
var mapIndex = 0;
function queryUserRole(){
	LoginWeb.loginUserRole(function(data){
		if(data){
			//alert(dwr.util.toDescriptiveString(data,3));
			userRoleMap = data;
		}
	});
	
}

function setRoleMenu(mark){
	var roleText = "";
	var menuPath = "";
	if(mark == "+"){
		mapIndex = mapIndex * 1 + 1;
	}else if(mark == "-"){
		mapIndex = mapIndex * 1 - 1;
	}
	if(userRoleMap.length == 0){
		return;
	}
	if(mapIndex * 1 == userRoleMap.length){
		mapIndex = 0;
	}else if(mapIndex * 1 == -1){
		mapIndex = userRoleMap.length - 1;
	}
	roleText = userRoleMap[mapIndex]["NAME"];
	jQuery("#roleText").text(roleText);
	menuPath = userRoleMap[mapIndex]["ID"];
	jQuery("#leftFrame").attr("src", "template/" + menuPath + ".html");
	//alert(roleText + "," + menuPath);
}

