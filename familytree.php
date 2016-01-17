<!DOCTYPE html>
<meta charset="utf-8">
<head>
<style>

	.node circle {
	  fill: #fff;
	  stroke: steelblue;
	  stroke-width: 3px;
	}

	.node text { font: 12px sans-serif; }

	.link {
	  fill: none;
	  stroke: #ccc;
	  stroke-width: 2px;
	}



</style>
</head>
<body>

<div id="body">
</div>

<script src="http://d3js.org/d3.v3.min.js"></script>
<script>

// **************
// Reference.
// Adapted from:
// http://bl.ocks.org/d3noob/8324872
// *****************
var margin = {top: 20, right: 120, bottom: 20, left: 120},
	width = 4000 - margin.right - margin.left,
	height = 3000 - margin.top - margin.bottom;

var i = 0;

var tree = d3.layout.tree()
	.size([height, width]);

var diagonal = d3.svg.diagonal()
	.projection(function(d) { return [d.x, d.y]; });

var svg = d3.select("body").append("svg")
	.attr("width", width + margin.right + margin.left)
	.attr("height", height + margin.top + margin.bottom)
    .append("g")
	.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

d3.json("familytree.json", function(json) {

update(json);

})


function update(source) {

  // Compute the new tree layout.
  var nodes = tree.nodes(source),
	    links = tree.links(nodes);

  // Normalize for fixed-depth.
  // nodes.forEach(function(d) { d.y = d.depth * 30; });
  nodes.forEach(function(d) {
    d.y = d.depth * 60;
  });

  // Declare the nodes…
  var node = svg.selectAll("g.node")
	  .data(nodes, function(d) { return d.id || (d.id = ++i); });

  // Enter the nodes.
  var nodeEnter = node.enter().append("g")
	                     .attr("class", "node")
	                     .attr("transform", function(d) {
                          return "translate(" + d.x + "," + d.y + ")";
                        });

  nodeEnter.append("circle")
	       .attr("r", function(d) {
          var radius = (d.age * 0.3)
          return d.age > 100 ? 0 : radius;
         })
	       .style("stroke", function(d) { return d.type; })
	       .style("fill", function(d) { return d.level; });

  nodeEnter.append("text")
	         .attr("x", function(d) {
		           return d.children || d._children ?
		          (d.value + 20) * -2 : d.value + 10 })
	         .attr("text-anchor", "middle")
           .attr("transform", "rotate(10 20, 20)")
           	.text(function(d) { return d.name; })
	         .style("fill-opacity", 1);

  nodeEnter.append("text")
           .attr("x", function(d) {
               return d.children || d._children ?
              (d.value + 20) * -2 : d.value + 10 })
           .attr("text-anchor", "middle")
           .attr("dy", "1em")
           .attr("transform", "rotate(10 20, 20)")
           .text(function(d) { return d.birthDate; })
           .style("fill-opacity", 1);


  // Declare the links…
  var link = svg.selectAll("path.link")
	              .data(links, function(d) { return d.target.id; });

  // Enter the links.
  link.enter().insert("path", "g")
	    .attr("class", "link")
  	  .style("stroke", function(d) { return d.target.level; })
	    .attr("d", diagonal);

}

// **************
// Reference.
// Adapted from:
// http://bl.ocks.org/d3noob/8324872
// *****************

</script>
</body>