<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style>

circle {
  fill: rgb(31, 119, 180);
  fill-opacity: .25;
  stroke: rgb(31, 119, 180);
  stroke-width: 1px;
}

.leaf circle {
  fill: #ff7f0e;
  fill-opacity: 1;
}

text {
  font: 10px sans-serif;
}

</style>

<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script src="http://d3js.org/d3.v3.min.js"></script>

<script>
$(document).ready(function(){
	$("#search").click(function(e){
		$.get('<%=request.getContextPath()%>/d3test/jsonTest.jsp', function(data) {
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

	var diameter = $("body").width(),
	    format = d3.format(",d");
	
	var pack = d3.layout.pack()
	    .size([diameter - 4, diameter - 4])
	    .value(function(d) { return d.size; });
	
	$("svg").remove();
	var svg = d3.select("#drawdiv").append("svg")
	    .attr("width", diameter)
	    .attr("height", diameter)
	  .append("g")
	    .attr("transform", "translate(2,2)");
	
	//d3.json("flare.json", function(error, root) {
	  var node = svg.datum(data).selectAll(".node")
	      .data(pack.nodes)
	    .enter().append("g")
	      .attr("class", function(d) { return d.children ? "node" : "leaf node"; })
	      .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
	
	  node.append("title")
	      .text(function(d) { return d.name + (d.children ? "" : ": " + format(d.size)); });
	
	  node.append("circle")
	      .attr("r", function(d) { return d.r; });
	
	  node.filter(function(d) { return !d.children; }).append("text")
	      .attr("dy", ".3em")
	      .style("text-anchor", "middle")
	      .text(function(d) { return d.name.substring(0, d.r / 3); });
	//});
	
	d3.select(self.frameElement).style("height", diameter + "px");

}

</script>

</head>
<body>

<button id="search">Search</button>

<div id="drawdiv"></div>

</body>
</html>