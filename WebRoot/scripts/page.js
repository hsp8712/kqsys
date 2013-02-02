var HPageTable = {
	go2page: function(formName, pageNo){
		
		if(pageNo == -1) {
			return;
		}
		
		var form = document.getElementsByName(formName)[0];
		if(form == null) {
			return;
		}
		var actionUrl = form.action;
		if(actionUrl == null) {
			return;
		}
		
		var askIndex = actionUrl.indexOf("?");
		if(askIndex > 0) {
			actionUrl += "&pageNo=" + pageNo;
		} else {
			actionUrl += "?pageNo=" + pageNo;
		}
		
		form.action = actionUrl;
		form.submit();
	}
};