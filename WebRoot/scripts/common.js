function showMsg(msg) {
	if(msg == null || msg == '') {
		return;
	}
	
	$.messager.show({
		title:'提示',
		msg:msg,
		timeout:5000,
		showType:'slide'
	});
}