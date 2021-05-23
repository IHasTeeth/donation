q = faunadb.query;
var client = new faunadb.Client({
  secret: "fnAEJ4zNqZACASEiy-9Cghn-WDLy2hB3EARhYm8i",
});

function auth() {
  var email = document.getElementById("email").value;
  var password = document.getElementById("password").value;
  client
    .query(
      q.CreateIndex({
        name: "pass_by_email",
        source: q.Collection("users"),
        terms: [{ field: ["data", "email"] }],
      })
    )
    .then((ret) => {});
  client.query(q.Get(q.Match(q.Index("pass_by_email"), email))).then((ret) => {
    if (ret.data.password === password)
      window.location.replace("/src/home.html");
  });
}
