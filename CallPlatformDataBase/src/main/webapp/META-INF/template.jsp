<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="util.SpringUtil" %>
<%@ page import="com.cqut.service.article.ArticleService"%>
<%@page import="com.cqut.cache.DataCacheManager"%>
<%@page import="com.cqut.util.DateUtil"%>
<%@page import="com.cqut.service.permission.ColumnsService"%>
<%@page import="com.cqut.service.system.SystemFileService"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	ArticleService articleService = (ArticleService) SpringUtil.getBeanByName("articleService");//拿到服务
	ColumnsService columnsService = (ColumnsService) SpringUtil.getBeanByName("columnsService");//拿到服务
	DataCacheManager dm = (DataCacheManager)SpringUtil.getBeanByName("dataCacheManager");
	
	String parentCode = request.getParameter("parentCode") + "";
	String columnCode = request.getParameter("columnCode") + "";
	String curPageStr = request.getParameter("curPage") + "";
	////
	String articleID = request.getParameter("articleID") + "";
	String totalPagesStr = "";
	String columnType = "";	
	if(parentCode.equals("") || parentCode.equals("null")/* || columnCode.equals("") || columnCode.equals("null")*/){//栏目不存在
		response.sendRedirect( basePath + "error.jsp");
		return;
	}
	
	List<Map<String,Object>> topColumns = (List<Map<String,Object>>) dm.getPageData("base","topColumns");//获得一级栏目
	Map<String,Object> parentInfo = null;
	for(Map<String,Object> m : topColumns){//找一级模块信息
		if((m.get("columnCode") + "").equals(parentCode)){
			parentInfo = m;
			break;
		}
	}
	
	////
	if(parentInfo == null){//一级模块不存在
		response.sendRedirect( basePath + "error.jsp");
		return;		
	}
	
	List<Map<String,Object>> columns = (List<Map<String,Object>>) dm.getPageData("base",parentCode);//根据parentCode获得所有子模块
	Map<String,Object> columnInfo = null;
	
	for(Map<String,Object> m : columns){//找子模块信息
		if((m.get("columnCode") + "").equals(columnCode)){
			columnInfo = m;
			break;
		}
	}
	
	if(columnInfo == null){
		columnInfo = columns.get(0);
		columnCode = columnInfo.get("columnCode") + "";
	}
	
	
	if(columnInfo == null){//子模块不存在，返回错误页面
		response.sendRedirect( basePath + "error.jsp");
		return;	
	}
	
	columnType = columnInfo.get("columnType") + "";//获得栏目类型 0:基础   1：文本  2:列表    3:图片    4:其他 
	
	Map<String,Object> article = null;//单个文章
	List<Map<String,Object>> articles = null;//文章列表
	List<Map<String,Object>> pictures = null;//图片列表
	
	int curPage = 1;
	int limit = 15;
	int totalPages = 0;
	
	if(curPageStr.equals("") || curPageStr.equals("null")){
		curPage =1;
	}else{
		try{
			curPage = Integer.parseInt(curPageStr);
		}catch(NumberFormatException e){
			curPage = 1;
		}
	}
	
	if(!articleID.equals("null") && !articleID.equals("")){//有文章ID，优先显示文章。
		article = articleService.getArticleById(articleID);
		if(article == null){
			response.sendRedirect( basePath + "error.jsp");
			return;		
		}
	}else if(columnType.equals("0")){//基础
		
	}else if(columnType.equals("1")){//文本
		article = articleService.getArticleByColumnCode(columnCode);
		if(article == null){
			//response.sendRedirect( basePath + "error.jsp");
			//return;		
		}
	}else if(columnType.equals("2")){//列表
		articles = articleService.getArticlesByColumnCode(columnCode,curPage,limit);
		if(articles != null && articles.size() > 0){
			try{
				totalPages = Integer.parseInt(articles.get(articles.size() - 1).get("totalPages") + "");
			}catch(Exception e){
				totalPages = 0;
			}
		}
	}else if(columnType.equals("3")){//图片
		pictures = articleService.getPicturesByColumnCode(columnCode,curPage,limit);
		if(pictures != null && pictures.size() > 0){
			try{
				totalPages = Integer.parseInt(pictures.get(pictures.size() - 1).get("totalPages") + "");
			}catch(Exception e){
				totalPages = 0;
			}
		}
	}else if(columnType.equals("4")){//其他
		//out.println("页面跳转中......");
		//out.println("<script>window.location.href='" + columnInfo.get("columnURL") + "';</script>");
		//return;
	}else{//类型不匹配
		response.sendRedirect( basePath + "error.jsp");
		return;		
	}
	
%>
	<link rel="stylesheet" type="text/css" href="css/base/template.css"/>
<div class="middle">
	<div class="middle_left">
		<div class="middle_left_search">
			<div class="middle_left_search_all">
				<img class="middle_left_search_control" src="images/enterprisesEntered/search_ico.png"/>
				<input id="div_text" type="text" value="请输入搜索内容"/>
				<a id="search" href="javascript:void(0);"><img id="search_img" src="images/enterprisesEntered/search_bt.png"/></a>
			</div>
		</div>
		<div class="middle_left_main" >
			<div class="middle_left_main_title"><p id="font_H"><%=parentInfo.get("columnName") %></div>
				 <div class="middle_left_main_body">
					<ul>
						<%int len = columns.size();%>
						<%for(int i = 0;i < len ; i++ ) {%>
							<%Map<String,Object> m = columns.get(i); %>
							<%if(!(m.get("columnType") + "").equals("4")){%>
								<li><a id="a_intro" href="<%=(m.get("columnType") + "").equals("0") ? (m.get("columnURL") + "?parentCode=" +parentInfo.get("columnCode") + "&") : parentInfo.get("columnURL") + "?" %>columnCode=<%=m.get("columnCode") %>"><span><%=m.get("columnName") %></span></a></li>
							<%} else{%>
								<li><a id="a_intro" href="<%=m.get("columnURL") %>"><span><%=m.get("columnName") %></span></a></li>
							<%} %>
						<%} %>
					</ul>
				</div>
				<div style="clear: both;"></div>
			</div>
		</div>
		
		<div class="middle_right">
			<div class="middle_right_top">
				您当前的位置：<a id="home" href="index.jsp">首页</a>&nbsp;><span style="margin-left:-5px;">></span>&nbsp;<a id="enterpeise" href="<%=parentInfo.get("columnURL") + "" %>"><%=parentInfo.get("columnName") %></a>&nbsp;><span style="margin-left:-5px;">></span>&nbsp;<a id="intro" href="<%=parentInfo.get("columnURL") + "?columnCode=" + columnInfo.get("columnCode") %>"><%=columnInfo.get("columnName")%></a>
				<%--
					<%if(!articleID.equals("null") && !articleID.equals("")) {%>
						&nbsp;><span style="margin-left:-5px;">></span>&nbsp;<a id="enterpeise" href="javascript:void(0);"><%=(article == null ? "" : article.get("me_title") )%></a>
					<%} %>
				--%>
			</div>
			<div class="middle_right_main">
				<div class="middle_right_main_title">
					<img id="img_arrow" src="images/enterprisesEntered/arrow.png"/>
					<p id="p_intro"><%=columnInfo.get("columnName")%></p>
				</div>
				<div class="middle_right_main_body">
					<div class="news" id="list_div">
						<% if(!articleID.equals("null") && !articleID.equals("")){%>
							<div id="news-content"><%=article == null ? "暂无数据." : article.get("me_content")%></div>
							<%if(article != null && (article.get("me_hasAttachment") + "").equals("true")){%>
					                <fieldset class="fileArea">
					                	<le gend>附件:</legend>
					                	<%
					                		SystemFileService fileService =(SystemFileService) SpringUtil.getBeanByName("systemFileService");
					                		String[] pros = {"id","fileName","fileSize"};
					                		List<Map<String,Object>> files = fileService.findMapByPropertiesQuick(pros,"ownerCode='" + articleID + "'",false);
					                	%>
					                		<%if(files == null || files.size() == 0) {%>
					                			<span>没有附件.</span>
					                		<%}else{ %>
					                			<ul id="filesList">
						                			<%for(Map<String,Object> m : files){ %>
						                				<li class="item"><a href="common/file.action?fileId=<%=m.get("id") %>" title="点击下载:<%=m.get("fileName") %>" target="_blank"><%=m.get("fileName") %></a></li>
						                			<%} %>
					                			</ul>
					                		<%} %>
					                </fieldset>
				                <%} %>
						<%
							}else if(columnType.equals("0")){//基础
						%>
							
						<%
							}else if(columnType.equals("1")){//文本
						%>		
								<div id="news-content"><%=article == null ? "暂无数据." : article.get("me_content")%>
									<%if(article != null && (article.get("me_hasAttachment") + "").equals("true")){%>
							                <fieldset class="fileArea">
							                	<le gend>附件:</legend>
							                	<%
							                		SystemFileService fileService =(SystemFileService) SpringUtil.getBeanByName("systemFileService");
							                		String[] pros = {"id","fileName","fileSize"};
							                		List<Map<String,Object>> files = fileService.findMapByPropertiesQuick(pros,"ownerCode='" + (article.get("me_ID") + "") + "'",false);
							                	%>
							                		<%if(files == null || files.size() == 0) {%>
							                			<span>没有附件.</span>
							                		<%}else{ %>
							                			<ul id="filesList">
								                			<%for(Map<String,Object> m : files){ %>
								                				<li class="item"><a href="common/file.action?fileId=<%=m.get("id") %>" title="点击下载:<%=m.get("fileName") %>" target="_blank"><%=m.get("fileName") %></a></li>
								                			<%} %>
							                			</ul>
							                		<%} %>
							                </fieldset>
						                <%} %>
								</div>
						<%
							}else if(columnType.equals("2")){//列表
						%>
								<ul class='news-list'>
									<%if(null != articles && articles.size() > 0) {%>
										<%len = articles.size(); 
										 String _path = request.getServletPath().replace("/","");
										%>
										<%for(int i = 0 ; i < len ; i ++) {%>
											<%if(i == len - 1){break;} %>
											<%Map<String,Object> m = articles.get(i); %>
											<li>
												<a href="<%=_path %>?columnCode=<%=columnCode %>&articleID=<%=m.get("ID") %>"><img class='list_bg_position' src='images/module/gardenDynamic/list_icon.png'/><%=m.get("title") %></a>
												<%=(m.get("isTop") + "").equals("true") ? "<img style=\"position: absolute;z-index:100;top:4px;\" src=\"images/top.gif\" alt=\"置顶\"/>" : "" %>
												<span class="news-time"><%=DateUtil.dateFormat((Date)m.get("modifyDate"),null) %></span>
											</li>
										<%} %>
									<%}else{ %>
										<li>暂无数据.</li>
									<%} %>
								</ul>
						<%
							}else if(columnType.equals("3")){//图片
						%>
									<%if(null != pictures && pictures.size() > 0) {%>
										<%len = pictures.size(); 
										String _path = request.getServletPath().replace("/","");
										%>
										<%for(int i = 0 ; i < len ; i ++) {%>
											<%if(i == len - 1){break;} %>
											<%Map<String,Object> m = pictures.get(i); %>
											<div class="pic-item">
												<a title="<%=m.get("title") %>" href="<%=_path %>?columnCode=<%=columnCode %>&articleID=<%=m.get("ID") %>" ><img alt="<%=m.get("title") %>" src="common/image.action?imgId=<%=m.get("picture") %>" />
												<span><%=m.get("title") %></span></a>
											</div>
										<%} %>
									<%}else{ %>
										暂无数据.
									<%} %>
						<%
							}else if(columnType.equals("4")){//其他
								response.sendRedirect("www.baidu.com");
						%>
							<a href="<%=columnInfo.get("columnURL") %>"><%=columnInfo.get("columnName") %></a>
						<%		
							}
						%>
						<div style="clear: both;"></div>
												<div style="width:100%;margin-bottom:10px;height:35px;"><div id="pager"></div></div>
					</div>
					
				</div>
				
			</div>
		</div>
</div>
<div style="clear: both;"></div>
<script type="text/javascript">
	$(document).ready(function(){
		clickSearch();
		<%if(!articleID.equals("null") && !articleID.equals("")) {%>
			document.title="<%=(article == null ? "" : article.get("me_title") )%>";
		<%} %>;
	});
		//点击搜索
	function clickSearch(){
		$("#search_img").click(function(){
			searchComment();						
		});	
	
		$("#div_text").click(function(){
			if($("#div_text").val() == "请输入搜索内容"){
				$("#div_text").val("");
				$("#div_text").css("color","#000000");
			}							
		});	
		
		$("#div_text").keyup(function(){
	        if(event.keyCode == 13){
	            //这里写你要执行的事件;
	            searchComment();
	        }
	    });
	}
	function searchComment() {
		var searchCondition = $("#div_text").val();
		if(searchCondition == "" || searchCondition == "请输入搜索内容"){
			alert("请输入搜索内容");
		}else if(searchCondition.trim() == ""){
			alert("您输入的搜索内容不能为空");
		}else{
			window.location.href="allSearch.jsp?searchCondition="+searchCondition;
		}
	}
	String.prototype.trim=function() {
					return this.replace(/(^\s*)|(\s*$)/g,'');
	}
</script>
<%if((articleID.equals("null") || articleID.equals("")) && (columnType.equals("2") || columnType.equals("3"))) {%>
	<script type="text/javascript" src="js/plugins/pager.js"></script>
	<script type="text/javascript">
	
	var pageInfo = {totalPages : '<%=totalPages%>',curPage : '<%=curPage%>' , path : window.location.href};
	pageInfo.path = pageInfo.path.replace(/&curPage=(\w)*/ig,"");
	$(document).ready(function(){
		$("#pager").pager( {
			pagenumber : pageInfo.curPage,
			pagecount : pageInfo.totalPages,
			buttonClickCallback : PageClick
		});
	});
	
	PageClick = function(pageclickednumber) {
		$("#pager").pager({
			pagenumber : pageclickednumber,
			pagecount :pageInfo.totalPages,
			buttonClickCallback : PageClick
		});
		pageInfo.path += "?&curPage=" + parseInt(pageclickednumber);
		window.location.href = pageInfo.path;
	}
	</script>
<%} %>

