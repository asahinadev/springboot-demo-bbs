$(function() {
	var modal1 = new bootstrap.Modal(document.getElementById('modal1'), {
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
			$("#modal1").find(".modal-body > p").text("正常に終了しました。");
			$("#modal1").on("hide.bs.modal", function() {
				location.href = "login?regist"
			});
			modal1.show()

		}, function(data, type, status) {
			// ダイアログ
			$("#modal1").find(".modal-body > p").text("エラーがあります。確認してください。");
			modal1.show()
		});
		return false;
	})
})
