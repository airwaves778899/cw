<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script src="http://d3js.org/d3.v3.min.js"></script>

    <style>
    
    .axis path{
        fill:none;
        stroke: black;
    }
    .axis {
        font-size:8pt;
        font-family:sans-serif;
    }
    .tick {
        fill:none;
        stroke:black;
    }
    circle{
        stroke:black;
        stroke-width:0.5px;
        fill:RoyalBlue;
        opacity:0.6;
    }
    
    
    
    </style>

<script>
$.ajaxSetup({cache: false});

var data = [
            {"collision_with_injury": 3.2, "dist_between_fail": 3924.0, "customer_accident_rate": 2.12}, 
            {"collision_with_injury": 2.8, "dist_between_fail": 3914.0, "customer_accident_rate": 1.3}, 
            {"collision_with_injury": 4.05, "dist_between_fail": 3550.0, "customer_accident_rate": 1.5}, 
            {"collision_with_injury": 4.7, "dist_between_fail": 3625.0, "customer_accident_rate": 0.79}, 
            {"collision_with_injury": 2.0, "dist_between_fail": 3171.0, "customer_accident_rate": 2.8}, 
            {"collision_with_injury": 9.29, "dist_between_fail": 2699.0, "customer_accident_rate": 1.28}, 
            {"collision_with_injury": 9.29, "dist_between_fail": 3008.0, "customer_accident_rate": 1.28}, 
            {"collision_with_injury": 2.3, "dist_between_fail": 2815.0, "customer_accident_rate": 1.74}, 
            {"collision_with_injury": 4.65, "dist_between_fail": 3366.0, "customer_accident_rate": 1.17}, 
            {"collision_with_injury": 5.32, "dist_between_fail": 3667.0, "customer_accident_rate": 0.82}, 
            {"collision_with_injury": 4.1, "dist_between_fail": 4170.0, "customer_accident_rate": 0.8}, 
            {"collision_with_injury": 3.87, "dist_between_fail": 3294.0, "customer_accident_rate": 1.01}, 
            {"collision_with_injury": 3.43, "dist_between_fail": 3595.0, "customer_accident_rate": 1.31}, 
            {"collision_with_injury": 6.23, "dist_between_fail": 4067.0, "customer_accident_rate": 1.54}, 
            {"collision_with_injury": 3.82, "dist_between_fail": 3353.0, "customer_accident_rate": 1.03}, 
            {"collision_with_injury": 6.91, "dist_between_fail": 3743.0, "customer_accident_rate": 1.17}, 
            {"collision_with_injury": 9.31, "dist_between_fail": 3259.0, "customer_accident_rate": 1.36}, 
            {"collision_with_injury": 7.01, "dist_between_fail": 3184.0, "customer_accident_rate": 1.25}, 
            {"collision_with_injury": 7.08, "dist_between_fail": 2813.0, "customer_accident_rate": 1.96}, 
            {"collision_with_injury": 10.3, "dist_between_fail": 3119.0, "customer_accident_rate": 0.63}, 
            {"collision_with_injury": 3.74, "dist_between_fail": 3494.0, "customer_accident_rate": 1.27}, 
            {"collision_with_injury": 4.37, "dist_between_fail": 3426.0, "customer_accident_rate": 1.21}, 
            {"collision_with_injury": 5.22, "dist_between_fail": 3415.0, "customer_accident_rate": 1.76}, 
            {"collision_with_injury": 3.98, "dist_between_fail": 3585.0, "customer_accident_rate": 1.17}, 
            {"collision_with_injury": 6.17, "dist_between_fail": 3162.0, "customer_accident_rate": 1.01}, 
            {"collision_with_injury": 1.92, "dist_between_fail": 3047.0, "customer_accident_rate": 1.02}, 
            {"collision_with_injury": 5.15, "dist_between_fail": 3650.0, "customer_accident_rate": 0.83}];
            
var margin = 50,
	width = 700,
	height = 300;

//the maximum and minimum values of the data, using d3.extent:
var x_extent = d3.extent(data, function(d){return d.collision_with_injury;});
var y_extent = d3.extent(data, function(d){return d.dist_between_fail;});

alert(x_extent);
alert(y_extent);
    
var x_scale = d3.scale.linear()
                .range([margin,width-margin])
                .domain(x_extent)

var y_scale = d3.scale.linear()
                .range([height-margin, margin])
                .domain(y_extent)
	
$(document).ready(function(){
	d3.select("#data")
	  .append("svg")
	    .attr("width", width)
	    .attr("height", height)
	  .selectAll("circle")
	  .data(data)
	  .enter()
	  .append("circle")
	    .attr('cx', function(d){return x_scale(d.collision_with_injury)})
        .attr('cy', function(d){return y_scale(d.dist_between_fail)})
        .attr('r', 5);
});

</script>

</head>
<body>
    <div id="data"></div>
</body>
</html>