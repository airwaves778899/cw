<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style>

.node circle {
  fill: #fff;
  stroke: steelblue;
  stroke-width: 1.5px;
}

.node {
  font: 16px sans-serif;
}

.link {
  fill: none;
  stroke: #ccc;
  stroke-width: 1.5px;
}

</style>

<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script src="http://d3js.org/d3.v3.min.js"></script>

<script>
$.ajaxSetup({cache: false});

$(document).ready(function(){
	//載入大分類
	$.get('queryData/queryAllMasterChannels.do', function(list) {
		$("#masterChannelSelect option").remove();
		$("#masterChannelSelect").append('<option value=""></option>');
		
		$.each(list, function(index, masterChannel){
			$("#masterChannelSelect").append('<option value="'+masterChannel.id+'">'+masterChannel.name+'</option>');
		});		
	});
	
	//載入次分類
	$("#masterChannelSelect").change(function(e){
		$("#subChannelSelect option").remove();	
		
		$.get('queryData/querySubChannels.do?masterChannelId='+$("#masterChannelSelect").val(), function(list) {			
			
			$("#subChannelSelect").append('<option value=""></option>');
			
			$.each(list, function(index, subChannel){
				$("#subChannelSelect").append('<option value="'+subChannel.id+'">'+subChannel.name+'</option>');
			});
		});
	});
	
	$("#query").click(function(e){
		if( $("#masterChannelSelect").val() ){
			$.get('queryData/queryJasonData.do?masterChannelId='+$("#masterChannelSelect").val(), function(data) {
				draw( data );
			});
		}
	});
});

</script>

<script>

function draw(data){

	var diameter = 1000;
	
	var tree = d3.layout.tree()
	    .size([360, diameter / 2 - 120])
	    .separation(function(a, b) { return (a.parent == b.parent ? 1 : 2) / a.depth; });

	var diagonal = d3.svg.diagonal.radial()
	    .projection(function(d) { return [d.y, d.x / 180 * Math.PI]; });

	$("svg").remove();
	var svg = d3.select("#drawdiv").append("svg")
	    .attr("width", diameter)
	    .attr("height", diameter - 150)
	    .append("g")
	    .attr("transform", "translate(" + diameter / 2 + "," + diameter / 2 + ")");
	
    var nodes = tree.nodes(data),
        links = tree.links(nodes);

    var link = svg.selectAll(".link")
        .data(links)
        .enter().append("path")
        .attr("class", "link")
        .attr("d", diagonal);

    var node = svg.selectAll(".node")
        .data(nodes)
        .enter().append("g")
        .attr("class", "node")
        .attr("transform", function(d) { return "rotate(" + (d.x - 90) + ")translate(" + d.y + ")"; })

    node.append("circle")
        .attr("r", 4.5);

    node.append("text")
        .attr("dy", ".31em")
        .attr("text-anchor", function(d) { return d.x < 180 ? "start" : "end"; })
        .attr("transform", function(d) { return d.x < 180 ? "translate(8)" : "rotate(180)translate(-8)"; })
        .text(function(d) { return d.name; });


    d3.select(self.frameElement).style("height", diameter - 150 + "px");

}
</script>

</head>
<body>

<div>
    <button id="query">QUERY</button>

	<select id="masterChannelSelect">
	</select>
	
    <select id="subChannelSelect">
	</select>

</div>

<div id="drawdiv">

</div>

</body>
</html>