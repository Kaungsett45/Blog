
function like(){
    var likeButton = document.getElementById("like");
    var currentColor = window.getComputedStyle(likeButton).backgroundColor;

    if (currentColor === "rgb(173, 216, 230)") {
        // If the background color is lightblue, change it back to the default.
        likeButton.style.backgroundColor = "";
    } else {
        // If the background color is not lightblue, set it to lightblue.
        likeButton.style.backgroundColor = "lightblue";
    }
}