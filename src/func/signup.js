q = faunadb.query;
var client = new faunadb.Client({
  secret: "fnAEJ4zNqZACASEiy-9Cghn-WDLy2hB3EARhYm8i",
});

function save() {
  var name = document.getElementById("name").value;
  var email = document.getElementById("email").value;
  var password = document.getElementById("password").value;
  var createP = client.query(
    q.Create(q.Collection("users"), {
      data: { name: name, password: password, email: email },
    })
  );

  createP.then(function (response) {
    console.log(response.ref);
    console.log("saved"); // Logs the ref to the console.
  });
  window.location.replace("/src/home.html");
}
