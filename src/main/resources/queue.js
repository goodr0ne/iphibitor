const data = ["some", "data", "inputted", "here", "for", "you", "x", "will", "be",
  "the", "16th", "symbol", "in", "this", "array", "x", "ha", "hah"];
const limit = 3;
var mapper = (a) => new Promise((resolve) => {
  console.out("mapper fired, a - " + a + ", resolve - " + resolve);
  setTimeout(resolve, Math.round(Math.random() * 900) + 100);
});
var results = [];

function queue(a, b, c) {
  if (c > 1) {
    if (c > a.length) {
      for (var i = 0; i < a.length; i++) {
        results.push(b([a[i]]));
      }
    } else {
      const len = a.length;
      let j = 0;
      while (j < len) {
        const size = Math.ceil((len - j) / c--);
        const part = a.slice(j, j += size);
        console.log("part used - " + part);
        results.push(b(part));
      }
    }
  } else {
    results.push(b(a));
  }
}

queue.then(function(value) {
  results.push(value);
});

queue(data, mapper, limit);