var enlargedImage=$("<img/>").css({
		 "position": "absolute",
		 "z-index" : 200,"cursor": "pointer",
		 "border": "10px solid rgb(230,230,230)",
		 "margin-left": "auto",
		 "margin-right": "auto"
}).hide();
	
$(document).ready(function() {

$("img.thumbnail").css({
	"cursor": "pointer"
});
$("img.thumbnail").click(function(event){

	 var y = event.pageY;
	 var x = event.pageX;
	 enlargedImage.attr("src",$(this).attr("src")).css({
	 "left": x-225,
	 "top" : y-200,"width": 450,
	 "height": 400
	}).hide().appendTo("body").fadeIn(500).one('click', function() {
		enlargedImage.remove();
	});
	 event.preventDefault();
	 
});
});

var positionThumbnail = function(event) {
		var tPosX = event.pageX - 5;
		var tPosY = event.pageY + 20;
		$(this).children().css({'position': 'absolute',
		'z-index': 8,'top': tPosY, 'left': tPosX});		
};

var hideThumbnail = function() {
		$(this).children().hide();
};
		
var showThumbnail = function(event) {
		$(this).children().show();
		positionThumbnail(event);
};

$(document).ready(function() {
	
		$("a.template_name").children().hide();
		$("a.template_name").hover(showThumbnail, hideThumbnail)
		.mousemove(positionThumbnail);
	
});
