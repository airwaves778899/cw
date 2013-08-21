var pubData = null;


var authorData = [];
var keywordData = [];
var venueData = [];

var minYearFilter;
var maxYearFilter;

function authorsLoaded(data) {
	data = JSON.parse(data);
	for (x in data) {
		var author = data[x];
		author.name = author.last_name;
		authorData[author.id] = author;
	}
}

function keywordsLoaded(data) {
	data = JSON.parse(data);
	// Index keyword array by keyword id
	for (i in data) {
		keywordObj = data[i];
		keywordObj.pubs = [];
		keywordObj.min_year = 9999;
		keywordObj.max_year = 0;
		keywordObj.toString = function() {
			return this.keyword;
		};
		keywordData[keywordObj.id] = keywordObj;
	}
}

function venuesLoaded(data) {
	data = JSON.parse(data);
	for(x in data){
		var venue = data[x];
		venueData[venue.id] = venue;
	}
}

function pubsLoaded(data) {
	pubData = JSON.parse(data);	

	for (x in pubData) {		
		var pub = pubData[x];
		
		// Process authors
		var authorURLs = pub.authors;
		var newAuthors = [];
		for (y in authorURLs) {
			var authorID = authorURLs[y];
			newAuthors.push(authorData[authorID]);
		}
		pub.authors = newAuthors;
		
		// Process keywords
		var keywordURLs = pub.keywords;
		var newKeywords = [];
		for (y in keywordURLs) {
			var keywordID = keywordURLs[y];
			newKeywords.push(keywordData[keywordID]);
		}
		pub.keywords = newKeywords;

		// Process year
		pub.pub_year = parseInt(pub.pub_year);
	}
	
	processKeywordData();
}

function processKeywordData() {
	for (pi in pubData) {
		pubObj = pubData[pi];
		var year = pubObj.pub_year;
		if (year < min_year) min_year = year;
		if (year > max_year) max_year = year;
		for (ki in pubObj.keywords) {
			var keywordObj = pubObj.keywords[ki];
			keywordObj.pubs.push(pubObj);
			if (year < keywordObj.min_year) keywordObj.min_year = year;
			if (year > keywordObj.max_year) keywordObj.max_year = year;
		}
	}
	
	minYearFilter = min_year;
	maxYearFilter = max_year;

	keywordData.splice(0, 1);  // Remove empty 0-index item
    displayKeywords();
	
	// Sort keywords by min year for display
	keywordData.sort(function(k1, k2) {
		return k1.min_year - k2.min_year;
	});
}

//Generate HTML for keyword filters
function displayKeywords() {
    keywordData.sort();
	var checked;
	var keyword;
	var str = genKeywordEntry(" checked", 'no_keyword_filter', NO_KEYWORD_FILTER, 0);
	for (i in keywordData) {
		var keywordObj = keywordData[i];
		keyword = keywordObj.keyword;
		checked = "";
		str += genKeywordEntry(checked, keyword, keyword, keywordObj.pubs.length);
	}
	
	$("#filter").html(str);	
}

function genKeywordEntry(checked, id, keyword, length) {
	var re = new RegExp(" ", "g");
	var entry = "<input id='" + id.replace(re, "_") + "' class='filter_button' keyword='" + keyword + "' type='radio' name='filter'" + checked + "> ";
	entry += "<a class='filter_link' href='javascript:;'>" + keyword;
	if (length > 0) {
		entry += " <font size='1'>(" + length + ")</font>";
	}
	entry += "</a></input><br>";
	return entry;
}

function updatePubs() {
	
}

function displayPubs() {
	updatePubs();
}


function updateFillerHeight() {
	var content = $("#content");
	var bottom = content.position().top + content.height();
	var filler = $("#vertical_filler");
	var h = bottom - filler.position().top;
	filler.height(h);	
}

function onresize() {
	$("#content").width($("#pagecell").width() - (250 + 20));
	updateFillerHeight();
	redisplayVis();
}