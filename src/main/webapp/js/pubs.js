var pubData = null;


var authorData = [];
var keywordData = [];
var venueData = [];


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
		}
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
		
	}
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