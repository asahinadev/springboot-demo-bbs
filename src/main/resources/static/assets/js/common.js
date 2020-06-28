function parseJson(form) {
	var data = form.serializeArray();

	var returnJson = {};
	for (idx = 0; idx < data.length; idx++) {
		returnJson[data[idx].name] = data[idx].value
	}

	var json = JSON.stringify(returnJson);
	return json;
}

$(function() {
	// $(".modal-dialog").hide();
})