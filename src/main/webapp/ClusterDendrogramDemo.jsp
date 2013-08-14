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
$(document).ready(function(){
	$("#search").click(function(e){
		$.get('<%=request.getContextPath()%>/jsonTest.jsp', function(data) {
			draw(data);
			//$.getJSON('./flare.json', function(data) {
			//	draw(data);
			//});
		});
	});
});

</script>

<script>

function draw(data){
//alert(JSON.stringify(data));
	var width = $("#drawWidth").val(),
    height = $("#drawHeight").val();

	var cluster = d3.layout.cluster()
	    .size([height, width - 160]);
	
	var diagonal = d3.svg.diagonal()
	    .projection(function(d) { return [d.y, d.x]; });
	
	$("svg").remove();
	var svg = d3.select("#drawdiv")
	    .append("svg")
	    .attr("width", width)
	    .attr("height", height)
	    .append("g")
	    .attr("transform", "translate(40,0)");
	

	  var nodes = cluster.nodes(data),
	      links = cluster.links(nodes);
	
	  var link = svg.selectAll(".link").data(links)
	      .enter().append("path")
	      .attr("class", "link")
	      .attr("d", diagonal);
	
	  var node = svg.selectAll(".node")
	      .data(nodes)
	      .enter().append("g")
	      .attr("class", "node")
	      .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; })
	
	  node.append("circle")
	      .attr("r", 4.5);
	
	  node.append("text")
	      .attr("dx", function(d) { return d.children ? -8 : 8; })
	      .attr("dy", 3)
	      .style("text-anchor", function(d) { return d.children ? "end" : "start"; })
	      .text(function(d) { return d.name; });

	
	d3.select(self.frameElement).style("height", height + "px");

}
</script>

</head>
<body>

<button id="search">Search</button>
<input type="text" id="drawWidth" value="800"/>
<input type="text" id="drawHeight" value="600"/>

<div id="drawdiv">

</div>

</body>
</html>