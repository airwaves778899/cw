<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CW</title>

    <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>

    <style>

    </style>

    <script>
    $.ajaxSetup({cache: false});
    $(document).ready(function(){
    	
    });
    
    function addTab(title, href) {
        var tt = $('#main-center');
        if (tt.tabs('exists', title)) {
            tt.tabs('select', title);
        } else {
            if (href) {
                var content = '<iframe scrolling="auto" frameborder="0"  src="' + href + '" style="width:100%;height:100%;"></iframe>';
            } 
            
            tt.tabs('add', {
                title: title,
                closable: true,
                content: content
            });
        }
    }    
    </script>
</head>

<body class="easyui-layout">
        
    <div data-options="region:'west',split:true" title="Navigation" style="width:220px;">
		<ul>
		  <li><a href="#" onclick="addTab('Node-Link Tree Demo','NodeLinkTreeDemo.jsp')">Node-Link Tree Demo</a></li>
		  <li><a href="#" onclick="addTab('Circle Packing Demo','CirclePacking.jsp')">Circle Packing Demo</a></li>
		  <li><a href="#" onclick="addTab('Cluster Dendrogram Demo','ClusterDendrogramDemo.jsp')">Cluster Dendrogram Demo</a></li>
		</ul>
		
		<h3>Getting Started with D3</h3>	
		<ul>
			<li><a href="#" onclick="addTab('Plaza Traffic','gsd3/mean_daily_plaza_traffic.jsp')">Plaza Traffic</a></li>
			<li><a href="#" onclick="addTab('Bus Breakdown Accident And Injury','gsd3/bus_breakdown_accident_and_injury.jsp')">Bus Breakdown Accident And Injury</a></li>
			<li><a href="#" onclick="addTab('Turnstile Traffic','gsd3/turnstile_traffic.jsp')">Turnstile Traffic</a></li>
			<li><a href="#" onclick="addTab('Line Tension','LineTension.jsp')">Line Tension</a></li>
		</ul>

    </div>
        
    <div data-options="region:'center'" style="background: #eee;">
        <div id="main-center" class="easyui-tabs" fit="true" border="false">        
        </div>
    </div>

</body>

</html> 