
$(document).ready(function () {
    var phoneNumber=document.getElementById("phoneNumber").value;
    if (phoneNumber!=null&&phoneNumber!=""){
        var hideButton=document.getElementById("hide");
        var showButton=document.getElementById("show");
        hideButton.style.display="block";
        showButton.style.display="none";
    }
});


