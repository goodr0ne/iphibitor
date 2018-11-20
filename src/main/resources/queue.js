var data = ["some", "data", "inputted", "here", "for", "you", "x", "will", "be",
  "the", "16th", "symbol", "in", "this", "array", "x", "ha", "hah"];
const limit = 3;
var mapper = (a) => new Promise((resolve) => {
  console.out("mapper fired, a - " + a + ", resolve - " + resolve);
  setTimeout(resolve, Math.round(Math.random() * 900) + 100);
});

function queue(a, b, c) {
  var output = [];
  if (c > 1) {
    if (c > a.length) {
      for (var i = 0; i < a.length; i++) {
        output.push(b([a[i]]));
      }
      return output;
    } else {
      const len = a.length;
      let j = 0;
      while (j < len) {
        const size = Math.ceil((len - j) / c--);
        const part = a.slice(j, j += size);
        console.log("part used - " + part);
        output.push(b(part));
      }
      console.log("output - " + output);
      return output;
    }
  } else {
    output.push(b(a));
  }
}

Promise.race(queue(data, mapper, limit)).then(function(result) {
  console.log("results - " + result);
});
