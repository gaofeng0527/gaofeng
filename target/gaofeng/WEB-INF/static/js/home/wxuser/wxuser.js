var startPage = 0;// 起始页
var pageSize = 10;// 每页显示多少条
var total = 0;// 总数
var currPage = 1;// 当前页面
var firstLoad = 0;// 页面首次加载分页
var $;
var form;
var laypage;

function hell(){
	layer.closeAll("iframe");
	getProducts();
	toPage();
}

// ajax 请求后台数据
function getProducts() {
	$.ajax({
		url : "/gaofeng/home/wxuser/findUserByPage",
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
	var uname = $("#uname").val();
	var uphone = $("#uphone").val();
	var area = $("#area").val();
	var status = $("#status").val();
	$.ajax({
		type : 'POST',
		url : '/gaofeng/home/wxuser/search',
		dataType : 'json',
		data : {uname:uname,uphone:uphone,area:area,status:status,startPage:startPage,pageSize:pageSize},
		async : false,
		success : function(data) {
			total = data.total;
			startPage = data.pageNum;
			renderDate(data.list);
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
			startPage = obj.curr;
			pageSize = obj.limit;
			if (firstLoad == 1) {
				search();
			}
			firstLoad = 1;
		}
	});
}


function renderDate(ulist, curr) {
	var dataHtml = '';
	if (ulist.length != 0) {
		for (var i = 0; i < ulist.length; i++) {
			dataHtml += '<tr>'
					+ '<td><input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose"></td>'
					+ '<td align="left">'
					+ ulist[i].nickName
					+ '</td>'
					+ '<td align="left">'
					+ ulist[i].uname
					+ '</td>'
					+ '<td>'
					+ ulist[i].uphone
					+ '</a></td>'
					+ '<td>'
					+ ulist[i].cardId
					+ '</td>'
					+ '<td>'
					+ ulist[i].address
					+ '</td>'
					+ '<td>'
					+ ulist[i].area
					+ '</td>'
					+ '<td>'
					+ '<a class="layui-btn layui-btn-mini user_edit" data-id = "'
					+ ulist[i].id
					+ '"><i class="iconfont icon-edit"></i> 编辑</a>'
					+ '</td>'
					+ '</tr>';
		}
	} else {
		dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
	}
	$(".users_content").html(dataHtml);
	 form.render();
	
}
function toPage() {
	laypage.render({
		elem : 'demo7',
		count : total,
		curr : startPage,
		layout : [ 'count', 'prev', 'page', 'next', 'limit', 'skip' ],
		jump : function(obj) {
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
	
	$(".export_btn").click(
			function() {
				window.open('/gaofeng/home/wxuser/exportWxUser');
	});
	
	//条件查询
	
	$("#search").click(function(){
		startPage = 1;
		search();
		searchToPage()
	});
	
	
	//显示所有
	$("#allList").click(function(){
		getProducts();
		toPage();
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
	$("body").on("click",".user_edit",function() { // 编辑
		var _this = $(this);
		var index = layer.open({
			title : "修改用户信息",
			type : 2,
			content : "wxUserEdit/"+ _this.attr("data-id"),
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
	
})