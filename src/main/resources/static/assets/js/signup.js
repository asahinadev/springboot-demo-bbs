$(function() {

	var confirmeEl = document.getElementById('confirme');
	var confirme = new bootstrap.Modal(confirmeEl, {
		keyboard : false
	});

	var alertEl = document.getElementById('alert');
	var alert = new bootstrap.Modal(alertEl, {
		keyboard : false
	});

	$("#btn1").on("click", function() {
		form = $("#form1");

		$.ajax({
			type : "POST",
			url : form.attr("action"),
			data : parseJson(form),
			dataType : "json",
			contentType : 'application/json; charset=utf-8',
		}).then(function(data) {

			// バリデーションクラスを除去
			$("#form1 .is-invalid").removeClass("is-invalid");
			$("#form1 .is-valid").removeClass("is-valid");
			$("#form1 .invalid-feedback").remove();
			$("#form1 .form-control:not(.is-invalid)").addClass("is-valid");

			// ダイアログ
			$("#alert .modal-body > p").text("正常に終了しました。");
			alertEl.addEventListener('hidden.bs.modal', function(e) {
				location.href = "/login";
			})
			alert.show()

		}, function(data, type, status) {

			// ダイアログ
			$("#alert .modal-body > p").text("エラーがあります。確認してください。");
			alert.show()

		});
		return false;
	})
})
