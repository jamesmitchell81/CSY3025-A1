<!DOCTYPE html>
<meta charset="utf-8">
<head>
<style>

.chart rect {
  fill: #cacaca;
}

.chart text {
  fill: black;
  font: 10px sans-serif;
  text-anchor: end;
}


</style>
</head>
<body>

<svg class="chart"></svg>

<script src="http://d3js.org/d3.v3.min.js"></script>
<script>

// **************
// Reference.
// adapted from:
// http://bost.ocks.org/mike/bar/2/
//  *****************

d3.json("birthplaces.json", function(json) {

var ages = [];
var people = [];

// get all the ages
for ( var i = 0; i < json[0].length; i++ )
{
  var p = json[0][i];
  var person = {};

  if ( (!(p.age > 100)) && (p.age !== 0) ) {
    person.age = p.age;
    person.name = p.name;
    people.push(person);
  }
}

show(people);

})


function show(people) {

var width = 420,
    barHeight = 20;

var x = d3.scale.linear()
    .domain([0, d3.max(people, function(d) { return d.age; })])
    .range([0, width]);

var chart = d3.select(".chart")
    .attr("width", 1000)
    .attr("height", barHeight * people.length);

var bar = chart.selectAll("g")
    .data(people)
  .enter().append("g")
    .attr("transform", function(d, i) { return "translate(0," + i * barHeight + ")"; });

bar.append("rect")
    .attr("x", 350)
    .attr("width", function(d) { return x(d.age); })
    .attr("height", barHeight - 1);

bar.append("text")
    .attr("x", function(d) { return (x(d.age) + 20) + 350; })
    .attr("y", barHeight / 2)
    .attr("dy", ".35em")
    .text(function(d) { return d.age; });

bar.append("text")
    .attr("x", function(d) { return x(70); })
    .attr("y", barHeight / 2)
    .attr("dy", ".35em")
    .attr("text-anchor", "start")
    .text(function(d) { return d.name; });

}

// **************
// Reference.
// adapted from:
// http://bost.ocks.org/mike/bar/2/
//  *****************

</script>
</body>