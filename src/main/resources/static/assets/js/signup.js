$(function() {

	// FORM
	var $form = $("#form1");
	
	// 選択ダイアログ
	var confirmeEl = document.getElementById('confirme');
	var $confirme  = $(confirmeEl);
	var bConfirme   = new bootstrap.Modal(confirmeEl, {
		keyboard : false
	});
	

	// アラートダイアログ
	var alertEl = document.getElementById('alert');
	var $alert  = $(alertEl);
	var bAlert = new bootstrap.Modal(alertEl, {
		keyboard : false
	});

	// 送信処理
	$confirme.find(".modal-body > p").text($("#confime_msg").attr("content"));
	$confirme.find(".modal-footer .btn-primary").on("click", function(){
		
		$.ajax({
			
			type        : $form.attr("method"),
			url         : $form.attr("action"),
			data        : parseJson($form),
			dataType    : "json",
			contentType : 'application/json; charset=utf-8',
			
		}).then(function(data) {

			// ダイアログ
			$alert.find(".modal-body > p").text("正常に終了しました。");
			alertEl.addEventListener('hidden.bs.modal', function(e) {
				location.href = $("#redirect_url").attr("content");
			})
			bAlert.show()

		}, function(data, type, status) {
			
			// ダイアログ表示
			$alert.find(data.responseJSON[0].defaultMessage);
			bAlert.show()
		});
		
		// ダイアログを閉じる
		bConfirme.hide();
	})
	
	// エラーメッセージ
	function invalid() {
		// ダイアログ
		$alert.find(".modal-body > p").text("エラーがあります。確認してください。");
		bAlert.show()

	}

	// 送信ボタン
	$("#btn1").on("click", function() {
		
		if ($form.find(".form-control:invalid").length != 0) {
			
			// ダイアログ表示
			invalid();
			
			return false;
		} 
		bConfirme.show();

		return false;
	})
})
