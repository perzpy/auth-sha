function getParamByName(name) {
	var params = document.location.search;
	params = params.substring(1);
	if(!params) return "";
	var paramArr = params.split("&");
	for(var i=0; i<paramArr.length; i++) {
		if(paramArr[i].indexOf(name) != -1) {
			return paramArr[i].substring(paramArr[i].indexOf("=") + 1);
		}
	}
}

String.prototype.trim=function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.ltrim=function(){
	return this.replace(/(^\s*)/g,"");
}
String.prototype.rtrim=function(){
	return this.replace(/(\s*$)/g,"");
}

window.alertJson = function(data) {
	alert(dwr.util.toDescriptiveString(data, 3));
}
