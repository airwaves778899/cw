<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script src="http://d3js.org/d3.v3.min.js"></script>

<style>
    .axis {
        font-family: arial;
        font-size:0.6em;
    }
    path {
        fill:none;
        stroke:black;
        stroke-width:2px;
    }
    .tick {
        fill:none;
        stroke:black;
    }
    circle{
        stroke:black;
        stroke-width:0.5px;
    }
    circle.times_square{
        fill:DeepPink;
    }
    circle.grand_central{
        fill:MediumSeaGreen;
    }
    path.times_square{
        stroke:DeepPink;
    }
    path.grand_central{
        stroke:MediumSeaGreen;
    }

</style>

<script>
$.ajaxSetup({cache: false});

var data = {
		    "times_square": 
	          [
	           {"count": 36.333333333333336, "time": 1328356800000}, 
	           {"count": 87.361111111111114, "time": 1328371200000}, 
	           {"count": 216.22222222222223, "time": 1328385600000}, 
	           {"count": 418.80555555555554, "time": 1328400000000}, 
	           {"count": 351.11111111111109, "time": 1328414400000}, 
	           {"count": 213.94444444444446, "time": 1328428800000}, 
	           {"count": 29.361111111111111, "time": 1328443200000}, 
	           {"count": 69.444444444444443, "time": 1328457600000}, 
	           {"count": 152.41666666666666, "time": 1328472000000}, 
	           {"count": 291.80555555555554, "time": 1328486400000}, 
	           {"count": 205.44444444444446, "time": 1328500800000}, 
	           {"count": 98.388888888888886, "time": 1328515200000}, 
	           {"count": 34.777777777777779, "time": 1328529600000}, 
	           {"count": 245.33333333333334, "time": 1328544000000}, 
	           {"count": 274.61111111111109, "time": 1328558400000}, 
	           {"count": 758.47222222222217, "time": 1328572800000}, 
	           {"count": 391.97222222222223, "time": 1328587200000}, 
	           {"count": 88.666666666666671, "time": 1328601600000}, 
	           {"count": 39.833333333333336, "time": 1328616000000}, 
	           {"count": 20.0, "time": 1328618993000}, 
	           {"count": 140.0, "time": 1328630400000}, 
	           {"count": 328.94444444444446, "time": 1328644800000}, 
	           {"count": 788.86111111111109, "time": 1328659200000}, 
	           {"count": 441.77777777777777, "time": 1328673600000}, 
	           {"count": 101.33333333333333, "time": 1328688000000}, 
	           {"count": 37.388888888888886, "time": 1328702400000}, 
	           {"count": 264.27777777777777, "time": 1328716800000}, 
	           {"count": 293.75, "time": 1328731200000}, 
	           {"count": 794.33333333333337, "time": 1328745600000}, 
	           {"count": 434.38888888888891, "time": 1328760000000}, 
	           {"count": 104.02777777777777, "time": 1328774400000}, 
	           {"count": 36.694444444444443, "time": 1328788800000}, 
	           {"count": 253.97222222222223, "time": 1328803200000}, 
	           {"count": 329.5625, "time": 1328817600000}, 
	           {"count": 70.0, "time": 1328832000000}, 
	           {"count": 467.33333333333331, "time": 1328846400000}, 
	           {"count": 120.86111111111111, "time": 1328860800000}, 
	           {"count": 39.138888888888886, "time": 1328875200000}, 
	           {"count": 249.66666666666666, "time": 1328889600000}, 
	           {"count": 334.30555555555554, "time": 1328904000000}, 
	           {"count": 815.86111111111109, "time": 1328918400000}, 
	           {"count": 475.05555555555554, "time": 1328932800000}
	          ], 
	        "grand_central": 
	          [
	           {"count": 22.842105263157894, "time": 1328356800000}, 
	           {"count": 143.57894736842104, "time": 1328371200000}, 
	           {"count": 329.94736842105266, "time": 1328385600000}, 
	           {"count": 411.57894736842104, "time": 1328400000000}, 
	           {"count": 255.42105263157896, "time": 1328414400000}, 
	           {"count": 89.578947368421055, "time": 1328428800000}, 
	           {"count": 14.368421052631579, "time": 1328443200000}, 
	           {"count": 99.84210526315789, "time": 1328457600000}, 
	           {"count": 220.31578947368422, "time": 1328472000000}, 
	           {"count": 301.73684210526318, "time": 1328486400000},
	           {"count": 141.89473684210526, "time": 1328500800000}, 
	           {"count": 75.0, "time": 1328515200000}, 
	           {"count": 56.315789473684212, "time": 1328529600000}, 
	           {"count": 606.66666666666663, "time": 1328538796000}, 
	           {"count": 196.58333333333334, "time": 1328544000000}, 
	           {"count": 405.21052631578948, "time": 1328558400000}, 
	           {"count": 27.857142857142858, "time": 1328560024000}, 
	           {"count": 683.71428571428567, "time": 1328572800000}, 
	           {"count": 492.21052631578948, "time": 1328587200000}, 
	           {"count": 73.0, "time": 1328601600000}, 
	           {"count": 72.578947368421055, "time": 1328616000000}, 
	           {"count": 767.68421052631584, "time": 1328630400000}, 
	           {"count": 467.21052631578948, "time": 1328644800000}, 
	           {"count": 1003.578947368421, "time": 1328659200000}, 
	           {"count": 501.63157894736844, "time": 1328673600000}, 
	           {"count": 73.421052631578945, "time": 1328688000000}, 
	           {"count": 62.157894736842103, "time": 1328702400000}, 
	           {"count": 531.0, "time": 1328716800000}, 
	           {"count": 405.68421052631578, "time": 1328731200000}, 
	           {"count": 1038.3157894736842, "time": 1328745600000}, 
	           {"count": 511.31578947368422, "time": 1328760000000}, 
	           {"count": 79.15789473684211, "time": 1328774400000}, 
	           {"count": 63.05263157894737, "time": 1328788800000}, 
	           {"count": 536.68421052631584, "time": 1328803200000}, 
	           {"count": 458.21052631578948, "time": 1328817600000}, 
	           {"count": 1025.2105263157894, "time": 1328832000000}, 
	           {"count": 561.84210526315792, "time": 1328846400000}, 
	           {"count": 101.89473684210526, "time": 1328860800000}, 
	           {"count": 58.789473684210527, "time": 1328875200000}, 
	           {"count": 481.31578947368422, "time": 1328889600000}, 
	           {"count": 479.89473684210526, "time": 1328904000000}, 
	           {"count": 1072.421052631579, "time": 1328918400000}, 
	           {"count": 508.84210526315792, "time": 1328932800000}
	        ]
	      };

var margin = 60,
width = 800 - margin,
height = 400 - margin;

var count_extent = d3.extent(
        data.times_square.concat(data.grand_central),
        function(d){return d.count;}
        );
        
var time_extent = d3.extent(
        data.times_square.concat(data.grand_central),
        function(d){return d.time;}
    );

//Y座標軸刻度
var count_scale = d3.scale.linear()
	.domain(count_extent)
	.range([height, margin]);

//X座標軸刻度
var time_scale = d3.time.scale()
	.domain(time_extent)
	.range([margin, width]);

//X座標軸範圍
var time_axis = d3.svg.axis()
	.scale(time_scale);

//Y座標軸範圍
var count_axis = d3.svg.axis()
    .scale(count_scale)
    .orient("left");

var line = d3.svg.line()
    .x(function(d){return time_scale(d.time);})
    .y(function(d){return count_scale(d.count);})
    .interpolate("linear");

$(document).ready(function(){
	d3.select("#data")
	  .append("svg")	    
	    .attr("width", width+margin)
        .attr("height", height+margin)
        .attr("class", "char");
	
	d3.select("svg")
	  .selectAll("circle.times_square")
	  .data(data.times_square)
      .enter()
      .append("circle")
         .attr("class", "times_square");
	
	d3.select("svg")
	  .selectAll("circle.grand_central")
	  .data(data.grand_central)
	  .enter()
	  .append("circle")
	    .attr("class", "grand_central");
	
	d3.selectAll("circle")
	  .attr("cy", function(d) {return count_scale(d.count);} )
	  .attr("cx", function(d){return time_scale(d.time);})
	  .attr("r", 3);
	
	//X座標軸
	d3.select("svg")
	  .append("g")
	    .attr("class", "x axis")
	    .attr("transform", "translate(0," + height + ")")
	  .call(time_axis);
	
	//Y座標軸
	d3.select("svg")
	  .append("g")
	    .attr("class", "y axis")
	    .attr("transform", "translate(" + margin + ",0)")
	  .call(count_axis);
	
	//X座標軸文字
	d3.select('.x.axis')
	  .append('text')
	    .text('時間')
	    .attr('x', function(){return (width / 1.6) - margin;})
	    .attr('y', margin/1.5)
	    .style("font-size", "150%");
	
	//Y座標軸文字
	d3.select('.y.axis')
	  .append('text')
	    .text('mean number of turnstile revolutions')
	    .attr('transform', "rotate (90, " + -margin + ", 0)")
	    .attr('x', 20)
        .attr('y', 0)
        .style("font-size", "150%");
	  
	
	d3.select('svg')
	  .append('path')
	  .attr('d', line(data.times_square))
	  .attr('class', 'times_square');
	
	d3.select('svg')
	  .append('path')
	  .attr('d', line(data.grand_central))
	  .attr('class', 'grand_central');
	  
});
</script>

</head>
<body>
    <div id="data"></div>
</body>
</html>