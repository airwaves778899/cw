var svg;
var svg_w;
var svg_h;
var bars_h = 300;
var min_year = 9999;
var max_year = 0;
var barMinYearX;
var barMaxYearX;

var xAxis;

var textSize = "large";
var displayExtras = true;

function createVis(height, requestedTextSize, shouldDisplayExtras) {
	displayExtras = shouldDisplayExtras;
	textSize = requestedTextSize;
	
	var vis = $("#vis");
	vis.html("");
	svg_w = vis.width();
	svg_h = height;
	
	svg = d3.select("#vis").append("svg:svg")
			.attr("id", "svg")
		    .attr("width", svg_w)
		    .attr("height", svg_h);
	
	svg.append("svg:rect")
		.attr("width", svg_w)
		.attr("height", svg_h)
		.style("fill", "white")
		.on("click", function() {
			//changeAuthorFilter(null);
		});
	
	if (displayExtras) {
		$("#svg").parent().prepend("<button style='position:absolute; left:10px' onclick='clearFilters()'>Reset</button>");
	    
		xAxis = d3.svg.axis()
				  .tickSize(20, 0, 4)
				  .tickSubdivide(0)
				  .tickFormat(d3.format("d"));
		
		svg.append("svg:g")
		   .attr("id", "x_axis");
	} else {
		$("#svg").parent().prepend("<button style='position:absolute' onclick='window.location=\"index.html\"'>Full Vis</button>");
	}
	
	displayBars();
}

function displayBars() {
	var x = d3.scale.linear()
	          .domain([min_year, max_year+1])
	          .rangeRound([270, svg_w-40]);

	var y = d3.scale.ordinal()
	          .domain(keywordData)
	          .rangeBands([40, bars_h], 0.1);
	
	var barHeight = y.rangeBand();
	barxscale = x;

	// Bar Selection
	bars = svg.selectAll("g.keyword")
	          .data(keywordData);
	
	// Groups
	groups = bars.enter().append("svg:g")
	             .attr("class", "keyword")
	             .attr("transform", function(d) {
	            	 var dy = y(d.keyword);
	            	 return "translate(" + 0 + "," + dy + ")";
	             });
	
	// Create bar background rectangles
	groups.append("svg:rect")
		  .attr("class", "key_bgnd")
		  .style("stroke-width", 3);

	// Update bar rectangles
	bars.selectAll(".key_bgnd")
		.attr("x", function(d) { return x(d.min_year - 0.1); })
		.attr("width", function (d) { return x(d.max_year + 1.2) - x(d.min_year); })
		.attr("height", barHeight);
	
	// Create bar labels
	groups.append("svg:text")
		.attr("class", "key_label")
		.attr("y", function(d) { return 0; })
		.attr("dx", 5)
		.attr("dy", 14)
		.attr("text-anchor", "start")
		.text(function(d) { return d.keyword + " (" + d.pubs.length + ")"; });
	// Update bar labels
	bars.selectAll(".key_label")
		.attr("x", function(d) { return x(d.min_year - 0.05); });
	
	// Create bar foreground rectangles
	groups.append("svg:rect")
		  .attr("class", "key_fgnd");
	groups.append("svg:title")
		  .attr("class", "key_title")
		  .text(function(d) { return d.keyword + " (" + d.pubs.length + ")"; });
	
	// Update bar rectangles
	bars.selectAll(".key_fgnd")
		.attr("x", function(d) { return x(d.min_year - 0.1); })
		.attr("width", function (d) { return x(d.max_year + 1.2) - x(d.min_year); })
		.attr("height", barHeight)
		.style("opacity", 0);
		//.on("mouseover", barMouseoverHandler)
		//.on("mouseout", barMouseoutHandler)
		//.on("click", barClickHandler);
	
	// Create bar dots
	dots = groups.selectAll("g.key_dot")
		         .data(function(d) { return d.pubs; });
	
	dots.enter().append("svg:circle")
		.attr("class", "key_dot")
		.attr("r", 6)
		.attr("cy", function(d, i) { return Math.floor(barHeight / 2) + 1; });
		//.on("mouseover", dotmouseover)
		//.on("mouseout", dotmouseout)
		//.on("click", dotclick);
	
	// Update bar dots
	bars.selectAll(".key_dot")
		.attr("display", function(d) { return d.display; })
		.attr("cx", function(d) { 
			var month_offset = 0;
			if (d.pub_month != "") {
				month_offset = (d.pub_month - 1) / 12;
			}
			return x(d.pub_year + month_offset);
		});
	
	// Update the x-axis
	xAxis.scale(x);
	svg.select("#x_axis")
		.call(xAxis);
	
	// Year range slider
	svg.selectAll("#year_bar")
		.data([0])
		.enter()
		.append("svg:rect")
		.attr("id", "year_bar")
		.attr("x", 0)
		.attr("y", 4)
		.attr("width", 0)
		.attr("height", 10);
	svg.selectAll(".year_button")
		.data([minYearFilter, maxYearFilter+1])
		.enter()
		.append("svg:path")
		.attr("class", "year_button")
		.attr("d", "M-7,4L-7,15L0,22L7,15L7,4L-7,4")
		.attr("id", function(d, i) { return i == 0 ? "min_year" : "max_year"; })
		.attr("selected", "false")
		.attr("y", 4);
		//.on("mouseover", yearMouseoverHandler)
		//.on("mouseout", yearMouseoutHandler)
		//.on("mousedown", yearMousedownHandler);
	yearSetX(0, x(minYearFilter));
	yearSetX(1, x(maxYearFilter+1));
}

function yearSetX(i, x) {
	if (i == 0) {
		barMinYearX = x;
		var btn = d3.select("#min_year");
	} else {
		barMaxYearX = x;
		var btn = d3.select("#max_year");
	}
	btn.attr("transform", function(d) { return "translate(" + x + ",0)"; });

	if ((barMinYearX != undefined) && (barMaxYearX != undefined)) {
		d3.select("#year_bar")
			.attr("x", barMinYearX+7)
			.attr("width", (barMaxYearX - barMinYearX - 13));
	}
}

function redisplayVis() {
	svg_w = $("#vis").width();
	//alert( svg_w );
}