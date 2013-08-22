<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script src="http://d3js.org/d3.v3.min.js"></script>

    <style>
        div.chart{
            font-family:sans-serif;
            font-size:0.7em;
        }
        div.bar {
            background-color:DarkRed;
            color:white;
            height:2em;
            line-height:2em;
            padding-right:1em;
            margin-bottom:2px;
            text-align:right;
            margin-left:0em;
        }
        div.label {
            height:3em;
            line-height:3em;
            padding-right:1em;
            margin-bottom:2px;
            float:left;
            width:20em;
            text-align:right;
        }
        
    </style>

<script>
$.ajaxSetup({cache: false});

var data = {
	    "cash": [
	             {
	                 "count": 26774.09756097561, 
	                 "id": 1, 
	                 "name": "Robert F. Kennedy Bridge Bronx Plaza"
	             }, 
	             {
	                 "count": 18612.77618364419, 
	                 "id": 2, 
	                 "name": "Robert F. Kennedy Bridge Manhattan Plaza"
	             }, 
	             {
	                 "count": 31343.0631276901, 
	                 "id": 3, 
	                 "name": "Bronx-Whitestone Bridge"
	             }, 
	             {
	                 "count": 9863.7658045977, 
	                 "id": 4, 
	                 "name": "Henry Hudson Bridge"
	             }, 
	             {
	                 "count": 3805.8350071736013, 
	                 "id": 5, 
	                 "name": "Marine Parkway-Gil Hodges Memorial Bridge"
	             }, 
	             {
	                 "count": 4577.186513629842, 
	                 "id": 6, 
	                 "name": "Cross Bay Veterans Memorial Bridge"
	             }, 
	             {
	                 "count": 13830.994261119082, 
	                 "id": 7, 
	                 "name": "Queens Midtown Tunnel"
	             }, 
	             {
	                 "count": 6900.047345767575, 
	                 "id": 8, 
	                 "name": "Brooklyn-Battery Tunnel"
	             }, 
	             {
	                 "count": 25262.48493543759, 
	                 "id": 9, 
	                 "name": "Throgs Neck Bridge"
	             }, 
	             {
	                 "count": 18275.3543758967, 
	                 "id": 11, 
	                 "name": "Verrazano-Narrows Bridge"
	             }
	         ], 
	         "electronic": [
	             {
	                 "count": 51316.53802008608, 
	                 "id": 1, 
	                 "name": "Robert F. Kennedy Bridge Bronx Plaza"
	             }, 
	             {
	                 "count": 67502.12482065997, 
	                 "id": 2, 
	                 "name": "Robert F. Kennedy Bridge Manhattan Plaza"
	             }, 
	             {
	                 "count": 76906.89383070302, 
	                 "id": 3, 
	                 "name": "Bronx-Whitestone Bridge"
	             }, 
	             {
	                 "count": 51861.5933908046, 
	                 "id": 4, 
	                 "name": "Henry Hudson Bridge"
	             }, 
	             {
	                 "count": 17488.73888091822, 
	                 "id": 5, 
	                 "name": "Marine Parkway-Gil Hodges Memorial Bridge"
	             }, 
	             {
	                 "count": 16056.855093256814, 
	                 "id": 6, 
	                 "name": "Cross Bay Veterans Memorial Bridge"
	             }, 
	             {
	                 "count": 65148.13916786227, 
	                 "id": 7, 
	                 "name": "Queens Midtown Tunnel"
	             }, 
	             {
	                 "count": 38908.203730272595, 
	                 "id": 8, 
	                 "name": "Brooklyn-Battery Tunnel"
	             }, 
	             {
	                 "count": 84575.18794835007, 
	                 "id": 9, 
	                 "name": "Throgs Neck Bridge"
	             }, 
	             {
	                 "count": 74291.31420373028, 
	                 "id": 11, 
	                 "name": "Verrazano-Narrows Bridge"
	             }
	         ]
	     };

$(document).ready(function(){
	console.clear();
	console.log("TEST...");
	console.log(data.cash);
	
	d3.select("#data")
	    .attr("class", "chart")
	  .selectAll("div.bar")
	  .data(data.cash).enter()
	  .append("div")
	    .attr("class", "bar")
	    .style("width", function(d){return d.count/100 + "px"})
	    .style("outline", "1px solid black")
	    .text(function(d) {return Math.round(d.count)} );

	//alert($("#data").html());
});
</script>

</head>
<body>
   <div>DATA : </div>
   <div id="data"></div>
</body>
</html>