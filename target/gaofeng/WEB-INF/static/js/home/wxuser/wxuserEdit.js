layui.config({
			base : "js/"
		}).use([ 'form', 'layer', 'jquery', 'layedit', 'laydate' ],
				function() {
					var form = layui.form(), 
						layer = layui.layer, 
						laypage = layui.laypage, 
						layedit = layui.layedit, 
						laydate = layui.laydate, 
						$ = layui.jquery;
					form.on("submit(editUser)", function(data) {
						$.ajax({
							type : 'POST',
							url : '/gaofeng/home/wxuser/wxUserEdit',
							dataType : 'json',
							data : JSON.stringify(data.field),
							contentType: 'application/json',
							async : true,
							success : function(result) {
								console.log(result.code);
								if (result.code == 0) {
									layer.msg('success:保存成功', {
										icon : 1,
										time : 1000
									});
									setTimeout(function() {
										
										layer.closeAll("iframe");
										parent.hell();
								 		//刷新父页面
								 		
									}, 800);
								} else {
									consol.alert(result.code);
									layer.msg('eles:保存失败！' + result.message, {
										icon : 2,
										time : 1000
									});
								}
							},
							error : function(result, type) {
								layer.msg('error:保存失败！', {
									icon : 2,
									time : 1000
								});
							}
						});
						console.log(data.field)
						return false;
					})

				})
