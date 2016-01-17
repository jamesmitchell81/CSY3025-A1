<!DOCTYPE html>
<meta charset="utf-8">
<head>
<style>

svg {
  border: 1px solid #000;
}

</style>
</head>
<body>

<div id="body">
</div>

<script src="http://d3js.org/d3.v3.min.js"></script>
<script>

var width = 1000,
    height = 1000;


var body = d3.select("#body").append("svg")
             .attr("width", width)
             .attr("height", height);

d3.select("svg").append("line")
                .attr("x1", 500).attr("x2", 500)
                .attr("y1", 0).attr("y2", 1000)
                .attr("stroke", "steelblue")
                .attr("stroke-dasharray", "3, 3");

d3.select("svg").append("line")
                .attr("x1", 0).attr("x2", 1000)
                .attr("y1", 500).attr("y2", 500)
                .attr("stroke", "steelblue")
                .attr("stroke-dasharray", "3, 3");

d3.json("birthplaces.json", function(json) {


console.log(json);

for ( var i = 0; i < json[0].length; i++ ) {

  var lon = json[0][i].long;
  var lat = json[0][i].lat;

  if ( (lon !== 0 ) || ( lat !== 0 )) {
    var x = lon + 500;
    var y = 500 - (lat * (1000 / 180));

    console.log(x, y);

    d3.select("svg")
      .append("circle")
      .attr("r", 2)
      .attr("cx", x)
      .attr("cy", y);
  }
}

});




</script>
</body>