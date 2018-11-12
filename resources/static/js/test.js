
$(document).ready(function () {
    alert("......");
    var phoneNumber=document.getElementById("phoneNumber").value;
    alert("-----");
    if (phoneNumber!=null&&phoneNumber!=""){
        alert("this is a js test!");
        var hideButton=document.getElementById("hide");
        var showButton=document.getElementById("show");
        hideButton.style.display="block";
        showButton.style.display="none";
    }
});


