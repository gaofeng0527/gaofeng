var startPage = 0;// 起始页
var pageSize = 10;// 每页显示多少条
var total = 0;// 总数
var currPage = 1;// 当前页面
var firstLoad = 0;// 页面首次加载分页
var $;
var laypage;
var form;
function hell(){
	layer.closeAll("iframe");
	getProducts();
	toPage();
}

// ajax 请求后台数据
function getProducts() {
	$.ajax({
		url : "/gaofeng/home/product/findProductByPage",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			startPage : startPage,
			pageSize : pageSize
		},
		success : function(data) {
			// 渲染页面
			total = data.total;
			startPage = data.pageNum;
			renderDate(data.list);

		}
	})
}

function search(){
	var title = $("#title").val();
	var area = $("#area").val();
	var operator = $("#operator").val();
	var status = $("#status").val();
	$.ajax({
		type : 'POST',
		url : '/gaofeng/home/product/search',
		dataType : 'json',
		data : {title:title,area:area,operator:operator,status:status,startPage : startPage,
			pageSize : pageSize},
		async : false,
		success : function(data) {
			total = data.total;
			startPage = data.pageNum;
			renderDate(data.list);
		},
		error : function(result, type) {
		}
	});
}

function searchToPage() {
	laypage.render({
		elem : 'demo7',
		count : total,
		curr : startPage,
		layout : [ 'count', 'prev', 'page', 'next', 'limit', 'skip' ],
		jump : function(obj) {
			console.log(obj);
			console.log(total);
			startPage = obj.curr;
			pageSize = obj.limit;
			if (firstLoad == 1) {
				search();
			}
			firstLoad = 1;

		}
	});
}
function renderDate(plist, curr) {
	var dataHtml = '';
	if (plist.length != 0) {
		for (var i = 0; i < plist.length; i++) {
			dataHtml += '<tr>'
					+ '<td><input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose"></td>'
					+ '<td align="left">'
					+ plist[i].title
					+ '</td>'
					+ '<td>'
					+ plist[i].price
					+ '</a></td>'
					+ '<td>'
					+ plist[i].area
					+ '</td>'
					+ '<td>'
					+ plist[i].operator
					+ '</td>'
					+ '<td>'
					+ plist[i].ptypeName
					+ '</td>'
					+ '<td>'
					+ plist[i].statusName
					+ '</td>'
					+ '<td>'
					+ '<a class="layui-btn layui-btn-mini links_edit" data-id = "'
					+ plist[i].id
					+ '"><i class="iconfont icon-edit"></i> 编辑</a>'
					+ '</td>'
					+ '</tr>';
		}
	} else {
		dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
	}
	$(".links_content").html(dataHtml);
	 form.render();
	
}
function toPage() {
	laypage.render({
		elem : 'demo7',
		count : total,
		curr : startPage,
		layout : [ 'count', 'prev', 'page', 'next', 'limit', 'skip' ],
		jump : function(obj) {
			console.log(obj);
			console.log(total);
			startPage = obj.curr;
			pageSize = obj.limit;
			if (firstLoad == 1) {
				getProducts();
			}
			firstLoad = 1;

		}
	});
}

layui.config({base : "js/"}).use([ 'form', 'layer', 'jquery', 'laypage', 'upload'],function() {
	form = layui.form(), 
	layer = layui.layer, 
	laypage = layui.laypage,
	upload = layui.upload,
	$ = layui.jquery;
	getProducts();
	toPage();
	// 
	$(".linksAdd_btn").click(
			function() {
				var index = layer.open({
					title : "添加产品",
					type : 2,
					scrollbar : true,
					area : ['100%','100%'],
					content : "productAdd",
					success : function(layero,index) {
						layer.tips('点击此处返回产品列表','.layui-layer-setwin .layui-layer-close',
								{tips : 3});
					}
				})
				// 改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
				$(window).resize(function() {
					layer.full(index);
				})
				layer.full(index);
		});
	
	//条件查询
	$("#search").click(function(){
		startPage = 1;
		search();
		searchToPage();
	});
	
	
	//显示所有
	$("#allList").click(function(){
		getProducts();
		toPage();
	});
	
	$(".export_btn").click(
			function() {
				window.open('/gaofeng/home/product/exportProduct2');
	});
	
	$(".down_btn").click(
			function() {
				window.open('/gaofeng/static/doc/product.xlsx');
	});

	// 全选
	form.on('checkbox(allChoose)',function(data) {
		var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
		child.each(function(index, item) {
			item.checked = data.elem.checked;
		});
		form.render('checkbox');
	});
	
	// 通过判断产品是否全部选中来确定全选按钮是否选中
	form.on("checkbox(choose)",function(data) {
		var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
		var childChecked = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"]):checked')
		data.elem.checked;
		if (childChecked.length == child.length) {
			$(data.elem).parents('table').find('thead input#allChoose').get(0).checked = true;
		} else {
			$(data.elem).parents('table').find('thead input#allChoose').get(0).checked = false;
		}
		form.render('checkbox');
	})
	
	// 操作
	$("body").on("click",".links_edit",function() { // 编辑
		var _this = $(this);
		var index = layer.open({
			title : "修改产品",
			type : 2,
			content : "productEdit/"+ _this.attr("data-id"),
			success : function(layero,index) {
				
				//layer.tips('点击此处返回产品列表','.layui-layer-setwin .layui-layer-close',{tips : 3});
			}
		});
		
		// 改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
		$(window).resize(function() {
			layer.full(index);
		})
		layer.full(index);
	})
	
	//文件上传
	 upload.render({
	    elem: '#test3'
	    ,url: '/gaofeng/home/product/importExcel'
	    ,accept: 'file' //普通文件
	    ,exts: 'xlsx|xls' //只允许上传压缩文件
	    ,done: function(res){
	      console.log(res)
	    }
	  });
	
	$("body").on("click",".links_del",function() { // 删除
		var _this = $(this);
		layer.confirm('确定删除此信息？',{icon : 3,title : '提示信息'},
				function(index) {
			$.ajax({
				type : 'GET',
				url : '/gaofeng/home/product/deleteProduct/'+ _this.attr("data-id"),
				dataType : 'json',
				contentType : 'application/json',
				async : true,
				success : function(result) {
					console.log(result.code);
					if (result.code == 0) {
						layer.msg('success:删除成功',{icon : 1,time : 1000});
						setTimeout(function() {
							firstLoad = 0;
							getProducts();
							toPage();
						}, 800);
					} else {
						layer.msg('eles:删除失败！'+ result.message,{icon : 2,time : 1000});
					}
				},error : function(result,type) {
					layer.msg('error:删除失败！',{icon : 2,time : 1000});
				}
			});
			layer.close(index);
		});
	})
})